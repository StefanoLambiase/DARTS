package action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import testSmellDetection.detector.IDetector;
import testSmellDetection.detector.TestSmellTextualDetector;
import testSmellDetection.testSmellInfo.eagerTest.EagerTestInfo;
import testSmellDetection.testSmellInfo.generalFixture.GeneralFixtureInfo;
import testSmellDetection.testSmellInfo.hardCodedTestData.HardCodedTestDataInfo;
import testSmellDetection.testSmellInfo.lackOfCohesion.LackOfCohesionInfo;
import testSmellDetection.testSmellInfo.testCodeDuplication.TestCodeDuplicationInfo;
import testSmellDetection.testSmellInfo.mysteryGuest.MysteryGuestInfo;
import windowCommitConstruction.CommitWindowFactory;
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
        ArrayList<HardCodedTestDataInfo> hardCodedTestDataInfos = detector.executeDetectionForHardCodedTestData();
        ArrayList<MysteryGuestInfo> mysteryGuestInfos = detector.executeDetectionForMysteryGuest();
        ArrayList<TestCodeDuplicationInfo> testCodeDuplicationInfos = detector.executeDetectionForTestCodeDuplication();

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
        for(HardCodedTestDataInfo info : hardCodedTestDataInfos){
            System.out.println("\n   HARD CODED TEST DATA: " + info.toString());
        }

        for(MysteryGuestInfo info : mysteryGuestInfos){
            System.out.println("\n   MYSTERY GUEST: " + info.toString());
        }

        for(TestCodeDuplicationInfo info : testCodeDuplicationInfos ){
            System.out.println("\n   TEST CODE DUPLICATION: " + info.toString());
        }

        if(generalFixtureInfos.isEmpty() && eagerTestInfos.isEmpty() && lackOfCohesionInfos.isEmpty() && hardCodedTestDataInfos.isEmpty() && mysteryGuestInfos.isEmpty() && testCodeDuplicationInfos.isEmpty()){
            System.out.println("\n Non si è trovato alcuno Smell");
        } else {
            //TestSmellWindowFactory.createWindow(true, false, anActionEvent.getProject(), generalFixtureInfos, eagerTestInfos, lackOfCohesionInfos);
            CommitWindowFactory.createWindow(true, false, anActionEvent.getProject(), generalFixtureInfos, eagerTestInfos, lackOfCohesionInfos, hardCodedTestDataInfos, mysteryGuestInfos, testCodeDuplicationInfos);
        }
    }

}
