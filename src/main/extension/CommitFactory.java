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
                System.out.println("\n\nHello! Inizio la fase pre-commit");

                //Questa parte riguarda l'analisi degli Smells
                //Eseguo l'analisi strutturale
                IDetector detector = new TestSmellStructuralDetector(myPanel.getProject());
                ArrayList<GeneralFixtureInfo> generalFixtureInfos = detector.executeDetectionForGeneralFixture();
                ArrayList<EagerTestInfo> eagerTestInfos = detector.executeDetectionForEagerTest();
                ArrayList<LackOfCohesionInfo> lackOfCohesionInfos = detector.executeDetectionForLackOfCohesion();

                //Creo la window
                /*
                if(listGFI.isEmpty() && listETI.isEmpty()){
                    System.out.println("\nNon si è trovato alcuno Smell");
                } else {
                    TestSmellWindowFactory.createWindow(true, false, myPanel.getProject(), listGFI, listETI, listLOCI);
                }

                 */

                //Eseguo l'analisi Testuale
                detector = new TestSmellTextualDetector(myPanel.getProject());
                generalFixtureInfos = detector.executeDetectionForGeneralFixture();
                eagerTestInfos = detector.executeDetectionForEagerTest();
                lackOfCohesionInfos = detector.executeDetectionForLackOfCohesion();

                //Creo la window
                /*
                if(listGFI.isEmpty() && listETI.isEmpty()){
                    System.out.println("\nNon si è trovato alcuno Smell");
                } else {
                    TestSmellWindowFactory.createWindow(false, true, myPanel.getProject(), listGFI, listETI, listLOCI);
                }
                //Chiamata finale per completare il commit

                 */
                return super.beforeCheckin();
            }
        };
        return checkinHandler;
    }


}
