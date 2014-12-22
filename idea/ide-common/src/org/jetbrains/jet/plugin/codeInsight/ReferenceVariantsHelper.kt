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

package org.jetbrains.jet.plugin.codeInsight

import org.jetbrains.jet.lang.descriptors.*
import org.jetbrains.jet.lang.psi.*
import org.jetbrains.jet.lang.psi.psiUtil.*
import org.jetbrains.jet.lang.resolve.BindingContext
import org.jetbrains.jet.lang.resolve.calls.smartcasts.SmartCastUtils
import org.jetbrains.jet.lang.resolve.calls.smartcasts.DataFlowInfo
import org.jetbrains.jet.lang.resolve.scopes.JetScope
import org.jetbrains.jet.lang.resolve.scopes.receivers.ExpressionReceiver

import java.util.*
import org.jetbrains.jet.lang.resolve.bindingContextUtil.getDataFlowInfo
import org.jetbrains.jet.lang.resolve.name.Name
import org.jetbrains.jet.lang.resolve.scopes.receivers.ReceiverValue
import org.jetbrains.jet.lang.resolve.scopes.getDescriptorsFiltered
import org.jetbrains.jet.lang.resolve.scopes.DescriptorKindFilter
import org.jetbrains.jet.lang.resolve.scopes.DescriptorKindExclude
import org.jetbrains.jet.lang.types.JetType
import org.jetbrains.jet.lang.types.TypeUtils
import org.jetbrains.jet.lang.types.checker.JetTypeChecker
import org.jetbrains.jet.lexer.JetTokens
import org.jetbrains.jet.plugin.util.CallType
import org.jetbrains.jet.plugin.util.substituteExtensionIfCallable

