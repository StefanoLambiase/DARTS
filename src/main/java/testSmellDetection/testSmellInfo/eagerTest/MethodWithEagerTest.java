package testSmellDetection.testSmellInfo.eagerTest;

import testSmellDetection.bean.PsiMethodBean;

import java.util.ArrayList;

public class MethodWithEagerTest {
    private PsiMethodBean methodWithEagerTest;
    private ArrayList<PsiMethodBean> listOfMethodCalled;

    public MethodWithEagerTest(PsiMethodBean methodWithEagerTest, ArrayList<PsiMethodBean> listOfMethodCalled) {
        this.methodWithEagerTest = methodWithEagerTest;
        this.listOfMethodCalled = listOfMethodCalled;
    }

    /* GETTERS & SETTERS */
    public PsiMethodBean getMethodWithEagerTest() {
        return methodWithEagerTest;
    }

    public void setMethodWithEagerTest(PsiMethodBean methodWithEagerTest) {
        this.methodWithEagerTest = methodWithEagerTest;
    }

    public ArrayList<PsiMethodBean> getListOfMethodCalled() {
        return listOfMethodCalled;
    }

    public void setListOfMethodCalled(ArrayList<PsiMethodBean> listOfMethodCalled) {
        this.listOfMethodCalled = listOfMethodCalled;
    }
}
