package testSmellDetection.textualRules;

import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiMethodCallExpression;
import com.intellij.psi.PsiReference;
import testSmellDetection.bean.PsiClassBean;
import testSmellDetection.bean.PsiMethodBean;
import testSmellDetection.testSmellInfo.eagerTest.MethodWithEagerTest;

import java.util.ArrayList;

public abstract class EagerTestTextual {

    public static boolean isEagerTest(PsiClassBean testClass, PsiClassBean productionClass){
        for(PsiMethodBean psiMethodBeanInside : testClass.getPsiMethodBeans()){
            String methodName = psiMethodBeanInside.getPsiMethod().getName();
            if(!methodName.equals(testClass.getPsiClass().getName()) &&
                    !methodName.toLowerCase().equals("setup") &&
                    !methodName.toLowerCase().equals("teardown")){
                ArrayList<PsiMethodCallExpression> methodCalls = psiMethodBeanInside.getMethodCalls();
                if(methodCalls.size() > 1){
                    ArrayList<PsiMethodBean> methodsInProduction = productionClass.getPsiMethodBeans();
                    int count = 0;
                    for(PsiMethodCallExpression callExpression : methodCalls){
                        PsiReference reference = callExpression.getMethodExpression().getReference();
                        if(reference != null){
                            PsiMethod calledMethod = (PsiMethod) reference.resolve();
                            for(PsiMethodBean methodInProduction : methodsInProduction){
                                if(calledMethod.getName().toLowerCase().equals(methodInProduction.getPsiMethod().getName().toLowerCase())){
                                    count ++;
                                }
                            }
                        }
                    }
                    if(count > 1)
                        return true;
                }
            }
        }
        return false;
    }

    public static ArrayList<MethodWithEagerTest> checkMethodsThatCauseEagerTest(PsiClassBean testClass, PsiClassBean productionClass) {
        ArrayList<MethodWithEagerTest> methodWithEagerTests = new ArrayList<>();
        for(PsiMethodBean psiMethodBeanInside : testClass.getPsiMethodBeans()){
            String methodName = psiMethodBeanInside.getPsiMethod().getName();
            if(!methodName.equals(testClass.getPsiClass().getName()) &&
                    !methodName.toLowerCase().equals("setup") &&
                    !methodName.toLowerCase().equals("teardown")){
                boolean isWithEagerTest = false;
                ArrayList<PsiMethodBean> methodCalledThatCauseEagerTest = new ArrayList<>();
                ArrayList<PsiMethodCallExpression> methodCalls = psiMethodBeanInside.getMethodCalls();
                if(methodCalls.size() > 1){
                    ArrayList<PsiMethodBean> methodsInProduction = productionClass.getPsiMethodBeans();
                    int count = 0;
                    for(PsiMethodCallExpression callExpression : methodCalls){
                        PsiReference reference = callExpression.getMethodExpression().getReference();
                        if(reference != null){
                            PsiMethod calledMethod = (PsiMethod) reference.resolve();
                            for(PsiMethodBean methodInProduction : methodsInProduction){
                                if(calledMethod.getName().toLowerCase().equals(methodInProduction.getPsiMethod().getName().toLowerCase())){
                                    count ++;
                                    methodCalledThatCauseEagerTest.add(methodInProduction);
                                }
                            }
                        }
                    }
                    if(count > 1)
                        isWithEagerTest = true;
                    if(isWithEagerTest){
                        MethodWithEagerTest methodWithEagerTest = new MethodWithEagerTest(psiMethodBeanInside, methodCalledThatCauseEagerTest);
                        methodWithEagerTests.add(methodWithEagerTest);
                    }
                }
            }
        }
        if(methodWithEagerTests.isEmpty()){
            return null;
        } else {
            return methodWithEagerTests;
        }
    }

}
