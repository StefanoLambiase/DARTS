package main.refactor.strategy;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.refactoring.extractMethod.ExtractMethodHandler;
import com.intellij.refactoring.extractMethod.ExtractMethodProcessor;
import com.intellij.refactoring.extractMethod.PrepareFailedException;
import com.intellij.refactoring.extractclass.ExtractClassProcessor;
import it.unisa.testSmellDiffusion.beans.ClassBean;
import it.unisa.testSmellDiffusion.beans.InstanceVariableBean;
import it.unisa.testSmellDiffusion.beans.MethodBean;
import it.unisa.testSmellDiffusion.testSmellInfo.generalFixture.GeneralFixtureInfo;
import it.unisa.testSmellDiffusion.testSmellInfo.generalFixture.MethodWithGeneralFixture;
import it.unisa.testSmellDiffusion.utility.TestSmellUtilities;
import main.refactor.IRefactor;

import java.util.*;

public class GeneralFixtureStrategy implements IRefactor {
    private GeneralFixtureInfo generalFixtureInfo;
    private MethodWithGeneralFixture methodWithGeneralFixture;
    private Project project;
    private ExtractClassProcessor processor;
    private Editor editor;

    public GeneralFixtureStrategy(MethodWithGeneralFixture methodWithGeneralFixture, Project project, GeneralFixtureInfo generalFixtureInfo){
        this.methodWithGeneralFixture = methodWithGeneralFixture;
        this.project = project;
        this.generalFixtureInfo = generalFixtureInfo;
    }

    @Override
    public void doRefactor() throws PrepareFailedException {
        ClassBean originalClass = generalFixtureInfo.getTestClass();
        PsiClass psiOriginalClass = PsiUtil.getPsi(originalClass, project);
        String packageName = originalClass.getBelongingPackage();

        List<PsiMethod> methodsToMove = new ArrayList<>();
        List<PsiField> fieldsToMove = new ArrayList<>();
        List<PsiClass> innerClasses = new ArrayList<>();

        MethodBean method = methodWithGeneralFixture.getMethod();
        PsiMethod infectedMethod = PsiUtil.getPsi(method, project, psiOriginalClass);
        methodsToMove.add(infectedMethod);

        Vector<InstanceVariableBean> instances = (Vector<InstanceVariableBean>) method.getUsedInstanceVariables();

        PsiElement[] elementsToMove = new PsiElement[instances.size()];

        if(instances.size() > 0) {

            for (int i = 0; i < instances.size(); i++) {
                fieldsToMove.add(psiOriginalClass.findFieldByName(instances.get(i).getName(), true));
            }
        }

        for(PsiClass innerClass: psiOriginalClass.getInnerClasses()){
            innerClasses.add(innerClass);
        }

        Collection<MethodBean> allMethods = generalFixtureInfo.getTestClass().getMethods();
        MethodBean setup = null;
        for(MethodBean metodo: allMethods) {
            PsiMethod metodo2 = PsiUtil.getPsi(metodo, project, psiOriginalClass);

            for (int i = 0; i < instances.size(); i++) {
                if (metodo.getUsedInstanceVariables().contains(instances.get(i)) &&
                        !methodsToMove.contains(metodo2)) {

                    if (!metodo.getName().equals("setUp") && !methodsToMove.contains(metodo2)) {
                        methodsToMove.add(metodo2);
                    } else if (metodo.getName().equals("setUp")) {
                        setup = metodo;
                    }
                }
            }
        }

        if(setup != null) {
            PsiMethod psiSetup = PsiUtil.getPsi(setup, project, psiOriginalClass);
            int k = 0;
            for (int j = 0; j < psiSetup.getBody().getStatements().length; j++) {
                PsiElement statement = psiSetup.getBody().getStatements()[j].getFirstChild();

                if (statement instanceof PsiAssignmentExpression) {
                    PsiElement element = ((PsiAssignmentExpression) statement).getLExpression();

                    for (int i = 0; i < instances.size(); i++) {

                        if (element.getText().equals(instances.get(i).getName())) {
                            elementsToMove[k++] = statement;
                        }
                    }
                }
            }
            editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
            ExtractMethodProcessor methodProcessor = new ExtractMethodProcessor(project, editor, elementsToMove, null, "setUpRefactored", "setUp", null);

            if (methodProcessor.prepare()) {
                methodProcessor.setMethodVisibility(PsiModifier.PUBLIC);
                methodProcessor.testPrepare();
                methodProcessor.testNullability();
                ExtractMethodHandler.extractMethod(project, methodProcessor);
            }

            //Creo l'annotation "Before" da aggiungere al metodo appena estratto
            PsiAnnotation annotation = JavaPsiFacade.getElementFactory(project).createAnnotationFromText("@Before", methodProcessor.getExtractedMethod().getContext());

            //Aggiungo l'annotation creata al metodo
            WriteCommandAction.runWriteCommandAction(project, () -> {
                psiOriginalClass.addBefore(annotation, methodProcessor.getExtractedMethod());
            });

            methodsToMove.add(methodProcessor.getExtractedMethod());

            //Utile per cancellare la chiamata al nuovo metodo creato
            WriteCommandAction.runWriteCommandAction(project, () -> {
                PsiStatement[] statements = psiSetup.getBody().getStatements();
                String methodName = methodProcessor.getExtractedMethod().getName() +"();";
                for (PsiStatement statement : statements) {
                    if (statement.getText().equals(methodName)) {
                        statement.delete();
                    }
                }
            });
        }
        String classShortName = originalClass.getName() + "s";

        processor = new ExtractClassProcessor(
                psiOriginalClass,
                fieldsToMove,
                methodsToMove,
                innerClasses,
                packageName,
                classShortName);
        processor.setPreviewUsages(true);
        processor.run();
    }
}
