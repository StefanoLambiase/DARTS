package testSmellDetection.textualRules;

import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import testSmellDetection.bean.PsiClassBean;
import testSmellDetection.testSmellInfo.hardCodedTestData.MethodWithHardCodedTestData;
import testSmellDetection.testSmellInfo.testCodeDuplication.MethodWithTestCodeDuplication;
import utility.ConverterUtilities;

import java.util.ArrayList;

public class TestCodeDuplicationTextualTest extends LightJavaCodeInsightFixtureTestCase {
    @Override
    protected String getTestDataPath() {
        return "src/test/resources/testdata/";
    }

    public void testTestCodeDuplicationNotPresent() throws Exception{
        myFixture.configureByFile("test/TestCodeDuplicationNotPresent.java");
        ArrayList<PsiClassBean> psiClassBeans = ConverterUtilities.getClassesFromPackages(getProject());
        ArrayList<MethodWithTestCodeDuplication> smellList = TestCodeDuplicationTextual.checkMethodsThatCauseTestCodeDuplication(psiClassBeans.get(0), myFixture.getProject());
        assertEquals(null, smellList);
    }

    public void testTestCodeDuplicationPresent() throws Exception{
        myFixture.configureByFile("test/TestCodeDuplicationPresent.java");
        ArrayList<PsiClassBean> psiClassBeans = ConverterUtilities.getClassesFromPackages(getProject());
//        ArrayList<MethodWithTestCodeDuplication> smellList = TestCodeDuplicationTextual.checkMethodsThatCauseTestCodeDuplication(psiClassBeans.get(0), myFixture.getTestDataPath() + "/test/TestCodeDuplicationPresent.java", myFixture.getDocument(psiClassBeans.get(0).getPsiClass().getContainingFile()));
        ArrayList<MethodWithTestCodeDuplication> smellList = TestCodeDuplicationTextual.checkMethodsThatCauseTestCodeDuplication(psiClassBeans.get(0), myFixture.getProject());
        assertEquals(1, smellList.size());
    }
}