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

package org.jetbrains.jet.evaluate;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestSuite;

import java.io.File;
import java.util.regex.Pattern;
import org.jetbrains.jet.JetTestUtils;
import org.jetbrains.jet.test.InnerTestClasses;
import org.jetbrains.jet.test.TestMetadata;

import org.jetbrains.jet.evaluate.AbstractEvaluateExpressionTest;

/** This class is generated by {@link org.jetbrains.jet.generators.tests.TestsPackage}. DO NOT MODIFY MANUALLY */
@SuppressWarnings("all")
@InnerTestClasses({EvaluateExpressionTestGenerated.Constant.class, EvaluateExpressionTestGenerated.IsPure.class, EvaluateExpressionTestGenerated.UsesVariableAsConstant.class})
public class EvaluateExpressionTestGenerated extends AbstractEvaluateExpressionTest {
    @TestMetadata("compiler/testData/evaluate/constant")
    public static class Constant extends AbstractEvaluateExpressionTest {
        public void testAllFilesPresentInConstant() throws Exception {
            JetTestUtils.assertAllTestsPresentByMetadata(this.getClass(), "org.jetbrains.jet.generators.tests.TestsPackage", new File("compiler/testData/evaluate/constant"), Pattern.compile("^(.+)\\.kt$"), true);
        }
        
        @TestMetadata("classObjectProperty.kt")
        public void testClassObjectProperty() throws Exception {
            doConstantTest("compiler/testData/evaluate/constant/classObjectProperty.kt");
        }
        
        @TestMetadata("compareTo.kt")
        public void testCompareTo() throws Exception {
            doConstantTest("compiler/testData/evaluate/constant/compareTo.kt");
        }
        
        @TestMetadata("differentTypes.kt")
        public void testDifferentTypes() throws Exception {
            doConstantTest("compiler/testData/evaluate/constant/differentTypes.kt");
        }
        
        @TestMetadata("divideByZero.kt")
        public void testDivideByZero() throws Exception {
            doConstantTest("compiler/testData/evaluate/constant/divideByZero.kt");
        }
        
        @TestMetadata("equals.kt")
        public void testEquals() throws Exception {
            doConstantTest("compiler/testData/evaluate/constant/equals.kt");
        }
        
        @TestMetadata("exceptionWhenEvaluate.kt")
        public void testExceptionWhenEvaluate() throws Exception {
            doConstantTest("compiler/testData/evaluate/constant/exceptionWhenEvaluate.kt");
        }
        
        @TestMetadata("finalProperty.kt")
        public void testFinalProperty() throws Exception {
            doConstantTest("compiler/testData/evaluate/constant/finalProperty.kt");
        }
        
        @TestMetadata("float.kt")
        public void testFloat() throws Exception {
            doConstantTest("compiler/testData/evaluate/constant/float.kt");
        }
        
        @TestMetadata("floatsAndDoubles.kt")
        public void testFloatsAndDoubles() throws Exception {
            doConstantTest("compiler/testData/evaluate/constant/floatsAndDoubles.kt");
        }
        
        @TestMetadata("integer.kt")
        public void testInteger() throws Exception {
            doConstantTest("compiler/testData/evaluate/constant/integer.kt");
        }
        
        @TestMetadata("integers.kt")
        public void testIntegers() throws Exception {
            doConstantTest("compiler/testData/evaluate/constant/integers.kt");
        }
        
        @TestMetadata("localVal.kt")
        public void testLocalVal() throws Exception {
            doConstantTest("compiler/testData/evaluate/constant/localVal.kt");
        }
        
        @TestMetadata("localVar.kt")
        public void testLocalVar() throws Exception {
            doConstantTest("compiler/testData/evaluate/constant/localVar.kt");
        }
        
        @TestMetadata("nonFinalProperty.kt")
        public void testNonFinalProperty() throws Exception {
            doConstantTest("compiler/testData/evaluate/constant/nonFinalProperty.kt");
        }
        
        @TestMetadata("strings.kt")
        public void testStrings() throws Exception {
            doConstantTest("compiler/testData/evaluate/constant/strings.kt");
        }
        
