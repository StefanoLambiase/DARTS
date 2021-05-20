package windowCommitConstruction.general;

import com.harukizaemon.simian.Block;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.OrderEnumerator;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiSubstitutor;
import com.intellij.psi.PsiVariable;
import com.intellij.refactoring.extractMethod.PrepareFailedException;
import refactor.IRefactor;
import refactor.strategy.EagerTestStrategy;
import refactor.strategy.GeneralFixtureStrategy;
import refactor.strategy.LackOfCohesionStrategy;
import testSmellDetection.bean.PsiMethodBean;
import testSmellDetection.testSmellInfo.TestSmellInfo;
import testSmellDetection.testSmellInfo.eagerTest.EagerTestInfo;
import testSmellDetection.testSmellInfo.eagerTest.MethodWithEagerTest;
import testSmellDetection.testSmellInfo.generalFixture.GeneralFixtureInfo;
import testSmellDetection.testSmellInfo.generalFixture.MethodWithGeneralFixture;
import testSmellDetection.testSmellInfo.lackOfCohesion.LackOfCohesionInfo;
import testSmellDetection.testSmellInfo.testCodeDuplication.MethodWithTestCodeDuplication;
import testSmellDetection.testSmellInfo.testCodeDuplication.TestCodeDuplicationInfo;
import utility.TestSmellUtilities;
import windowCommitConstruction.testSmellPanel.ETSmellPanel;
import windowCommitConstruction.testSmellPanel.GFSmellPanel;
import windowCommitConstruction.testSmellPanel.LOCSmellPanel;
import windowCommitConstruction.contextualAnalysisPanel.ContextualAnalysisFrame;
import windowCommitConstruction.testSmellPanel.TCDSmellPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

public class RefactorWindow extends JPanel implements ActionListener{
    private JPanel rootPanel;
    private JButton doRefactorButton;
    private JPanel refactorPanel;
    private JPanel methodBodyPanel;
    private JPanel tipsPanel;
    private JTextArea methodTextArea;
    private JLabel tipsTextLabel;
    private JButton refactorPreviewButton;
    private JButton executeContextualAnalysis;


    private MethodWithGeneralFixture methodWithGeneralFixture;
    private MethodWithEagerTest methodWithEagerTest;
    private PsiMethodBean methodWithLOC;
    private MethodWithTestCodeDuplication methodWithTestCodeDuplication;

    private GeneralFixtureInfo generalFixtureInfo = null;
    private EagerTestInfo eagerTestInfo = null;
    private LackOfCohesionInfo lackOfCohesionInfo = null;
    private TestCodeDuplicationInfo testCodeDuplicationInfo = null;

    private Project project;

    private GFSmellPanel gfSmellPanel;
    private ETSmellPanel etSmellPanel;
    private LOCSmellPanel locSmellPanel;
    private TCDSmellPanel tcdSmellPanel;

    /**
     * Call this for General Fixture Panel.
     * @param methodWithGeneralFixture
     * @param generalFixtureInfo
     * @param project
     */
    public RefactorWindow(MethodWithGeneralFixture methodWithGeneralFixture, GeneralFixtureInfo generalFixtureInfo, Project project, GFSmellPanel gfSmellPanel) {
        super();
        this.methodWithGeneralFixture = methodWithGeneralFixture;
        this.generalFixtureInfo = generalFixtureInfo;
        this.project = project;
        this.gfSmellPanel = gfSmellPanel;

        String methodName = "<html> Method " + methodWithGeneralFixture.getMethodWithGeneralFixture().getPsiMethod().getName() + " is affected by General Fixture because it doesn't use the following variables: <br/>";

        for(PsiVariable instance : methodWithGeneralFixture.getVariablesNotInMethod()){
            methodName = methodName + "   - " + instance.getText() + "<br/>";
        }

        methodName = methodName + "<br/>The Smell will be removed using these refactoring operations:<br/>";
        methodName = methodName + "   - Extract method: setup method will be splitted into two different methods<br/>";
        methodName = methodName + "   - Extract class: the test class will be splitted into two separated classes</html>";

        tipsTextLabel.setText(methodName);

        String signature = methodWithGeneralFixture.getMethodWithGeneralFixture().getPsiMethod().getSignature(PsiSubstitutor.EMPTY).toString();
        String methodBody = methodWithGeneralFixture.getMethodWithGeneralFixture().getPsiMethod().getBody().getText();
        signature = signature + " " + methodBody;
        methodTextArea.setText(signature);

        refactorPreviewButton.addActionListener(this);
//        setupContextualAnalysisButton(generalFixtureInfo);
    }

