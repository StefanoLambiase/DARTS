package main.extension;

import com.intellij.openapi.vcs.CheckinProjectPanel;
import com.intellij.openapi.vcs.changes.CommitContext;
import com.intellij.openapi.vcs.checkin.CheckinHandler;
import com.intellij.openapi.vcs.checkin.CheckinHandlerFactory;
import main.testSmellDetection.detector.IDetector;
import main.testSmellDetection.detector.TestSmellStructuralDetector;
import main.testSmellDetection.detector.TestSmellTextualDetector;
import main.testSmellDetection.testSmellInfo.eagerTest.EagerTestInfo;
import main.testSmellDetection.testSmellInfo.generalFixture.GeneralFixtureInfo;
import main.testSmellDetection.testSmellInfo.lackOfCohesion.LackOfCohesionInfo;
import main.windowCommitConstruction.CommitWindowFactory;
import main.windowConstruction.TestSmellWindowFactory;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class CommitFactory  extends CheckinHandlerFactory{

    //Oggetto usato per ottenere i file salvati durante la fase di commit
    private CheckinProjectPanel myPanel;

    @NotNull
    @Override
    public CheckinHandler createHandler(@NotNull CheckinProjectPanel panel, @NotNull CommitContext commitContext) {
        myPanel = panel;

        final CheckinHandler checkinHandler = new CheckinHandler() {
            @Override
            public ReturnResult beforeCheckin() {
                //Stampa di inizio
                System.out.println("\n\n############# COMMIT FACTORY ##################\n\n");
                System.out.println("\n Inizio la fase di detection come Commit Factory");

                //Questa parte riguarda l'analisi degli Smells
                // Analisi testuale
                IDetector detector = new TestSmellTextualDetector(myPanel.getProject());

                ArrayList<GeneralFixtureInfo> generalFixtureInfos = detector.executeDetectionForGeneralFixture();
                ArrayList<EagerTestInfo> eagerTestInfos = detector.executeDetectionForEagerTest();
                ArrayList<LackOfCohesionInfo> lackOfCohesionInfos = detector.executeDetectionForLackOfCohesion();

                // Creo la window
                if(generalFixtureInfos.isEmpty() && eagerTestInfos.isEmpty() && lackOfCohesionInfos.isEmpty()){
                    System.out.println("\nNon si è trovato alcuno Smell");
                } else {
                    //CommitWindowFactory.createWindow(true, false, myPanel.getProject(), generalFixtureInfos, eagerTestInfos, lackOfCohesionInfos);
                    CommitWindowFactory.createWindow(true, false, myPanel.getProject(), generalFixtureInfos, null, null);
                }

                // Analisi Strutturale
                /*
                detector = new TestSmellStructuralDetector(myPanel.getProject());
                generalFixtureInfos = detector.executeDetectionForGeneralFixture();
                eagerTestInfos = detector.executeDetectionForEagerTest();
                lackOfCohesionInfos = detector.executeDetectionForLackOfCohesion();

                // Creo la window
                if(generalFixtureInfos.isEmpty() && eagerTestInfos.isEmpty() && lackOfCohesionInfos.isEmpty()){
                    System.out.println("\nNon si è trovato alcuno Smell");
                } else {
                    TestSmellWindowFactory.createWindow(false, true, myPanel.getProject(), generalFixtureInfos, eagerTestInfos, lackOfCohesionInfos);
                }
                */

                return super.beforeCheckin();
            }
        };
        return checkinHandler;
    }

}
