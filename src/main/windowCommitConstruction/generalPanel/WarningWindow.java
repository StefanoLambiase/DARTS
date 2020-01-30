package main.windowCommitConstruction.generalPanel;

import com.intellij.openapi.project.Project;
import main.testSmellDetection.testSmellInfo.eagerTest.EagerTestInfo;
import main.testSmellDetection.testSmellInfo.generalFixture.GeneralFixtureInfo;
import main.testSmellDetection.testSmellInfo.lackOfCohesion.LackOfCohesionInfo;
import main.windowCommitConstruction.CommitWindowFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class WarningWindow extends JFrame {
    private Project project;

    private ArrayList<GeneralFixtureInfo> generalFixtureInfos;
    private ArrayList<EagerTestInfo> eagerTestInfos;
    private ArrayList<LackOfCohesionInfo> lackOfCohesionInfos;

    private JButton ignoreButton;
    private JButton seeButton;
    private JPanel rootPanel;

    public WarningWindow(Project project, ArrayList<GeneralFixtureInfo> generalFixtureInfos, ArrayList<EagerTestInfo> eagerTestInfos, ArrayList<LackOfCohesionInfo> lackOfCohesionInfos) throws HeadlessException {
        super("Test Smell Notifier");
        this.project = project;
        this.generalFixtureInfos = generalFixtureInfos;
        this.eagerTestInfos = eagerTestInfos;
        this.lackOfCohesionInfos = lackOfCohesionInfos;

        ignoreButton.setActionCommand("ignore");
        seeButton.setActionCommand("see");
        this.setContentPane(rootPanel);


        ignoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        seeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CommitWindowFactory.createWindow(true, false, project, generalFixtureInfos, eagerTestInfos, lackOfCohesionInfos);
            }
        });
    }
}
