package main.oldWindowConstruction;

import com.intellij.openapi.project.Project;
import main.testSmellDetection.testSmellInfo.eagerTest.EagerTestInfo;
import main.oldWindowConstruction.testSmellPanel.ClassWithEagerTestPanel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;

public class EagerTestPanel extends JPanel {
    private ArrayList<EagerTestInfo> classesWithEagerTest;

    public EagerTestPanel(ArrayList<EagerTestInfo> classesWithET, Project project){
        //Parte relativa al titolo ed al bordo del panel
        TitledBorder border = new TitledBorder("EAGER TEST");
        border.setTitleJustification(TitledBorder.CENTER);
        border.setTitlePosition(TitledBorder.TOP);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(border);

        if (!classesWithET.isEmpty()){
            //Parte relativa all'inizializzazione del Panel per EagerTest
            classesWithEagerTest = classesWithET;

            //Creazione della parte alta del JPanel
            JPanel topPanel = new JPanel(new GridLayout(1,3));
            topPanel.add(new JLabel("CLASS NAME"));
            topPanel.add(new JLabel("PRODUCTION CLASS NAME"));
            JLabel dettagliLabel = new JLabel("METHODS DETAILS");
            dettagliLabel.setHorizontalAlignment(SwingConstants.CENTER);
            topPanel.add(dettagliLabel);

            //Imposto le dimensioni
            topPanel.setMinimumSize(new Dimension(Integer.MAX_VALUE,1000));
            topPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE,1000));
            this.add(topPanel);

            //Parte relativa alla creazione delle singole info per ogni classe affetta da EagerTest
            for(EagerTestInfo eti : classesWithEagerTest){
                JPanel classPanel = new ClassWithEagerTestPanel(eti, project);
                classPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 5000));
                this.add(classPanel);
            }
        } else {
            JPanel emptyPanel = new JPanel(new GridLayout(1,1));
            JLabel emptyLabel = new JLabel("Nessuno Smell Trovato!");
            emptyPanel.add(emptyLabel);
            this.add(emptyPanel);
        }
    }


    public ArrayList<EagerTestInfo> getClassesWithEagerTest() {
        return classesWithEagerTest;
    }

    public void setClassesWithEagerTest(ArrayList<EagerTestInfo> classesWithEagerTest) {
        this.classesWithEagerTest = classesWithEagerTest;
    }


}
