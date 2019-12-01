package main.testSmellDetection.testSmellInfo.eagerTest;

import main.testSmellDetection.bean.PsiClassBean;

import java.util.ArrayList;

public class EagerTestInfo {
    private PsiClassBean classWithEagerTest;
    private PsiClassBean productionClass;
    private ArrayList<MethodWithEagerTest> methodsThatCauseEagerTest;

    public EagerTestInfo(PsiClassBean classWithEagerTest, PsiClassBean productionClass, ArrayList<MethodWithEagerTest> methodsThatCauseEagerTest) {
        this.classWithEagerTest = classWithEagerTest;
        this.productionClass = productionClass;
        this.methodsThatCauseEagerTest = methodsThatCauseEagerTest;
    }

    @Override
    public String toString() {
        return "EagerTestInfo{" +
                "classWithEagerTest=" + classWithEagerTest +
                ", productionClass=" + productionClass +
                ", methodsThatCauseEagerTest=" + methodsThatCauseEagerTest +
                '}';
    }

    /* GETTERS & SETTERS */
    public PsiClassBean getClassWithEagerTest() {
        return classWithEagerTest;
    }

    public void setClassWithEagerTest(PsiClassBean classWithEagerTest) {
        this.classWithEagerTest = classWithEagerTest;
    }

    public PsiClassBean getProductionClass() {
        return productionClass;
    }

    public void setProductionClass(PsiClassBean productionClass) {
        this.productionClass = productionClass;
    }

    public ArrayList<MethodWithEagerTest> getMethodsThatCauseEagerTest() {
        return methodsThatCauseEagerTest;
    }

    public void setMethodsThatCauseEagerTest(ArrayList<MethodWithEagerTest> methodsThatCauseEagerTest) {
        this.methodsThatCauseEagerTest = methodsThatCauseEagerTest;
    }
}
