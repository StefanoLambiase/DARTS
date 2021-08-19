package stats;

import javax.swing.*;
import java.awt.*;

public class StatsPanel extends JPanel {

  JPanel statsInfo;

  private Session session;

  public StatsPanel() {
    this.session = Stats.getInstance().getLastSession();
    initialize();
  }

  private void initialize() {
    this.setLayout(new GridLayout(1, 1));

    this.setLayout(new BorderLayout());

    this.add(new JLabel("Statistiche", SwingConstants.CENTER), BorderLayout.NORTH);

    this.statsInfo = new JPanel(new GridLayout(7, 1, 1, 1));
    this.add(this.statsInfo, BorderLayout.WEST);

    this.statsInfo.add(new JLabel("Session ID: " + this.session.getID()));
    this.statsInfo.add(new JLabel(this.session.getKind() + " Detection"));
    this.statsInfo.add(new JLabel("Execution time: " + this.session.getExecutionTime() + "ms"));
    this.statsInfo.add(new JLabel("Number of classes of the project: " + this.session.getnOfTotalClasses()));
    this.statsInfo.add(new JLabel("Number of General Fixture founded: " + this.session.getNOfGF()));
    this.statsInfo.add(new JLabel("Number of Eager Test founded: " + this.session.getNOfET()));
    this.statsInfo.add(new JLabel("Number of Lack Of Cohesion founded: " + this.session.getNOfLOC()));
  }
}
