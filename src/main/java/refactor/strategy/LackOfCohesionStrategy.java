package refactor.strategy;

import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.refactoring.extractclass.ExtractClassProcessor;
import refactor.IRefactor;
import stats.Action;
import stats.Stats;
import testSmellDetection.bean.PsiClassBean;
import testSmellDetection.bean.PsiMethodBean;
import testSmellDetection.testSmellInfo.lackOfCohesion.LackOfCohesionInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

        PsiClassBean originalClassBean = informations.getClassWithSmell();
        PsiClass psiOriginalClass = originalClassBean.getPsiClass();
        //PsiClass psiOriginalClass = PsiUtil.getPsi(originalClass, project);
        String packageName = originalClassBean.getPsiPackage().getName();

        ArrayList<PsiMethodBean> infectedMethodList = informations.getMethodsThatCauseLackOfCohesion();
        ArrayList<PsiMethodBean> allMethods = originalClassBean.getPsiMethodBeans();

        for(PsiMethod method: psiOriginalClass.getMethods()){
            method.getName();
        }
        PsiMethod setup = null;
        for (PsiMethod metodo : psiOriginalClass.getAllMethods()) {
            if (metodo.getName().equals("setUp")) {
                setup = metodo;
            }
        }
        if (setup != null) {
            //PsiMethod psiSetup = PsiUtil.getPsi(setup, project, psiOriginalClass);
            //PsiMethod psiSetup = setup.getPsiMethod();

            for (int j = 0; j < setup.getBody().getStatements().length; j++) {
                PsiElement statement = setup.getBody().getStatements()[j].getFirstChild();
                PsiElement element = ((PsiAssignmentExpression) statement).getLExpression();

                for (PsiMethodBean metodo : infectedMethodList) {
                    //PsiMethod psiMethod = PsiUtil.getPsi(metodo, project, psiOriginalClass);
                    PsiMethod psiMethod = metodo.getPsiMethod();

                    ArrayList<PsiVariable> instances = metodo.getUsedInstanceVariables();

                    for (int i = 0; i < instances.size(); i++) {

                        if (element.getText().equals(instances.get(i).getName())) {
                            methodsToMove.add(psiMethod);
                            fieldsToMove.add(psiOriginalClass.findFieldByName(instances.get(i).getName(), true));
                        }
                    }
                }
            }

            if (!methodsToMove.isEmpty()) {
                methodsToMove.add(setup);
            }

            for (PsiClass innerClass : psiOriginalClass.getInnerClasses()) {
                innerClasses.add(innerClass);
            }

            String classShortName = "Refactored" + originalClassBean.getName();

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

    public void doAfterRefactor() {
        PsiClassBean originalClassBean = this.informations.getClassWithSmell();

        Action action = new Action();
        action.setClassName(originalClassBean.getPsiClass().getName());
        action.setMethodName(this.informations.getMethodsThatCauseLackOfCohesion().get(0).getPsiMethod().getName());
        action.setPackageName(originalClassBean.getPsiPackage().getName());
        action.setSmellKind(Action.SmellKindEnum.LACK_OF_COHESION);
        action.setActionKind(Action.ActionKindEnum.REFACTORING_PREVIEW);
        action.setTimestamp(new Date().getTime());
        action.setActionCanceled(false);
        action.setActionDone(true);

        Stats.getInstance().getLastSession().getActions().add(action);
        System.out.println("adding action:" + action);
    }
}