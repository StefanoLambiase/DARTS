package main.testSmellDetection.testSmellInfo.generalFixture;

import main.testSmellDetection.bean.PsiClassBean;

import java.util.ArrayList;

public class GeneralFixtureInfo {
    private PsiClassBean classWithGeneralFixture;
    private ArrayList<MethodWithGeneralFixture> methodsThatCauseGeneralFixture;

    public GeneralFixtureInfo(PsiClassBean classWithGeneralFixture, ArrayList<MethodWithGeneralFixture> methodsThatCauseGeneralFixture) {
        this.classWithGeneralFixture = classWithGeneralFixture;
        this.methodsThatCauseGeneralFixture = methodsThatCauseGeneralFixture;
    }

    @Override
    public String toString() {
        return "GeneralFixtureInfo{" +
                "classWithGeneralFixture=" + classWithGeneralFixture +
                ", methodsThatCauseGeneralFixture=" + methodsThatCauseGeneralFixture +
                '}';
    }

    /* GETTERS & SETTERS */
    public PsiClassBean getClassWithGeneralFixture() {
        return classWithGeneralFixture;
    }

    public void setClassWithGeneralFixture(PsiClassBean classWithGeneralFixture) {
        this.classWithGeneralFixture = classWithGeneralFixture;
    }

    public ArrayList<MethodWithGeneralFixture> getMethodsThatCauseGeneralFixture() {
        return methodsThatCauseGeneralFixture;
    }

    public void setMethodsThatCauseGeneralFixture(ArrayList<MethodWithGeneralFixture> methodsThatCauseGeneralFixture) {
        this.methodsThatCauseGeneralFixture = methodsThatCauseGeneralFixture;
    }
}
