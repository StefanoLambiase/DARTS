package main.windowCommitConstruction;

import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBScrollPane;
import main.testSmellDetection.testSmellInfo.generalFixture.GeneralFixtureInfo;
import main.windowCommitConstruction.testSmellPanel.GFSmellPanel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;

public class GeneralFixtureCP extends JPanel implements ListSelectionListener {
    private ArrayList<GeneralFixtureInfo> classesWithGeneralFixture;
    private Project project;

    private JSplitPane firstSplitPane;
    private JSplitPane secondSplitPane;

    private JBList classList;
    private JBList smellList;
    private JBPanel refactorPreviewPanel;

    ArrayList<String> classesNames;

    public GeneralFixtureCP(ArrayList<GeneralFixtureInfo> classesWithGF, Project project){
        // Inizializzazione delle variabili.
        this.project = project;

        smellList = new JBList();
        refactorPreviewPanel = new JBPanel();

        classesNames = new ArrayList<>();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Inizio la costruzione del Panel.
        if(!classesWithGF.isEmpty()){
            //Parte relativa all'inizializzazione del Panel per GeneralFixture.
            classesWithGeneralFixture = classesWithGF;

            // Mi prendo tutti i nomi delle classi affette dallo smell.
            for (GeneralFixtureInfo gfi : classesWithGeneralFixture){
                classesNames.add(gfi.getClassWithGeneralFixture().getName());
            }

            // Setup della lista delle classi.
            classList = new JBList(classesNames);
            classList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            classList.setSelectedIndex(0);
            classList.addListSelectionListener(this);
            JBScrollPane classScrollPane = new JBScrollPane(classList);

            // Inizializzo la secondSplitPane per la prima esecuzione.
            secondSplitPane = new GFSmellPanel(classesWithGeneralFixture.get(0), project);

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
        updateSmellPanel(classesWithGeneralFixture.get(list.getSelectedIndex()));
    }

    protected void updateSmellPanel (GeneralFixtureInfo gfi) {
        secondSplitPane = new GFSmellPanel(gfi, project);
        firstSplitPane.setRightComponent(secondSplitPane);
        secondSplitPane.setMinimumSize(new Dimension(200, 100));
    }

}
