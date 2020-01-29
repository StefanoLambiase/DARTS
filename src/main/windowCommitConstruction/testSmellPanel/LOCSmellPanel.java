package main.windowCommitConstruction.testSmellPanel;

import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBScrollPane;
import main.testSmellDetection.bean.PsiMethodBean;
import main.testSmellDetection.testSmellInfo.lackOfCohesion.LackOfCohesionInfo;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;

public class LOCSmellPanel extends JSplitPane implements ListSelectionListener {
    private JBList smellList;
    private JBPanel refactorPreviewPanel;

    ArrayList<String> methodsNames;

    private LackOfCohesionInfo lackOfCohesionInfo;

    public LOCSmellPanel(LackOfCohesionInfo lackOfCohesionInfo, Project project){
        this.refactorPreviewPanel = new JBPanel();
        this.lackOfCohesionInfo = lackOfCohesionInfo;
        this.methodsNames = new ArrayList<>();

        for(PsiMethodBean methodWithLackOfCohesion : lackOfCohesionInfo.getMethodsThatCauseLackOfCohesion()){
            methodsNames.add(methodWithLackOfCohesion.getName());
        }

        smellList = new JBList(methodsNames);
        smellList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        smellList.setSelectedIndex(0);
        smellList.addListSelectionListener(this);
        JBScrollPane classScrollPane = new JBScrollPane(smellList);

        // Creazione dello split pane con la lista degli smell e la preview del refactoring.
        this.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        this.setLeftComponent(smellList);
        this.setRightComponent(refactorPreviewPanel);
        this.setOneTouchExpandable(true);
        this.setDividerLocation(150);

        // Fornisco le dimensioni minime dei due panel e una dimensione di base per l'intero panel.
        Dimension minimumSize = new Dimension(200, 100);
        smellList.setMinimumSize(minimumSize);
        refactorPreviewPanel.setMinimumSize(minimumSize);
        this.setPreferredSize(new Dimension(400, 200));

        // Starto il secondo panel per la prima volta.
        updateRefactorPreviewLabel(methodsNames.get(smellList.getSelectedIndex()));
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        JList list = (JList)e.getSource();
        updateRefactorPreviewLabel(methodsNames.get(list.getSelectedIndex()));
    }

    //Renders the selected image
    protected void updateRefactorPreviewLabel (String smellName) {
        JBLabel label = new JBLabel(smellName);
        refactorPreviewPanel.add(label);
    }
}
