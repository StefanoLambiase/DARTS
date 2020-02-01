package main.testSmellDetection.testSmellInfo;

import main.testSmellDetection.bean.PsiClassBean;

public class TestSmellInfo {
    protected PsiClassBean classWithSmell;

    public TestSmellInfo(PsiClassBean clallWithSmell) {
        this.classWithSmell = clallWithSmell;
    }

    public PsiClassBean getClassWithSmell() {
        return classWithSmell;
    }

    public void setClassWithSmell(PsiClassBean clallWithSmell) {
        this.classWithSmell = clallWithSmell;
    }
}
