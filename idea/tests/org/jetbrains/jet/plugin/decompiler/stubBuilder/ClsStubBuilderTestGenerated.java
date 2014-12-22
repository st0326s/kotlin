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

package org.jetbrains.jet.plugin.decompiler.stubBuilder;

import com.intellij.testFramework.TestDataPath;
import org.jetbrains.jet.JUnit3RunnerWithInners;
import org.jetbrains.jet.JetTestUtils;
import org.jetbrains.jet.test.InnerTestClasses;
import org.jetbrains.jet.test.TestMetadata;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.regex.Pattern;

/** This class is generated by {@link org.jetbrains.jet.generators.tests.TestsPackage}. DO NOT MODIFY MANUALLY */
@SuppressWarnings("all")
@TestMetadata("idea/testData/decompiler/stubBuilder")
@TestDataPath("$PROJECT_ROOT")
@RunWith(JUnit3RunnerWithInners.class)
public class ClsStubBuilderTestGenerated extends AbstractClsStubBuilderTest {
    public void testAllFilesPresentInStubBuilder() throws Exception {
        JetTestUtils.assertAllTestsPresentByMetadata(this.getClass(), new File("idea/testData/decompiler/stubBuilder"), Pattern.compile("^([^\\.]+)$"), false);
    }

    @TestMetadata("AnnotationClass")
    public void testAnnotationClass() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/testData/decompiler/stubBuilder/AnnotationClass/");
        doTest(fileName);
    }

    @TestMetadata("Annotations")
    public void testAnnotations() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/testData/decompiler/stubBuilder/Annotations/");
        doTest(fileName);
    }

    @TestMetadata("ClassMembers")
    public void testClassMembers() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/testData/decompiler/stubBuilder/ClassMembers/");
        doTest(fileName);
    }

    @TestMetadata("ClassObject")
    public void testClassObject() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/testData/decompiler/stubBuilder/ClassObject/");
        doTest(fileName);
    }

    @TestMetadata("DataClass")
    public void testDataClass() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/testData/decompiler/stubBuilder/DataClass/");
        doTest(fileName);
    }

    @TestMetadata("Delegation")
    public void testDelegation() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/testData/decompiler/stubBuilder/Delegation/");
        doTest(fileName);
    }

    @TestMetadata("DependencyOnNestedClasses")
    public void testDependencyOnNestedClasses() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/testData/decompiler/stubBuilder/DependencyOnNestedClasses/");
        doTest(fileName);
    }

    @TestMetadata("Enum")
    public void testEnum() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/testData/decompiler/stubBuilder/Enum/");
        doTest(fileName);
    }

    @TestMetadata("FlexibleTypes")
    public void testFlexibleTypes() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/testData/decompiler/stubBuilder/FlexibleTypes/");
        doTest(fileName);
    }

    @TestMetadata("InheritingClasses")
    public void testInheritingClasses() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/testData/decompiler/stubBuilder/InheritingClasses/");
        doTest(fileName);
    }

    @TestMetadata("NestedClasses")
    public void testNestedClasses() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/testData/decompiler/stubBuilder/NestedClasses/");
        doTest(fileName);
    }

    @TestMetadata("Objects")
    public void testObjects() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/testData/decompiler/stubBuilder/Objects/");
        doTest(fileName);
    }

    @TestMetadata("PrivateToThis")
    public void testPrivateToThis() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/testData/decompiler/stubBuilder/PrivateToThis/");
        doTest(fileName);
    }

    @TestMetadata("TopLevelMembersAnnotatedPackage")
    public void testTopLevelMembersAnnotatedPackage() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/testData/decompiler/stubBuilder/TopLevelMembersAnnotatedPackage/");
        doTest(fileName);
    }

    @TestMetadata("TopLevelMembersPackage")
    public void testTopLevelMembersPackage() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/testData/decompiler/stubBuilder/TopLevelMembersPackage/");
        doTest(fileName);
    }

    @TestMetadata("TypeBoundsAndDelegationSpecifiers")
    public void testTypeBoundsAndDelegationSpecifiers() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/testData/decompiler/stubBuilder/TypeBoundsAndDelegationSpecifiers/");
        doTest(fileName);
    }

    @TestMetadata("TypeParams")
    public void testTypeParams() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/testData/decompiler/stubBuilder/TypeParams/");
        doTest(fileName);
    }

    @TestMetadata("Types")
    public void testTypes() throws Exception {
        String fileName = JetTestUtils.navigationMetadata("idea/testData/decompiler/stubBuilder/Types/");
        doTest(fileName);
    }
}
