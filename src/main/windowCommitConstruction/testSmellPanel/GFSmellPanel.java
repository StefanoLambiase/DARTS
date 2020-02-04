package main.windowCommitConstruction.testSmellPanel;

import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBScrollPane;
import main.testSmellDetection.testSmellInfo.generalFixture.GeneralFixtureInfo;
import main.testSmellDetection.testSmellInfo.generalFixture.MethodWithGeneralFixture;
import main.windowCommitConstruction.GeneralFixtureCP;
import main.windowCommitConstruction.general.RefactorWindow;
import main.windowCommitConstruction.general.listRenderer.CustomListRenderer2;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;

public class GFSmellPanel extends JSplitPane implements ListSelectionListener {
    private JBList smellList;
    private JBPanel refactorPreviewPanel;
    DefaultListModel model;

    private ArrayList<String> methodsNames = new ArrayList<>();

    private GeneralFixtureInfo generalFixtureInfo;
    private Project project;
    private GeneralFixtureCP generalFixtureCP;

    Dimension minimumSize = new Dimension(150, 100);

    public GFSmellPanel(GeneralFixtureInfo generalFixtureInfo, Project project, GeneralFixtureCP generalFixtureCP){
        this.project = project;
        this.generalFixtureCP = generalFixtureCP;

        model = new DefaultListModel ();

        this.refactorPreviewPanel = new JBPanel();
        this.generalFixtureInfo = generalFixtureInfo;

        for(MethodWithGeneralFixture methodWithGeneralFixture : generalFixtureInfo.getMethodsThatCauseGeneralFixture()){
            model.addElement(methodWithGeneralFixture.getMethodWithGeneralFixture().getName());
            methodsNames.add(methodWithGeneralFixture.getMethodWithGeneralFixture().getName());
        }

        smellList = new JBList(model);
        smellList.setCellRenderer( new CustomListRenderer2(smellList));

        smellList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        smellList.setSelectedIndex(0);
        smellList.addListSelectionListener(this);
        JBScrollPane smellScrollPane = new JBScrollPane(smellList);
        smellScrollPane.setBorder(new TitledBorder("METHODS"));

        // Creazione dello split pane con la lista degli smell e la preview del refactoring.
        this.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        this.setLeftComponent(smellScrollPane);
        this.setOneTouchExpandable(true);
        this.setDividerLocation(150);

        // Fornisco le dimensioni minime dei due panel e una dimensione di base per l'intero panel.
        smellScrollPane.setMinimumSize(minimumSize);
        refactorPreviewPanel.setMinimumSize(minimumSize);
        this.setPreferredSize(new Dimension(400, 200));

        // Starto il secondo panel per la prima volta.
        updateRefactorPreviewLabel(smellList.getSelectedIndex());
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        updateRefactorPreviewLabel(smellList.getSelectedIndex());
    }

    //Renders the selected image
    protected void updateRefactorPreviewLabel (int index) {
        MethodWithGeneralFixture methodWithGeneralFixture;
        if(index == -1){
            methodWithGeneralFixture = generalFixtureInfo.getMethodsThatCauseGeneralFixture().get(0);
        } else {
            methodWithGeneralFixture = generalFixtureInfo.getMethodsThatCauseGeneralFixture().get(index);
        }
        RefactorWindow refactorWindow = new RefactorWindow(methodWithGeneralFixture, generalFixtureInfo, project, this);
        this.setRightComponent(refactorWindow.getRootPanel());
    }

    public void doAfterRefactor(){
        int index = smellList.getSelectedIndex();

        methodsNames.remove(index);
        generalFixtureInfo.getMethodsThatCauseGeneralFixture().remove(index);
        model.remove(index);

        if(model.getSize() == 0){
            generalFixtureCP.doAfterRefactor(generalFixtureInfo);
        } else {
            if(index == model.getSize()){
                index --;
            }
            smellList.setSelectedIndex(index);
            smellList.ensureIndexIsVisible(index);
            updateRefactorPreviewLabel(smellList.getSelectedIndex());
        }
    }

}
