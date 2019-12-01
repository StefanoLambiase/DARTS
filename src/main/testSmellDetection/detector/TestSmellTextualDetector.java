package main.testSmellDetection.detector;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiPackage;
import main.testSmellDetection.bean.PsiClassBean;
import main.testSmellDetection.bean.PsiMethodBean;
import main.testSmellDetection.testSmellInfo.eagerTest.EagerTestInfo;
import main.testSmellDetection.testSmellInfo.eagerTest.MethodWithEagerTest;
import main.testSmellDetection.testSmellInfo.generalFixture.GeneralFixtureInfo;
import main.testSmellDetection.testSmellInfo.generalFixture.MethodWithGeneralFixture;
import main.testSmellDetection.testSmellInfo.lackOfCohesion.LackOfCohesionInfo;
import main.testSmellDetection.textualRules.EagerTestTextual;
import main.testSmellDetection.textualRules.GeneralFixtureTextual;
import main.testSmellDetection.textualRules.LackOfCohesionOfTestSmellTextual;
import main.utility.ConverterUtilities;
import main.utility.TestSmellUtilities;

import java.util.ArrayList;

public class TestSmellTextualDetector {
    private ArrayList<PsiPackage> packages;
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
            if(GeneralFixtureTextual.isGeneralFixture(testClass)){
                ArrayList<MethodWithGeneralFixture> methodWithGeneralFixtures = GeneralFixtureTextual.checkMethodsThatCauseGeneralFixture(testClass);
                classesWithGeneralFixture.add(new GeneralFixtureInfo(testClass, methodWithGeneralFixtures));
            }
        }
        return classesWithGeneralFixture;
    }


    public ArrayList<EagerTestInfo> executeDetectionForEagerTest() {
        ArrayList<EagerTestInfo> classesWithEagerTest = new ArrayList<>();
        for(PsiClassBean testClass : testClasses){
            if(EagerTestTextual.isEagerTest(testClass, testClass.getProductionClass())){
                ArrayList<MethodWithEagerTest> methodWithEagerTests = EagerTestTextual.checkMethodsThatCauseEagerTest(testClass, testClass.getProductionClass());
                classesWithEagerTest.add(new EagerTestInfo(testClass, testClass.getProductionClass(), methodWithEagerTests));
            }
        }
        return classesWithEagerTest;
    }


    public ArrayList<LackOfCohesionInfo> executeDetectionForLackOfCohesion() {
        ArrayList<LackOfCohesionInfo> classesWithLackOfCohesion = new ArrayList<>();
        for(PsiClassBean testClass : testClasses){
            if(LackOfCohesionOfTestSmellTextual.isLackOfCohesionTestMethods(testClass)){
                ArrayList<PsiMethodBean> methodsWithLackOfCohesion = LackOfCohesionOfTestSmellTextual.checkMethodsThatCauseLackOfCohesion(testClass);
                classesWithLackOfCohesion.add(new LackOfCohesionInfo(testClass, testClass.getProductionClass(), methodsWithLackOfCohesion));
            }
        }
        return classesWithLackOfCohesion;
    }
}
