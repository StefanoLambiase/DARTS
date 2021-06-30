package action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import testSmellDetection.detector.IDetector;
import testSmellDetection.detector.TestSmellStructuralDetector;
import testSmellDetection.testSmellInfo.eagerTest.EagerTestInfo;
import testSmellDetection.testSmellInfo.generalFixture.GeneralFixtureInfo;
import testSmellDetection.testSmellInfo.hardCodedTestData.HardCodedTestDataInfo;
import testSmellDetection.testSmellInfo.lackOfCohesion.LackOfCohesionInfo;
import oldWindowConstruction.TestSmellWindowFactory;
import testSmellDetection.testSmellInfo.testCodeDuplication.TestCodeDuplicationInfo;
import testSmellDetection.testSmellInfo.mysteryGuest.MysteryGuestInfo;
import windowCommitConstruction.CommitWindowFactory;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Questa classe descrive la action per eseguire una analisi Strutturale sul progetto attualmente attivo
 */
public class StructuralDetectionAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        IDetector detector = new TestSmellStructuralDetector(anActionEvent.getProject());
        ArrayList<GeneralFixtureInfo> generalFixtureInfos = detector.executeDetectionForGeneralFixture();
        ArrayList<EagerTestInfo> eagerTestInfos = detector.executeDetectionForEagerTest();
        ArrayList<LackOfCohesionInfo> lackOfCohesionInfos = detector.executeDetectionForLackOfCohesion();
        ArrayList<HardCodedTestDataInfo> hardCodedTestDataInfos = detector.executeDetectionForHardCodedTestData();
        ArrayList<MysteryGuestInfo> mysteryGuestInfos = detector.executeDetectionForMysteryGuest();
        ArrayList<TestCodeDuplicationInfo> testCodeDuplicationInfos = detector.executeDetectionForTestCodeDuplication();

        System.out.println("\nDETECTOR STRUTTURALE: risultato dell'analisi.");
        for(GeneralFixtureInfo info : generalFixtureInfos){
            System.out.println("\n   GENERAL FIXTURE: " + info.toString());
        }
        for(EagerTestInfo info : eagerTestInfos){
            System.out.println("\n   EAGER TEST: " + info.toString());
        }
        for(LackOfCohesionInfo info : lackOfCohesionInfos){
            System.out.println("\n   LACK OF COHESION: " + info.toString());
        }
        for(MysteryGuestInfo info : mysteryGuestInfos){
            System.out.println("\n   MYSTERY GUEST: " + info.toString());
        }

        if(generalFixtureInfos.isEmpty() && eagerTestInfos.isEmpty() && lackOfCohesionInfos.isEmpty()){
            System.out.println("\nNon si è trovato alcuno Smell");
        } else {
            //TestSmellWindowFactory.createWindow(false, true, anActionEvent.getProject(), generalFixtureInfos, eagerTestInfos, lackOfCohesionInfos);
            CommitWindowFactory.createWindow(false, true, anActionEvent.getProject(), generalFixtureInfos, eagerTestInfos, lackOfCohesionInfos, hardCodedTestDataInfos, mysteryGuestInfos, testCodeDuplicationInfos);
        }
    }

}
