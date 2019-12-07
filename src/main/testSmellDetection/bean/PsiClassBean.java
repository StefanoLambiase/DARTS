package main.testSmellDetection.bean;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiPackage;

import java.util.ArrayList;

public class PsiClassBean {
    private PsiClass psiClass;
    private PsiPackage psiPackage;
    private ArrayList<PsiMethodBean> psiMethodBeans;

    private PsiClassBean productionClass;

    public PsiClassBean(PsiClass psiClass, ArrayList<PsiMethodBean> psiMethodBeans) {
        this.psiClass = psiClass;
        this.psiMethodBeans = psiMethodBeans;
        productionClass = null;
    }

    public PsiClassBean(PsiClass psiClass, ArrayList<PsiMethodBean> psiMethodBeans, PsiClassBean productionClass) {
        this.psiClass = psiClass;
        this.psiMethodBeans = psiMethodBeans;
        this.productionClass = productionClass;
    }

    public PsiClassBean(PsiClass psiClass, PsiPackage psiPackage) {
        this.psiClass = psiClass;
        this.psiPackage = psiPackage;
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

    public PsiClassBean getProductionClass() {
        return productionClass;
    }

    public void setProductionClass(PsiClassBean productionClass) {
        this.productionClass = productionClass;
    }

    public PsiPackage getPsiPackage() {
        return psiPackage;
    }

    public void setPsiPackage(PsiPackage psiPackage) {
        this.psiPackage = psiPackage;
    }
}
