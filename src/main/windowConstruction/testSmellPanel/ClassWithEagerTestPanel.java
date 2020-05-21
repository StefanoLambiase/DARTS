package main.windowConstruction.testSmellPanel;

import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiPackageStatement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.refactoring.extractMethod.PrepareFailedException;
import data.TestClassAnalysis;
import data.TestProjectAnalysis;
import gui.PluginInitGUI;
import init.PluginInit;
import it.unisa.testSmellDiffusion.beans.PackageBean;
import it.unisa.testSmellDiffusion.utility.FileUtility;
import it.unisa.testSmellDiffusion.utility.FolderToJavaProjectConverter;
import javafx.scene.control.RadioButton;
import main.contextualAnalysis.DataMiner;
import main.refactor.IRefactor;
import main.refactor.strategy.EagerTestStrategy;
import main.testSmellDetection.bean.PsiMethodBean;
import main.testSmellDetection.testSmellInfo.eagerTest.EagerTestInfo;
import main.testSmellDetection.testSmellInfo.eagerTest.MethodWithEagerTest;
import main.windowConstruction.ContextualAnalysisFrame;
import org.repodriller.RepoDriller;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Vector;

/**
 * Questa classe rappresenta la GUI nella quale vengono mostrate le informazioni riguardanti una classe affetta da EagerTest
 */
public class ClassWithEagerTestPanel extends JPanel {

    private static final Logger LOGGER = Logger.getInstance("global");

