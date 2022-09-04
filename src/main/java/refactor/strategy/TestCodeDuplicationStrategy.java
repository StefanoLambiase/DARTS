package refactor.strategy;
import com.harukizaemon.simian.Block;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.refactoring.extractMethod.ExtractMethodHandler;
import com.intellij.refactoring.extractMethod.PrepareFailedException;
import refactor.IRefactor;
import stats.Action;
import stats.Stats;
import testSmellDetection.bean.PsiClassBean;
import testSmellDetection.testSmellInfo.testCodeDuplication.MethodWithTestCodeDuplication;
import testSmellDetection.testSmellInfo.testCodeDuplication.TestCodeDuplicationInfo;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
public class TestCodeDuplicationStrategy implements IRefactor {
    private TestCodeDuplicationInfo testCodeDuplicationInfo;
    private MethodWithTestCodeDuplication methodWithTestCodeDuplication;
    private Project project;
    private Editor editor;
    /*
     *costruttore usato per instanziare un IRefactor usabile ovunque
     */
    public TestCodeDuplicationStrategy(MethodWithTestCodeDuplication methodWithTestCodeDuplication, Project project, TestCodeDuplicationInfo testCodeDuplicationInfo) {
        this.methodWithTestCodeDuplication = methodWithTestCodeDuplication;
        this.project = project;
        this.testCodeDuplicationInfo = testCodeDuplicationInfo;
    }
    /*
     * metodo implementato tramite l'interfaccia
     * contiene la logica applicativa per la risoluzione dello smell
     */
    @Override
    public void doRefactor() throws PrepareFailedException {
        PsiClassBean classBeanPsi = testCodeDuplicationInfo.getClassWithSmell();
        PsiClass classPsi = classBeanPsi.getPsiClass();
        // Ottengo il file corrente ed il riferimento al documento
        PsiDocumentManager documentManager = PsiDocumentManager.getInstance(project);
        PsiFile file = classPsi.getContainingFile();
        Document document = documentManager.getDocument(file);
        editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
        Map<String, ArrayList<Block>> duplicatedCodeBlock = methodWithTestCodeDuplication.getBlocksOfDuplicatedCode();
        ArrayList<PsiElement> elementsToMoveTemp;
        for (Map.Entry<String, ArrayList<Block>> entryBlock : duplicatedCodeBlock.entrySet()) {
            System.out.println(entryBlock.getKey());
            elementsToMoveTemp = new ArrayList<>();
            Block firstBlockRepetition = entryBlock.getValue().get(0);
            int offset = document.getLineStartOffset(firstBlockRepetition.getStartLineNumber() - 1);
            PsiElement element = file.getViewProvider().findElementAt(offset).getNextSibling();
            elementsToMoveTemp.add(element);
            while (element.getNextSibling() != null && document.getLineNumber(element.getNextSibling().getTextOffset()) <= firstBlockRepetition.getEndLineNumber() - 1) {
                element = element.getNextSibling();
                elementsToMoveTemp.add(element);
            }
            PsiElement[] elementsToMove = elementsToMoveTemp.toArray(new PsiElement[elementsToMoveTemp.size()]); //conversione da arrayList ad array
            for (PsiElement findElement : elementsToMove) System.out.println(findElement.getText());
            ExtractMethodHandler.invokeOnElements(project, editor, file, elementsToMove);
        }
    }

    @Override
    public void doAfterRefactor() {
        PsiClassBean originalClassBean = testCodeDuplicationInfo.getClassWithSmell();
        getActionForStats(originalClassBean);
    }

    /**
     * Method that uses getter and setter's Action class, in order to obtain from psiClassBean
     * the result of the General Fixture smell that we ne need for Stats. Finally adding the action to the session.
     * @param psiClassBean
     */
    public void getActionForStats(PsiClassBean psiClassBean){
        Action action = new Action();
        action.setClassName(psiClassBean.getPsiClass().getName());
        action.setMethodName(methodWithTestCodeDuplication.getMethodWithTestCodeDuplication().getPsiMethod().getName());
        action.setPackageName(psiClassBean.getPsiPackage().getName());
        action.setSmellKind(Action.SmellKindEnum.TEST_CODE_DUPLICATION);
        action.setActionKind(Action.ActionKindEnum.REFACTORING_PREVIEW);
        action.setTimestamp(new Date().getTime());
        action.setActionCanceled(false);
        action.setActionDone(true);

        Stats.getInstance().getLastSession().getActions().add(action);
        System.out.println("adding action:" + action);
    }
}