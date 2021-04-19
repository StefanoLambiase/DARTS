package windowCommitConstruction.testSmellPanel;

import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBScrollPane;
import testSmellDetection.testSmellInfo.eagerTest.EagerTestInfo;
import testSmellDetection.testSmellInfo.eagerTest.MethodWithEagerTest;
import windowCommitConstruction.EagerTestCP;
import windowCommitConstruction.general.RefactorWindow;
import windowCommitConstruction.general.listRenderer.CustomListRenderer2;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;

public class ETSmellPanel  extends JSplitPane implements ListSelectionListener {
    private JBList smellList;
    private JBPanel refactorPreviewPanel;

    ArrayList<String> methodsNames = new ArrayList<>();

    private EagerTestInfo eagerTestInfo;
    private Project project;
    private EagerTestCP eagerTestCP;
    DefaultListModel model;

    Dimension minimumSize = new Dimension(150, 100);

    public ETSmellPanel(EagerTestInfo eagerTestInfo, Project project, EagerTestCP eagerTestCP){
        this.project = project;
        this.eagerTestCP = eagerTestCP;
        this.eagerTestCP = eagerTestCP;

        model = new DefaultListModel ();

        this.refactorPreviewPanel = new JBPanel();
        this.eagerTestInfo = eagerTestInfo;

        for(MethodWithEagerTest methodWithEagerTest : eagerTestInfo.getMethodsThatCauseEagerTest()){
            model.addElement(methodWithEagerTest.getMethodWithEagerTest().getName());
            methodsNames.add(methodWithEagerTest.getMethodWithEagerTest().getName());
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
        MethodWithEagerTest methodWithEagerTest;
        if(index == -1 && model.getSize() == 0){
            return;
        } else if(index == -1){
            methodWithEagerTest = eagerTestInfo.getMethodsThatCauseEagerTest().get(0);
        } else {
            methodWithEagerTest = eagerTestInfo.getMethodsThatCauseEagerTest().get(index);
        }
        RefactorWindow refactorWindow = new RefactorWindow(methodWithEagerTest, eagerTestInfo, project, this);
        this.setRightComponent(refactorWindow.getRootPanel());
    }

    public void doAfterRefactor() {
        int index = smellList.getSelectedIndex();

        methodsNames.remove(index);
        eagerTestInfo.getMethodsThatCauseEagerTest().remove(index);
        model.remove(index);

        if(model.getSize() == 0){
            eagerTestCP.doAfterRefactor();
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
