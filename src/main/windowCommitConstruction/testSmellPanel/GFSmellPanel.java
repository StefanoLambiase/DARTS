package main.windowCommitConstruction.testSmellPanel;

import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBScrollPane;
import main.testSmellDetection.testSmellInfo.generalFixture.GeneralFixtureInfo;
import main.testSmellDetection.testSmellInfo.generalFixture.MethodWithGeneralFixture;
import main.utility.TestSmellUtilities;
import main.windowCommitConstruction.generalPanel.RefactorWindow;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;

public class GFSmellPanel extends JSplitPane implements ListSelectionListener {
    private JBList smellList;
    private JBPanel refactorPreviewPanel;

    private ArrayList<String> methodsNames = new ArrayList<>();

    private GeneralFixtureInfo generalFixtureInfo;
    private Project project;

    public GFSmellPanel(GeneralFixtureInfo generalFixtureInfo, Project project){
        this.project = project;

        this.refactorPreviewPanel = new JBPanel();
        this.generalFixtureInfo = generalFixtureInfo;

        for(MethodWithGeneralFixture methodWithGeneralFixture : generalFixtureInfo.getMethodsThatCauseGeneralFixture()){
            methodsNames.add(methodWithGeneralFixture.getMethodWithGeneralFixture().getName());
        }

        smellList = new JBList(methodsNames);
        smellList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        smellList.setSelectedIndex(0);
        smellList.addListSelectionListener(this);
        JBScrollPane smellScrollPane = new JBScrollPane(smellList);

        // Creazione dello split pane con la lista degli smell e la preview del refactoring.
        this.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        this.setLeftComponent(smellScrollPane);
        this.setOneTouchExpandable(true);
        this.setDividerLocation(150);

        // Fornisco le dimensioni minime dei due panel e una dimensione di base per l'intero panel.
        Dimension minimumSize = new Dimension(200, 100);
        smellScrollPane.setMinimumSize(minimumSize);
        refactorPreviewPanel.setMinimumSize(minimumSize);
        this.setPreferredSize(new Dimension(400, 200));

        // Starto il secondo panel per la prima volta.
        updateRefactorPreviewLabel(smellList.getSelectedIndex());
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        JList list = (JList)e.getSource();
        updateRefactorPreviewLabel(list.getSelectedIndex());
    }

    //Renders the selected image
    protected void updateRefactorPreviewLabel (int index) {
        MethodWithGeneralFixture methodWithGeneralFixture = generalFixtureInfo.getMethodsThatCauseGeneralFixture().get(index);
        RefactorWindow refactorWindow = new RefactorWindow(methodWithGeneralFixture, generalFixtureInfo, project, this);
        this.setRightComponent(refactorWindow.getRootPanel());
    }

    public void doAfterRefactor(String methodName){
        this.rightComponent = null;

        int i = 0;

        for(int index = 0; index < methodsNames.size(); index ++){
            if(methodsNames.get(index).equals(methodName)){
                methodsNames.remove(index);
                i = index;
            }
        }

        System.out.println(TestSmellUtilities.ANSI_RED + "Nome metodo: " + generalFixtureInfo.getMethodsThatCauseGeneralFixture().get(i).getMethodWithGeneralFixture().getName());
        generalFixtureInfo.getMethodsThatCauseGeneralFixture().remove(i);

        smellList = new JBList(methodsNames);
        smellList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        smellList.setSelectedIndex(0);
        smellList.addListSelectionListener(this);
        JBScrollPane smellScrollPane = new JBScrollPane(smellList);
        this.setLeftComponent(smellScrollPane);
        if(!(smellList.getSelectedIndex() == -1))
            updateRefactorPreviewLabel(smellList.getSelectedIndex());
    }
}
