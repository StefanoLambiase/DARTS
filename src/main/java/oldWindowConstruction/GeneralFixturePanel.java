package oldWindowConstruction;

import com.intellij.openapi.project.Project;
import testSmellDetection.testSmellInfo.generalFixture.GeneralFixtureInfo;
import oldWindowConstruction.testSmellPanel.ClassWithGeneralFixturePanel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;

public class GeneralFixturePanel extends JPanel{
    private ArrayList<GeneralFixtureInfo> classesWithGeneralFixture;

    public GeneralFixturePanel(ArrayList<GeneralFixtureInfo> classesWithGF, Project project){
        TitledBorder border = new TitledBorder("GENERAL FIXTURE");
        border.setTitleJustification(TitledBorder.CENTER);
        border.setTitlePosition(TitledBorder.TOP);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(border);

        if(!classesWithGF.isEmpty()){
            //Parte relativa all'inizializzazione del Panel per GeneralFixture
            classesWithGeneralFixture = classesWithGF;

            //Parte relativa alla creazione del TopPanel
            JPanel topPanel = new JPanel();
            topPanel.setLayout(new GridLayout(1,2));

            JLabel nomeClasse = new JLabel("CLASS NAME");
            nomeClasse.setHorizontalAlignment(SwingConstants.CENTER);
            topPanel.add(nomeClasse);

            JLabel dettagliMetodi = new JLabel("METHODS DETAILS");
            dettagliMetodi.setHorizontalAlignment(SwingConstants.CENTER);
            topPanel.add(dettagliMetodi);

            //Imposto le dimensioni
            topPanel.setMinimumSize(new Dimension(Integer.MAX_VALUE,1000));
            topPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE,1000));
            this.add(topPanel);

            //Parte relativa alla creazione delle singole info per ogni classe affetta da GeneralFixture
            for(GeneralFixtureInfo gfi : classesWithGeneralFixture){
                JPanel classPanel = new ClassWithGeneralFixturePanel(gfi, project);
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

    public ArrayList<GeneralFixtureInfo> getClassesWithGeneralFixture() {
        return classesWithGeneralFixture;
    }

    public void setClassesWithGeneralFixture(ArrayList<GeneralFixtureInfo> classesWithGeneralFixture) {
        this.classesWithGeneralFixture = classesWithGeneralFixture;
    }


}
