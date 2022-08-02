package testSmellDetection.testSmellInfo.lackOfCohesion;

import testSmellDetection.bean.PsiClassBean;
import testSmellDetection.bean.PsiMethodBean;
import testSmellDetection.testSmellInfo.TestSmellInfo;

import java.util.ArrayList;

public class LackOfCohesionInfo extends TestSmellInfo {
    private PsiClassBean productionClass;
    private ArrayList<PsiMethodBean> methodsThatCauseLackOfCohesion;

    public LackOfCohesionInfo(PsiClassBean classWithLackOfCohesion, PsiClassBean productionClass, ArrayList<PsiMethodBean> methodsThatCauseLackOfCohesion) {
        super(classWithLackOfCohesion);
        this.productionClass = productionClass;
        this.methodsThatCauseLackOfCohesion = methodsThatCauseLackOfCohesion;
    }

    @Override
    public String toString() {
        return "LackOfCohesionInfo{" +
                "classWithLackOfCohesion=" + classWithSmell +
                ", productionClass=" + productionClass +
                ", methodsThatCauseLackOfCohesion=" + methodsThatCauseLackOfCohesion +
                '}';
    }

    /* GETTERS & SETTERS */
    public PsiClassBean getClassWithSmell() {
        return classWithSmell;
    }

    public void setClassWithSmell(PsiClassBean classWithSmell) {
        this.classWithSmell = classWithSmell;
    }

    public PsiClassBean getProductionClass() {
        return productionClass;
    }

    public void setProductionClass(PsiClassBean productionClass) {
        this.productionClass = productionClass;
    }

    public ArrayList<PsiMethodBean> getMethodsThatCauseLackOfCohesion() {
        return methodsThatCauseLackOfCohesion;
    }

    public void setMethodsThatCauseLackOfCohesion(ArrayList<PsiMethodBean> methodsThatCauseLackOfCohesion) {
        this.methodsThatCauseLackOfCohesion = methodsThatCauseLackOfCohesion;
    }
}
