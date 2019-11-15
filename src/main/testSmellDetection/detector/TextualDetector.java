package main.testSmellDetection.detector;

import it.unisa.testSmellDiffusion.beans.ClassBean;
import it.unisa.testSmellDiffusion.beans.InstanceVariableBean;
import it.unisa.testSmellDiffusion.beans.MethodBean;
import it.unisa.testSmellDiffusion.computation.TestSmellTextualDetector;
import it.unisa.testSmellDiffusion.testSmellInfo.eagerTest.EagerTestInfo;
import it.unisa.testSmellDiffusion.testSmellInfo.eagerTest.MethodWithEagerTest;
import it.unisa.testSmellDiffusion.testSmellInfo.generalFixture.GeneralFixtureInfo;
import it.unisa.testSmellDiffusion.testSmellInfo.generalFixture.MethodWithGeneralFixture;
import it.unisa.testSmellDiffusion.testSmellInfo.lackOfCohesion.LackOfCohesionInfo;
import it.unisa.testSmellDiffusion.utility.TestSmellUtilities;
import main.testSmellDetection.IDetector;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Vector;

public class TextualDetector implements IDetector {
    private TestSmellTextualDetector tsm;

    /**
     * Costruttore usato per instanziare un detector usabile ovunque
     */
    public TextualDetector(){
        tsm = new TestSmellTextualDetector();
    }


    public ArrayList<ClassBean> getAllTestClassesInTheProject(@NotNull String pFolderPath){
        System.out.println("\nDETECTOR: sono il TextualDetector, inizio a lavorare:\nEcco la path del progetto attivo: "+pFolderPath);
        ArrayList<ClassBean> myTestClasses = TestSmellUtilities.getAllTestClasses(pFolderPath);
        System.out.println("\nEcco le classi trovate:");
        for (ClassBean cb : myTestClasses){
            System.out.println("\n"+cb.toString());
        }
        return myTestClasses;
    }


    /**
     * Verifica la presenza di classi affette da GeneralFixture all'interno delle classi di test passate
     * @param testClasses le classi di test da analizzare
     * @return un array con le informazioni sulle classi affette da GeneralFixture
     */
    public ArrayList<GeneralFixtureInfo> executeDetectionForGeneralFixture(@NotNull ArrayList<ClassBean> testClasses){
        //Parte relativa a GeneralFixture
        System.out.println("\nDETECTOR: inizio a cercare per GENERAL FIXTURE: ");
        ArrayList<GeneralFixtureInfo> classesWithGeneralFixture = tsm.checkGeneralFixture(testClasses);

        //Parte relativa alla stampa dei dati ottenuti
        for(GeneralFixtureInfo gfi : classesWithGeneralFixture){
            String className = "\nNOME CLASSE: "+gfi.getTestClass().getName();

            for(MethodWithGeneralFixture method : gfi.getMethodsThatCauseGeneralFixture()){
                String methodName = "\nNome Metodo: "+method.getMethod().getName();

                for(InstanceVariableBean instance : method.getListOfInstances()){
                    methodName = new StringBuilder()
                            .append(methodName)
                            .append("\n   "+instance.getName())
                            .toString();
                }
                className = new StringBuilder()
                        .append(className)
                        .append(methodName)
                        .toString();
            }
            System.out.println(className);
        }
        return classesWithGeneralFixture;
    }

    /**
     * Verifica la presenza di classi affette da EagerTest all'interno delle classi di test passate
     * @param testClasses le classi di test da analizzare
     * @param allClassesInTheProject tutte le classi presente nel progetto. Necessarie per trovare la production class
     * @return un array con le informazioni sulle classi affette da EagerTest
     */
    public ArrayList<EagerTestInfo> executeDetectionForEagerTest(@NotNull ArrayList<ClassBean> testClasses, @NotNull Vector<ClassBean> allClassesInTheProject){
        //Parte relativa a EagerTest
        System.out.println("\nDETECTOR: inizio a cercare per EagerTest: ");
        ArrayList<EagerTestInfo> classesWithEagerTest = tsm.checkEagerTest(testClasses, allClassesInTheProject);

        for(EagerTestInfo eti : classesWithEagerTest){
            String className = "\nNOME CLASSE: "+eti.getTestClass().getName();
            String productionClassName = "\nNOME PRODUCTION CLASS: "+eti.getProductionClass().getName();

            for(MethodWithEagerTest method : eti.getMethodsThatCauseEagerTest()){
                String methodName = "\nNome Metodo: "+method.getMethod().getName()+"\n   Lista metodi chiamati:";

                for(MethodBean mb : method.getListOfMethodsCalled()){
                    methodName = new StringBuilder()
                            .append(methodName)
                            .append("\n   "+mb.getName())
                            .toString();
                }
                className = new StringBuilder()
                        .append(className)
                        .append(productionClassName)
                        .append(methodName)
                        .toString();
            }
            System.out.println(className);
        }

        return classesWithEagerTest;
    }


    //Metodi dell'interfaccia usati in virtù dello Strategy Desgin Pattern

    /**
     * Metodo per ottenere le informazioni riguardanti gli Smells GeneralFixture presenti in un dato progetto
     * @param pFolderPath la folder del progetto da analizzare
     * @return una lista delle informazioni sugli smells
     */
    @Override
    public ArrayList<GeneralFixtureInfo> executeDetectionForGeneralFixture(@NotNull String pFolderPath) {
        return executeDetectionForGeneralFixture(getAllTestClassesInTheProject(pFolderPath));
    }

    /**
     * Metodo per ottenere le informazioni riguardanti gli Smells EagerTest presenti in un dato progetto
     * @param pFolderPath la folder del progetto da analizzare
     * @return una lista delle informazioni sugli smells
     */
    @Override
    public ArrayList<EagerTestInfo> executeDetectionForEagerTest(@NotNull String pFolderPath) {
        return executeDetectionForEagerTest(getAllTestClassesInTheProject(pFolderPath), TestSmellUtilities.getAllClasses(pFolderPath));
    }

    /**
     * Metodo per ottenere le informazioni riguardanti gli Smells LackOfCohesion presenti in un dato progetto
     * @param pFolderPath la folder del progetto da analizzare
     * @return la lista delle informazioni sugli smells
     */
    @Override
    public ArrayList<LackOfCohesionInfo> executeDetectionForLackOfCohesion(@NotNull String pFolderPath){
        //Parte relativa a LackOfCohesion
        System.out.println("\nDETECTOR: inizio a cercare per LackOfCohesion: ");
        ArrayList<LackOfCohesionInfo> classesWithLackOfCohesion = tsm.checkLackOfCohesion(getAllTestClassesInTheProject(pFolderPath), pFolderPath);

        return classesWithLackOfCohesion;
    }


}
