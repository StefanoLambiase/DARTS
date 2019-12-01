package main.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import it.unisa.testSmellDiffusion.testSmellInfo.eagerTest.EagerTestInfo;
import it.unisa.testSmellDiffusion.testSmellInfo.generalFixture.GeneralFixtureInfo;
import it.unisa.testSmellDiffusion.testSmellInfo.lackOfCohesion.LackOfCohesionInfo;
import main.testSmellDetection.detector.IDetector;
import main.testSmellDetection.detector.TestSmellStructuralDetector;
import main.toolWindowConstruction.TestSmellWindowFactory;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Questa classe descrive la action per eseguire una analisi Strutturale sul progetto attualmente attivo
 */
public class StructuralDetectionAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        //Mi prendo la folder del progetto attivo
        String pFolderPath = anActionEvent.getProject().getBasePath();
        IDetector detector = new TestSmellStructuralDetector(pFolderPath);

        //Eseguo l'analisi
        if(pFolderPath != null){
            ArrayList<GeneralFixtureInfo> listGFI = detector.executeDetectionForGeneralFixture();
            ArrayList<EagerTestInfo> listETI = detector.executeDetectionForEagerTest();
            ArrayList<LackOfCohesionInfo> listLOCI = detector.executeDetectionForLackOfCohesion();

            //Creo la window
            if(listGFI.isEmpty() && listETI.isEmpty()){
                System.out.println("\nNon si è trovato alcuno Smell");
            } else {
                TestSmellWindowFactory.createWindow(false, true, anActionEvent.getProject(), listGFI, listETI, listLOCI);
            }
        } else {
            System.out.println("\nVi è stato un errore con l'ottenumento della folder del progetto attivo");
        }
    }

}
