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
import main.testSmellDetection.detector.StructuralDetector;
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
                detector = new TextualDetector();

                ArrayList<GeneralFixtureInfo> listGFI = detector.executeDetectionForGeneralFixture(myPanel.getProject().getBasePath());
                ArrayList<EagerTestInfo> listETI = detector.executeDetectionForEagerTest(myPanel.getProject().getBasePath());
                ArrayList<LackOfCohesionInfo> listLOCI = detector.executeDetectionForLackOfCohesion(myPanel.getProject().getBasePath());

                //Creo la ToolWindow
                if(myTestClasses.isEmpty()){
                    System.out.println("\nNon si e' committata alcuna classe di test");
                } else {
                    new TestSmellWindowFactory().registerToolWindow(true, false, myPanel.getProject(), listGFI, listETI, listLOCI);
                }

                //Eseguo l'analisi Strutturale
                detector = new StructuralDetector();

                listGFI = detector.executeDetectionForGeneralFixture(myPanel.getProject().getBasePath());
                listETI = detector.executeDetectionForEagerTest(myPanel.getProject().getBasePath());
                listLOCI = detector.executeDetectionForLackOfCohesion(myPanel.getProject().getBasePath());

                //Creo la ToolWindow
                if(myTestClasses.isEmpty()){
                    System.out.println("\nNon si e' committata alcuna classe di test");
                } else {
                    new TestSmellWindowFactory().registerToolWindow(false, true, myPanel.getProject(), listGFI, listETI, listLOCI);
                }
                //Chiamata finale per completare il commit
                return super.beforeCheckin();
            }
        };
        return checkinHandler;
    }


}
