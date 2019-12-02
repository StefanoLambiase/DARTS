package main.windowConstruction;

import com.intellij.ui.components.JBTabbedPane;

import javax.swing.*;

public class PrincipalFrame extends JFrame {
    private JBTabbedPane detectionTp;
    private JBTabbedPane textualTp;
    private JBTabbedPane structuralTp;

    public PrincipalFrame(){
        super("DARTS");
        detectionTp = new JBTabbedPane();
        textualTp = null;
        structuralTp = null;
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
    public JBTabbedPane getDetectionTp(){
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
