package windowCommitConstruction;

import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.JBTabbedPane;
import testSmellDetection.testSmellInfo.eagerTest.EagerTestInfo;
import testSmellDetection.testSmellInfo.generalFixture.GeneralFixtureInfo;
import testSmellDetection.testSmellInfo.hardCodedTestData.HardCodedTestDataInfo;
import testSmellDetection.testSmellInfo.lackOfCohesion.LackOfCohesionInfo;
import testSmellDetection.testSmellInfo.testCodeDuplication.TestCodeDuplicationInfo;
import testSmellDetection.testSmellInfo.mysteryGuest.MysteryGuestInfo;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CommitWindowFactory {
    private static JPanel generalFixturePanel;
    private static JPanel eagerTestPanel;
    private static JPanel lackOfCohesionPanel;

    private static JPanel hardCodedTestDataPanel;
    private static JPanel mysteryGuestPanel;
    private static JPanel testCodeDuplicationPanel;

    public static void createWindow(Boolean textual, Boolean structural,
                                    Project project,
                                    ArrayList<GeneralFixtureInfo> listGFI,
                                    ArrayList<EagerTestInfo> listETI,
                                    ArrayList<LackOfCohesionInfo> listLOCI,
                                    ArrayList<HardCodedTestDataInfo> listHCTDI,
                                    ArrayList<MysteryGuestInfo> listMGI,
                                    ArrayList<TestCodeDuplicationInfo> listTCDI) {
        CommitPrincipalFrame principalFrame = null;
        //Controllo per vedere se la window esiste già.
        boolean frameExist = false;
        Frame[] frames = JFrame.getFrames();
        for(Frame frame : frames){
            if(frame.getName().equals("DARTSCommitWindow")){
                principalFrame = (CommitPrincipalFrame) frame;
                frameExist = true;
            }
        }
        if(!frameExist){
            principalFrame = new CommitPrincipalFrame();
            principalFrame.setName("DARTSCommitWindow");
            principalFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }

        JBTabbedPane detectionTp =  principalFrame.getDetectionTp();
        if(textual){
            principalFrame.removeTextualPanel();
            principalFrame.addTextualPanel(createPanel(project, listGFI, listETI, listLOCI, listHCTDI, listMGI, listTCDI));
        }
        if(structural){
            principalFrame.removeStructuralPanel();
            principalFrame.addStructuralPanel(createPanel(project, listGFI, listETI, listLOCI, listHCTDI, listMGI, listTCDI));
        }
        principalFrame.add(detectionTp);
        // Mostra la schermata al centro dello schermo
        principalFrame.setLocationRelativeTo(null);

        //Imposta la dimensione della finestra in modo che si adatti al suo contenuto
        principalFrame.pack();

        principalFrame.setVisible(true);
    }

    private static JBTabbedPane createPanel(Project project,
                                            ArrayList<GeneralFixtureInfo> listGFI,
                                            ArrayList<EagerTestInfo> listETI,
                                            ArrayList<LackOfCohesionInfo> listLOCI,
                                            ArrayList<HardCodedTestDataInfo> listHCTDI,
                                            ArrayList<MysteryGuestInfo> listMGI,
                                            ArrayList<TestCodeDuplicationInfo> listTCDI){
        // Controllo se ho trovato degli smells.
        if (listGFI != null) {
            generalFixturePanel = new GeneralFixtureCP(listGFI, project);
        }
        if (listETI != null) {
            eagerTestPanel = new EagerTestCP(listETI, project);
        }
        if (listLOCI != null){
            lackOfCohesionPanel = new LackOfCohesionCP(listLOCI, project);
        }if (listHCTDI != null){
            hardCodedTestDataPanel = new HardCodedTestDataCP(listHCTDI, project);
        }
        if (listMGI != null){
            mysteryGuestPanel = new MysteryGuestCP(listMGI, project);
        }
        if (listTCDI != null){
            testCodeDuplicationPanel = new TestCodeDuplicationCP(listTCDI, project);
        }

        //In questa parte costruisco le tab della window.
        JBTabbedPane tp = new JBTabbedPane();
        tp.setPreferredSize(new Dimension(1000, 500));
        if(listGFI != null){
            JBScrollPane scroll = new JBScrollPane(generalFixturePanel);
            tp.add("GeneralFixture", scroll);
        }
        if (listETI != null){
            JBScrollPane scroll = new JBScrollPane(eagerTestPanel);
            tp.add("EagerTest", scroll);
        }
        if (listLOCI != null) {
            JBScrollPane scroll = new JBScrollPane(lackOfCohesionPanel);
            tp.add("LackOfCohesion", scroll);
        }if (listHCTDI != null) {
            JBScrollPane scroll = new JBScrollPane(hardCodedTestDataPanel);
            tp.add("HardCodedTestData", scroll);
        }
        if (listMGI != null) {
            JBScrollPane scroll = new JBScrollPane(mysteryGuestPanel);
            tp.add("MysteryGuest", scroll);
        }
        if (listTCDI != null) {
            JBScrollPane scroll = new JBScrollPane(testCodeDuplicationPanel);
            tp.add("TestCodeDuplication", scroll);
        }
        return tp;
    }
}
