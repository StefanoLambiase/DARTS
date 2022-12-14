package testSmellDetection.testSmellInfo.hardCodedTestData;

import testSmellDetection.bean.PsiClassBean;
import testSmellDetection.bean.PsiMethodBean;
import testSmellDetection.testSmellInfo.TestSmellInfo;

import java.util.ArrayList;

public class HardCodedTestDataInfo extends TestSmellInfo {
    private ArrayList<MethodWithHardCodedTestData> methodsThatCauseHardCodedTestData;

    public HardCodedTestDataInfo(PsiClassBean classWithHardCodedTestData, ArrayList<MethodWithHardCodedTestData> methodsThatCauseHardCodedTestData) {
        super(classWithHardCodedTestData);
        this.methodsThatCauseHardCodedTestData = methodsThatCauseHardCodedTestData;
    }

    @Override
    public String toString() {
        return "HardCodedTestDataInfo{" +
                "classWithHardCodedTestDataInfo=" + classWithSmell +
                ", methodsThatCauseHardCodedTestData=" + methodsThatCauseHardCodedTestData +
                '}';
    }

    /* GETTERS & SETTERS */
    public PsiClassBean getClassWithSmell() {
        return classWithSmell;
    }

    public void setClassWithSmell(PsiClassBean classWithSmell) {
        this.classWithSmell = classWithSmell;
    }

    public ArrayList<MethodWithHardCodedTestData> getMethodsThatCauseHardCodedTestData() {
        return methodsThatCauseHardCodedTestData;
    }

    public void setMethodsThatCauseHardCodedTestData(ArrayList<MethodWithHardCodedTestData> methodsThatCauseHardCodedTestData) {
        this.methodsThatCauseHardCodedTestData = methodsThatCauseHardCodedTestData;
    }
}
