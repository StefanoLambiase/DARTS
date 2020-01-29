package main.windowCommitConstruction;

import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBScrollPane;
import main.testSmellDetection.testSmellInfo.eagerTest.EagerTestInfo;
import main.testSmellDetection.testSmellInfo.generalFixture.GeneralFixtureInfo;
import main.windowCommitConstruction.testSmellPanel.ETSmellPanel;
import main.windowCommitConstruction.testSmellPanel.GFSmellPanel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;

public class EagerTestCP extends JPanel implements ListSelectionListener {
    private ArrayList<EagerTestInfo> classesWithEagerTest;
    private Project project;

    private JSplitPane firstSplitPane;
    private JSplitPane secondSplitPane;

    private JBList classList;

    ArrayList<String> classesNames;

    public EagerTestCP(ArrayList<EagerTestInfo> classesWithET, Project project){
        // Inizializzazione delle variabili.
        this.project = project;

        classesNames = new ArrayList<>();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Inizio la costruzione del Panel.
        if(!classesWithET.isEmpty()){
            //Parte relativa all'inizializzazione del Panel per GeneralFixture.
            classesWithEagerTest = classesWithET;

            // Mi prendo tutti i nomi delle classi affette dallo smell.
            for (EagerTestInfo eti : classesWithEagerTest){
                classesNames.add(eti.getClassWithEagerTest().getName());
            }

            // Setup della lista delle classi.
            classList = new JBList(classesNames);
            classList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            classList.setSelectedIndex(0);
            classList.addListSelectionListener(this);
            JBScrollPane classScrollPane = new JBScrollPane(classList);

            // Inizializzo la secondSplitPane per la prima esecuzione.
            secondSplitPane = new ETSmellPanel(classesWithEagerTest.get(0), project);

            // Creazione dello split pane con la lista delle classi e la secondSplitPane.
            firstSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, classScrollPane, secondSplitPane);
            firstSplitPane.setOneTouchExpandable(true);
            firstSplitPane.setDividerLocation(150);

            Dimension minimumSize = new Dimension(200, 100);
            classScrollPane.setMinimumSize(minimumSize);
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
        JList list = (JList)e.getSource();
        updateSmellPanel(classesWithEagerTest.get(list.getSelectedIndex()));
    }

    protected void updateSmellPanel (EagerTestInfo eti) {
        secondSplitPane = new ETSmellPanel(eti, project);
        firstSplitPane.setRightComponent(secondSplitPane);
        secondSplitPane.setMinimumSize(new Dimension(200, 100));
    }
}
