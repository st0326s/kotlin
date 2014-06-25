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

package org.jetbrains.k2js.translate.expression.foreach;

import com.google.common.collect.Lists;
import com.google.dart.compiler.backend.js.ast.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.jet.lang.psi.JetExpression;
import org.jetbrains.jet.lang.psi.JetForExpression;
import org.jetbrains.jet.lang.types.JetType;
import org.jetbrains.k2js.translate.context.TemporaryVariable;
import org.jetbrains.k2js.translate.context.TranslationContext;
import org.jetbrains.k2js.translate.general.Translation;
import org.jetbrains.k2js.translate.utils.BindingUtils;

import java.util.List;

import static org.jetbrains.jet.lang.resolve.DescriptorUtils.getClassDescriptorForType;
import static org.jetbrains.k2js.translate.utils.JsAstUtils.*;
import static org.jetbrains.k2js.translate.utils.PsiUtils.getLoopRange;
import static org.jetbrains.k2js.translate.utils.TemporariesUtils.temporariesInitialization;

public final class RangeForTranslator extends ForTranslator {

    @NotNull
    public static JsStatement doTranslate(@NotNull JetForExpression expression,
                                          @NotNull TranslationContext context) {
        return (new RangeForTranslator(expression, context).translate());
    }

    public static boolean isApplicable(@NotNull JetForExpression expression,
                                       @NotNull TranslationContext context) {
        JetExpression loopRange = getLoopRange(expression);
        JetType rangeType = BindingUtils.getTypeForExpression(context.bindingContext(), loopRange);
        //TODO: better check
        //TODO: long range?
        return getClassDescriptorForType(rangeType).getName().asString().equals("IntRange");
    }

    @NotNull
    private final TemporaryVariable rangeExpression;
    @NotNull
    private final TemporaryVariable start;
    @NotNull
    private final TemporaryVariable end;
    @NotNull
    private final TemporaryVariable increment;

    private RangeForTranslator(@NotNull JetForExpression forExpression, @NotNull TranslationContext context) {
        super(forExpression, context);
        rangeExpression = context.declareTemporary(Translation.translateAsExpression(getLoopRange(expression), context));
        start = context().declareTemporary(getProperty("start"));
        end = context().declareTemporary(getProperty("end"));
        increment = context().declareTemporary(getProperty("increment"));
    }

    @NotNull
    private JsBlock translate() {
        List<JsStatement> blockStatements = Lists.newArrayList();
        blockStatements.add(temporariesInitialization(rangeExpression, start, end, increment).makeStmt());
        blockStatements.add(generateForExpression());
        return new JsBlock(blockStatements);
    }

    @NotNull
    private JsFor generateForExpression() {
        JsFor result = new JsFor(initExpression(), getCondition(), getIncrExpression());
        result.setBody(translateBody(null));
        return result;
    }

    @NotNull
    private JsVars initExpression() {
        return newVar(parameterName, start.reference());
    }

    @NotNull
    private JsExpression getCondition() {
        return lessThanEq(parameterName.makeRef(), end.reference());
    }

    @NotNull
    private JsExpression getIncrExpression() {
        return addAssign(parameterName.makeRef(), increment.reference());
    }

    @NotNull
    private JsExpression getProperty(@NotNull String funName) {
        return new JsNameRef(funName, rangeExpression.reference());
    }
}