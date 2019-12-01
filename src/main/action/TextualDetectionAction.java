package main.action;


import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import main.testSmellDetection.bean.PsiClassBean;
import main.testSmellDetection.textualRules.EagerTestTextual;
import main.testSmellDetection.textualRules.GeneralFixtureTextual;
import main.testSmellDetection.textualRules.LackOfCohesionOfTestSmellTextual;
import main.utility.ConverterUtilities;
import main.testSmellDetection.detector.IDetector;
import main.testSmellDetection.detector.TextualDetector;
import main.utility.TestSmellUtilities;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Questa classe descrive la action per eseguire una analisi Strutturale sul progetto attualmente attivo
 */
public class TextualDetectionAction extends AnAction {

    /* TOOL WINDOW
    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        //Mi prendo la folder del progetto attivo
        String pFolderPath = anActionEvent.getProject().getBasePath();
        IDetector detector = new TextualDetector(pFolderPath);

        //Eseguo l'analisi
        if(pFolderPath != null){
            ArrayList<GeneralFixtureInfo> listGFI = detector.executeDetectionForGeneralFixture();
            ArrayList<EagerTestInfo> listETI = detector.executeDetectionForEagerTest();
            ArrayList<LackOfCohesionInfo> listLOCI = detector.executeDetectionForLackOfCohesion();

            //Creo la ToolWindow
            if(listGFI.isEmpty() && listETI.isEmpty()){
                System.out.println("\nNon si è trovato alcuno Smell");
            } else {
                new TestSmellToolWindowFactory().registerToolWindow(true, false, anActionEvent.getProject(), listGFI, listETI, listLOCI);
            }
        } else {
            System.out.println("\nVi è stato un errore con l'ottenumento della folder del progetto attivo");
        }
    }
     */


    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        //Mi prendo la folder del progetto attivo
        String pFolderPath = anActionEvent.getProject().getBasePath();
        IDetector detector = new TextualDetector(pFolderPath);

        /* ANALISI NUOVA. COMMENTARE PRIMA DI USARE L'ALTRA. */
        ArrayList<PsiClassBean> classes = usePSI(anActionEvent.getProject());
        ArrayList<PsiClassBean> testClasses = TestSmellUtilities.getAllTestClasses(classes);

        for(PsiClassBean classBean : testClasses){
            PsiClassBean productionClass = TestSmellUtilities.findProductionClass(classBean, classes);
            System.out.println("\nANALISI CLASSE: " + classBean.getPsiClass().getName());
            System.out.println("\n   Production Class: " + productionClass.getPsiClass().getName());
            // General Fixture
            if(GeneralFixtureTextual.isGeneralFixture(classBean)){
                System.out.println("\n   La classe è affetta da General Fixture: " + classBean.getPsiClass().getName());
            }
            // Eager Test
            if(EagerTestTextual.isEagerTest(classBean, productionClass)){
                System.out.println("\n   La classe è affetta da Eager Test: " + classBean.getPsiClass().getName());
            }
            // Lack of Cohesion
            if(LackOfCohesionOfTestSmellTextual.isLackOfCohesionTestMethods(classBean)){
                System.out.println("\n   La classe è affetta da Lack of Cohesion: " + classBean.getPsiClass().getName());
            }
        }

        /* ANALISI VECCHIA, DECOMMENTARE SE SI VUOLE USARE. */
        /*
        //Eseguo l'analisi
        if(pFolderPath != null){
            ArrayList<GeneralFixtureInfo> listGFI = detector.executeDetectionForGeneralFixture();
            ArrayList<EagerTestInfo> listETI = detector.executeDetectionForEagerTest();
            ArrayList<LackOfCohesionInfo> listLOCI = detector.executeDetectionForLackOfCohesion();

            //Creo la window
            if(listGFI.isEmpty() && listETI.isEmpty()){
                System.out.println("\nNon si è trovato alcuno Smell");
            } else {
                TestSmellWindowFactory.createWindow(true, false, anActionEvent.getProject(), listGFI, listETI, listLOCI);
            }
        } else {
            System.out.println("\nVi è stato un errore con l'ottenumento della folder del progetto attivo");
        }

         */
    }


    public ArrayList<PsiClassBean> usePSI(Project myProject){
        ArrayList<PsiClassBean> classes = ConverterUtilities.getClassesFromPackages(myProject);

        System.out.println("\nSONO IL DETECTOR TESTUALE: ECCO LA LISTA DELLE CLASSI DI TEST:");
        ArrayList<PsiClassBean> testClasses = TestSmellUtilities.getAllTestClasses(classes);
        if(testClasses.size() > 0){
            for(PsiClassBean psiClassBean : testClasses){
                System.out.println("\n" + psiClassBean.getPsiClass().getName());
            }
        }
        System.out.println("\nSONO IL DETECTOR TESTUALE: ECCO LA LISTA DELLE PRODUCTION CLASSES:");
        ArrayList<PsiClassBean> productionClasses = TestSmellUtilities.getAllProductionClasses(classes, testClasses);
        if(productionClasses.size() > 0){
            for(PsiClassBean psiClassBean : productionClasses){
                System.out.println("\n" + psiClassBean.getPsiClass().getName());
            }
        }
        return classes;
    }

}
