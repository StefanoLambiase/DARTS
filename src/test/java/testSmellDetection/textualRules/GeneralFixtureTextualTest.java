package testSmellDetection.textualRules;

import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import testSmellDetection.bean.PsiClassBean;
import testSmellDetection.testSmellInfo.generalFixture.MethodWithGeneralFixture;
import utility.ConverterUtilities;

import java.util.ArrayList;

public class GeneralFixtureTextualTest extends LightJavaCodeInsightFixtureTestCase {
    @Override
    protected String getTestDataPath() {
        return "src/test/resources/testdata/";
    }

    public void testGeneralFixtureNotPresent() {
        myFixture.configureByFile("test/GeneralFixtureNotPresent.java");
        ArrayList<PsiClassBean> psiClassBeans = ConverterUtilities.getClassesFromPackages(getProject());
        ArrayList<MethodWithGeneralFixture> smellList = GeneralFixtureTextual.checkMethodsThatCauseGeneralFixture(psiClassBeans.get(0));
        assertEquals(null, smellList);
    }

    public void testGeneralFixturePresent() {
        myFixture.configureByFile("test/GeneralFixturePresent.java");
        ArrayList<PsiClassBean> psiClassBeans = ConverterUtilities.getClassesFromPackages(getProject());
        ArrayList<MethodWithGeneralFixture> smellList = GeneralFixtureTextual.checkMethodsThatCauseGeneralFixture(psiClassBeans.get(0));
        assertEquals(1, smellList.size());
    }
}