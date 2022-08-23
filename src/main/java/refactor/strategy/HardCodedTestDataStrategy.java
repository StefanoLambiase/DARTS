package refactor.strategy;

import com.intellij.lang.refactoring.RefactoringSupportProvider;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.refactoring.actions.IntroduceVariableAction;
import com.intellij.refactoring.extractMethod.ExtractMethodHandler;
import com.intellij.refactoring.extractMethod.ExtractMethodProcessor;
import com.intellij.refactoring.extractMethod.PrepareFailedException;
import com.intellij.refactoring.introduceVariable.*;
import refactor.IRefactor;
import stats.Action;
import stats.Stats;
import testSmellDetection.bean.PsiClassBean;
import testSmellDetection.bean.PsiMethodBean;
import testSmellDetection.testSmellInfo.eagerTest.EagerTestInfo;
import testSmellDetection.testSmellInfo.eagerTest.MethodWithEagerTest;
import testSmellDetection.testSmellInfo.hardCodedTestData.HardCodedTestDataInfo;
import testSmellDetection.testSmellInfo.hardCodedTestData.MethodWithHardCodedTestData;

import java.util.ArrayList;
import java.util.Date;

public class HardCodedTestDataStrategy implements IRefactor {
    private HardCodedTestDataInfo hardCodedTestDataInfo;
    private MethodWithHardCodedTestData methodWithHardCodedTestData;
    private Project project;
    private Editor editor;

    /*
     *costruttore usato per instanziare un IRefactor usabile ovunque
     */
    public HardCodedTestDataStrategy(MethodWithHardCodedTestData methodWithHardCodedTestData, Project project, HardCodedTestDataInfo hardCodedTestDataInfo) {
        this.methodWithHardCodedTestData = methodWithHardCodedTestData;
        this.project = project;
        this.hardCodedTestDataInfo = hardCodedTestDataInfo;
    }

    /*
     * metodo implementato tramite l'interfaccia
     * contiene la logica applicativa per la risoluzione dello smell
     */
    @Override
    public void doRefactor() throws PrepareFailedException {
        ArrayList<PsiExpression> constantExpressions = methodWithHardCodedTestData.getListOfMethodsCalledWithConstants();
        IntroduceVariableHandler introduceVariableHandler = new IntroduceVariableHandler();
        editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
        introduceVariableHandler.invoke(project, editor, constantExpressions.get(0));
//        for(PsiExpression constantExpression : constantExpressions) {
//            introduceVariableHandler.invoke(project, editor, constantExpression);
//        }
    }

    @Override
    public void doAfterRefactor() {
        PsiClassBean originalClassBean = hardCodedTestDataInfo.getClassWithSmell();
        getActionForStats(originalClassBean);
    }

    /**
     * Method that uses getter and setter's Action class, in order to obtain from psiClassBean
     * the result of the Eager Test smell that we ne need for Stats. Finally adding the action to the session.
     * @param psiClassBean
     */
    public void getActionForStats(PsiClassBean psiClassBean){
        Action action = new Action();
        action.setClassName(psiClassBean.getPsiClass().getName());
        action.setMethodName(methodWithHardCodedTestData.getMethodWithHardCodedTestData().getPsiMethod().getName());
        action.setPackageName(psiClassBean.getPsiPackage().getName());
        action.setSmellKind(Action.SmellKindEnum.HARD_CODED_TEST_DATA);
        action.setActionKind(Action.ActionKindEnum.REFACTORING_PREVIEW);
        action.setTimestamp(new Date().getTime());
        action.setActionCanceled(false);
        action.setActionDone(true);
        Stats.getInstance().getLastSession().getActions().add(action);
        System.out.println("adding action: " + action);
    }
}
