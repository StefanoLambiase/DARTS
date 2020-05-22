package main.windowConstruction;

import a.b.D;
import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBScrollPane;
import org.repodriller.domain.Commit;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.HashMap;
import java.util.Vector;

public class ContextualAnalysisResultsFrame extends JFrame {
    private JFrame mainFrame;
    private static String className;
    private static JLabel resultsInfos, commitListLabel, commitInfoLabel, commitHashLabel, commitAuthorLabel, commitMsgLabel;
    private static JList commitList;
    private static Vector<Commit> commitListItems;
    private static String reviser;
    private static double lineCoverage, branchCoverage;
    private static int flakyTestsNumber;

    private static void addComponents(Container pane) {
        pane.setLayout(new GridBagLayout());
        GridBagConstraints layoutConstraints = new GridBagConstraints();

        // Results Infos Label COL0,ROW0
        resultsInfos = new JLabel("<html><p>The " + className + " class has been analysed in the period of time choosen. " +
                "Since then, the class has been fixed " + commitListItems.size() + " times. " +
                "His test class has " + flakyTestsNumber + " flaky methods, a line coverage of " + lineCoverage + " and a branch coverage of " + branchCoverage + ". " +
                "We suggest " + reviser + " for review and fixing activities because he's the one who did the most commits for bug fixing for this class." +
                "</p></html>");
        resultsInfos.setPreferredSize(new Dimension(800, 100));
        layoutConstraints.fill = GridBagConstraints.HORIZONTAL;
        layoutConstraints.insets = new Insets(20, 20, 0, 20);
        layoutConstraints.gridwidth = 2; // 2 columns wide
        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 0;
        layoutConstraints.anchor = GridBagConstraints.LINE_START;
        pane.add(resultsInfos, layoutConstraints);

        // Commit Analysed Label COL0,ROW1
        commitListLabel = new JLabel("Commit Analyzed");
        layoutConstraints.fill = GridBagConstraints.HORIZONTAL;
        layoutConstraints.insets = new Insets(20, 20, 0, 0);
        layoutConstraints.gridwidth = 1;
        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 1;
        layoutConstraints.anchor = GridBagConstraints.CENTER;
        pane.add(commitListLabel, layoutConstraints);

        // Commit Selected Info Label COL1,ROW1
        commitInfoLabel = new JLabel("Commit Info");
        layoutConstraints.fill = GridBagConstraints.HORIZONTAL;
        layoutConstraints.insets = new Insets(20, 20, 0, 20);
        layoutConstraints.gridwidth = 1;
        layoutConstraints.gridx = 1;
        layoutConstraints.gridy = 1;
        layoutConstraints.anchor = GridBagConstraints.CENTER;
        pane.add(commitInfoLabel, layoutConstraints);

        // Commit Analysed List COL0,ROW2
        commitList = new JList(commitListItems);
        commitList.setPreferredSize(new Dimension(250, 200));
        commitList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    Commit commitSelected = (Commit) commitList.getSelectedValue();
                    commitHashLabel.setText("Hash: " + commitSelected.getHash());
                    commitAuthorLabel.setText("Author: " + commitSelected.getAuthor().getName());
                    commitMsgLabel.setText("<html><p>Message: " + commitSelected.getMsg() + "</p></html>");
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(commitList);
        scrollPane.setPreferredSize(new Dimension(250, 200));
        layoutConstraints.fill = GridBagConstraints.VERTICAL;
        layoutConstraints.insets = new Insets(20, 20, 20, 0);
        layoutConstraints.gridheight = 3; // 3 rows big
        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 2;
        layoutConstraints.anchor = GridBagConstraints.LINE_START;
        pane.add(scrollPane, layoutConstraints);

        // Commit Hash Label COL1,ROW2
        commitHashLabel = new JLabel("Hash:");
        layoutConstraints.fill = GridBagConstraints.HORIZONTAL;
        layoutConstraints.insets = new Insets(20, 20, 0, 20);
        layoutConstraints.gridheight = 1;
        layoutConstraints.gridx = 1;
        layoutConstraints.gridy = 2;
        layoutConstraints.anchor = GridBagConstraints.LINE_START;
        pane.add(commitHashLabel, layoutConstraints);

        // Commit Author Label COL1,ROW3
        commitAuthorLabel = new JLabel("Author:");
        layoutConstraints.fill = GridBagConstraints.HORIZONTAL;
        layoutConstraints.insets = new Insets(20, 20, 0, 20);
        layoutConstraints.gridx = 1;
        layoutConstraints.gridy = 3;
        layoutConstraints.anchor = GridBagConstraints.LINE_START;
        pane.add(commitAuthorLabel, layoutConstraints);

        // Commit Message Label COL1,ROW4
        commitMsgLabel = new JLabel("Message:");
        layoutConstraints.fill = GridBagConstraints.HORIZONTAL;
        layoutConstraints.insets = new Insets(20, 20, 0, 20);
        layoutConstraints.gridx = 1;
        layoutConstraints.gridy = 4;
        layoutConstraints.anchor = GridBagConstraints.LINE_START;
        pane.add(commitMsgLabel, layoutConstraints);
    }

    public ContextualAnalysisResultsFrame(String className,
                                          Vector<Commit> commitAnalyzed,
                                          String reviser,
                                          double lineCoverage,
                                          double branchCoverage,
                                          int flakyTestsNumber) {
        commitListItems = commitAnalyzed;
        this.className = className;
        this.reviser = reviser;
        this.lineCoverage = lineCoverage;
        this.branchCoverage = branchCoverage;
        this.flakyTestsNumber = flakyTestsNumber;
        mainFrame = new JFrame("Contextual Analysis Results");
        mainFrame.setResizable(false);
        addComponents(mainFrame.getContentPane());
        mainFrame.pack();
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setLocation(dimension.width/2-mainFrame.getSize().width/2, dimension.height/2-mainFrame.getSize().height/2);
        mainFrame.setVisible(true);
    }
}