public class ReferenceVariantsHelper(
        private val context: BindingContext,
        private val visibilityFilter: (DeclarationDescriptor) -> Boolean
) {

    public data class ReceiversData(
            public val receivers: Collection<ReceiverValue>,
            public val callType: CallType
    ) {
        class object {
            val Empty = ReceiversData(listOf(), CallType.NORMAL)
        }
    }

    public fun getReferenceVariants(
            expression: JetSimpleNameExpression,
            kindFilter: DescriptorKindFilter,
            shouldCastToRuntimeType: Boolean,
            nameFilter: (Name) -> Boolean
    ): Collection<DeclarationDescriptor> {
        return getReferenceVariantsNoVisibilityFilter(expression, kindFilter, shouldCastToRuntimeType, nameFilter).filter(visibilityFilter)
    }

    private fun getReferenceVariantsNoVisibilityFilter(
            expression: JetSimpleNameExpression,
            kindFilter: DescriptorKindFilter,
            shouldCastToRuntimeType: Boolean,
            nameFilter: (Name) -> Boolean
    ): Collection<DeclarationDescriptor> {
        val parent = expression.getParent()
        val resolutionScope = context[BindingContext.RESOLUTION_SCOPE, expression] ?: return listOf()

        if (parent is JetImportDirective || parent is JetPackageDirective) {
            val restrictedFilter = kindFilter.restrictedToKinds(DescriptorKindFilter.PACKAGES_MASK) ?: return listOf()
            return resolutionScope.getDescriptorsFiltered(restrictedFilter, nameFilter)
        }

        if (parent is JetUserType) {
            val restrictedFilter = kindFilter.restrictedToKinds(DescriptorKindFilter.CLASSIFIERS_MASK or DescriptorKindFilter.PACKAGES_MASK) ?: return listOf()
            return resolutionScope.getDescriptorsFiltered(restrictedFilter, nameFilter)
        }

        val pair = getExplicitReceiverData(expression)
        if (pair != null) {
            val (receiverExpression, callType) = pair

            // Process as call expression
            val descriptors = HashSet<DeclarationDescriptor>()

            val qualifier = context[BindingContext.QUALIFIER, receiverExpression]
            if (qualifier != null) {
                // It's impossible to add extension function for package or class (if it's class object, expression type is not null)
                qualifier.scope.getDescriptorsFiltered(kindFilter exclude DescriptorKindExclude.Extensions, nameFilter).filterTo(descriptors)  { callType.canCall(it) }
            }

            val expressionType = if (shouldCastToRuntimeType)
                                        getQualifierRuntimeType(receiverExpression)
                                    else
                                        context[BindingContext.EXPRESSION_TYPE, receiverExpression]
            if (expressionType != null && !expressionType.isError()) {
                val receiverValue = ExpressionReceiver(receiverExpression, expressionType)
                val dataFlowInfo = context.getDataFlowInfo(expression)

                descriptors.addMembers(receiverValue, callType, dataFlowInfo, kindFilter, nameFilter)

                descriptors.addCallableExtensions(resolutionScope, receiverValue, dataFlowInfo, callType, kindFilter, nameFilter)
            }

            return descriptors
        }
        else {
            val descriptorsSet = HashSet<DeclarationDescriptor>()

            val receivers = resolutionScope.getImplicitReceiversHierarchy()
            receivers.flatMapTo(descriptorsSet) {
                it.getType().getMemberScope().getDescriptorsFiltered(kindFilter exclude DescriptorKindExclude.Extensions, nameFilter)
            }

            val dataFlowInfo = context.getDataFlowInfo(expression)
            val receiverValues = receivers.map { it.getValue() }

            for (descriptor in resolutionScope.getDescriptorsFiltered(kindFilter, nameFilter)) {
                if (descriptor is CallableDescriptor && descriptor.getExtensionReceiverParameter() != null) {
                    descriptorsSet.addAll(descriptor.substituteExtensionIfCallable(receiverValues, context, dataFlowInfo, CallType.NORMAL))
                }
                else {
                    descriptorsSet.add(descriptor)
                }
            }

            return descriptorsSet
        }
    }

    private fun MutableCollection<DeclarationDescriptor>.addMembers(
            receiverValue: ExpressionReceiver,
            callType: CallType,
            dataFlowInfo: DataFlowInfo,
            kindFilter: DescriptorKindFilter,
            nameFilter: (Name) -> Boolean) {
        var memberFilter = kindFilter exclude DescriptorKindExclude.Extensions
        for (variant in SmartCastUtils.getSmartCastVariantsWithLessSpecificExcluded(receiverValue, context, dataFlowInfo)) {
            val members = variant.getMemberScope().getDescriptorsFiltered(DescriptorKindFilter.ALL, nameFilter) // filter by kind later because of constructors
            for (member in members) {
                if (member is ClassDescriptor) {
                    if (member.isInner()) {
                        member.getConstructors().filterTo(this) { callType.canCall(it) && memberFilter.accepts(it) }
                    }
                }
                else if (callType.canCall(member) && memberFilter.accepts(member)) {
                    this.add(member)
                }
            }
        }
    }

    public fun getReferenceVariantsReceivers(expression: JetSimpleNameExpression): ReceiversData {
        val receiverData = getExplicitReceiverData(expression)
        if (receiverData != null) {
            val receiverExpression = receiverData.first
            val expressionType = context[BindingContext.EXPRESSION_TYPE, receiverExpression] ?: return ReceiversData.Empty
            return ReceiversData(listOf(ExpressionReceiver(receiverExpression, expressionType)), receiverData.second)
        }
        else {
            val resolutionScope = context[BindingContext.RESOLUTION_SCOPE, expression] ?: return ReceiversData.Empty
            return ReceiversData(resolutionScope.getImplicitReceiversHierarchy().map { it.getValue() }, CallType.NORMAL)
        }
    }

    private fun getQualifierRuntimeType(receiver: JetExpression): JetType? {
        val type = context[BindingContext.EXPRESSION_TYPE, receiver]
        if (type != null && TypeUtils.canHaveSubtypes(JetTypeChecker.DEFAULT, type)) {
            val evaluator = receiver.getContainingFile().getCopyableUserData(JetCodeFragment.RUNTIME_TYPE_EVALUATOR)
            return evaluator?.invoke(receiver)
        }
        return type
    }

    private fun MutableCollection<DeclarationDescriptor>.addCallableExtensions(
            resolutionScope: JetScope,
            receiver: ReceiverValue,
            dataFlowInfo: DataFlowInfo,
            callType: CallType,
            kindFilter: DescriptorKindFilter,
            nameFilter: (Name) -> Boolean
    ) {
        if (!kindFilter.excludes.contains(DescriptorKindExclude.Extensions)) {
            for (callable in resolutionScope.getDescriptorsFiltered(kindFilter.exclude(DescriptorKindExclude.NonExtensions), nameFilter)) {
                addAll((callable as CallableDescriptor).substituteExtensionIfCallable(receiver, callType, context, dataFlowInfo))
            }
        }
    }

    public fun getPackageReferenceVariants(
            expression: JetSimpleNameExpression,
            nameFilter: (Name) -> Boolean
    ): Collection<DeclarationDescriptor> {
        val resolutionScope = context[BindingContext.RESOLUTION_SCOPE, expression] ?: return listOf()
        return resolutionScope.getDescriptorsFiltered(DescriptorKindFilter.PACKAGES, nameFilter).filter(visibilityFilter)
    }

    class object {
        public fun getExplicitReceiverData(expression: JetSimpleNameExpression): Pair<JetExpression, CallType>? {
            val receiverExpression = expression.getReceiverExpression() ?: return null
            val parent = expression.getParent()
            val callType = when (parent) {
                is JetBinaryExpression -> CallType.INFIX

                is JetCallExpression -> {
                    if ((parent.getParent() as JetQualifiedExpression).getOperationSign() == JetTokens.SAFE_ACCESS)
                        CallType.SAFE
                    else
                        CallType.NORMAL
                }

                is JetQualifiedExpression -> {
                    if (parent.getOperationSign() == JetTokens.SAFE_ACCESS)
                        CallType.SAFE
                    else
                        CallType.NORMAL
                }

                is JetUnaryExpression -> CallType.UNARY

                else -> return null
            }
            return receiverExpression to callType
        }
    }
}
