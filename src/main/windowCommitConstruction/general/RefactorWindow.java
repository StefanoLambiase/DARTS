package main.windowCommitConstruction.general;

import com.intellij.openapi.project.Project;
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
    private JPanel buttonPanel;


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

        doRefactorButton.addActionListener(this);
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

        doRefactorButton.addActionListener(this);
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

        doRefactorButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            if(generalFixtureInfo != null){
                IRefactor refactor = new GeneralFixtureStrategy(methodWithGeneralFixture, project, generalFixtureInfo);
                refactor.doRefactor();
                gfSmellPanel.doAfterRefactor(methodWithGeneralFixture.getMethodWithGeneralFixture().getName());
            } else if(eagerTestInfo != null){
                IRefactor refactor = new EagerTestStrategy(methodWithEagerTest, project, eagerTestInfo);
                refactor.doRefactor();
                etSmellPanel.doAfterRefactor(methodWithEagerTest.getMethodWithEagerTest().getName());
            } else if(lackOfCohesionInfo != null){
                IRefactor refactor = new LackOfCohesionStrategy(lackOfCohesionInfo, project);
                refactor.doRefactor();
                locSmellPanel.doAfterRefactor(methodWithLOC.getName());
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
