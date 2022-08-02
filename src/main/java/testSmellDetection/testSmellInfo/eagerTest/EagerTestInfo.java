package testSmellDetection.testSmellInfo.eagerTest;

import testSmellDetection.bean.PsiClassBean;
import testSmellDetection.testSmellInfo.TestSmellInfo;

import java.util.ArrayList;

public class EagerTestInfo extends TestSmellInfo {
    private PsiClassBean productionClass;
    private ArrayList<MethodWithEagerTest> methodsThatCauseEagerTest;

    public EagerTestInfo(PsiClassBean classWithEagerTest, PsiClassBean productionClass, ArrayList<MethodWithEagerTest> methodsThatCauseEagerTest) {
        super(classWithEagerTest);
        this.productionClass = productionClass;
        this.methodsThatCauseEagerTest = methodsThatCauseEagerTest;
    }

    @Override
    public String toString() {
        return "EagerTestInfo{" +
                "classWithEagerTest=" + classWithSmell +
                ", productionClass=" + productionClass +
                ", methodsThatCauseEagerTest=" + methodsThatCauseEagerTest +
                '}';
    }

    /* GETTERS & SETTERS */
    public PsiClassBean getClassWithEagerTest() {
        return classWithSmell;
    }

    public void setClassWithEagerTest(PsiClassBean classWithEagerTest) {
        this.classWithSmell = classWithEagerTest;
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
