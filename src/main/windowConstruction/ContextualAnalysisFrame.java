package main.windowConstruction;

import com.intellij.openapi.application.PathManager;
import com.intellij.openapi.project.Project;
import data.ClassCoverageInfo;
import data.FlakyTestsInfo;
import data.TestClassAnalysis;
import data.TestProjectAnalysis;
import it.unisa.testSmellDiffusion.beans.ClassBean;
import it.unisa.testSmellDiffusion.beans.PackageBean;
import it.unisa.testSmellDiffusion.testMutation.TestMutationUtilities;
import main.contextualAnalysis.DataMiner;
import main.testSmellDetection.testSmellInfo.TestSmellInfo;
import org.repodriller.RepoDriller;
import processor.CoverageProcessor;
import processor.FlakyTestsProcessor;
import processor.MutationCoverageProcessor;
import storage.ReportManager;
import utils.VectorFind;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

public class ContextualAnalysisFrame extends JFrame {
    private static TestProjectAnalysis projectAnalysis;
    private static JFrame mainFrame;
    private static JLabel productionClassLabel, productionClassName, timePeriod, analyseCommitSince, sinceDate;
    private static String projectPath, productionClass;
    private static JSpinner timeNumber;
    private static JComboBox timePicker;
    private static JButton startAnalysis;
    private static GregorianCalendar sinceCommitDate;
    private static TestSmellInfo smellInfo;

