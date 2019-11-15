package main.toolWindowConstruction;

import com.intellij.openapi.project.Project;
import it.unisa.testSmellDiffusion.testSmellInfo.eagerTest.EagerTestInfo;
import main.toolWindowConstruction.testSmellPanel.ClassWithEagerTestPanel;

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

            //Mi prendo le dimensioni dello schermo
            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            int width = gd.getDisplayMode().getWidth();
            int height = gd.getDisplayMode().getHeight();

            topPanel.setMinimumSize(new Dimension(width-70,40));
            topPanel.setPreferredSize(new Dimension(width-70, 40));
            topPanel.setMaximumSize(new Dimension(width-70, 40));
            this.add(topPanel);

            //Parte relativa alla creazione delle singole info per ogni classe affetta da EagerTest
            for(EagerTestInfo eti : classesWithEagerTest){
                this.add(new ClassWithEagerTestPanel(eti, project));
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
