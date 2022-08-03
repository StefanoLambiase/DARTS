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
import stats.Session;
import stats.Stats;
import testSmellDetection.detector.IDetector;
import testSmellDetection.detector.TestSmellTextualDetector;
import testSmellDetection.testSmellInfo.eagerTest.EagerTestInfo;
import testSmellDetection.testSmellInfo.generalFixture.GeneralFixtureInfo;
import testSmellDetection.testSmellInfo.lackOfCohesion.LackOfCohesionInfo;
import utility.StatsSerializator;
import windowCommitConstruction.CommitWindowFactory;

import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Questa classe descrive la action per eseguire una analisi Strutturale sul progetto attualmente attivo
 */
public class TextualDetectionAction extends AnAction {

    private Stats stats;
    private Session lastSession;

    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {

        this.stats = Stats.getInstance();
        this.stats.addSession(new Session());
        this.lastSession = stats.getLastSession();

        this.lastSession.setProjectName(anActionEvent.getProject().getName());
        this.stats.incrementNOfExecutionTextual();
        this.lastSession.setKind("Textual");

        long startTime = System.currentTimeMillis();
        this.lastSession.setStartTime(startTime);

        IDetector detector = new TestSmellTextualDetector(anActionEvent.getProject());

        this.lastSession.setnOfTotalClasses(detector.getClassBeansNumber());
        this.lastSession.setnOfTotalMethod(detector.getMethodBeansNumber());

        ArrayList<GeneralFixtureInfo> generalFixtureInfos = detector.executeDetectionForGeneralFixture();
        this.lastSession.setNOfGF(generalFixtureInfos.size());

        ArrayList<EagerTestInfo> eagerTestInfos = detector.executeDetectionForEagerTest();
        this.lastSession.setNOfET(eagerTestInfos.size());

        ArrayList<LackOfCohesionInfo> lackOfCohesionInfos = detector.executeDetectionForLackOfCohesion();
        ArrayList<HardCodedTestDataInfo> hardCodedTestDataInfos = detector.executeDetectionForHardCodedTestData();
        ArrayList<MysteryGuestInfo> mysteryGuestInfos = detector.executeDetectionForMysteryGuest();
        ArrayList<TestCodeDuplicationInfo> testCodeDuplicationInfos = detector.executeDetectionForTestCodeDuplication();

        this.lastSession.setNOfLOC(lackOfCohesionInfos.size());

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

        long endTime = System.currentTimeMillis();
        this.lastSession.setEndTime(endTime);

        if(generalFixtureInfos.isEmpty() && eagerTestInfos.isEmpty() && lackOfCohesionInfos.isEmpty() && hardCodedTestDataInfos.isEmpty() && mysteryGuestInfos.isEmpty() && testCodeDuplicationInfos.isEmpty()){
            System.out.println("\n Non si è trovato alcuno Smell");
        } else {
            //TestSmellWindowFactory.createWindow(true, false, anActionEvent.getProject(), generalFixtureInfos, eagerTestInfos, lackOfCohesionInfos);
            CommitWindowFactory.createWindow(true, false, anActionEvent.getProject(), generalFixtureInfos, eagerTestInfos, lackOfCohesionInfos, hardCodedTestDataInfos, mysteryGuestInfos, testCodeDuplicationInfos);
        }

        String PATH = Paths.get(anActionEvent.getProject().getBasePath()).toAbsolutePath().normalize() + "/stats.json";

        this.stats.setFILE_PATH(PATH);
    }

}
