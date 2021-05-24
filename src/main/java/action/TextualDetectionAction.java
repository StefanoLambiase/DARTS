package action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
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
        this.stats.incrementNOfExecutionStructural();
        this.lastSession.setKind("Textual");

        long startTime = System.currentTimeMillis();
        this.lastSession.setStartTime(startTime);

        IDetector detector = new TestSmellTextualDetector(anActionEvent.getProject());

        ArrayList<GeneralFixtureInfo> generalFixtureInfos = detector.executeDetectionForGeneralFixture();
        this.lastSession.setNOfGF(generalFixtureInfos.size());

        ArrayList<EagerTestInfo> eagerTestInfos = detector.executeDetectionForEagerTest();
        this.lastSession.setNOfET(eagerTestInfos.size());

        ArrayList<LackOfCohesionInfo> lackOfCohesionInfos = detector.executeDetectionForLackOfCohesion();
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

        long endTime = System.currentTimeMillis();
        this.lastSession.setEndTime(endTime);

        if(generalFixtureInfos.isEmpty() && eagerTestInfos.isEmpty() && lackOfCohesionInfos.isEmpty()){
            System.out.println("\n Non si è trovato alcuno Smell");
        } else {
            //TestSmellWindowFactory.createWindow(true, false, anActionEvent.getProject(), generalFixtureInfos, eagerTestInfos, lackOfCohesionInfos);
            CommitWindowFactory.createWindow(true, false, anActionEvent.getProject(), generalFixtureInfos, eagerTestInfos, lackOfCohesionInfos);
        }

        String PATH = Paths.get(anActionEvent.getProject().getBasePath()).toAbsolutePath().normalize() + "/stats.json";

        if (StatsSerializator.serialize(Stats.getInstance(), PATH)) {
            System.out.println("File salvato in: " + PATH);
        }
    }

}
