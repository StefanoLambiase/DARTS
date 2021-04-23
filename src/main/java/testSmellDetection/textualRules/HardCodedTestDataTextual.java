package testSmellDetection.textualRules;

import com.intellij.psi.*;
import com.intellij.psi.util.PsiUtil;
import testSmellDetection.bean.PsiClassBean;
import testSmellDetection.bean.PsiMethodBean;
import testSmellDetection.testSmellInfo.eagerTest.MethodWithEagerTest;
import testSmellDetection.testSmellInfo.hardCodedTestData.MethodWithHardCodedTestData;

import java.util.ArrayList;

public abstract class HardCodedTestDataTextual {
    public static ArrayList<MethodWithHardCodedTestData> checkMethodsThatCauseHardCodedTestData(PsiClassBean testClass) {
        ArrayList<MethodWithHardCodedTestData> methodsWithHardCodedTestData = new ArrayList<>();
        for(PsiMethodBean psiMethodBeanInside : testClass.getPsiMethodBeans()){
            String methodName = psiMethodBeanInside.getPsiMethod().getName();
            if(!methodName.equals(testClass.getPsiClass().getName()) &&
                    !methodName.toLowerCase().equals("setup") &&
                    !methodName.toLowerCase().equals("teardown")){
                ArrayList<PsiMethodCallExpression> methodsCalled = psiMethodBeanInside.getMethodCalls();
                ArrayList<PsiExpression> constantExpression = new ArrayList<>();
                for(PsiMethodCallExpression callExpression : methodsCalled){
                    PsiExpression[] psiExpressions = callExpression.getArgumentList().getExpressions();
                    for(PsiExpression psiExpression : psiExpressions) {
                        if(PsiUtil.isConstantExpression(psiExpression)) {
                            constantExpression.add(psiExpression);
//                            System.out.println("\u001B[35m");
//                            System.out.println(callExpression.getText());
//                            System.out.println(PsiUtil.isConstantExpression(callExpression.getArgumentList().getExpressions()[0]));
//                            System.out.println(callExpression.getArgumentList().getExpressions()[0].getType());
//                            System.out.println(callExpression.resolveMethod());
//                            System.out.println(callExpression.getArgumentList().getExpressionTypes()[0]);
//                            System.out.println(callExpression.getArgumentList().getExpressionTypes()[0].getResolveScope());
//                            System.out.println("\u001B[0m");
                        }
                    }
                }
                    if(!constantExpression.isEmpty()){
                        MethodWithHardCodedTestData methodWithHardCodedTestData = new MethodWithHardCodedTestData(psiMethodBeanInside, constantExpression);
                        methodsWithHardCodedTestData.add(methodWithHardCodedTestData);
                    }
            }
        }
        if(methodsWithHardCodedTestData.isEmpty()){
            return null;
        } else {
            return methodsWithHardCodedTestData;
        }
    }
}
