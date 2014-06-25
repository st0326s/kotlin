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

package org.jetbrains.jet.plugin.debugger.evaluate;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestSuite;

import java.io.File;
import java.util.regex.Pattern;
import org.jetbrains.jet.JetTestUtils;
import org.jetbrains.jet.test.InnerTestClasses;
import org.jetbrains.jet.test.TestMetadata;

import org.jetbrains.jet.plugin.debugger.evaluate.AbstractCodeFragmentHighlightingTest;

/** This class is generated by {@link org.jetbrains.jet.generators.tests.TestsPackage}. DO NOT MODIFY MANUALLY */
@SuppressWarnings("all")
@InnerTestClasses({CodeFragmentHighlightingTestGenerated.CodeFragments.class, CodeFragmentHighlightingTestGenerated.Imports.class})
public class CodeFragmentHighlightingTestGenerated extends AbstractCodeFragmentHighlightingTest {
    @TestMetadata("idea/testData/checker/codeFragments")
    public static class CodeFragments extends AbstractCodeFragmentHighlightingTest {
        public void testAllFilesPresentInCodeFragments() throws Exception {
            JetTestUtils.assertAllTestsPresentByMetadata(this.getClass(), "org.jetbrains.jet.generators.tests.TestsPackage", new File("idea/testData/checker/codeFragments"), Pattern.compile("^(.+)\\.kt$"), false);
        }
        
        @TestMetadata("binaryExpression.kt")
        public void testBinaryExpression() throws Exception {
            doTest("idea/testData/checker/codeFragments/binaryExpression.kt");
        }
        
        @TestMetadata("blockCodeFragment.kt")
        public void testBlockCodeFragment() throws Exception {
            doTest("idea/testData/checker/codeFragments/blockCodeFragment.kt");
        }
        
        @TestMetadata("callExpression.kt")
        public void testCallExpression() throws Exception {
            doTest("idea/testData/checker/codeFragments/callExpression.kt");
        }
        
        @TestMetadata("contextElementAsStatement.kt")
        public void testContextElementAsStatement() throws Exception {
            doTest("idea/testData/checker/codeFragments/contextElementAsStatement.kt");
        }
        
        @TestMetadata("privateFunArgumentsResolve.kt")
        public void testPrivateFunArgumentsResolve() throws Exception {
            doTest("idea/testData/checker/codeFragments/privateFunArgumentsResolve.kt");
        }
        
        @TestMetadata("privateFunTypeArguments.kt")
        public void testPrivateFunTypeArguments() throws Exception {
            doTest("idea/testData/checker/codeFragments/privateFunTypeArguments.kt");
        }
        
        @TestMetadata("privateMember.kt")
        public void testPrivateMember() throws Exception {
            doTest("idea/testData/checker/codeFragments/privateMember.kt");
        }
        
        @TestMetadata("protectedMember.kt")
        public void testProtectedMember() throws Exception {
            doTest("idea/testData/checker/codeFragments/protectedMember.kt");
        }
        
        @TestMetadata("simpleNameExpression.kt")
        public void testSimpleNameExpression() throws Exception {
            doTest("idea/testData/checker/codeFragments/simpleNameExpression.kt");
        }
        
        @TestMetadata("smartCasts.kt")
        public void testSmartCasts() throws Exception {
            doTest("idea/testData/checker/codeFragments/smartCasts.kt");
        }
        
    }
    
    @TestMetadata("idea/testData/checker/codeFragments/imports")
    public static class Imports extends AbstractCodeFragmentHighlightingTest {
        public void testAllFilesPresentInImports() throws Exception {
            JetTestUtils.assertAllTestsPresentByMetadata(this.getClass(), "org.jetbrains.jet.generators.tests.TestsPackage", new File("idea/testData/checker/codeFragments/imports"), Pattern.compile("^(.+)\\.kt$"), true);
        }
        
        @TestMetadata("hashMap.kt")
        public void testHashMap() throws Exception {
            doTestWithImport("idea/testData/checker/codeFragments/imports/hashMap.kt");
        }
        
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite("CodeFragmentHighlightingTestGenerated");
        suite.addTestSuite(CodeFragments.class);
        suite.addTestSuite(Imports.class);
        return suite;
    }
}