package main.testSmellDetection.detector;

import it.unisa.testSmellDiffusion.computation.TestSmellStructuralDetector;
import it.unisa.testSmellDiffusion.testSmellInfo.eagerTest.EagerTestInfo;
import it.unisa.testSmellDiffusion.testSmellInfo.generalFixture.GeneralFixtureInfo;
import it.unisa.testSmellDiffusion.testSmellInfo.lackOfCohesion.LackOfCohesionInfo;
import main.testSmellDetection.IDetector;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class StructuralDetector implements IDetector {
    private TestSmellStructuralDetector tsm;

    /**
     * Costruttore usato per instanziare un detector usabile ovunque
     */
    public StructuralDetector(){
        tsm = new TestSmellStructuralDetector();
    }


    //Metodi dell'interfaccia usati in virtù dello Strategy Desgin Pattern

    /**
     * Metodo per ottenere le informazioni riguardanti gli Smells GeneralFixture presenti in un dato progetto
     * @param pFolderPath la folder del progetto da analizzare
     * @return una lista delle informazioni sugli smells
     */
    @Override
    public ArrayList<GeneralFixtureInfo> executeDetectionForGeneralFixture(@NotNull String pFolderPath) {
        //Parte relativa a GeneralFixture
        System.out.println("\nDETECTOR: inizio a cercare per GENERAL FIXTURE: ");
        ArrayList<GeneralFixtureInfo> classesWithGeneralFixture = tsm.checkGeneralFixture(pFolderPath, 3, 3);

        return classesWithGeneralFixture;
    }

    /**
     * Metodo per ottenere le informazioni riguardanti gli Smells EagerTest presenti in un dato progetto
     * @param pFolderPath la folder del progetto da analizzare
     * @return una lista delle informazioni sugli smells
     */
    @Override
    public ArrayList<EagerTestInfo> executeDetectionForEagerTest(@NotNull String pFolderPath) {
        //Parte relativa a EagerTest
        System.out.println("\nDETECTOR: inizio a cercare per EagerTest: ");
        ArrayList<EagerTestInfo> classesWithEagerTest = tsm.checkEagerTest(pFolderPath);

        return classesWithEagerTest;
    }

    /**
     * Metodo per ottenere le informazioni riguardanti gli Smells LackOfCohesion presenti in un dato progetto
     * @param pFolderPath la folder del progetto da analizzare
     * @return la lista delle informazioni sugli smells
     */
    @Override
    public ArrayList<LackOfCohesionInfo> executeDetectionForLackOfCohesion(@NotNull String pFolderPath){
        //Parte relativa a EagerTest
        System.out.println("\nDETECTOR: inizio a cercare per LackOfCohesion: ");
        ArrayList<LackOfCohesionInfo> classesWithLackOfCohesion = tsm.checkLackOfCohesion(pFolderPath);

        return classesWithLackOfCohesion;
    }
}