    public ClassWithEagerTestPanel(EagerTestInfo eti, Project project){
        //Setto il contorno del JPanel
        Border blackline = BorderFactory.createLineBorder(Color.black);
        this.setBorder(blackline);

        // Costruisco la struttura del JPanel riguardante una specifica classe affetta da EagerTest.
        // Test Class.
        PsiFile parentFile = (PsiFile) PsiTreeUtil.getParentOfType(eti.getClassWithEagerTest().getPsiClass(), PsiFile.class);
        PsiPackageStatement psiPackageStatement = PsiTreeUtil.getChildOfType(parentFile, PsiPackageStatement.class);
        String packageString = psiPackageStatement.getPackageName();
        JLabel classNameLabel = new JLabel("   " + packageString + "." + eti.getClassWithEagerTest().getPsiClass().getName());
        // Production class.
        parentFile = (PsiFile) PsiTreeUtil.getParentOfType(eti.getProductionClass().getPsiClass(), PsiFile.class);
        psiPackageStatement = PsiTreeUtil.getChildOfType(parentFile, PsiPackageStatement.class);
        packageString = psiPackageStatement.getPackageName();
        JLabel pClassNameLabel = new JLabel(packageString + "." + eti.getProductionClass().getPsiClass().getName());

        // parte riguardante i metodi
        JPanel listOfMethodsPanel = new JPanel();
        listOfMethodsPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.BLACK));
        listOfMethodsPanel.setLayout(new GridLayout(eti.getMethodsThatCauseEagerTest().size(), 1));

        for(MethodWithEagerTest mb : eti.getMethodsThatCauseEagerTest()){
            JPanel methodPanel = new JPanel(new GridLayout(1, 3));
            JLabel methodName = new JLabel("   " + mb.getMethodWithEagerTest().getPsiMethod().getName());
            JButton methodButton = new JButton("details");
            JButton refactoringButton = new JButton("Do Refactor");
            JButton contextualAnalysisButton = new JButton("Execute contextual analysis");

            methodButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame detailsFrame = new JFrame(eti.getClassWithEagerTest().getPsiClass().getName());
                    Container cp = detailsFrame.getContentPane();
                    cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));

                    //Parte relativa alla creazione della scritta informativa
                    String methodName = " Method: " + mb.getMethodWithEagerTest().getPsiMethod().getName() + " calls the following methods: ";
                    JLabel methodNameLabel = new JLabel(methodName);
                    methodNameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                    methodNameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                    methodNameLabel.setMinimumSize(new Dimension(500, 35));
                    methodNameLabel.setPreferredSize(new Dimension(500, 35));
                    methodNameLabel.setMaximumSize(new Dimension(500, 35));
                    cp.add(methodNameLabel);

                    for(PsiMethodBean mbCalled : mb.getListOfMethodCalled()){
                        JLabel methodCalledName = new JLabel("   " + mbCalled.getPsiMethod().getName());
                        methodCalledName.setAlignmentX(Component.LEFT_ALIGNMENT);
                        methodCalledName.setMinimumSize(new Dimension(500, 35));
                        methodCalledName.setPreferredSize(new Dimension(500, 35));
                        methodCalledName.setMaximumSize(new Dimension(500, 35));

                        cp.add(methodCalledName);
                    }

                    //Parte relativa alla refactoring tips
                    JLabel refactoringLabel = new JLabel("It can be removed using this refactoring operations:");
                    JLabel firstRefactoringLabel = new JLabel("Extract method: affected method can be splitted into smaller methods, each one testing a specific behavior of the tested object.");

                    refactoringLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                    firstRefactoringLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

                    cp.add(refactoringLabel);
                    cp.add(firstRefactoringLabel);

                    //Parte relativa alla creazione del frame
                    detailsFrame.setSize(500,300);
                    detailsFrame.setVisible(true);
                }
            });

            //parte relativa all'azione del bottone per il refactoring automatico

            refactoringButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    IRefactor refactor = new EagerTestStrategy(mb, project, eti);
                    try {
                        refactor.doRefactor();
                    } catch (PrepareFailedException e1) {
                        e1.printStackTrace();
                    }
                }
            });

            contextualAnalysisButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String userDir = System.getProperty("user.home");

                    String pluginFolder = userDir + File.separator + "vitrum";
                    String pluginFolderWin = userDir + "\\vitrum";
                    File config = new File(pluginFolder + "/default_config.ini");
                    File jacocoProp = new File(pluginFolder + "/jacoco-agent.properties");
                    if (!config.exists()) {
                        String output = "[NONDA]" +
                                "\nname=Number of Non-Documented Assertions " +
                                "\ndescription=Number of assert statements without a description " +
                                "\ndetectionThreshold=1.0 " +
                                "\nguardThreshold=3.0 " +
                                "\nbelongingSmells=ASSERTION_ROULETTE " +
                                "\n[APCMC]" +
                                "\nname=Average Production Class Methods Calls " +
                                "\ndescription=Number of production class' methods calls in the test suite, divided by the number of test methods " +
                                "\ndetectionThreshold=1.0 " +
                                "\nguardThreshold=3.0 " +
                                "\nbelongingSmells=EAGER_TEST " +
                                "\n[MEXR] " +
                                "\nname=Methods using External Resources " +
                                "\ndescription=Number of external resources uses made by test methods" +
                                "\ndetectionThreshold=1.0 " +
                                "\nguardThreshold=3.0 " +
                                "\nbelongingSmells=MYSTERY_GUEST " +
                                "\n[NEXEA] " +
                                "\nname=Number of EXternal resources Existence Assumptions " +
                                "\ndescription=Number of assumptions made in test methods about the existence of external resources (e.g. Files, Database) " +
                                "\ndetectionThreshold=1.0 " +
                                "\nguardThreshold=3.0 " +
                                "\nbelongingSmells=RESOURCE_OPTIMISM " +
                                "\n[GFMR]" +
                                "\nname=General Fixture Methods Rate " +
                                "\ndescription=The rate of test methods not using all the set-up variables defined " +
                                "\ndetectionThreshold=1.0 " +
                                "\nguardThreshold=3.0 " +
                                "\nbelongingSmells=GENERAL_FIXTURE " +
                                "\n[MTOOR] " +
                                "\nname=Methods Testing Other Objects Rate " +
                                "\ndescription=The rate of methods testing objects which are different from the production class " +
                                "\ndetectionThreshold=1.0 " +
                                "\nguardThreshold=3.0 " +
                                "\nbelongingSmells=INDIRECT_TESTING " +
                                "\n[TSEC] " +
                                "\nname=toString invocations in Equality Checks " +
                                "\ndescription=The number of toString invocations in equality checks " +
                                "\ndetectionThreshold=1.0 " +
                                "\nguardThreshold=3.0 " +
                                "\nbelongingSmells=SENSITIVE_EQUALITY";
                        File plugin = new File(pluginFolder);
                        plugin.mkdirs();
                        File plugin2 = new File(pluginFolderWin);
                        plugin2.mkdirs();
                        FileUtility.writeFile(output, pluginFolder + "/" + "default_config.ini");

                    }
                    if (!jacocoProp.exists()) {
                        if(SystemInfo.getOsNameAndVersion().toLowerCase().contains("windows")) {
                            pluginFolderWin = pluginFolderWin.replace("\\", "\\\\");
                            String output = "destfile = " + pluginFolderWin + "\\\\jacoco.exec";
                            FileUtility.writeFile(output, pluginFolderWin + "\\" + "jacoco-agent.properties");
                        }
                        else{
                            String output = "destfile = " + pluginFolder + "/jacoco.exec";
                            FileUtility.writeFile(output, pluginFolder + "/" + "jacoco-agent.properties");
                        }
                    }
                    TestProjectAnalysis projectAnalysis = new TestProjectAnalysis();
                    //Project proj = e.getData(PlatformDataKeys.PROJECT);
                    String projectFolder = project.getBasePath();
                    File root = new File(projectFolder);
                    String srcPath = root.getAbsolutePath() + "/src";
                    String mainPath = srcPath + "/main";
                    String testPath = srcPath + "/test";
                    File main = new File(mainPath);
                    File test = new File(testPath);
                    if (!main.exists() || !test.exists()) {
                        JOptionPane.showMessageDialog(null, "PROJECT'S FOLDER STRUCTURE IS NOT CORRECT. PLEASE USE MAVEN DIRECTORY LAYOUT.");
                    } else {
                        boolean isMaven = false;
                        for (File file : root.listFiles()) {
                            if (file.isFile() && file.getName().equalsIgnoreCase("pom.xml"))
                                isMaven = true;
                        }
                        projectAnalysis.setMaven(isMaven);
                        String projectSDK = ProjectRootManager.getInstance(project).getProjectSdk().getHomePath();
                        LOGGER.info(projectSDK);
                        String os = SystemInfo.getOsNameAndVersion();
                        String javaPath;
                        if(os.toLowerCase().contains("windows"))
                            javaPath = projectSDK + "/bin/java.exe";
                        else
                            javaPath = projectSDK + "/bin/java";
                        projectAnalysis.setName(project.getName());
                        projectAnalysis.setPath(project.getBasePath());
                        projectAnalysis.setJavaPath(javaPath);
                        Vector<TestClassAnalysis> classAnalysis = new Vector<>();
                        if ((test.isDirectory()) && (!test.isHidden())) {
                            try {
                                Vector<PackageBean> testPackages = FolderToJavaProjectConverter.convert(test.getAbsolutePath());
                                if (testPackages.size() == 1 && testPackages.get(0).getClasses().size() == 0)
                                    JOptionPane.showMessageDialog(null, "TESTING SOURCE FILES NOT FOUND");
                                else {
                                    Vector<PackageBean> packages = FolderToJavaProjectConverter.convert(mainPath);
                                    projectAnalysis.setPackages(packages);
                                    projectAnalysis.setTestPackages(testPackages);
                                    projectAnalysis.setConfigPath(System.getProperty("user.home") + "/vitrum");
                                    new ContextualAnalysisFrame(project, eti, projectAnalysis);

                                } } catch(Exception ex){
                                ex.printStackTrace();
                            }


                        }
                    }
                }
                    //new RepoDriller().start(new DataMiner(eti, project.getBasePath()));
            });

            methodPanel.add(methodName);
            methodPanel.add(methodButton);
            methodPanel.add(refactoringButton);
            methodPanel.add(contextualAnalysisButton);

            listOfMethodsPanel.add(methodPanel);
        }
        this.setLayout(new GridLayout(1,3));
        this.add(classNameLabel);
        this.add(pClassNameLabel);
        this.add(listOfMethodsPanel);
    }
}
