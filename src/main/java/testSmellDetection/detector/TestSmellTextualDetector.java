package testSmellDetection.detector;

import com.intellij.openapi.project.Project;
import testSmellDetection.bean.PsiClassBean;
import testSmellDetection.bean.PsiMethodBean;
import testSmellDetection.testSmellInfo.eagerTest.EagerTestInfo;
import testSmellDetection.testSmellInfo.eagerTest.MethodWithEagerTest;
import testSmellDetection.testSmellInfo.generalFixture.GeneralFixtureInfo;
import testSmellDetection.testSmellInfo.generalFixture.MethodWithGeneralFixture;
import testSmellDetection.testSmellInfo.hardCodedTestData.HardCodedTestDataInfo;
import testSmellDetection.testSmellInfo.hardCodedTestData.MethodWithHardCodedTestData;
import testSmellDetection.testSmellInfo.lackOfCohesion.LackOfCohesionInfo;
import testSmellDetection.testSmellInfo.testCodeDuplication.MethodWithTestCodeDuplication;
import testSmellDetection.testSmellInfo.testCodeDuplication.TestCodeDuplicationInfo;
import testSmellDetection.testSmellInfo.mysteryGuest.MethodWithMysteryGuest;
import testSmellDetection.testSmellInfo.mysteryGuest.MysteryGuestInfo;
import testSmellDetection.textualRules.EagerTestTextual;
import testSmellDetection.textualRules.GeneralFixtureTextual;
import testSmellDetection.textualRules.HardCodedTestDataTextual;
import testSmellDetection.textualRules.LackOfCohesionOfTestSmellTextual;
import testSmellDetection.textualRules.TestCodeDuplicationTextual;
import testSmellDetection.textualRules.MysteryGuestTextual;
import utility.ConverterUtilities;
import utility.TestSmellUtilities;

import java.util.ArrayList;

public class TestSmellTextualDetector implements IDetector{
    private ArrayList<PsiClassBean> classBeans;
    private ArrayList<PsiClassBean> testClasses;
    private ArrayList<PsiClassBean> productionClasses;

    public TestSmellTextualDetector(Project project){
        classBeans = ConverterUtilities.getClassesFromPackages(project);
        testClasses = TestSmellUtilities.getAllTestClasses(classBeans);
        productionClasses = TestSmellUtilities.getAllProductionClasses(classBeans, testClasses);
    }

    public ArrayList<GeneralFixtureInfo> executeDetectionForGeneralFixture() {
        ArrayList<GeneralFixtureInfo> classesWithGeneralFixture = new ArrayList<>();
        for(PsiClassBean testClass : testClasses){
            ArrayList<MethodWithGeneralFixture> methodWithGeneralFixtures = GeneralFixtureTextual.checkMethodsThatCauseGeneralFixture(testClass);
            if(methodWithGeneralFixtures != null){
                classesWithGeneralFixture.add(new GeneralFixtureInfo(testClass, methodWithGeneralFixtures));
            }
            /* OLD IMPLEMENTATION */
            /*
            if(GeneralFixtureTextual.isGeneralFixture(testClass)){
                ArrayList<MethodWithGeneralFixture> methodWithGeneralFixtures = GeneralFixtureTextual.checkMethodsThatCauseGeneralFixture(testClass);
                classesWithGeneralFixture.add(new GeneralFixtureInfo(testClass, methodWithGeneralFixtures));
            }
            */
        }
        return classesWithGeneralFixture;
    }


    public ArrayList<EagerTestInfo> executeDetectionForEagerTest() {
        ArrayList<EagerTestInfo> classesWithEagerTest = new ArrayList<>();
        for(PsiClassBean testClass : testClasses){
            if(testClass.getProductionClass() != null) {
                ArrayList<MethodWithEagerTest> methodWithEagerTests = EagerTestTextual.checkMethodsThatCauseEagerTest(testClass, testClass.getProductionClass());
                if (methodWithEagerTests != null) {
                    classesWithEagerTest.add(new EagerTestInfo(testClass, testClass.getProductionClass(), methodWithEagerTests));
                }
            }
        }
        return classesWithEagerTest;
    }


    public ArrayList<LackOfCohesionInfo> executeDetectionForLackOfCohesion() {
        ArrayList<LackOfCohesionInfo> classesWithLackOfCohesion = new ArrayList<>();
        for(PsiClassBean testClass : testClasses){
            if(testClass.getProductionClass() != null) {
                ArrayList<PsiMethodBean> methodsWithLackOfCohesion = LackOfCohesionOfTestSmellTextual.checkMethodsThatCauseLackOfCohesion(testClass);
                if (methodsWithLackOfCohesion != null) {
                    classesWithLackOfCohesion.add(new LackOfCohesionInfo(testClass, testClass.getProductionClass(), methodsWithLackOfCohesion));
                }
            }
        }
        return classesWithLackOfCohesion;
    }

    public ArrayList<HardCodedTestDataInfo> executeDetectionForHardCodedTestData() {
        ArrayList<HardCodedTestDataInfo> classesWithHardCodedTestData = new ArrayList<>();
        for(PsiClassBean testClass : testClasses){
            if(testClass.getProductionClass() != null) {
                ArrayList<MethodWithHardCodedTestData> methodsWithHardCodedTestData = HardCodedTestDataTextual.checkMethodsThatCauseHardCodedTestData(testClass);
                if (methodsWithHardCodedTestData != null) {
                    classesWithHardCodedTestData.add(new HardCodedTestDataInfo(testClass, methodsWithHardCodedTestData));
                }
            }
        }

        return classesWithHardCodedTestData;
    }
  
  public ArrayList<MysteryGuestInfo> executeDetectionForMysteryGuest() {
        ArrayList<MysteryGuestInfo> classesWithMysteryGuest = new ArrayList<>();
        for(PsiClassBean testClass : testClasses){
            if(testClass.getProductionClass() != null) {
                ArrayList<MethodWithMysteryGuest> methodWithMysteryGuests = MysteryGuestTextual.checkMethodsThatCauseMysteryGuest(testClass);
                if (methodWithMysteryGuests != null) {
                    classesWithMysteryGuest.add(new MysteryGuestInfo(testClass, methodWithMysteryGuests));
                }
            }
        }
        return classesWithMysteryGuest;
    }
  
  public ArrayList<TestCodeDuplicationInfo> executeDetectionForTestCodeDuplication() {
        ArrayList<TestCodeDuplicationInfo> classesWithTestCodeDuplication = new ArrayList<>();
        for (PsiClassBean testClass : testClasses) {
            if (testClass.getProductionClass() != null) {
                ArrayList<MethodWithTestCodeDuplication> methodsWithTestCodeDuplication = TestCodeDuplicationTextual.checkMethodsThatCauseTestCodeDuplication(testClass);
                if (methodsWithTestCodeDuplication != null) {
                    classesWithTestCodeDuplication.add(new TestCodeDuplicationInfo(testClass, methodsWithTestCodeDuplication));
                }
            }
        }
        return classesWithTestCodeDuplication;
  }
}
