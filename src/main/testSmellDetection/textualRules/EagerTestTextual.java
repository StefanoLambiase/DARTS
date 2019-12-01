package main.testSmellDetection.textualRules;

import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiMethodCallExpression;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiVariable;
import main.testSmellDetection.bean.PsiClassBean;
import main.testSmellDetection.bean.PsiMethodBean;

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

}