    /**
     * Call this for Eager Test Panel.
     * @param methodWithEagerTest
     * @param eagerTestInfo
     * @param project
     */
    public RefactorWindow(MethodWithEagerTest methodWithEagerTest, EagerTestInfo eagerTestInfo, Project project, ETSmellPanel etSmellPanel) {
        super();
        this.methodWithEagerTest = methodWithEagerTest;
        this.eagerTestInfo = eagerTestInfo;
        this.project = project;
        this.etSmellPanel = etSmellPanel;

        String methodName = "<html> Method " + methodWithEagerTest.getMethodWithEagerTest().getPsiMethod().getName() + " is affected by Eager Test because it calls the following methods: <br/>";

        for(PsiMethodBean mbCalled : methodWithEagerTest.getListOfMethodCalled()){
            methodName = methodName + "   - " + mbCalled.getPsiMethod().getName() + "<br/>";
        }

        methodName = methodName + "<br/>The Smell will be removed  using the following refactor operation:<br/>";
        methodName = methodName + "   - Extract method: affected method will be splitted into smaller methods, each one testing a specific behavior of the tested object.</html>";

        tipsTextLabel.setText(methodName);

        String signature = methodWithEagerTest.getMethodWithEagerTest().getPsiMethod().getSignature(PsiSubstitutor.EMPTY).toString();
        String methodBody = methodWithEagerTest.getMethodWithEagerTest().getPsiMethod().getBody().getText();
        signature = signature + " " + methodBody;
        methodTextArea.setText(signature);

        refactorPreviewButton.addActionListener(this);
//        setupContextualAnalysisButton(eagerTestInfo);
    }

    /**
     * Call this for Lack of Cohesion Panel.
     * @param lackOfCohesionInfo
     * @param project
     */
    public RefactorWindow(PsiMethodBean methodWithLOC, LackOfCohesionInfo lackOfCohesionInfo, Project project, LOCSmellPanel locSmellPanel) {
        super();
        this.methodWithLOC = methodWithLOC;
        this.lackOfCohesionInfo = lackOfCohesionInfo;
        this.project = project;
        this.locSmellPanel = locSmellPanel;

        String methodName = "<html> Method " + methodWithLOC.getPsiMethod().getName() + " is affected by Lack of Cohesion of Test Methods: <br/>";

        methodName = methodName + "<br/>The Smell will be removed using one of this refactoring operations:<br/>";
        methodName = methodName + "   - Extract method: setup method will be splitted into two different methods<br/>";
        methodName = methodName + "   - Extract class: the test class will be splitted into two separated classes</html>";

        tipsTextLabel.setText(methodName);

        String signature = methodWithLOC.getPsiMethod().getSignature(PsiSubstitutor.EMPTY).toString();
        String methodBody = methodWithLOC.getPsiMethod().getBody().getText();
        signature = signature + " " + methodBody;
        methodTextArea.setText(signature);

        refactorPreviewButton.addActionListener(this);
//        setupContextualAnalysisButton(lackOfCohesionInfo);
    }

