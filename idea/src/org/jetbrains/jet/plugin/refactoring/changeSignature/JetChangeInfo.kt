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

package org.jetbrains.jet.plugin.refactoring.changeSignature

import com.intellij.lang.Language
import com.intellij.openapi.util.text.StringUtil
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiModifier
import com.intellij.psi.PsiType
import com.intellij.refactoring.changeSignature.*
import com.intellij.usageView.UsageInfo
import com.intellij.util.Function
import com.intellij.util.VisibilityUtil
import com.intellij.util.containers.ContainerUtil
import kotlin.Function1
import kotlin.Pair
import org.jetbrains.jet.asJava.*
import org.jetbrains.jet.lang.descriptors.FunctionDescriptor
import org.jetbrains.jet.lang.descriptors.ValueParameterDescriptor
import org.jetbrains.jet.lang.descriptors.Visibilities
import org.jetbrains.jet.lang.descriptors.Visibility
import org.jetbrains.jet.lang.psi.JetFunction
import org.jetbrains.jet.lang.psi.JetFunctionLiteral
import org.jetbrains.jet.lang.resolve.DescriptorToSourceUtils
import org.jetbrains.jet.lang.types.JetType
import org.jetbrains.jet.lang.types.TypeSubstitutor
import org.jetbrains.jet.lang.types.lang.KotlinBuiltIns
import org.jetbrains.jet.lexer.JetTokens
import org.jetbrains.jet.plugin.JetLanguage
import org.jetbrains.jet.plugin.caches.resolve.*
import org.jetbrains.jet.plugin.refactoring.changeSignature.usages.JetFunctionDefinitionUsage
import org.jetbrains.jet.plugin.util.IdeDescriptorRenderers
import java.util.HashMap
import kotlin.properties.Delegates

public class JetChangeInfo(
        val methodDescriptor: JetMethodDescriptor,
        private var name: String,
        val newReturnType: JetType?,
        var newReturnTypeText: String,
        var newVisibility: Visibility,
        parameterInfos: List<JetParameterInfo>,
        val context: PsiElement
): ChangeInfo {
    private val newParameters = parameterInfos.toArrayList()
    private val originalPsiMethod: PsiMethod? = getCurrentPsiMethod()

    private val oldNameToParameterIndex: Map<String, Int> by Delegates.lazy {
        val map = HashMap<String, Int>()

        val parameters = methodDescriptor.baseDescriptor.getValueParameters()
        parameters.indices.forEach { i -> map[parameters.get(i).getName().asString()] = i }

        map
    }

    public val isParameterSetOrOrderChanged: Boolean by Delegates.lazy {
        newParameters.size() != methodDescriptor.getParametersCount() ||
        newParameters.indices.any { i -> newParameters.get(i).getOldIndex() != i }
    }

    private var isPrimaryMethodUpdated: Boolean = false
    private var javaChangeInfo: JavaChangeInfo? = null

    private fun getCurrentPsiMethod(): PsiMethod? {
        val psiMethods = getMethod().toLightMethods()
        assert(psiMethods.size() <= 1) { "Multiple light methods: " + getMethod().getText() }
        return psiMethods.firstOrNull()
    }

    public fun getOldParameterIndex(oldParameterName: String): Int? = oldNameToParameterIndex[oldParameterName]

    override fun isParameterTypesChanged(): Boolean = true

    override fun isParameterNamesChanged(): Boolean = true

    override fun isParameterSetOrOrderChanged(): Boolean = isParameterSetOrOrderChanged

    public fun getNewParametersCount(): Int = newParameters.size()

    override fun getNewParameters(): Array<JetParameterInfo> = newParameters.copyToArray()

    public fun setNewParameter(index: Int, parameterInfo: JetParameterInfo) {
        newParameters.set(index, parameterInfo)
    }

    public fun addParameter(parameterInfo: JetParameterInfo) {
        newParameters.add(parameterInfo)
    }

    public fun removeParameter(index: Int) {
        newParameters.remove(index);
    }

    override fun isGenerateDelegate(): Boolean = false

    override fun getNewName(): String = name

    fun setNewName(value: String) {
        name = value
    }

    override fun isNameChanged(): Boolean = name != methodDescriptor.getName()

    public fun isVisibilityChanged(): Boolean = newVisibility != methodDescriptor.getVisibility()

    override fun getMethod(): PsiElement {
        return methodDescriptor.getMethod()
    }

    override fun isReturnTypeChanged(): Boolean = newReturnTypeText != methodDescriptor.renderOriginalReturnType()

    override fun getLanguage(): Language = JetLanguage.INSTANCE

    public fun getNewSignature(inheritedFunction: JetFunctionDefinitionUsage<PsiElement>): String {
        val buffer = StringBuilder()

        if (isConstructor) {
            buffer.append(name)

            if (newVisibility != Visibilities.PUBLIC) {
                buffer.append(' ').append(newVisibility).append(' ')
            }
        }
        else {
            if (newVisibility != Visibilities.INTERNAL) {
                buffer.append(newVisibility).append(' ')
            }

            buffer.append(JetTokens.FUN_KEYWORD).append(' ').append(name)
        }

        buffer.append(getNewParametersSignature(inheritedFunction))

        if (newReturnType != null && !KotlinBuiltIns.isUnit(newReturnType) && !isConstructor)
            buffer.append(": ").append(newReturnTypeText)

        return buffer.toString()
    }

    public fun isRefactoringTarget(inheritedFunctionDescriptor: FunctionDescriptor?): Boolean {
        return inheritedFunctionDescriptor != null
               && getMethod() == DescriptorToSourceUtils.descriptorToDeclaration(inheritedFunctionDescriptor)
    }

    public fun getNewParametersSignature(inheritedFunction: JetFunctionDefinitionUsage<PsiElement>): String {
        val isLambda = inheritedFunction.getDeclaration() is JetFunctionLiteral
        if (isLambda && newParameters.size() == 1 && !newParameters.get(0).requiresExplicitType(inheritedFunction)) {
            return newParameters.get(0).getDeclarationSignature(0, inheritedFunction)
        }

        return newParameters.indices
                .map { i -> newParameters[i].getDeclarationSignature(i, inheritedFunction) }
                .joinToString(prefix = "(", separator = ", ", postfix = ")")
    }

    public fun renderReturnType(inheritedFunction: JetFunctionDefinitionUsage<PsiElement>): String {
        val typeSubstitutor = inheritedFunction.getOrCreateTypeSubstitutor() ?: return newReturnTypeText
        val currentBaseFunction = inheritedFunction.getBaseFunction().getCurrentFunctionDescriptor() ?: return newReturnTypeText
        return currentBaseFunction.getReturnType().renderTypeWithSubstitution(typeSubstitutor, newReturnTypeText, false)
    }

    public fun primaryMethodUpdated() {
        isPrimaryMethodUpdated = true
        javaChangeInfo = null
    }

    public fun getOrCreateJavaChangeInfo(): JavaChangeInfo? {
        if (javaChangeInfo == null) {
            val currentPsiMethod = getCurrentPsiMethod()
            if (originalPsiMethod == null || currentPsiMethod == null) return null

            /*
             * When primaryMethodUpdated is false, changes to the primary Kotlin declaration are already confirmed, but not yet applied.
             * It means that originalPsiMethod has already expired, but new one can't be created until Kotlin declaration is updated
             * (signified by primaryMethodUpdated being true). It means we can't know actual PsiType, visibility, etc.
             * to use in JavaChangeInfo. However they are not actually used at this point since only parameter count and order matters here
             * So we resort to this hack and pass around "default" type (void) and visibility (package-local)
             */
            val javaVisibility = if (isPrimaryMethodUpdated)
                VisibilityUtil.getVisibilityModifier(currentPsiMethod.getModifierList())
            else
                PsiModifier.PACKAGE_LOCAL

            val newJavaParameters = newParameters.withIndices().map { pair ->
                val (i, info) = pair
                val type = if (isPrimaryMethodUpdated)
                    currentPsiMethod.getParameterList().getParameters()[i].getType()
                else
                    PsiType.VOID
                ParameterInfoImpl(info.getOldIndex(), info.getName(), type, info.defaultValueForCall)
            }.copyToArray()

            val returnType = if (isPrimaryMethodUpdated) currentPsiMethod.getReturnType() else PsiType.VOID

            javaChangeInfo = ChangeSignatureProcessor(getMethod().getProject(),
                                                      originalPsiMethod,
                                                      false,
                                                      javaVisibility,
                                                      getNewName(),
                                                      returnType,
                                                      newJavaParameters).getChangeInfo()
            javaChangeInfo!!.updateMethod(currentPsiMethod)
        }

        return javaChangeInfo
    }
}

