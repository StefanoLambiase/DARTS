package main.testSmellDetection.bean;

import com.intellij.psi.PsiClass;

import java.util.ArrayList;

public class PsiClassBean {
    private PsiClass psiClass;
    private ArrayList<PsiMethodBean> psiMethodBeans;

    public PsiClassBean(PsiClass psiClass, ArrayList<PsiMethodBean> psiMethodBeans) {
        this.psiClass = psiClass;
        this.psiMethodBeans = psiMethodBeans;
    }

    @Override
    public String toString() {
        return "PsiClassBean{" +
                "psiClass=" + psiClass +
                ", psiMethodBeans=" + psiMethodBeans +
                '}';
    }

    /* GETTERS & SETTERS */
    public PsiClass getPsiClass() {
        return psiClass;
    }

    public void setPsiClass(PsiClass psiClass) {
        this.psiClass = psiClass;
    }

    public ArrayList<PsiMethodBean> getPsiMethodBeans() {
        return psiMethodBeans;
    }

    public void setPsiMethodBeans(ArrayList<PsiMethodBean> psiMethodBeans) {
        this.psiMethodBeans = psiMethodBeans;
    }
}