    /**
     * Call this for Test Code Duplication.
     *
     * @param testCodeDuplicationInfo
     * @param project
     */
    public RefactorWindow(MethodWithTestCodeDuplication methodWithTestCodeDuplication, TestCodeDuplicationInfo testCodeDuplicationInfo, Project project, TCDSmellPanel tcdSmellPanel) {
        super();
        this.methodWithTestCodeDuplication = methodWithTestCodeDuplication;
        this.testCodeDuplicationInfo = testCodeDuplicationInfo;
        this.project = project;
        this.tcdSmellPanel = tcdSmellPanel;
        String methodName = "<html> Method " + methodWithTestCodeDuplication.getMethodWithTestCodeDuplication().getPsiMethod().getName() + " is affected by Test Code Duplication because it has the following repeated code blocks: <br/>";
        for (Map.Entry<String, ArrayList<Block>> entryBlock : methodWithTestCodeDuplication.getBlocksOfDuplicatedCode().entrySet()) {
            methodName = methodName + "   - Same block: <br/>";
            for (Block block : entryBlock.getValue()) {
                methodName = methodName + "&nbsp &nbsp &nbsp &nbsp - from line " + block.getStartLineNumber() + " to line " + block.getEndLineNumber() + "<br/>";
            }
            methodName = methodName + "<br/>";
        }
        methodName = methodName + "<br/>The Smell will be removed  using the following refactor operation:<br/>";
        methodName = methodName + "   - Extract Method.</html>";
        tipsTextLabel.setText(methodName);
        String signature = methodWithTestCodeDuplication.getMethodWithTestCodeDuplication().getPsiMethod().getSignature(PsiSubstitutor.EMPTY).toString();
        String methodBody = methodWithTestCodeDuplication.getMethodWithTestCodeDuplication().getPsiMethod().getBody().getText();
        signature = signature + " " + methodBody;
        methodTextArea.setText(signature);
        refactorPreviewButton.addActionListener(this);
//        setupContextualAnalysisButton(lackOfCohesionInfo);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            if(generalFixtureInfo != null){
                IRefactor refactor = new GeneralFixtureStrategy(methodWithGeneralFixture, project, generalFixtureInfo);
                refactor.doRefactor();
                gfSmellPanel.doAfterRefactor();
            } else if(eagerTestInfo != null){
                IRefactor refactor = new EagerTestStrategy(methodWithEagerTest, project, eagerTestInfo);
                refactor.doRefactor();
                etSmellPanel.doAfterRefactor();
            } else if(lackOfCohesionInfo != null){
                IRefactor refactor = new LackOfCohesionStrategy(lackOfCohesionInfo, project);
                refactor.doRefactor();
                locSmellPanel.doAfterRefactor();
            } else {
                System.out.println("\n\n" + TestSmellUtilities.ANSI_RED + "All Info are NULL\n");
            }
        } catch (PrepareFailedException exception){
            exception.printStackTrace();
        }
    }

    /**
     * Use this for have the principal panel.
     * @return the principal panel.
     */
    public JPanel getRootPanel() {
        return rootPanel;
    }

    /**
    * This method is called when generating panels and it's used to setup the Contextual Analysis button's action listener.
    * @param testSmellInfo
     */
