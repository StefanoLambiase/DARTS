package testSmellDetection.textualRules;

import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import testSmellDetection.bean.PsiClassBean;
import testSmellDetection.testSmellInfo.hardCodedTestData.MethodWithHardCodedTestData;
import testSmellDetection.testSmellInfo.mysteryGuest.MethodWithMysteryGuest;
import utility.ConverterUtilities;

import java.util.ArrayList;

public class MysteryGuestTextualTest extends LightJavaCodeInsightFixtureTestCase {
    @Override
    protected String getTestDataPath() {
        return "src/test/resources/testdata/";
    }

    public void testMysteryGuestNotPresent() {
        myFixture.configureByFile("test/MysteryGuestNotPresent.java");
        ArrayList<PsiClassBean> psiClassBeans = ConverterUtilities.getClassesFromPackages(getProject());
        ArrayList<MethodWithMysteryGuest> smellList = MysteryGuestTextual.checkMethodsThatCauseMysteryGuest(psiClassBeans.get(0));
        assertEquals(null, smellList);
    }

    public void testMysteryGuestPresent() {
        myFixture.configureByFile("test/MysteryGuestPresent.java");
        ArrayList<PsiClassBean> psiClassBeans = ConverterUtilities.getClassesFromPackages(getProject());
        ArrayList<MethodWithMysteryGuest> smellList = MysteryGuestTextual.checkMethodsThatCauseMysteryGuest(psiClassBeans.get(0));
        assertEquals(1, smellList.size());
    }
}