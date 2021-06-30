package extension;

import com.intellij.openapi.vcs.CheckinProjectPanel;
import com.intellij.openapi.vcs.changes.CommitContext;
import com.intellij.openapi.vcs.checkin.CheckinHandler;
import com.intellij.openapi.vcs.checkin.CheckinHandlerFactory;
import com.intellij.openapi.vfs.VirtualFile;
import testSmellDetection.detector.IDetector;
import testSmellDetection.detector.TestSmellStructuralDetector;
import testSmellDetection.detector.TestSmellTextualDetector;
import testSmellDetection.testSmellInfo.eagerTest.EagerTestInfo;
import testSmellDetection.testSmellInfo.generalFixture.GeneralFixtureInfo;
import testSmellDetection.testSmellInfo.hardCodedTestData.HardCodedTestDataInfo;
import testSmellDetection.testSmellInfo.lackOfCohesion.LackOfCohesionInfo;
import windowCommitConstruction.general.WarningWindow;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.ArrayList;


public class CommitFactory  extends CheckinHandlerFactory{
    //Oggetto usato per ottenere i file salvati durante la fase di commit
    private CheckinProjectPanel myPanel;

    private ArrayList<GeneralFixtureInfo> generalFixtureInfos;
    private ArrayList<EagerTestInfo> eagerTestInfos;
    private ArrayList<LackOfCohesionInfo> lackOfCohesionInfos;
    private ArrayList<HardCodedTestDataInfo> hardCodedTestDataInfos;

    private ArrayList<GeneralFixtureInfo> generalFixtureInfos2;
    private ArrayList<EagerTestInfo> eagerTestInfos2;
    private ArrayList<LackOfCohesionInfo> lackOfCohesionInfos2;
    private ArrayList<HardCodedTestDataInfo> hardCodedTestDataInfos2;

    @NotNull
    @Override
    public CheckinHandler createHandler(@NotNull CheckinProjectPanel panel, @NotNull CommitContext commitContext) {
        myPanel = panel;

        final CheckinHandler checkinHandler = new CheckinHandler() {

            @Override
            public ReturnResult beforeCheckin() {
                generalFixtureInfos2 = new ArrayList<>();
                eagerTestInfos2 = new ArrayList<>();
                lackOfCohesionInfos2 = new ArrayList<>();
                hardCodedTestDataInfos2 = new ArrayList<>();

                //Stampa di inizio
                System.out.println("\n\n############# COMMIT FACTORY ##################\n\n");
                System.out.println("\n Inizio la fase di detection come Commit Factory");

                //Questa parte riguarda l'analisi degli Smells

                /* ANALISI TESTUALE */
                IDetector detector = new TestSmellTextualDetector(myPanel.getProject());


                IDetector detector2 = new TestSmellStructuralDetector(myPanel.getProject());

                generalFixtureInfos = detector.executeDetectionForGeneralFixture();
                eagerTestInfos = detector.executeDetectionForEagerTest();
                hardCodedTestDataInfos = detector.executeDetectionForHardCodedTestData();
                //lackOfCohesionInfos = detector.executeDetectionForLackOfCohesion();
                lackOfCohesionInfos = detector2.executeDetectionForLackOfCohesion();

                /* PARTE USATA PER FARE L'ANALISI SOLO DELLE CLASSI DI TEST CHE VENGONO COMMITTATE */
                ArrayList<VirtualFile> listOfFiles = (ArrayList<VirtualFile>) panel.getVirtualFiles();
                ArrayList<String> filesNames = new ArrayList<>();
                for(VirtualFile virtualFile : listOfFiles){
                    if(virtualFile.getExtension() != null && virtualFile.getExtension().equals("java") && (virtualFile.getName().contains("Test") || virtualFile.getName().contains("test"))){
                        filesNames.add(virtualFile.getNameWithoutExtension());
                    }
                }

                boolean find = false;
                for(String s : filesNames){
                    for(GeneralFixtureInfo gfi : generalFixtureInfos){
                        if(gfi.getClassWithGeneralFixture().getName().equals(s) && !find){
                            generalFixtureInfos2.add(gfi);
                            find = true;
                        }
                    }
                    find = false;
                }
                for(String s : filesNames){
                    for(EagerTestInfo eti : eagerTestInfos){
                        if(eti.getClassWithEagerTest().getName().equals(s) && !find){
                            eagerTestInfos2.add(eti);
                            find = true;
                        }
                    }
                    find = false;
                }
                for(String s : filesNames){
                    for(LackOfCohesionInfo loci : lackOfCohesionInfos){
                        if(loci.getClassWithSmell().getName().equals(s) && !find){
                            lackOfCohesionInfos2.add(loci);
                            find = true;
                        }
                    }
                    find = false;
                }
                for(String s : filesNames){
                    for(HardCodedTestDataInfo hctdi : hardCodedTestDataInfos){
                        if(hctdi.getClassWithSmell().getName().equals(s) && !find){
                            hardCodedTestDataInfos2.add(hctdi);
                            find = true;
                        }
                    }
                    find = false;
                }
                /* FINE PARTE PER ANALISI DELLE CLASSI CHE VENGONO COMMITTATE */

                return super.beforeCheckin();
            }

            @Override
            public void checkinSuccessful() {

                // Creo la window
                if(generalFixtureInfos2.isEmpty() && eagerTestInfos2.isEmpty() && lackOfCohesionInfos2.isEmpty()){
                    System.out.println("\n   Non si è trovato alcuno Smell");
                } else {
                    /* La prima linea esegue l'analisi su tutte le classi del sistema, la seconda solo sulle classi che vengono committate */
                    //WarningWindow warningWindow = new WarningWindow(myPanel.getProject(), generalFixtureInfos, eagerTestInfos, lackOfCohesionInfos);
                    WarningWindow warningWindow = new WarningWindow(myPanel.getProject(), generalFixtureInfos2, eagerTestInfos2, lackOfCohesionInfos2, hardCodedTestDataInfos2);
                    warningWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                    warningWindow.setLocationRelativeTo(null);
                    warningWindow.pack();
                    warningWindow.setVisible(true);
                }
            }
        };


        return checkinHandler;
    }

}
