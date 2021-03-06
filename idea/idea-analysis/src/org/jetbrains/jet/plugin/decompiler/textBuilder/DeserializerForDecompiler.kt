/*
 * Copyright 2010-2014 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.jet.plugin.decompiler.textBuilder

import org.jetbrains.jet.lang.resolve.name.ClassId
import org.jetbrains.jet.descriptors.serialization.JavaProtoBufUtil
import org.jetbrains.jet.lang.descriptors.*
import org.jetbrains.jet.lang.descriptors.impl.MutablePackageFragmentDescriptor
import org.jetbrains.jet.lang.resolve.kotlin.*
import org.jetbrains.jet.lang.resolve.name.FqName
import org.jetbrains.jet.lang.resolve.name.Name
import org.jetbrains.jet.storage.LockBasedStorageManager
import java.util.Collections
import com.intellij.openapi.vfs.VirtualFile
import org.jetbrains.jet.descriptors.serialization.descriptors.DeserializedPackageMemberScope
import org.jetbrains.jet.lang.resolve.java.resolver.ErrorReporter
import org.jetbrains.jet.lang.resolve.java.PackageClassUtils
import com.intellij.openapi.diagnostic.Logger
import org.jetbrains.jet.descriptors.serialization.context.DeserializationComponents
import org.jetbrains.jet.lang.PlatformToKotlinClassMap
import org.jetbrains.jet.lang.types.lang.KotlinBuiltIns
import org.jetbrains.jet.lang.descriptors.impl.ModuleDescriptorImpl

public fun DeserializerForDecompiler(classFile: VirtualFile): DeserializerForDecompiler {
    val kotlinClass = KotlinBinaryClassCache.getKotlinBinaryClass(classFile)
    assert(kotlinClass != null) { "Decompiled data factory shouldn't be called on an unsupported file: " + classFile }
    val packageFqName = kotlinClass!!.getClassId().getPackageFqName()
    return DeserializerForDecompiler(classFile.getParent()!!, packageFqName)
}

public class DeserializerForDecompiler(val packageDirectory: VirtualFile, val directoryPackageFqName: FqName) : ResolverForDecompiler {

    private val moduleDescriptor =
            ModuleDescriptorImpl(Name.special("<module for building decompiled sources>"), listOf(), PlatformToKotlinClassMap.EMPTY)

    private fun createDummyModule(name: String) = ModuleDescriptorImpl(Name.special("<$name>"), listOf(), PlatformToKotlinClassMap.EMPTY)

    override fun resolveTopLevelClass(classId: ClassId) = deserializationComponents.deserializeClass(classId)

    override fun resolveDeclarationsInPackage(packageFqName: FqName): Collection<DeclarationDescriptor> {
        assert(packageFqName == directoryPackageFqName, "Was called for $packageFqName but only $directoryPackageFqName is expected.")
        val binaryClassForPackageClass = localClassFinder.findKotlinClass(PackageClassUtils.getPackageClassId(packageFqName))
        val annotationData = binaryClassForPackageClass?.getClassHeader()?.annotationData
        if (annotationData == null) {
            LOG.error("Could not read annotation data for $packageFqName from ${binaryClassForPackageClass?.getClassId()}")
            return Collections.emptyList()
        }
        val packageData = JavaProtoBufUtil.readPackageDataFrom(annotationData)
        val membersScope = DeserializedPackageMemberScope(
                createDummyPackageFragment(packageFqName),
                packageData.getPackageProto(),
                packageData.getNameResolver(),
                deserializationComponents
        ) { listOf() }
        return membersScope.getDescriptors()
    }

    private val localClassFinder = LocalClassFinder(packageDirectory, directoryPackageFqName)
    private val localClassDataFinder = LocalClassDataFinder(localClassFinder, LOG)

    private val storageManager = LockBasedStorageManager.NO_LOCKS

    private val annotationAndConstantLoader =
            BinaryClassAnnotationAndConstantLoaderImpl(moduleDescriptor, storageManager, localClassFinder, LoggingErrorReporter(LOG))

    private val packageFragmentProvider = object : PackageFragmentProvider {
        override fun getPackageFragments(fqName: FqName): List<PackageFragmentDescriptor> {
            return listOf(createDummyPackageFragment(fqName))
        }

        override fun getSubPackagesOf(fqName: FqName, nameFilter: (Name) -> Boolean): Collection<FqName> {
            throw UnsupportedOperationException("This method is not supposed to be called.")
        }
    }

    {
        moduleDescriptor.initialize(packageFragmentProvider)
        moduleDescriptor.addDependencyOnModule(moduleDescriptor)
        moduleDescriptor.addDependencyOnModule(KotlinBuiltIns.getInstance().getBuiltInsModule())
        val moduleContainingMissingDependencies = createDummyModule("module containing missing dependencies for decompiled sources")
        moduleContainingMissingDependencies.addDependencyOnModule(moduleContainingMissingDependencies)
        moduleContainingMissingDependencies.initialize(
                PackageFragmentProviderForMissingDependencies(moduleContainingMissingDependencies)
        )
        moduleDescriptor.addDependencyOnModule(moduleContainingMissingDependencies)
        moduleDescriptor.seal()
        moduleContainingMissingDependencies.seal()
    }

    private val deserializationComponents = DeserializationComponents(
            storageManager, moduleDescriptor, localClassDataFinder, annotationAndConstantLoader, packageFragmentProvider,
            JavaFlexibleTypeCapabilitiesDeserializer
    )

    private fun createDummyPackageFragment(fqName: FqName): MutablePackageFragmentDescriptor {
        return MutablePackageFragmentDescriptor(moduleDescriptor, fqName)
    }

    class object {
        private val LOG = Logger.getInstance(javaClass<DeserializerForDecompiler>())
    }
}
