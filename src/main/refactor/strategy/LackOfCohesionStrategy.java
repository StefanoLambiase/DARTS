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
import it.unisa.testSmellDiffusion.testSmellInfo.lackOfCohesion.LackOfCohesionInfo;
import main.refactor.IRefactor;
import org.eclipse.jdt.core.dom.Type;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

public class LackOfCohesionStrategy implements IRefactor {
    private Project project;
    private LackOfCohesionInfo informations;

    public LackOfCohesionStrategy(LackOfCohesionInfo informations, Project project) {
        this.informations = informations;
        this.project = project;
    }

    @Override
    public void doRefactor(){
        ExtractClassProcessor processor;

        List<PsiMethod> methodsToMove = new ArrayList<>();
        List<PsiField> fieldsToMove = new ArrayList<>();
        List<PsiClass> innerClasses = new ArrayList<>();

        ClassBean originalClass = informations.getTestClass();
        PsiClass psiOriginalClass = PsiUtil.getPsi(originalClass, project);
        String packageName = originalClass.getBelongingPackage();

        ArrayList<MethodBean> infectedMethodList = informations.getMethodsThatCauseLackOfCohesion();
        Collection<MethodBean> allMethods = informations.getTestClass().getMethods();

        MethodBean setup = null;
        for (MethodBean metodo : allMethods) {
            if (metodo.getName().equals("setUp")) {
                setup = metodo;
            }
        }
        if (setup != null) {
            PsiMethod psiSetup = PsiUtil.getPsi(setup, project, psiOriginalClass);

            for (int j = 0; j < psiSetup.getBody().getStatements().length; j++) {
                PsiElement statement = psiSetup.getBody().getStatements()[j].getFirstChild();
                PsiElement element = ((PsiAssignmentExpression) statement).getLExpression();

                for (MethodBean metodo : infectedMethodList) {
                    PsiMethod psiMethod = PsiUtil.getPsi(metodo, project, psiOriginalClass);
                    Vector<InstanceVariableBean> instances = (Vector<InstanceVariableBean>) metodo.getUsedInstanceVariables();

                    for (int i = 0; i < instances.size(); i++) {

                        if (element.getText().equals(instances.get(i).getName())) {
                            methodsToMove.add(psiMethod);
                            fieldsToMove.add(psiOriginalClass.findFieldByName(instances.get(i).getName(), true));
                        }
                    }
                }
            }

            if (!methodsToMove.isEmpty()) {
                methodsToMove.add(psiSetup);
            }

            for (PsiClass innerClass : psiOriginalClass.getInnerClasses()) {
                innerClasses.add(innerClass);
            }

            String classShortName = "Refactored" + originalClass.getName();

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
}