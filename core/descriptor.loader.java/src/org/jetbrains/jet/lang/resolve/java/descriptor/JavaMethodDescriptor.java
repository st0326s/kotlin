/*
 * Copyright 2010-2013 JetBrains s.r.o.
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

package org.jetbrains.jet.lang.resolve.java.descriptor;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.jet.lang.descriptors.DeclarationDescriptor;
import org.jetbrains.jet.lang.descriptors.FunctionDescriptor;
import org.jetbrains.jet.lang.descriptors.ComplexFunctionDescriptor;
import org.jetbrains.jet.lang.descriptors.SourceElement;
import org.jetbrains.jet.lang.descriptors.annotations.Annotations;
import org.jetbrains.jet.lang.descriptors.impl.FunctionDescriptorImpl;
import org.jetbrains.jet.lang.descriptors.impl.SimpleFunctionDescriptorImpl;
import org.jetbrains.jet.lang.resolve.name.Name;

public class JavaMethodDescriptor extends SimpleFunctionDescriptorImpl implements JavaCallableMemberDescriptor {
    private Boolean hasStableParameterNames = null;
    private Boolean hasSynthesizedParameterNames = null;

    protected JavaMethodDescriptor(
            @NotNull DeclarationDescriptor containingDeclaration,
            @Nullable ComplexFunctionDescriptor original,
            @NotNull Annotations annotations,
            @NotNull Name name,
            @NotNull Kind kind,
            @NotNull SourceElement source
    ) {
        super(containingDeclaration, original, annotations, name, kind, source);
    }

    @NotNull
    public static JavaMethodDescriptor createJavaMethod(
            @NotNull DeclarationDescriptor containingDeclaration,
            @NotNull Annotations annotations,
            @NotNull Name name,
            @NotNull SourceElement source
    ) {
        return new JavaMethodDescriptor(containingDeclaration, null, annotations, name, Kind.DECLARATION, source);
    }

    @Override
    public boolean hasStableParameterNames() {
        assert hasStableParameterNames != null : "hasStableParameterNames was not set: " + this;
        return hasStableParameterNames;
    }

    public void setHasStableParameterNames(boolean hasStableParameterNames) {
        this.hasStableParameterNames = hasStableParameterNames;
    }

    @Override
    public boolean hasSynthesizedParameterNames() {
        assert hasSynthesizedParameterNames != null : "hasSynthesizedParameterNames was not set: " + this;
        return hasSynthesizedParameterNames;
    }

    public void setHasSynthesizedParameterNames(boolean hasSynthesizedParameterNames) {
        this.hasSynthesizedParameterNames = hasSynthesizedParameterNames;
    }

    @NotNull
    @Override
    protected FunctionDescriptorImpl createSubstitutedCopy(
            @NotNull DeclarationDescriptor newOwner,
            @Nullable FunctionDescriptor original,
            @NotNull Kind kind
    ) {
        JavaMethodDescriptor result = new JavaMethodDescriptor(
                newOwner,
                (ComplexFunctionDescriptor) original,
                getAnnotations(),
                getName(),
                kind,
                SourceElement.NO_SOURCE
        );
        result.setHasStableParameterNames(hasStableParameterNames());
        result.setHasSynthesizedParameterNames(hasSynthesizedParameterNames());
        return result;
    }
}
