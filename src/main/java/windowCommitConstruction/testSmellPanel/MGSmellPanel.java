package windowCommitConstruction.testSmellPanel;

import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBScrollPane;
import testSmellDetection.testSmellInfo.eagerTest.EagerTestInfo;
import testSmellDetection.testSmellInfo.eagerTest.MethodWithEagerTest;
import testSmellDetection.testSmellInfo.mysteryGuest.MethodWithMysteryGuest;
import testSmellDetection.testSmellInfo.mysteryGuest.MysteryGuestInfo;
import windowCommitConstruction.EagerTestCP;
import windowCommitConstruction.MysteryGuestCP;
import windowCommitConstruction.general.RefactorWindow;
import windowCommitConstruction.general.listRenderer.CustomListRenderer2;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;

public class MGSmellPanel  extends JSplitPane implements ListSelectionListener {
    private JBList smellList;
    private JBPanel refactorPreviewPanel;

    ArrayList<String> methodsNames = new ArrayList<>();

    private MysteryGuestInfo mysteryGuestInfo;
    private Project project;
    private MysteryGuestCP mysteryGuestCP;
    DefaultListModel model;

    Dimension minimumSize = new Dimension(150, 100);

    public MGSmellPanel(MysteryGuestInfo mysteryGuestInfo, Project project, MysteryGuestCP mysteryGuestCP){
        this.project = project;
        this.mysteryGuestCP = mysteryGuestCP;

        model = new DefaultListModel ();

        this.refactorPreviewPanel = new JBPanel();
        this.mysteryGuestInfo = mysteryGuestInfo;

        for(MethodWithMysteryGuest methodWithMysteryGuest : mysteryGuestInfo.getMethodsThatCauseMysteryGuest()){
            model.addElement(methodWithMysteryGuest.getMethodWithMysteryGuest().getName());
            methodsNames.add(methodWithMysteryGuest.getMethodWithMysteryGuest().getName());
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
        MethodWithMysteryGuest methodWithMysteryGuest;
        if(index == -1 && model.getSize() == 0){
            return;
        } else if(index == -1){
            methodWithMysteryGuest = mysteryGuestInfo.getMethodsThatCauseMysteryGuest().get(0);
        } else {
            methodWithMysteryGuest = mysteryGuestInfo.getMethodsThatCauseMysteryGuest().get(index);
        }
        RefactorWindow refactorWindow = new RefactorWindow(methodWithMysteryGuest, mysteryGuestInfo, project, this);
        this.setRightComponent(refactorWindow.getRootPanel());
    }

    public void doAfterRefactor() {
//        int index = smellList.getSelectedIndex();
//
//        methodsNames.remove(index);
//        eagerTestInfo.getMethodsThatCauseEagerTest().remove(index);
//        model.remove(index);
//
//        if(model.getSize() == 0){
//            eagerTestCP.doAfterRefactor();
//        } else {
//            if(index == model.getSize()){
//                index --;
//            }
//            smellList.setSelectedIndex(index);
//            smellList.ensureIndexIsVisible(index);
//            updateRefactorPreviewLabel(smellList.getSelectedIndex());
//        }
    }

}
