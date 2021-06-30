package refactor.strategy;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.refactoring.extractMethod.ExtractMethodHandler;
import com.intellij.refactoring.extractMethod.ExtractMethodProcessor;
import com.intellij.refactoring.extractMethod.PrepareFailedException;
import com.intellij.refactoring.introduceVariable.*;
import refactor.IRefactor;
import testSmellDetection.bean.PsiClassBean;
import testSmellDetection.bean.PsiMethodBean;
import testSmellDetection.testSmellInfo.eagerTest.EagerTestInfo;
import testSmellDetection.testSmellInfo.eagerTest.MethodWithEagerTest;
import testSmellDetection.testSmellInfo.hardCodedTestData.HardCodedTestDataInfo;
import testSmellDetection.testSmellInfo.hardCodedTestData.MethodWithHardCodedTestData;

import java.util.ArrayList;

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
        for(PsiExpression constantExpression : constantExpressions) {
            introduceVariableHandler.invoke(project, editor, constantExpression);
        }
    }
}
