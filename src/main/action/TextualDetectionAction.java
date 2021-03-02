package main.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import main.testSmellDetection.detector.IDetector;
import main.testSmellDetection.detector.TestSmellTextualDetector;
import main.testSmellDetection.testSmellInfo.eagerTest.EagerTestInfo;
import main.testSmellDetection.testSmellInfo.generalFixture.GeneralFixtureInfo;
import main.testSmellDetection.testSmellInfo.lackOfCohesion.LackOfCohesionInfo;
import main.windowCommitConstruction.CommitWindowFactory;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Questa classe descrive la action per eseguire una analisi Strutturale sul progetto attualmente attivo
 */
public class TextualDetectionAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        IDetector detector = new TestSmellTextualDetector(anActionEvent.getProject());
        ArrayList<GeneralFixtureInfo> generalFixtureInfos = detector.executeDetectionForGeneralFixture();
        ArrayList<EagerTestInfo> eagerTestInfos = detector.executeDetectionForEagerTest();
        ArrayList<LackOfCohesionInfo> lackOfCohesionInfos = detector.executeDetectionForLackOfCohesion();

        System.out.println("\n\n ########################### ACTION - DETECTOR TESTUALE: risultato dell'analisi. ###########################\n\n");
        for(GeneralFixtureInfo info : generalFixtureInfos){
            System.out.println("\n   GENERAL FIXTURE: " + info.toString());
        }
        for(EagerTestInfo info : eagerTestInfos){
            System.out.println("\n   EAGER TEST: " + info.toString());
        }
        for(LackOfCohesionInfo info : lackOfCohesionInfos){
            System.out.println("\n   LACK OF COHESION: " + info.toString());
        }

        if(generalFixtureInfos.isEmpty() && eagerTestInfos.isEmpty() && lackOfCohesionInfos.isEmpty()){
            System.out.println("\n Non si è trovato alcuno Smell");
        } else {
            //TestSmellWindowFactory.createWindow(true, false, anActionEvent.getProject(), generalFixtureInfos, eagerTestInfos, lackOfCohesionInfos);
            CommitWindowFactory.createWindow(true, false, anActionEvent.getProject(), generalFixtureInfos, eagerTestInfos, lackOfCohesionInfos);
        }
    }

}
