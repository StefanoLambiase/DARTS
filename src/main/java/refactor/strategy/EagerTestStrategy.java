package refactor.strategy;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.refactoring.extractMethod.*;
import com.intellij.refactoring.extractMethod.ExtractMethodHandler;
import com.intellij.refactoring.extractMethod.ExtractMethodProcessor;
import com.intellij.refactoring.extractMethod.PrepareFailedException;
import refactor.IRefactor;
import stats.Action;
import stats.Stats;
import testSmellDetection.bean.PsiClassBean;
import testSmellDetection.bean.PsiMethodBean;
import testSmellDetection.testSmellInfo.eagerTest.EagerTestInfo;
import testSmellDetection.testSmellInfo.eagerTest.MethodWithEagerTest;

import java.util.ArrayList;
import java.util.Date;

/* Classe utile per eseguire le tecniche di Refactoring atte all'eliminazione di Eager Test
 * implementa l'interfaccia IRefactor
 */
public class EagerTestStrategy implements IRefactor {
    private EagerTestInfo eagerTestInfo; //variabile utile per ottenere informazioni sulla classe e sul metodo affetto da Eager Test
    private MethodWithEagerTest methodWithEagerTest; // variabile utile per ottenere il metodo infetto
    private Project project; //progetto sul quale è stato invocato il plugin
    private Editor editor;  //editor utilizzato al momento dell'invocazione del plugin

    /*
     *costruttore usato per instanziare un IRefactor usabile ovunque
     */
    public EagerTestStrategy(MethodWithEagerTest methodWithEagerTest, Project project, EagerTestInfo eagerTestInfo) {
        this.methodWithEagerTest = methodWithEagerTest;
        this.project = project;
        this.eagerTestInfo = eagerTestInfo;
    }

    /*
     * metodo implementato tramite l'interfaccia
     * contiene la logica applicativa per la risoluzione dello smell
     */
    @Override
    public void doRefactor() throws PrepareFailedException {

        PsiClassBean classBeanPsi = eagerTestInfo.getClassWithEagerTest(); //test class contenente lo smell
        PsiClass classPsi = classBeanPsi.getPsiClass();
        //PsiClass classPsi = PsiUtil.getPsi(badClass, project); //Psi relativo alla test class
        String initialMethodName = methodWithEagerTest.getMethodWithEagerTest().getName(); //nome del metodo infetto
        boolean isEmpty = true; //usato per verifiche sull'array

        PsiMethodBean psiMethodBean = methodWithEagerTest.getMethodWithEagerTest();
        PsiMethod psiMethod = psiMethodBean.getPsiMethod();
        //PsiMethod psiMethod = PsiUtil.getPsi(badMethod, project, classPsi); //Psi relativo al metodo infetto
        PsiType type = psiMethod.getReturnType(); //tipo di ritorno del metodo

        ArrayList<PsiElement> elementsToMoveTemp = new ArrayList<>(); //elementi da spostare tramite extract method

        for (int i = 0; i < classPsi.getAllMethods().length; i++) {

            //cerco il metodo infetto tra tutti i metodi della classe di test
            if (classPsi.getAllMethods()[i].getName().equals(initialMethodName)) {

                for (int j = 0; j < classPsi.getAllMethods()[i].getBody().getStatements().length; j++) {

                    PsiElement statement = classPsi.getAllMethods()[i].getBody().getStatements()[j].getFirstChild();

                    if (statement instanceof PsiMethodCallExpression && statement.getFirstChild() instanceof PsiReferenceExpression) {
                        PsiReferenceExpression referenceExpression = (PsiReferenceExpression) statement.getFirstChild();
                        String identifier = referenceExpression.getReferenceName();

                        //verifico che l'array è vuoto
                        if (isEmpty) {
                            elementsToMoveTemp.add(classPsi.getAllMethods()[i].getBody().getStatements()[j]); //inserisco il primo statement da spostare
                            isEmpty = false; //setto al false in seguito al riempimento
                        } else {

                            PsiReferenceExpression psiReferenceExpression = (PsiReferenceExpression) elementsToMoveTemp.get(0).getFirstChild().getFirstChild();
                            String referenceName = psiReferenceExpression.getReferenceName();

                            //controllo se lo statement da spostare è lo stesso di quello presente nell'array
                            if (identifier.equals(referenceName)) {
                                elementsToMoveTemp.add(classPsi.getAllMethods()[i].getBody().getStatements()[j]);
                            }
                        }
                    }
                }
            }
        }
        PsiElement[] elementsToMove = elementsToMoveTemp.toArray( new PsiElement[elementsToMoveTemp.size()]); //conversione da arrayList ad array
        editor = FileEditorManager.getInstance(project).getSelectedTextEditor();

        //instanzio un processor per manipolare l'extract class
        ExtractMethodProcessor processor = new ExtractMethodProcessor(project, editor, elementsToMove, type, "ExtractMethodRefactoring", "refactoredOne", null);
        //controllo preliminare per inizializzare i parametri del processor
        if (processor.prepare()) {
            processor.testPrepare();
            //this method doesn't work and I don't know why.
            //processor.testNullability();
            if(processor.showDialog()) {
                ExtractMethodHandler.extractMethod(project, processor);
            }
        }

        if(processor.getExtractedMethod() != null) {

            //Creo l'annotation "@Test" da aggiungere al metodo appena estratto
            PsiAnnotation annotation = JavaPsiFacade.getElementFactory(project).createAnnotationFromText("@Test",processor.getExtractedMethod().getContext());

            //Aggiungo l'annotation creata al metodo
            WriteCommandAction.runWriteCommandAction(project, () -> {
                classPsi.addBefore(annotation, processor.getExtractedMethod());
            });

            //elimino la chiamata al metodo appena creato
            WriteCommandAction.runWriteCommandAction(project, () -> {
                PsiStatement[] statements = psiMethod.getBody().getStatements();
                String methodName = processor.getExtractedMethod().getName() + "();";
                for(PsiStatement statement : statements){
                    if(statement.getText().equals(methodName)){
                        statement.delete();
                    }
                }
            });
        }
    }

    public void doAfterRefactor() {
        PsiClassBean originalClassBean = eagerTestInfo.getClassWithEagerTest();
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
        action.setMethodName(methodWithEagerTest.getMethodWithEagerTest().getPsiMethod().getName());
        action.setPackageName(psiClassBean.getPsiPackage().getName());
        action.setSmellKind(Action.SmellKindEnum.EAGER_TEST);
        action.setActionKind(Action.ActionKindEnum.REFACTORING_PREVIEW);
        action.setTimestamp(new Date().getTime());
        action.setActionCanceled(false);
        action.setActionDone(true);
        Stats.getInstance().getLastSession().getActions().add(action);
        System.out.println("adding action: " + action);
    }
}
