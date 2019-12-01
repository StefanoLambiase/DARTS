package main.testSmellDetection.testSmellInfo.lackOfCohesion;

import main.testSmellDetection.bean.PsiClassBean;
import main.testSmellDetection.bean.PsiMethodBean;

import java.util.ArrayList;

public class LackOfCohesionInfo {
    private PsiClassBean classWithLackOfCohesion;
    private PsiClassBean productionClass;
    private ArrayList<PsiMethodBean> methodsThatCauseLackOfCohesion;

    public LackOfCohesionInfo(PsiClassBean classWithLackOfCohesion, PsiClassBean productionClass, ArrayList<PsiMethodBean> methodsThatCauseLackOfCohesion) {
        this.classWithLackOfCohesion = classWithLackOfCohesion;
        this.productionClass = productionClass;
        this.methodsThatCauseLackOfCohesion = methodsThatCauseLackOfCohesion;
    }

    @Override
    public String toString() {
        return "LackOfCohesionInfo{" +
                "classWithLackOfCohesion=" + classWithLackOfCohesion +
                ", productionClass=" + productionClass +
                ", methodsThatCauseLackOfCohesion=" + methodsThatCauseLackOfCohesion +
                '}';
    }

    /* GETTERS & SETTERS */
    public PsiClassBean getClassWithLackOfCohesion() {
        return classWithLackOfCohesion;
    }

    public void setClassWithLackOfCohesion(PsiClassBean classWithLackOfCohesion) {
        this.classWithLackOfCohesion = classWithLackOfCohesion;
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
