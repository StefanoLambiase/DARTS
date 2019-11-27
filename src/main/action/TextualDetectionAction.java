package main.action;


import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import it.unisa.testSmellDiffusion.testSmellInfo.eagerTest.EagerTestInfo;
import it.unisa.testSmellDiffusion.testSmellInfo.generalFixture.GeneralFixtureInfo;
import it.unisa.testSmellDiffusion.testSmellInfo.lackOfCohesion.LackOfCohesionInfo;
import main.psi.ConverterUtils;
import main.testSmellDetection.IDetector;
import main.testSmellDetection.detector.TextualDetector;
import main.toolWindowConstruction.TestSmellWindowFactory;
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
    }

    public void usePSI(Project myProject){
        ArrayList<PsiClass> classes = ConverterUtils.getClassesFromPackages(myProject);
    }

}
