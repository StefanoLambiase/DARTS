package main.testSmellDetection.detector;

import it.unisa.testSmellDiffusion.beans.ClassBean;
import it.unisa.testSmellDiffusion.utility.ProjectStructureBuilder;
import it.unisa.testSmellDiffusion.testSmellInfo.eagerTest.EagerTestInfo;
import it.unisa.testSmellDiffusion.testSmellInfo.generalFixture.GeneralFixtureInfo;
import it.unisa.testSmellDiffusion.testSmellInfo.lackOfCohesion.LackOfCohesionInfo;
import it.unisa.testSmellDiffusion.utility.TestSmellUtilities;
import main.testSmellDetection.IDetector;
import main.testSmellDetection.structuralDetector.*;

import java.util.ArrayList;

public class TestSmellStructuralDetector implements IDetector{
    //Classe che contiene al suo interno la lista dei package, classi, classi di test, e production classes del progetto in esame.
    private ProjectStructureBuilder psb;

    //Valori usati nella detection di GeneralFixture e che potrebbero essere modificati dall'utente.
    private int numberOfProductionTypes;
    private int numberOfObjectUsesInSetup;

    public TestSmellStructuralDetector(String pFolderPath){
        psb = new ProjectStructureBuilder(pFolderPath);
        numberOfProductionTypes = 3;
        numberOfObjectUsesInSetup = 3;
    }

    @Override
    public ArrayList<GeneralFixtureInfo> executeDetectionForGeneralFixture(){
        System.out.println("\nDETECTOR STRUTTURALE: Esecuzione per General Fixture.");
        ArrayList<GeneralFixtureInfo> classesWithGeneralFixture = new ArrayList<>();

        ArrayList<ClassBean> testClasses = psb.getAllTestClasses();
        ArrayList<ClassBean> productionClasses = psb.getAllProductionClasses();
        StructuralDetectorGeneralFixture sdgf = new StructuralDetectorGeneralFixture();

        for(ClassBean testClass : testClasses) {
            if(sdgf.isGeneralFixture(testClass, testClasses, productionClasses, numberOfProductionTypes, numberOfObjectUsesInSetup)) {
                //Mi costruisco un oggetto che contenga le info di GeneralFixture
                GeneralFixtureInfo classWithGeneralFixture = new GeneralFixtureInfo(testClass);
                classWithGeneralFixture.checkMethodsThatCauseGeneralFixture();
                classesWithGeneralFixture.add(classWithGeneralFixture);
            }
        }
        return classesWithGeneralFixture;
    }

    @Override
    public ArrayList<EagerTestInfo> executeDetectionForEagerTest(){
        System.out.println("\nDETECTOR STRUTTURALE: Esecuzione per Eager Test.");
        ArrayList<EagerTestInfo> classesWithEagerTest = new ArrayList<>();

        ArrayList<ClassBean> testClasses = psb.getAllTestClasses();
        ArrayList<ClassBean> productionClasses = psb.getAllProductionClasses();
        StructuralDetectorEagerTest sdet = new StructuralDetectorEagerTest();

        for(ClassBean testClass : testClasses) {
            ClassBean productionClass = TestSmellUtilities.findProductionClass(testClass, psb.getAllClasses());
            if(sdet.isEagerTest(testClass, testClasses, productionClasses)) {
                //Mi costruisco un oggetto che contenga le info di EagerTest
                EagerTestInfo classWithEagerTest = new EagerTestInfo(testClass, productionClass);
                classWithEagerTest.checkMethodsThatCauseEagerTest();
                classesWithEagerTest.add(classWithEagerTest);
            }
        }
        return classesWithEagerTest;
    }

    @Override
    public ArrayList<LackOfCohesionInfo> executeDetectionForLackOfCohesion(){
        System.out.println("\nDETECTOR STRUTTURALE: Esecuzione per Lack of Cohesion.");
        ArrayList<LackOfCohesionInfo> classesWithLackOfCohesion = new ArrayList<>();

        ArrayList<ClassBean> testClasses = psb.getAllTestClasses();
        StructuralDetectorLackOfCohesion sdloc = new StructuralDetectorLackOfCohesion();

        for(ClassBean testClass : testClasses) {
            if(sdloc.isLackOfCohesion(testClass)) {
                //Mi costruisco un oggetto che contenga le info di LackOfCohesion
                ClassBean pClass = TestSmellUtilities.findProductionClass(testClass, psb.getAllClasses());
                LackOfCohesionInfo classWithLackOfCohesion = new LackOfCohesionInfo(testClass, pClass);
                classWithLackOfCohesion.checkMethodsThatCauseLackOfCohesion(psb.getAllPackages());
                classesWithLackOfCohesion.add(classWithLackOfCohesion);
            }
        }
        return classesWithLackOfCohesion;
    }


}
