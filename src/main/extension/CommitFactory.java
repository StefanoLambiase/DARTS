package main.extension;

import com.intellij.openapi.vcs.CheckinProjectPanel;
import com.intellij.openapi.vcs.changes.CommitContext;
import com.intellij.openapi.vcs.checkin.CheckinHandler;
import com.intellij.openapi.vcs.checkin.CheckinHandlerFactory;
import it.unisa.testSmellDiffusion.beans.ClassBean;
import it.unisa.testSmellDiffusion.testSmellInfo.eagerTest.EagerTestInfo;
import it.unisa.testSmellDiffusion.testSmellInfo.generalFixture.GeneralFixtureInfo;
import it.unisa.testSmellDiffusion.testSmellInfo.lackOfCohesion.LackOfCohesionInfo;
import it.unisa.testSmellDiffusion.utility.TestSmellUtilities;
import main.testSmellDetection.IDetector;
import main.testSmellDetection.detector.TestSmellStructuralDetector;
import main.testSmellDetection.detector.TextualDetector;
import main.toolWindowConstruction.TestSmellWindowFactory;
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
                IDetector detector;

                //Mi salvo la lista di classi di test del progetto attivo
                ArrayList<ClassBean> myTestClasses = TestSmellUtilities.getAllTestClasses(myPanel.getProject().getBasePath());

                //Eseguo l'analisi Testuale
                detector = new TextualDetector(myPanel.getProject().getBasePath());

                ArrayList<GeneralFixtureInfo> listGFI = detector.executeDetectionForGeneralFixture();
                ArrayList<EagerTestInfo> listETI = detector.executeDetectionForEagerTest();
                ArrayList<LackOfCohesionInfo> listLOCI = detector.executeDetectionForLackOfCohesion();

                //Creo la window
                if(listGFI.isEmpty() && listETI.isEmpty()){
                    System.out.println("\nNon si è trovato alcuno Smell");
                } else {
                    TestSmellWindowFactory.createWindow(true, false, myPanel.getProject(), listGFI, listETI, listLOCI);
                }

                //Eseguo l'analisi Strutturale
                detector = new TestSmellStructuralDetector(myPanel.getProject().getBasePath());

                listGFI = detector.executeDetectionForGeneralFixture();
                listETI = detector.executeDetectionForEagerTest();
                listLOCI = detector.executeDetectionForLackOfCohesion();

                //Creo la window
                if(listGFI.isEmpty() && listETI.isEmpty()){
                    System.out.println("\nNon si è trovato alcuno Smell");
                } else {
                    TestSmellWindowFactory.createWindow(false, true, myPanel.getProject(), listGFI, listETI, listLOCI);
                }
                //Chiamata finale per completare il commit
                return super.beforeCheckin();
            }
        };
        return checkinHandler;
    }


}