public val JetChangeInfo.originalBaseFunctionDescriptor: FunctionDescriptor
    get() = methodDescriptor.baseDescriptor

public val JetChangeInfo.isConstructor: Boolean get() = methodDescriptor.isConstructor

public val JetChangeInfo.oldName: String?
    get() = (methodDescriptor.getMethod() as? JetFunction)?.getName()

public val JetChangeInfo.affectedFunctions: Collection<UsageInfo> get() = methodDescriptor.affectedFunctions

public fun ChangeInfo.toJetChangeInfo(originalChangeSignatureDescriptor: JetMethodDescriptor): JetChangeInfo {
    val method = getMethod() as PsiMethod

    val functionDescriptor = method.getJavaMethodDescriptor()
    val parameterDescriptors = functionDescriptor.getValueParameters()

    //noinspection ConstantConditions
    val originalParameterDescriptors = originalChangeSignatureDescriptor.baseDescriptor.getValueParameters()

    val newParameters = getNewParameters().withIndices().map { pair ->
        val (i, info) = pair
        val oldIndex = info.getOldIndex()
        val currentType = parameterDescriptors[i].getType()

        with(JetParameterInfo(originalIndex = oldIndex,
                              name = info.getName(),
                              type = if (oldIndex >= 0) originalParameterDescriptors[oldIndex].getType() else currentType,
                              defaultValueForCall = info.getDefaultValue() ?: "")) {
            currentTypeText = IdeDescriptorRenderers.SOURCE_CODE.renderType(currentType)
            this
        }
    }

    val returnType = functionDescriptor.getReturnType()
    val returnTypeText = if (returnType != null) IdeDescriptorRenderers.SOURCE_CODE.renderType(returnType) else ""

    return JetChangeInfo(originalChangeSignatureDescriptor,
                          getNewName(),
                          returnType,
                          returnTypeText,
                          functionDescriptor.getVisibility(),
                          newParameters,
                          method)
}