package action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;
import stats.Session;
import stats.Stats;
import testSmellDetection.detector.IDetector;
import testSmellDetection.detector.TestSmellStructuralDetector;
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
public class StructuralDetectionAction extends AnAction {

    private Stats stats;
    private Session lastSession;


    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {

        this.stats = Stats.getInstance();
        this.stats.addSession(new Session());
        this.lastSession = stats.getLastSession();

        this.lastSession.setProjectName(anActionEvent.getProject().getName());
        this.stats.incrementNOfExecutionStructural();

        this.lastSession.setKind("Structural");

        long startTime = System.currentTimeMillis();
        this.lastSession.setStartTime(startTime);

        IDetector detector = new TestSmellStructuralDetector(anActionEvent.getProject());

        this.lastSession.setnOfTotalClasses(detector.getClassBeansNumber());
        this.lastSession.setnOfTotalMethod(detector.getMethodBeansNumber());

        ArrayList<GeneralFixtureInfo> generalFixtureInfos = detector.executeDetectionForGeneralFixture();
        this.lastSession.setNOfGF(generalFixtureInfos.size());

        ArrayList<EagerTestInfo> eagerTestInfos = detector.executeDetectionForEagerTest();
        this.lastSession.setNOfET(eagerTestInfos.size());

        ArrayList<LackOfCohesionInfo> lackOfCohesionInfos = detector.executeDetectionForLackOfCohesion();
        this.lastSession.setNOfLOC(lackOfCohesionInfos.size());

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

        long endTime = System.currentTimeMillis();
        this.lastSession.setEndTime(endTime);

        //ToDo: Contare la densità degli smell rispetto al numero di classi che ci sono nel progetto?
        //System.out.println("Densità General Fixture: "+ lastSession.getNOfGF() / lastSession.getnOfTotalClasses() + "\n");
        //System.out.println("Densità Eager Test: " + lastSession.getNOfET() / lastSession.getnOfTotalMethod() + "\n");
        //System.out.println("Densità Lack Of Cohesion: " + lastSession.getNOfLOC() / lastSession.getnOfTotalClasses() + "\n");

        if(generalFixtureInfos.isEmpty() && eagerTestInfos.isEmpty() && lackOfCohesionInfos.isEmpty()){
            System.out.println("\nNon si è trovato alcuno Smell");
        } else {
            //TestSmellWindowFactory.createWindow(false, true, anActionEvent.getProject(), generalFixtureInfos, eagerTestInfos, lackOfCohesionInfos);
            CommitWindowFactory.createWindow(false, true, anActionEvent.getProject(), generalFixtureInfos, eagerTestInfos, lackOfCohesionInfos);
        }

        String PATH = Paths.get(anActionEvent.getProject().getBasePath()).toAbsolutePath().normalize() + "/stats.json";

        this.stats.setFILE_PATH(PATH);
    }

}
