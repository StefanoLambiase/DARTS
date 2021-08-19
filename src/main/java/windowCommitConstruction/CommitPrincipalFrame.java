package windowCommitConstruction;

import com.intellij.ui.components.JBTabbedPane;
import stats.Stats;
import utility.StatsSerializator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class CommitPrincipalFrame extends JFrame {
    private JBTabbedPane detectionTp;
    private JBTabbedPane textualTp;
    private JBTabbedPane structuralTp;

    public CommitPrincipalFrame(){
        super("DARTS");
        detectionTp = new JBTabbedPane();
        textualTp = null;
        structuralTp = null;

        this.setMinimumSize(new Dimension(800, 500));

        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {

            }

            @Override
            public void windowClosed(WindowEvent e) {
                System.out.println("Cercando di salvare in: " + Stats.getInstance().getFILE_PATH());
                if (StatsSerializator.serialize(Stats.getInstance(), Stats.getInstance().getFILE_PATH())) {
                    System.out.println("File salvato in: " + Stats.getInstance().getFILE_PATH());
                }
            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }

    public void addTextualPanel(JBTabbedPane textualTp){
        this.setTextualTp(textualTp);
        detectionTp.add("Textual Detection", textualTp);
    }

    public void addStructuralPanel(JBTabbedPane structuralTp){
        this.setStructuralTp(structuralTp);
        detectionTp.add("Structural Detection", structuralTp);
    }

    public void removeTextualPanel(){
        detectionTp.remove(textualTp);
        textualTp = null;
    }

    public void removeStructuralPanel(){
        detectionTp.remove(structuralTp);
        structuralTp = null;
    }

    /* GETTERS & SETTERS */

    public JBTabbedPane getDetectionTp() {
        return detectionTp;
    }

    public void setDetectionTp(JBTabbedPane detectionTp) {
        this.detectionTp = detectionTp;
    }

    public JBTabbedPane getTextualTp() {
        return textualTp;
    }

    public void setTextualTp(JBTabbedPane textualTp) {
        this.textualTp = textualTp;
    }

    public JBTabbedPane getStructuralTp() {
        return structuralTp;
    }

    public void setStructuralTp(JBTabbedPane structuralTp) {
        this.structuralTp = structuralTp;
    }
}
