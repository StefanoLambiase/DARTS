package windowCommitConstruction;

import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBScrollPane;
import testSmellDetection.testSmellInfo.eagerTest.EagerTestInfo;
import testSmellDetection.testSmellInfo.mysteryGuest.MysteryGuestInfo;
import windowCommitConstruction.general.listRenderer.CustomListRenderer;
import windowCommitConstruction.testSmellPanel.ETSmellPanel;
import windowCommitConstruction.testSmellPanel.MGSmellPanel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;

public class MysteryGuestCP extends JPanel implements ListSelectionListener {
    private ArrayList<MysteryGuestInfo> classesWithMysteryGuest;
    private Project project;

    private JSplitPane firstSplitPane;
    private JSplitPane secondSplitPane;

    private JBList classList;

    DefaultListModel model;

    ArrayList<String> classesNames;

    public MysteryGuestCP(ArrayList<MysteryGuestInfo> classesWithMysteryGuest, Project project){
        // Inizializzazione delle variabili.
        this.project = project;
        model = new DefaultListModel();

        classesNames = new ArrayList<>();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Inizio la costruzione del Panel.
        if(!classesWithMysteryGuest.isEmpty()){
            //Parte relativa all'inizializzazione del Panel per GeneralFixture.
            this.classesWithMysteryGuest = classesWithMysteryGuest;

            // Mi prendo tutti i nomi delle classi affette dallo smell.
            for (MysteryGuestInfo mgi : classesWithMysteryGuest){
                if(mgi.getMethodsThatCauseMysteryGuest() != null)
                    model.addElement(mgi);
            }

            // Setup della lista delle classi.
            classList = new JBList(model);
            classList.setCellRenderer( new CustomListRenderer(classList));
            classList.setBorder ( BorderFactory.createEmptyBorder ( 5, 5, 5, 5 ) );

            classList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            classList.setSelectedIndex(0);
            classList.addListSelectionListener(this);
            JBScrollPane classScrollPane = new JBScrollPane(classList);
            classScrollPane.setBorder(new TitledBorder("CLASSES"));

            // Inizializzo la secondSplitPane per la prima esecuzione.
            if(classesWithMysteryGuest.get(0).getMethodsThatCauseMysteryGuest() != null)
                secondSplitPane = new MGSmellPanel(classesWithMysteryGuest.get(0), project, this);

            // Creazione dello split pane con la lista delle classi e la secondSplitPane.
            firstSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, classScrollPane, secondSplitPane);
            firstSplitPane.setOneTouchExpandable(true);
            firstSplitPane.setDividerLocation(150);

            Dimension minimumSize = new Dimension(150, 100);
            classScrollPane.setMinimumSize(minimumSize);

            if(secondSplitPane != null)
                secondSplitPane.setMinimumSize(minimumSize);

            firstSplitPane.setPreferredSize(new Dimension(400, 200));

            this.add(firstSplitPane);
        } else {
            JPanel emptyPanel = new JPanel(new GridLayout(1,1));
            JLabel emptyLabel = new JLabel("Nessuno Smell Trovato!");
            emptyPanel.add(emptyLabel);
            this.add(emptyPanel);
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        updateSmellPanel(classList.getSelectedIndex());
    }

    protected void updateSmellPanel (int index) {
        MysteryGuestInfo mgi;
        if(index == -1 && model.getSize() == 0){
            return;
        } else if(index == -1 && model.getSize() != 0){
            mgi = (MysteryGuestInfo) classList.getModel().getElementAt(0);
        } else {
            mgi = (MysteryGuestInfo) classList.getModel().getElementAt(index);
        }
        secondSplitPane = new MGSmellPanel(mgi, project, this);
        firstSplitPane.setRightComponent(secondSplitPane);
        secondSplitPane.setMinimumSize(new Dimension(150, 100));
    }

    public void doAfterRefactor(){
        int index = classList.getSelectedIndex();

        classesWithMysteryGuest.remove(index);
        model.remove(index);

        if(model.getSize() == 0){
            JLabel label = new JLabel("All smells resolved");
            this.removeAll();
            this.validate();
            this.repaint();
            this.add(label);
        } else {
            if(index == model.getSize()){
                index --;
            }
            classList.setSelectedIndex(index);
            classList.ensureIndexIsVisible(index);
            updateSmellPanel(classList.getSelectedIndex());
        }
    }
}
