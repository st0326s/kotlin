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

package org.jetbrains.jet.lang.resolve.java.structure.reflect

import java.lang.reflect.Modifier
import org.jetbrains.jet.lang.descriptors.Visibility
import org.jetbrains.jet.lang.descriptors.Visibilities
import org.jetbrains.jet.lang.resolve.java.JavaVisibilities
import org.jetbrains.jet.lang.resolve.name.ClassId
import org.jetbrains.jet.lang.resolve.name.Name
import org.jetbrains.jet.lang.resolve.name.FqName

private fun calculateVisibility(modifiers: Int): Visibility {
    if (Modifier.isPublic(modifiers)) return Visibilities.PUBLIC
    if (Modifier.isPrivate(modifiers)) return Visibilities.PRIVATE
    if (Modifier.isProtected(modifiers)) {
        return if (Modifier.isStatic(modifiers)) JavaVisibilities.PROTECTED_STATIC_VISIBILITY else JavaVisibilities.PROTECTED_AND_PACKAGE
    }
    return JavaVisibilities.PACKAGE_VISIBILITY
}

val Class<*>.classId: ClassId
    get() = getDeclaringClass()?.classId?.createNestedClassId(Name.identifier(getSimpleName())) ?: ClassId.topLevel(FqName(getName()))