//    private void setupContextualAnalysisButton(TestSmellInfo testSmellInfo) {
//        executeContextualAnalysis.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (testSmellInfo.getClassWithSmell().getProductionClass() == null) {
//                    JOptionPane.showMessageDialog(null, "Unable to retrieve the Production Class!");
//                } else {
//                    String userDir = System.getProperty("user.home");
//
//                    String pluginFolder = userDir + File.separator + "vitrum";
//                    String pluginFolderWin = userDir + "\\vitrum";
//                    File config = new File(pluginFolder + "/default_config.ini");
//                    File jacocoProp = new File(pluginFolder + "/jacoco-agent.properties");
//                    if (!config.exists()) {
//                        String output = "[NONDA]" +
//                                "\nname=Number of Non-Documented Assertions " +
//                                "\ndescription=Number of assert statements without a description " +
//                                "\ndetectionThreshold=1.0 " +
//                                "\nguardThreshold=3.0 " +
//                                "\nbelongingSmells=ASSERTION_ROULETTE " +
//                                "\n[APCMC]" +
//                                "\nname=Average Production Class Methods Calls " +
//                                "\ndescription=Number of production class' methods calls in the test suite, divided by the number of test methods " +
//                                "\ndetectionThreshold=1.0 " +
//                                "\nguardThreshold=3.0 " +
//                                "\nbelongingSmells=EAGER_TEST " +
//                                "\n[MEXR] " +
//                                "\nname=Methods using External Resources " +
//                                "\ndescription=Number of external resources uses made by test methods" +
//                                "\ndetectionThreshold=1.0 " +
//                                "\nguardThreshold=3.0 " +
//                                "\nbelongingSmells=MYSTERY_GUEST " +
//                                "\n[NEXEA] " +
//                                "\nname=Number of EXternal resources Existence Assumptions " +
//                                "\ndescription=Number of assumptions made in test methods about the existence of external resources (e.g. Files, Database) " +
//                                "\ndetectionThreshold=1.0 " +
//                                "\nguardThreshold=3.0 " +
//                                "\nbelongingSmells=RESOURCE_OPTIMISM " +
//                                "\n[GFMR]" +
//                                "\nname=General Fixture Methods Rate " +
//                                "\ndescription=The rate of test methods not using all the set-up variables defined " +
//                                "\ndetectionThreshold=1.0 " +
//                                "\nguardThreshold=3.0 " +
//                                "\nbelongingSmells=GENERAL_FIXTURE " +
//                                "\n[MTOOR] " +
//                                "\nname=Methods Testing Other Objects Rate " +
//                                "\ndescription=The rate of methods testing objects which are different from the production class " +
//                                "\ndetectionThreshold=1.0 " +
//                                "\nguardThreshold=3.0 " +
//                                "\nbelongingSmells=INDIRECT_TESTING " +
//                                "\n[TSEC] " +
//                                "\nname=toString invocations in Equality Checks " +
//                                "\ndescription=The number of toString invocations in equality checks " +
//                                "\ndetectionThreshold=1.0 " +
//                                "\nguardThreshold=3.0 " +
//                                "\nbelongingSmells=SENSITIVE_EQUALITY";
//                        File plugin = new File(pluginFolder);
//                        plugin.mkdirs();
//                        File plugin2 = new File(pluginFolderWin);
//                        plugin2.mkdirs();
//                        FileUtility.writeFile(output, pluginFolder + "/" + "default_config.ini");
//
//                    }
//                    if (!jacocoProp.exists()) {
//                        if (SystemInfo.getOsNameAndVersion().toLowerCase().contains("windows")) {
//                            pluginFolderWin = pluginFolderWin.replace("\\", "\\\\");
//                            String output = "destfile = " + pluginFolderWin + "\\\\jacoco.exec";
//                            FileUtility.writeFile(output, pluginFolderWin + "\\" + "jacoco-agent.properties");
//                        } else {
//                            String output = "destfile = " + pluginFolder + "/jacoco.exec";
//                            FileUtility.writeFile(output, pluginFolder + "/" + "jacoco-agent.properties");
//                        }
//                    }
//                    TestProjectAnalysis projectAnalysis = new TestProjectAnalysis();
//                    //Project proj = e.getData(PlatformDataKeys.PROJECT);
//                    String projectFolder = project.getBasePath();
//                    File root = new File(projectFolder);
//                    String srcPath = root.getAbsolutePath() + "/src";
//                    String mainPath = srcPath + "/main";
//                    String testPath = srcPath + "/test";
//                    File main = new File(mainPath);
//                    File test = new File(testPath);
//                    if (!main.exists() || !test.exists()) {
//                        JOptionPane.showMessageDialog(null, "PROJECT'S FOLDER STRUCTURE IS NOT CORRECT. PLEASE USE MAVEN DIRECTORY LAYOUT.");
//                    } else {
//                        boolean isMaven = false;
//                        for (File file : root.listFiles()) {
//                            if (file.isFile() && file.getName().equalsIgnoreCase("pom.xml"))
//                                isMaven = true;
//                        }
//                        projectAnalysis.setMaven(isMaven);
//                        String projectSDK = ProjectRootManager.getInstance(project).getProjectSdk().getHomePath();
//                        //LOGGER.info(projectSDK);
//                        String os = SystemInfo.getOsNameAndVersion();
//                        String javaPath;
//                        if (os.toLowerCase().contains("windows"))
//                            javaPath = projectSDK + "/bin/java.exe";
//                        else
//                            javaPath = projectSDK + "/bin/java";
//                        projectAnalysis.setName(project.getName());
//                        projectAnalysis.setPath(project.getBasePath());
//                        projectAnalysis.setJavaPath(javaPath);
//                        VirtualFile[] libraries = OrderEnumerator.orderEntries(project).runtimeOnly().librariesOnly().getClassesRoots();
//                        ArrayList<String> librariesPaths = new ArrayList<>();
//                        for (VirtualFile file : libraries) {
//                            librariesPaths.add(file.getPath());
//                        }
//                        projectAnalysis.setLibrariesPaths(librariesPaths);
//                        Vector<TestClassAnalysis> classAnalysis = new Vector<>();
//                        if ((test.isDirectory()) && (!test.isHidden())) {
//                            try {
//                                Vector<PackageBean> testPackages = FolderToJavaProjectConverter.convert(test.getAbsolutePath());
//                                if (testPackages.size() == 1 && testPackages.get(0).getClasses().size() == 0)
//                                    JOptionPane.showMessageDialog(null, "TESTING SOURCE FILES NOT FOUND");
//                                else {
//                                    Vector<PackageBean> packages = FolderToJavaProjectConverter.convert(mainPath);
//                                    projectAnalysis.setPackages(packages);
//                                    projectAnalysis.setTestPackages(testPackages);
//                                    projectAnalysis.setConfigPath(System.getProperty("user.home") + "/vitrum");
//                                    new ContextualAnalysisFrame(project, testSmellInfo, projectAnalysis);
//
//                                }
//                            } catch (Exception ex) {
//                                ex.printStackTrace();
//                            }
//
//
//                        }
//                    }
//                }
//            }
//        });
//    }
}
