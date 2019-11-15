package main.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Editor;
import it.unisa.testSmellDiffusion.testSmellInfo.eagerTest.EagerTestInfo;
import it.unisa.testSmellDiffusion.testSmellInfo.generalFixture.GeneralFixtureInfo;
import it.unisa.testSmellDiffusion.testSmellInfo.lackOfCohesion.LackOfCohesionInfo;
import main.testSmellDetection.IDetector;
import main.testSmellDetection.detector.StructuralDetector;
import main.toolWindowConstruction.TestSmellWindowFactory;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Questa classe descrive la action per eseguire una analisi Strutturale sul progetto attualmente attivo
 */
public class StructuralDetectionAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        IDetector detector = new StructuralDetector();
        Editor editor = (Editor) anActionEvent.getDataContext().getData("editor");
        //Eseguo l'analisi
        ArrayList<GeneralFixtureInfo> listGFI = detector.executeDetectionForGeneralFixture(anActionEvent.getProject().getBasePath());
        ArrayList<EagerTestInfo> listETI = detector.executeDetectionForEagerTest(anActionEvent.getProject().getBasePath());
        ArrayList<LackOfCohesionInfo> listLOCI = detector.executeDetectionForLackOfCohesion(anActionEvent.getProject().getBasePath());

        //Creo la ToolWindow
        if(listGFI.isEmpty() && listETI.isEmpty()){
            System.out.println("\nNon si è trovato alcuno Smell");
        } else {
            new TestSmellWindowFactory().registerToolWindow(false, true, anActionEvent.getProject(), listGFI, listETI, listLOCI);
        }
    }


}
