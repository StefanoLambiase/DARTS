package main.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import it.unisa.testSmellDiffusion.testSmellInfo.eagerTest.EagerTestInfo;
import it.unisa.testSmellDiffusion.testSmellInfo.generalFixture.GeneralFixtureInfo;
import it.unisa.testSmellDiffusion.testSmellInfo.lackOfCohesion.LackOfCohesionInfo;
import main.testSmellDetection.IDetector;
import main.testSmellDetection.detector.TextualDetector;
import main.toolWindowConstruction.TestSmellWindowFactory;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Questa classe descrive la action per eseguire una analisi Strutturale sul progetto attualmente attivo
 */
public class TextualDetectionAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        IDetector detector = new TextualDetector();

        //Mi prendo la folder del progetto attivo
        String pFolderPath = anActionEvent.getProject().getBasePath();

        //Eseguo l'analisi
        if(pFolderPath != null){
            ArrayList<GeneralFixtureInfo> listGFI = detector.executeDetectionForGeneralFixture(pFolderPath);
            ArrayList<EagerTestInfo> listETI = detector.executeDetectionForEagerTest(pFolderPath);
            ArrayList<LackOfCohesionInfo> listLOCI = detector.executeDetectionForLackOfCohesion(pFolderPath);

            //Creo la ToolWindow
            if(listGFI.isEmpty() && listETI.isEmpty()){
                System.out.println("\nNon si è trovato alcuno Smell");
            } else {
                new TestSmellWindowFactory().registerToolWindow(true, false, anActionEvent.getProject(), listGFI, listETI, listLOCI);
            }
        } else {
            System.out.println("\nVi è stato un errore con l'ottenumento della folder del progetto attivo");
        }
    }


}
