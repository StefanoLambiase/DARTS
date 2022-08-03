package testSmellDetection.textualRules;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiPackage;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import testSmellDetection.bean.PsiClassBean;
import testSmellDetection.testSmellInfo.hardCodedTestData.MethodWithHardCodedTestData;
import utility.ConverterUtilities;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class HardCodedTestDataTextualTest extends LightJavaCodeInsightFixtureTestCase {
    @Override
    protected String getTestDataPath() {
        return "src/test/resources/testdata/";
    }

    public void testHardCodedTestDataNotPresent() {
        myFixture.configureByFile("test/HardCodedTestDataNotPresent.java");
        ArrayList<PsiClassBean> psiClassBeans = ConverterUtilities.getClassesFromPackages(getProject());
        ArrayList<MethodWithHardCodedTestData> smellList = HardCodedTestDataTextual.checkMethodsThatCauseHardCodedTestData(psiClassBeans.get(0));
        assertEquals(null, smellList);
    }

    public void testHardCodedTestDataPresent() {
        myFixture.configureByFile("test/HardCodedTestDataPresent.java");
        ArrayList<PsiClassBean> psiClassBeans = ConverterUtilities.getClassesFromPackages(getProject());
        ArrayList<MethodWithHardCodedTestData> smellList = HardCodedTestDataTextual.checkMethodsThatCauseHardCodedTestData(psiClassBeans.get(0));
        assertEquals(2, smellList.size());
    }
}