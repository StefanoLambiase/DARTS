package main.windowCommitConstruction;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoadingFrame extends JFrame {
    private JFrame mainFrame;
    private JPanel panel;
    private JLabel loadingLabel;
    private JProgressBar progressBar;

    public LoadingFrame() {
        panel = new JPanel();
        panel.setBorder(new EmptyBorder(50, 50, 50, 50));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        loadingLabel = new JLabel("Loading, please wait..");
        panel.add(loadingLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setVisible(true);
        panel.add(progressBar);
        mainFrame = new JFrame("Performing Analysis");
        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mainFrame.setPreferredSize(new Dimension(400, 150));
        mainFrame.add(panel, BorderLayout.CENTER);
        mainFrame.setResizable(false);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setLocation(dimension.width/2-mainFrame.getSize().width/2, dimension.height/2-mainFrame.getSize().height/2);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    public void dispose() {
        mainFrame.dispose();
    }
}
