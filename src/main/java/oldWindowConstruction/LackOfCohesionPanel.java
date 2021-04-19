package oldWindowConstruction;

import com.intellij.openapi.project.Project;
import testSmellDetection.testSmellInfo.lackOfCohesion.LackOfCohesionInfo;
import oldWindowConstruction.testSmellPanel.ClassWithLackOfCohesionPanel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;

public class LackOfCohesionPanel extends JPanel {
    private ArrayList<LackOfCohesionInfo> classesWithLackOfCohesion;

    public LackOfCohesionPanel(ArrayList<LackOfCohesionInfo> classesWithLOC, Project project){
        //Parte relativa al titolo ed al bordo del panel
        TitledBorder border = new TitledBorder("LACK OF COHESION");
        border.setTitleJustification(TitledBorder.CENTER);
        border.setTitlePosition(TitledBorder.TOP);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(border);

        if (!classesWithLOC.isEmpty()){
            //Parte relativa all'inizializzazione del Panel per EagerTest
            classesWithLackOfCohesion = classesWithLOC;

            //Creazione della parte alta del JPanel
            JPanel topPanel = new JPanel(new GridLayout(1,3));
            topPanel.add(new JLabel("CLASS NAME"));
            topPanel.add(new JLabel("PRODUCTION CLASS NAME"));
            JLabel dettagliLabel = new JLabel("DETAILS");
            dettagliLabel.setHorizontalAlignment(SwingConstants.CENTER);
            topPanel.add(dettagliLabel);

            //Imposto le dimensioni
            topPanel.setMinimumSize(new Dimension(Integer.MAX_VALUE,1000));
            topPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE,1000));
            this.add(topPanel);

            //Parte relativa alla creazione delle singole info per ogni classe affetta da EagerTest
            for(LackOfCohesionInfo loci : classesWithLackOfCohesion){
                JPanel classPanel = new ClassWithLackOfCohesionPanel(loci, project);
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
}
