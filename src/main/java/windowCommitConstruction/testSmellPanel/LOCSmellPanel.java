package windowCommitConstruction.testSmellPanel;

import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBScrollPane;
import testSmellDetection.bean.PsiMethodBean;
import testSmellDetection.testSmellInfo.lackOfCohesion.LackOfCohesionInfo;
import windowCommitConstruction.LackOfCohesionCP;
import windowCommitConstruction.general.RefactorWindow;
import windowCommitConstruction.general.listRenderer.CustomListRenderer2;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;

public class LOCSmellPanel extends JSplitPane implements ListSelectionListener {
    private JBList smellList;
    private JBPanel refactorPreviewPanel;

    ArrayList<String> methodsNames = new ArrayList<>();

    private LackOfCohesionInfo lackOfCohesionInfo;
    private Project project;
    private LackOfCohesionCP lackOfCohesionCP;
    DefaultListModel model;

    Dimension minimumSize = new Dimension(150, 100);

    public LOCSmellPanel(LackOfCohesionInfo lackOfCohesionInfo, Project project, LackOfCohesionCP lackOfCohesionCP){
        this.project = project;
        this.lackOfCohesionCP = lackOfCohesionCP;

        model = new DefaultListModel ();

        this.refactorPreviewPanel = new JBPanel();
        this.lackOfCohesionInfo = lackOfCohesionInfo;

        for(PsiMethodBean methodWithLackOfCohesion : lackOfCohesionInfo.getMethodsThatCauseLackOfCohesion()){
            model.addElement(methodWithLackOfCohesion.getName());
            methodsNames.add(methodWithLackOfCohesion.getName());
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
        JList list = (JList)e.getSource();
        updateRefactorPreviewLabel(list.getSelectedIndex());
    }

    //Renders the selected image
    protected void updateRefactorPreviewLabel (int index) {
        PsiMethodBean methodWithLOC;
        if(index == -1 && model.getSize() == 0){
            return;
        } else if(index == -1){
            methodWithLOC = lackOfCohesionInfo.getMethodsThatCauseLackOfCohesion().get(0);
        } else {
            methodWithLOC = lackOfCohesionInfo.getMethodsThatCauseLackOfCohesion().get(index);
        }
        RefactorWindow refactorWindow = new RefactorWindow(methodWithLOC, lackOfCohesionInfo, project, this);
        this.setRightComponent(refactorWindow.getRootPanel());
    }

    public void doAfterRefactor(){
        int index = smellList.getSelectedIndex();

        methodsNames.remove(index);
        lackOfCohesionInfo.getMethodsThatCauseLackOfCohesion().remove(index);
        model.remove(index);

        if(model.getSize() == 0){
            lackOfCohesionCP.doAfterRefactor();
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