    private static void addComponents (Container pane) {
        pane.setLayout(new GridBagLayout());
        GridBagConstraints layoutConstraints = new GridBagConstraints();

        // ProductionClassLabel COL0,ROW0 0[x--]
        productionClassLabel = new JLabel("Production class:");
        layoutConstraints.insets = new Insets(20, 20, 0, 0);
        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 0;
        layoutConstraints.anchor = GridBagConstraints.LINE_END;
        pane.add(productionClassLabel, layoutConstraints);
        // ProductionClassName label COL1,ROW0 0[-x-]
        productionClassName = new JLabel(productionClass + ".class");
        layoutConstraints.insets = new Insets(20, 0, 0, 0);
        layoutConstraints.gridx = 1;
        layoutConstraints.gridy = 0;
        layoutConstraints.anchor = GridBagConstraints.LINE_END;
        pane.add(productionClassName, layoutConstraints);
        // TimePeriod label COL0,ROW1 1[x--]
        timePeriod = new JLabel("Time period:");
        layoutConstraints.insets = new Insets(0, 20, 0, 0);
        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 1;
        layoutConstraints.anchor = GridBagConstraints.LINE_END;
        pane.add(timePeriod, layoutConstraints);
        // TimeNumber Spinner COL1,ROW1 1[-x-]
        SpinnerModel timeNumberModel = new SpinnerNumberModel(1,1,200,1);
        timeNumber = new JSpinner(timeNumberModel);
        timeNumber.setEnabled(true);
        timeNumber.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                // day/month number modified, updating the date and the timestamp in the frame
                subtractDate();
                sinceDate.setText(printDateLabel());
            }
        });
        layoutConstraints.gridx = 1;
        layoutConstraints.gridy = 1;
        layoutConstraints.anchor = GridBagConstraints.CENTER;
        pane.add(timeNumber, layoutConstraints);
        // TimePicker ComboBox COL2,ROW1 1[--x]
        String[] choices = {"days", "months"};
        timePicker = new JComboBox(choices);
        timePicker.setSelectedIndex(0);
        timePicker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Day/month choice changed, updating the date and the timestamp in the frame
                subtractDate();
                sinceDate.setText(printDateLabel());
            }
        });
        layoutConstraints.gridx = 2;
        layoutConstraints.gridy = 1;
        layoutConstraints.anchor = GridBagConstraints.LINE_START;
        pane.add(timePicker, layoutConstraints);
        // AnalyseCommitSince Label COL0,ROW2 2[x--]
        analyseCommitSince = new JLabel("Analyse commit since:");
        layoutConstraints.insets = new Insets(0, 20, 20, 0);
        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 2;
        layoutConstraints.anchor = GridBagConstraints.LINE_END;
        pane.add(analyseCommitSince, layoutConstraints);
        // SinceDate Label COL1,ROW2 2[-x-]
        sinceDate = new JLabel(printDateLabel());
        layoutConstraints.insets = new Insets(0, 0, 20, 0);
        layoutConstraints.gridx = 1;
        layoutConstraints.gridy = 2;
        layoutConstraints.anchor = GridBagConstraints.LINE_END;
        pane.add(sinceDate, layoutConstraints);
        // StartAnalysis Button COL2,ROW2 2[--x]
        startAnalysis = new JButton("Start Analysis");
        startAnalysis.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.setVisible(false);
                String intellijpath = PathManager.getPluginsPath();
                String pluginPath = intellijpath + "/TestFactorsPlugin/lib";
                projectAnalysis.setPluginPath(pluginPath);
                Vector<PackageBean> packages = projectAnalysis.getPackages();
                Vector<PackageBean> testPackages = projectAnalysis.getTestPackages();
                TestMutationUtilities utils = new TestMutationUtilities();
                ArrayList<ClassBean> classes = utils.getClasses(packages);
                Vector<ClassCoverageInfo> coverageInfos = null;
                Vector<FlakyTestsInfo> flakyInfos = null;
                Vector<TestClassAnalysis> classAnalyses = new Vector<>();

                coverageInfos = CoverageProcessor.calculate(projectAnalysis);

                flakyInfos = FlakyTestsProcessor.calculate(projectAnalysis, 10);

                for (ClassBean prodClass : classes) {
                    ClassBean testSuite = utils.getTestClassBy(prodClass.getName(), testPackages);

                    if (testSuite != null) {

                        TestClassAnalysis analysis = new TestClassAnalysis();
                        analysis.setName(testSuite.getName());
                        analysis.setBelongingPackage(testSuite.getBelongingPackage());
                        analysis.setProductionClass(prodClass.getBelongingPackage() + "." + prodClass.getName());

                        if (coverageInfos != null) {
                            analysis.setCoverage(VectorFind.findCoverageInfo(coverageInfos, testSuite.getName()));
                        } else {
                            analysis.setCoverage(new ClassCoverageInfo());
                        }

                        analysis.setMutationCoverage(MutationCoverageProcessor.calculate(testSuite, prodClass, projectAnalysis, 10));

                        if(flakyInfos != null) {
                            analysis.setFlakyTests(VectorFind.findFlakyInfo(flakyInfos, testSuite.getName()));
                        }else
                            analysis.setFlakyTests(new FlakyTestsInfo());
                        classAnalyses.add(analysis);
                    }
                }
                projectAnalysis.setClassAnalysis(classAnalyses);
                String smellyClassName = smellInfo.getClassWithSmell().getName();

                if(smellyClassName != null){

                    for (TestClassAnalysis classAnalysis : classAnalyses) {
                        if (classAnalysis.getName().equalsIgnoreCase(smellyClassName)) {
                            System.out.println("branch coverage:" + classAnalysis.getCoverage().getBranchCoverage());
                            System.out.println("line coverage" + classAnalysis.getCoverage().getLineCoverage());
                            System.out.println("n. of flaxy methods" + classAnalysis.getFlakyTests().getFlakyMethods().size());
                            System.out.println("mutation coverage" + classAnalysis.getMutationCoverage().getMutationCoverage());
                        }
                    }
                }
                new RepoDriller().start(new DataMiner(smellInfo, projectPath, sinceCommitDate));
            }
        });
        layoutConstraints.insets = new Insets(0, 0, 20, 20);
        layoutConstraints.gridx = 2;
        layoutConstraints.gridy = 2;
        layoutConstraints.anchor = GridBagConstraints.CENTER;
        pane.add(startAnalysis, layoutConstraints);
    }

    public ContextualAnalysisFrame(Project project, TestSmellInfo smellInfo, TestProjectAnalysis projectAnalysis) {
        mainFrame = new JFrame("Contextual Analysis");
        this.projectAnalysis = projectAnalysis;
        this.projectPath = project.getBasePath();
        this.smellInfo = smellInfo;
        this.productionClass = smellInfo.getClassWithSmell().getProductionClass().getName();
        sinceCommitDate = new GregorianCalendar();
        sinceCommitDate.add(Calendar.DAY_OF_YEAR, -1);
        mainFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addComponents(mainFrame.getContentPane());
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setLocation(dimension.width/2-mainFrame.getSize().width/2, dimension.height/2-mainFrame.getSize().height/2);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    private static void subtractDate() {
        boolean isDay = (timePicker.getSelectedIndex() == 0) ? true : false;
        sinceCommitDate = new GregorianCalendar();
        sinceCommitDate.add(Calendar.DAY_OF_YEAR, -1);
        if(isDay) {
            sinceCommitDate.add(Calendar.DAY_OF_YEAR, -((int) timeNumber.getValue()));
        } else {
            sinceCommitDate.add(Calendar.MONTH, -((int) timeNumber.getValue()));
        }
    }

    private static String printDateLabel() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(sinceCommitDate.getTime());
    }
}
