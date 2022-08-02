package testSmellDetection.testSmellInfo.generalFixture;

import com.intellij.psi.PsiVariable;
import testSmellDetection.bean.PsiMethodBean;

import java.util.ArrayList;

public class MethodWithGeneralFixture {
    private PsiMethodBean methodWithGeneralFixture;
    private ArrayList<PsiVariable> variablesNotInMethod;

    public MethodWithGeneralFixture(PsiMethodBean methodWithGeneralFixture, ArrayList<PsiVariable> variablesNotInMethod) {
        this.methodWithGeneralFixture = methodWithGeneralFixture;
        this.variablesNotInMethod = variablesNotInMethod;
    }

    /* GETTERS & SETTERS */
    public PsiMethodBean getMethodWithGeneralFixture() {
        return methodWithGeneralFixture;
    }

    public void setMethodWithGeneralFixture(PsiMethodBean methodWithGeneralFixture) {
        this.methodWithGeneralFixture = methodWithGeneralFixture;
    }

    public ArrayList<PsiVariable> getVariablesNotInMethod() {
        return variablesNotInMethod;
    }

    public void setVariablesNotInMethod(ArrayList<PsiVariable> variablesNotInMethod) {
        this.variablesNotInMethod = variablesNotInMethod;
    }
}