        @TestMetadata("topLevelVal.kt")
        public void testTopLevelVal() throws Exception {
            doConstantTest("compiler/testData/evaluate/constant/topLevelVal.kt");
        }
        
        @TestMetadata("topLevelVar.kt")
        public void testTopLevelVar() throws Exception {
            doConstantTest("compiler/testData/evaluate/constant/topLevelVar.kt");
        }
        
        @TestMetadata("unaryMinusIndepWoExpType.kt")
        public void testUnaryMinusIndepWoExpType() throws Exception {
            doConstantTest("compiler/testData/evaluate/constant/unaryMinusIndepWoExpType.kt");
        }
        
        @TestMetadata("unaryMinusIndependentExpType.kt")
        public void testUnaryMinusIndependentExpType() throws Exception {
            doConstantTest("compiler/testData/evaluate/constant/unaryMinusIndependentExpType.kt");
        }
        
    }
    
    @TestMetadata("compiler/testData/evaluate/isPure")
    public static class IsPure extends AbstractEvaluateExpressionTest {
        public void testAllFilesPresentInIsPure() throws Exception {
            JetTestUtils.assertAllTestsPresentByMetadata(this.getClass(), "org.jetbrains.jet.generators.tests.TestsPackage", new File("compiler/testData/evaluate/isPure"), Pattern.compile("^(.+)\\.kt$"), true);
        }
        
        @TestMetadata("innerToType.kt")
        public void testInnerToType() throws Exception {
            doIsPureTest("compiler/testData/evaluate/isPure/innerToType.kt");
        }
        
        @TestMetadata("namedConstants.kt")
        public void testNamedConstants() throws Exception {
            doIsPureTest("compiler/testData/evaluate/isPure/namedConstants.kt");
        }
        
        @TestMetadata("toType.kt")
        public void testToType() throws Exception {
            doIsPureTest("compiler/testData/evaluate/isPure/toType.kt");
        }
        
        @TestMetadata("unaryMinusIndepWoExpType.kt")
        public void testUnaryMinusIndepWoExpType() throws Exception {
            doIsPureTest("compiler/testData/evaluate/isPure/unaryMinusIndepWoExpType.kt");
        }
        
        @TestMetadata("unaryMinusIndependentExpType.kt")
        public void testUnaryMinusIndependentExpType() throws Exception {
            doIsPureTest("compiler/testData/evaluate/isPure/unaryMinusIndependentExpType.kt");
        }
        
    }
    
    @TestMetadata("compiler/testData/evaluate/usesVariableAsConstant")
    public static class UsesVariableAsConstant extends AbstractEvaluateExpressionTest {
        public void testAllFilesPresentInUsesVariableAsConstant() throws Exception {
            JetTestUtils.assertAllTestsPresentByMetadata(this.getClass(), "org.jetbrains.jet.generators.tests.TestsPackage", new File("compiler/testData/evaluate/usesVariableAsConstant"), Pattern.compile("^(.+)\\.kt$"), true);
        }
        
        @TestMetadata("binaryTypes.kt")
        public void testBinaryTypes() throws Exception {
            doUsesVariableAsConstantTest("compiler/testData/evaluate/usesVariableAsConstant/binaryTypes.kt");
        }
        
        @TestMetadata("NamedConstants.kt")
        public void testNamedConstants() throws Exception {
            doUsesVariableAsConstantTest("compiler/testData/evaluate/usesVariableAsConstant/NamedConstants.kt");
        }
        
        @TestMetadata("OtherTypes.kt")
        public void testOtherTypes() throws Exception {
            doUsesVariableAsConstantTest("compiler/testData/evaluate/usesVariableAsConstant/OtherTypes.kt");
        }
        
        @TestMetadata("simpleTypes.kt")
        public void testSimpleTypes() throws Exception {
            doUsesVariableAsConstantTest("compiler/testData/evaluate/usesVariableAsConstant/simpleTypes.kt");
        }
        
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite("EvaluateExpressionTestGenerated");
        suite.addTestSuite(Constant.class);
        suite.addTestSuite(IsPure.class);
        suite.addTestSuite(UsesVariableAsConstant.class);
        return suite;
    }
}