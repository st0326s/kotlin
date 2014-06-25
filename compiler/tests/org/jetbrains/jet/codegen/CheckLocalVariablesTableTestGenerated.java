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

package org.jetbrains.jet.codegen;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestSuite;

import java.io.File;
import java.util.regex.Pattern;
import org.jetbrains.jet.JetTestUtils;
import org.jetbrains.jet.test.InnerTestClasses;
import org.jetbrains.jet.test.TestMetadata;

import org.jetbrains.jet.codegen.AbstractCheckLocalVariablesTableTest;

/** This class is generated by {@link org.jetbrains.jet.generators.tests.TestsPackage}. DO NOT MODIFY MANUALLY */
@SuppressWarnings("all")
@TestMetadata("compiler/testData/checkLocalVariablesTable")
public class CheckLocalVariablesTableTestGenerated extends AbstractCheckLocalVariablesTableTest {
    public void testAllFilesPresentInCheckLocalVariablesTable() throws Exception {
        JetTestUtils.assertAllTestsPresentByMetadata(this.getClass(), "org.jetbrains.jet.generators.tests.TestsPackage", new File("compiler/testData/checkLocalVariablesTable"), Pattern.compile("^(.+)\\.kt$"), true);
    }
    
    @TestMetadata("catchClause.kt")
    public void testCatchClause() throws Exception {
        doTest("compiler/testData/checkLocalVariablesTable/catchClause.kt");
    }
    
    @TestMetadata("copyFunction.kt")
    public void testCopyFunction() throws Exception {
        doTest("compiler/testData/checkLocalVariablesTable/copyFunction.kt");
    }
    
    @TestMetadata("inlineLambdaWithItParam.kt")
    public void testInlineLambdaWithItParam() throws Exception {
        doTest("compiler/testData/checkLocalVariablesTable/inlineLambdaWithItParam.kt");
    }
    
    @TestMetadata("inlineLambdaWithParam.kt")
    public void testInlineLambdaWithParam() throws Exception {
        doTest("compiler/testData/checkLocalVariablesTable/inlineLambdaWithParam.kt");
    }
    
    @TestMetadata("inlineSimple.kt")
    public void testInlineSimple() throws Exception {
        doTest("compiler/testData/checkLocalVariablesTable/inlineSimple.kt");
    }
    
    @TestMetadata("inlineSimpleChain.kt")
    public void testInlineSimpleChain() throws Exception {
        doTest("compiler/testData/checkLocalVariablesTable/inlineSimpleChain.kt");
    }
    
    @TestMetadata("itInLambda.kt")
    public void testItInLambda() throws Exception {
        doTest("compiler/testData/checkLocalVariablesTable/itInLambda.kt");
    }
    
    @TestMetadata("itInReturnedLambda.kt")
    public void testItInReturnedLambda() throws Exception {
        doTest("compiler/testData/checkLocalVariablesTable/itInReturnedLambda.kt");
    }
    
    @TestMetadata("lambdaAsVar.kt")
    public void testLambdaAsVar() throws Exception {
        doTest("compiler/testData/checkLocalVariablesTable/lambdaAsVar.kt");
    }
    
    @TestMetadata("localFun.kt")
    public void testLocalFun() throws Exception {
        doTest("compiler/testData/checkLocalVariablesTable/localFun.kt");
    }
    
}