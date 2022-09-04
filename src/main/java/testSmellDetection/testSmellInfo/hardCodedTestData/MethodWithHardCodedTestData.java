package testSmellDetection.testSmellInfo.hardCodedTestData;

import com.intellij.psi.PsiExpression;
import testSmellDetection.bean.PsiMethodBean;

import java.util.ArrayList;

public class MethodWithHardCodedTestData {
    private PsiMethodBean methodWithHardCodedTestData;
    private ArrayList<PsiExpression> constantExpression;

    public MethodWithHardCodedTestData(PsiMethodBean methodWithHardCodedTestData, ArrayList<PsiExpression> constantExpression) {
        this.methodWithHardCodedTestData = methodWithHardCodedTestData;
        this.constantExpression = constantExpression;
    }

    /* GETTERS & SETTERS */
    public PsiMethodBean getMethodWithHardCodedTestData() {
        return methodWithHardCodedTestData;
    }

    public void setMethodWithHardCodedTestData(PsiMethodBean methodWithHardCodedTestData) {
        this.methodWithHardCodedTestData = methodWithHardCodedTestData;
    }

    public ArrayList<PsiExpression> getListOfMethodsCalledWithConstants() {
        return constantExpression;
    }

    public void setListOfConstantExpression(ArrayList<PsiExpression> constantExpression) {
        this.constantExpression = constantExpression;
    }
}
