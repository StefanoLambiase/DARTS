package testSmellDetection.textualRules;

import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiMethodCallExpression;
import com.intellij.psi.util.PsiUtil;
import testSmellDetection.bean.PsiClassBean;
import testSmellDetection.bean.PsiMethodBean;
import testSmellDetection.testSmellInfo.hardCodedTestData.MethodWithHardCodedTestData;

import java.util.ArrayList;

public abstract class HardCodedTestDataTextual {
    public static ArrayList<MethodWithHardCodedTestData> checkMethodsThatCauseHardCodedTestData(PsiClassBean testClass) {
        ArrayList<MethodWithHardCodedTestData> methodsWithHardCodedTestData = new ArrayList<>();
        for (PsiMethodBean psiMethodBeanInside : testClass.getPsiMethodBeans()) {
            String methodName = psiMethodBeanInside.getPsiMethod().getName();
            if (!methodName.equals(testClass.getPsiClass().getName()) &&
                    !methodName.toLowerCase().equals("setup") &&
                    !methodName.toLowerCase().equals("teardown")) {
                ArrayList<PsiMethodCallExpression> methodsCalled = psiMethodBeanInside.getMethodCalls();
                ArrayList<PsiExpression> constantExpression = new ArrayList<>();
                for (PsiMethodCallExpression callExpression : methodsCalled) {
                    PsiExpression[] psiExpressions = callExpression.getArgumentList().getExpressions();
                    for (PsiExpression psiExpression : psiExpressions) {
                        if (PsiUtil.isConstantExpression(psiExpression)) {
                            constantExpression.add(psiExpression);
                        }
                    }
                }
                if (!constantExpression.isEmpty()) {
                    MethodWithHardCodedTestData methodWithHardCodedTestData = new MethodWithHardCodedTestData(psiMethodBeanInside, constantExpression);
                    methodsWithHardCodedTestData.add(methodWithHardCodedTestData);
                }
            }
        }
        if (methodsWithHardCodedTestData.isEmpty()) {
            return null;
        } else {
            return methodsWithHardCodedTestData;
        }
    }
}
