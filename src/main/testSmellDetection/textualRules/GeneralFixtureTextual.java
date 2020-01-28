package main.testSmellDetection.textualRules;

import com.intellij.psi.PsiVariable;
import main.testSmellDetection.bean.PsiClassBean;
import main.testSmellDetection.bean.PsiMethodBean;
import main.testSmellDetection.testSmellInfo.generalFixture.MethodWithGeneralFixture;

import java.util.ArrayList;

public abstract class GeneralFixtureTextual {

    public static boolean isGeneralFixture(PsiClassBean testClass){
        ArrayList<PsiMethodBean> methods = testClass.getPsiMethodBeans();
        for(PsiMethodBean methodBean : methods){
            if(methodBean.getPsiMethod().getName().toLowerCase().equals("setup")){
                ArrayList<PsiVariable> instanceVariableInitSetup = methodBean.getInitInstanceVariables();
                if(instanceVariableInitSetup.size() > 0){
                    for(PsiMethodBean psiMethodBeanInside : methods){
                        String methodName = psiMethodBeanInside.getPsiMethod().getName();
                        if(!methodName.equals(testClass.getPsiClass().getName()) &&
                                !methodName.toLowerCase().equals("setup") &&
                                !methodName.toLowerCase().equals("teardown")){
                            ArrayList<PsiVariable> instanceVariableUses = psiMethodBeanInside.getUsedInstanceVariables();
                            for(PsiVariable psiInstanceVariable : instanceVariableInitSetup){
                                if(!instanceVariableUses.contains(psiInstanceVariable))
                                    return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public static ArrayList<MethodWithGeneralFixture> checkMethodsThatCauseGeneralFixture(PsiClassBean testClass){
        ArrayList<MethodWithGeneralFixture> methodWithGeneralFixtures = new ArrayList<>();
        ArrayList<PsiMethodBean> methods = testClass.getPsiMethodBeans();
        for(PsiMethodBean methodBean : methods){
            if(methodBean.getPsiMethod().getName().toLowerCase().equals("setup")){
                ArrayList<PsiVariable> instanceVariableInitSetup = methodBean.getInitInstanceVariables();
                if(instanceVariableInitSetup.size() > 0){
                    for(PsiMethodBean psiMethodBeanInside : methods){
                        String methodName = psiMethodBeanInside.getPsiMethod().getName();
                        if(!methodName.equals(testClass.getPsiClass().getName()) &&
                                !methodName.toLowerCase().equals("setup") &&
                                !methodName.toLowerCase().equals("teardown")){
                            Boolean isWithGeneralFixture = false;
                            ArrayList<PsiVariable> instanceVariableNotUsed = new ArrayList<>();

                            ArrayList<PsiVariable> instanceVariableUses = psiMethodBeanInside.getUsedInstanceVariables();
                            for(PsiVariable psiInstanceVariable : instanceVariableInitSetup){
                                if(!instanceVariableUses.contains(psiInstanceVariable)) {
                                    isWithGeneralFixture = true;
                                    instanceVariableNotUsed.add(psiInstanceVariable);
                                }
                            }
                            if(isWithGeneralFixture){
                                MethodWithGeneralFixture methodWithGeneralFixture = new MethodWithGeneralFixture(psiMethodBeanInside, instanceVariableNotUsed);
                                methodWithGeneralFixtures.add(methodWithGeneralFixture);
                            }
                        }
                    }
                }
            }
        }
        return methodWithGeneralFixtures;
    }

}
