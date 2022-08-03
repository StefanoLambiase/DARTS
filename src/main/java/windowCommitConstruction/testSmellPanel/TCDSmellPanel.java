package windowCommitConstruction.testSmellPanel;
import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBScrollPane;
import testSmellDetection.testSmellInfo.testCodeDuplication.MethodWithTestCodeDuplication;
import testSmellDetection.testSmellInfo.testCodeDuplication.TestCodeDuplicationInfo;
import windowCommitConstruction.TestCodeDuplicationCP;
import windowCommitConstruction.general.RefactorWindow;
import windowCommitConstruction.general.listRenderer.CustomListRenderer2;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;

public class TCDSmellPanel extends JSplitPane implements ListSelectionListener {
    private JBList smellList;
    private JBPanel refactorPreviewPanel;
    ArrayList<String> methodsNames = new ArrayList<>();
    private TestCodeDuplicationInfo testCodeDuplicationInfo;
    private Project project;
    private TestCodeDuplicationCP testCodeDuplicationCP;
    DefaultListModel model;
    Dimension minimumSize = new Dimension(150, 100);
    public TCDSmellPanel(TestCodeDuplicationInfo testCodeDuplicationInfo, Project project, TestCodeDuplicationCP testCodeDuplicationCP) {
        this.project = project;
        this.testCodeDuplicationCP = testCodeDuplicationCP;
        model = new DefaultListModel();
        this.refactorPreviewPanel = new JBPanel();
        this.testCodeDuplicationInfo = testCodeDuplicationInfo;
        for (MethodWithTestCodeDuplication methodWithTestCodeDuplication : testCodeDuplicationInfo.getMethodsThatCauseTestCodeDuplication()) {
            model.addElement(methodWithTestCodeDuplication.getMethodWithTestCodeDuplication().getName());
            methodsNames.add(methodWithTestCodeDuplication.getMethodWithTestCodeDuplication().getName());
        }
        smellList = new JBList(model);
        smellList.setCellRenderer(new CustomListRenderer2(smellList));
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
        JList list = (JList) e.getSource();
        updateRefactorPreviewLabel(list.getSelectedIndex());
    }
    //Renders the selected image
    protected void updateRefactorPreviewLabel(int index) {
        MethodWithTestCodeDuplication methodWithTestCodeDuplication;
        if (index == -1 && model.getSize() == 0) {
            return;
        } else if (index == -1) {
            methodWithTestCodeDuplication = this.testCodeDuplicationInfo.getMethodsThatCauseTestCodeDuplication().get(0);
        } else {
            methodWithTestCodeDuplication = this.testCodeDuplicationInfo.getMethodsThatCauseTestCodeDuplication().get(index);
        }
        RefactorWindow refactorWindow = new RefactorWindow(methodWithTestCodeDuplication, this.testCodeDuplicationInfo, project, this);
        this.setRightComponent(refactorWindow.getRootPanel());
    }
    public void doAfterRefactor() {
        int index = smellList.getSelectedIndex();
        methodsNames.remove(index);
        testCodeDuplicationInfo.getMethodsThatCauseTestCodeDuplication().remove(index);
        model.remove(index);
        if (model.getSize() == 0) {
            testCodeDuplicationCP.doAfterRefactor();
        } else {
            if (index == model.getSize()) {
                index--;
            }
            smellList.setSelectedIndex(index);
            smellList.ensureIndexIsVisible(index);
            updateRefactorPreviewLabel(smellList.getSelectedIndex());
        }
    }
}