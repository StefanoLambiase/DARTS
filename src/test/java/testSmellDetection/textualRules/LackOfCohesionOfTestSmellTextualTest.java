package testSmellDetection.textualRules;

import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import testSmellDetection.bean.PsiClassBean;
import testSmellDetection.bean.PsiMethodBean;
import utility.ConverterUtilities;
import utility.TestSmellUtilities;

import java.util.ArrayList;

public class LackOfCohesionOfTestSmellTextualTest extends LightJavaCodeInsightFixtureTestCase {
    @Override
    protected String getTestDataPath() {
        return "src/test/resources/testdata/";
    }

    public void testLackOfCohesionOfTestSmellNotPresent() {
        myFixture.configureByFile("main/LackOfCohesionOfTestSmell.java");
        myFixture.configureByFile("test/LackOfCohesionOfTestSmellNotPresent.java");
        ArrayList<PsiClassBean> psiClassBeans = ConverterUtilities.getClassesFromPackages(getProject());
        ArrayList<PsiClassBean> testClassBeans = TestSmellUtilities.getAllTestClasses(psiClassBeans);
        ArrayList<PsiMethodBean> smellList = LackOfCohesionOfTestSmellTextual.checkMethodsThatCauseLackOfCohesion(testClassBeans.get(0));
        assertEquals(null, smellList);
    }

    public void testLackOfCohesionOfTestSmellPresent() {
        myFixture.configureByFile("main/LackOfCohesionOfTestSmell.java");
        myFixture.configureByFile("test/LackOfCohesionOfTestSmellPresent.java");
        ArrayList<PsiClassBean> psiClassBeans = ConverterUtilities.getClassesFromPackages(getProject());
        ArrayList<PsiClassBean> testClassBeans = TestSmellUtilities.getAllTestClasses(psiClassBeans);
        ArrayList<PsiMethodBean> smellList = LackOfCohesionOfTestSmellTextual.checkMethodsThatCauseLackOfCohesion(testClassBeans.get(0));
        assertEquals(1, smellList.size());
    }
}