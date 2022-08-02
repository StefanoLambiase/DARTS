package testSmellDetection.testSmellInfo.generalFixture;

import testSmellDetection.bean.PsiClassBean;
import testSmellDetection.testSmellInfo.TestSmellInfo;

import java.util.ArrayList;

public class GeneralFixtureInfo extends TestSmellInfo {
    private ArrayList<MethodWithGeneralFixture> methodsThatCauseGeneralFixture;

    public GeneralFixtureInfo(PsiClassBean classWithGeneralFixture, ArrayList<MethodWithGeneralFixture> methodsThatCauseGeneralFixture) {
        super(classWithGeneralFixture);
        this.methodsThatCauseGeneralFixture = methodsThatCauseGeneralFixture;
    }

    @Override
    public String toString() {
        return "GeneralFixtureInfo{" +
                "classWithGeneralFixture=" + classWithSmell +
                ", methodsThatCauseGeneralFixture=" + methodsThatCauseGeneralFixture +
                '}';
    }

    /* GETTERS & SETTERS */
    public PsiClassBean getClassWithGeneralFixture() {
        return classWithSmell;
    }

    public void setClassWithGeneralFixture(PsiClassBean classWithGeneralFixture) {
        this.classWithSmell = classWithGeneralFixture;
    }

    public ArrayList<MethodWithGeneralFixture> getMethodsThatCauseGeneralFixture() {
        return methodsThatCauseGeneralFixture;
    }

    public void setMethodsThatCauseGeneralFixture(ArrayList<MethodWithGeneralFixture> methodsThatCauseGeneralFixture) {
        this.methodsThatCauseGeneralFixture = methodsThatCauseGeneralFixture;
    }
}
