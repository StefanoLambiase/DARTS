package testSmellDetection.testSmellInfo.testCodeDuplication;

import testSmellDetection.bean.PsiClassBean;
import testSmellDetection.bean.PsiMethodBean;
import testSmellDetection.testSmellInfo.TestSmellInfo;
import testSmellDetection.testSmellInfo.eagerTest.MethodWithEagerTest;

import java.util.ArrayList;

public class TestCodeDuplicationInfo extends TestSmellInfo {
    private ArrayList<MethodWithTestCodeDuplication> methodsThatCauseTestCodeDuplication;

    public TestCodeDuplicationInfo(PsiClassBean classWithTestCodeDuplication, ArrayList<MethodWithTestCodeDuplication> methodsThatCauseTestCodeDuplication) {
        super(classWithTestCodeDuplication);
        this.methodsThatCauseTestCodeDuplication = methodsThatCauseTestCodeDuplication;
    }

    public ArrayList<MethodWithTestCodeDuplication> getMethodsThatCauseTestCodeDuplication() {
        return methodsThatCauseTestCodeDuplication;
    }

    public void setMethodsThatCauseTestCodeDuplication(ArrayList<MethodWithTestCodeDuplication> methodsThatCauseTestCodeDuplication) {
        this.methodsThatCauseTestCodeDuplication = methodsThatCauseTestCodeDuplication;
    }

    @Override
    public String toString() {
        return "TestCodeDuplicationInfo{" +
                "classWithSmell=" + classWithSmell +
                ", methodsThatCauseTestCodeDuplication=" + methodsThatCauseTestCodeDuplication +
                '}';
    }
}
