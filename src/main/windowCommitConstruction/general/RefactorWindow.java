package main.windowCommitConstruction.general;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiSubstitutor;
import com.intellij.psi.PsiVariable;
import com.intellij.refactoring.extractMethod.PrepareFailedException;
import main.refactor.IRefactor;
import main.refactor.strategy.EagerTestStrategy;
import main.refactor.strategy.GeneralFixtureStrategy;
import main.refactor.strategy.LackOfCohesionStrategy;
import main.testSmellDetection.bean.PsiMethodBean;
import main.testSmellDetection.testSmellInfo.eagerTest.EagerTestInfo;
import main.testSmellDetection.testSmellInfo.eagerTest.MethodWithEagerTest;
import main.testSmellDetection.testSmellInfo.generalFixture.GeneralFixtureInfo;
import main.testSmellDetection.testSmellInfo.generalFixture.MethodWithGeneralFixture;
import main.testSmellDetection.testSmellInfo.lackOfCohesion.LackOfCohesionInfo;
import main.utility.TestSmellUtilities;
import main.windowCommitConstruction.testSmellPanel.ETSmellPanel;
import main.windowCommitConstruction.testSmellPanel.GFSmellPanel;
import main.windowCommitConstruction.testSmellPanel.LOCSmellPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RefactorWindow extends JPanel implements ActionListener{
    private JPanel rootPanel;
    private JButton doRefactorButton;
    private JPanel refactorPanel;
    private JPanel methodBodyPanel;
    private JPanel tipsPanel;
    private JTextArea methodTextArea;
    private JLabel tipsTextLabel;
    private JButton refactorPreviewButton;


    private MethodWithGeneralFixture methodWithGeneralFixture;
    private MethodWithEagerTest methodWithEagerTest;
    private PsiMethodBean methodWithLOC;

    private GeneralFixtureInfo generalFixtureInfo = null;
    private EagerTestInfo eagerTestInfo = null;
    private LackOfCohesionInfo lackOfCohesionInfo = null;

    private Project project;

    private GFSmellPanel gfSmellPanel;
    private ETSmellPanel etSmellPanel;
    private LOCSmellPanel locSmellPanel;

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

        String methodName = "<html> Method: " + methodWithGeneralFixture.getMethodWithGeneralFixture().getPsiMethod().getName() + " doesn't use the following variables: <br/>";

        for(PsiVariable instance : methodWithGeneralFixture.getVariablesNotInMethod()){
            methodName = methodName + "   - " + instance.getText() + "<br/>";
        }

        methodName = methodName + "<br/>The Smell can be removed using one of this refactoring operations:<br/>";
        methodName = methodName + "   - Extract method: setup method can be split into two different methods<br/>";
        methodName = methodName + "   - Extract class: the test class can be split into two separated classes</html>";

        tipsTextLabel.setText(methodName);

        String signature = methodWithGeneralFixture.getMethodWithGeneralFixture().getPsiMethod().getSignature(PsiSubstitutor.EMPTY).toString();
        String methodBody = methodWithGeneralFixture.getMethodWithGeneralFixture().getPsiMethod().getBody().getText();
        signature = signature + " " + methodBody;
        methodTextArea.setText(signature);

        refactorPreviewButton.addActionListener(this);
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

        String methodName = "<html> Method: " + methodWithEagerTest.getMethodWithEagerTest().getPsiMethod().getName() + " calls the following methods: <br/>";

        for(PsiMethodBean mbCalled : methodWithEagerTest.getListOfMethodCalled()){
            methodName = methodName + "   - " + mbCalled.getPsiMethod().getName() + "<br/>";
        }

        methodName = methodName + "<br/>The Smell can be removed using one of this refactoring operations:<br/>";
        methodName = methodName + "   - Extract method: affected method can be splitted into smaller methods, each one testing a specific behavior of the tested object.</html>";

        tipsTextLabel.setText(methodName);

        String signature = methodWithEagerTest.getMethodWithEagerTest().getPsiMethod().getSignature(PsiSubstitutor.EMPTY).toString();
        String methodBody = methodWithEagerTest.getMethodWithEagerTest().getPsiMethod().getBody().getText();
        signature = signature + " " + methodBody;
        methodTextArea.setText(signature);

        refactorPreviewButton.addActionListener(this);
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

        String methodName = "<html> Method: " + methodWithLOC.getPsiMethod().getName() + " is affected by Lack of Cohesion of Test Methods: <br/>";

        methodName = methodName + "<br/>The Smell can be removed using one of this refactoring operations:<br/>";
        methodName = methodName + "   - Extract method: setup method can be split into two different methods<br/>";
        methodName = methodName + "   - Extract class: the test class can be split into two separated classes</html>";

        tipsTextLabel.setText(methodName);

        String signature = methodWithLOC.getPsiMethod().getSignature(PsiSubstitutor.EMPTY).toString();
        String methodBody = methodWithLOC.getPsiMethod().getBody().getText();
        signature = signature + " " + methodBody;
        methodTextArea.setText(signature);

        refactorPreviewButton.addActionListener(this);
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
}
