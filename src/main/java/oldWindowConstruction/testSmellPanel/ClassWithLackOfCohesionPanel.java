package oldWindowConstruction.testSmellPanel;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.OrderEnumerator;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiPackageStatement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.refactoring.extractMethod.PrepareFailedException;
import data.TestClassAnalysis;
import data.TestProjectAnalysis;
import it.unisa.testSmellDiffusion.beans.PackageBean;
import it.unisa.testSmellDiffusion.utility.FileUtility;
import it.unisa.testSmellDiffusion.utility.FolderToJavaProjectConverter;
import refactor.IRefactor;
import refactor.strategy.LackOfCohesionStrategy;
import testSmellDetection.testSmellInfo.lackOfCohesion.LackOfCohesionInfo;
import windowCommitConstruction.contextualAnalysisPanel.ContextualAnalysisFrame;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

public class ClassWithLackOfCohesionPanel extends JPanel {

    private static final Logger LOGGER = Logger.getInstance("global");

    public ClassWithLackOfCohesionPanel(LackOfCohesionInfo loci, Project project){
        //Setto il contorno del JPanel
        Border blackline = BorderFactory.createLineBorder(Color.black);
        this.setBorder(blackline);

        //Costruisco la struttura del JPanel riguardante una specifica classe affetta da LackOfCohesion
        // Test Class.
        PsiFile parentFile = (PsiFile) PsiTreeUtil.getParentOfType(loci.getClassWithSmell().getPsiClass(), PsiFile.class);
        PsiPackageStatement psiPackageStatement = PsiTreeUtil.getChildOfType(parentFile, PsiPackageStatement.class);
        String packageString = psiPackageStatement.getPackageName();
        JLabel classNameLabel = new JLabel("   " + packageString + "." + loci.getClassWithSmell().getPsiClass().getName());
        // Production class.
        parentFile = (PsiFile) PsiTreeUtil.getParentOfType(loci.getProductionClass().getPsiClass(), PsiFile.class);
        psiPackageStatement = PsiTreeUtil.getChildOfType(parentFile, PsiPackageStatement.class);
        packageString = psiPackageStatement.getPackageName();
        JLabel pClassNameLabel = new JLabel(packageString + "." + loci.getProductionClass().getPsiClass().getName());

        JButton refactoringButton = new JButton("Do Refactor");
        JButton methodButton = new JButton("details");
        JButton contextualAnalysisButton = new JButton("Execute contextual analysis");

        methodButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame detailsFrame = new JFrame(loci.getClassWithSmell().getPsiClass().getName());
                Container cp = detailsFrame.getContentPane();
                cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));

                //Parte relativa alla refactoring tips
                JLabel refactoringLabel = new JLabel("The Smell can be removed using one of this refactoring operations:");
                JLabel firstRefactoringLabel = new JLabel("Extract method: the method can be move in an other class");
                JLabel secondRefactoringLabel = new JLabel("Extract class: the test class can be split into two separated classes");

                refactoringLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                firstRefactoringLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                secondRefactoringLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

                cp.add(refactoringLabel);
                cp.add(firstRefactoringLabel);
                cp.add(secondRefactoringLabel);

                //Parte relativa alla creazione del frame
                detailsFrame.setSize(500,300);
                detailsFrame.setVisible(true);
            }
        });

        //parte relativa all'azione del bottone per il refactoring automatico

        refactoringButton.addActionListener(e -> {

            IRefactor refactor = new LackOfCohesionStrategy(loci, project);
            try {
                refactor.doRefactor();
            } catch (PrepareFailedException e1) {
                e1.printStackTrace();
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
                    VirtualFile[] libraries = OrderEnumerator.orderEntries(project).runtimeOnly().librariesOnly().getClassesRoots();
                    ArrayList<String> librariesPaths = new ArrayList<>();
                    for(VirtualFile file : libraries){
                        librariesPaths.add(file.getPath());
                    }
                    projectAnalysis.setLibrariesPaths(librariesPaths);
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
                                new ContextualAnalysisFrame(project, loci, projectAnalysis);

                            } } catch(Exception ex){
                            ex.printStackTrace();
                        }


                    }
                }
            }
            //new RepoDriller().start(new DataMiner(eti, project.getBasePath()));
        });

        this.setLayout(new GridLayout(1,3));
        this.add(classNameLabel);
        this.add(pClassNameLabel);
        this.add(methodButton);
        this.add(refactoringButton);
        this.add(contextualAnalysisButton);
    }



}
