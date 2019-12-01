package main.testSmellDetection.bean;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiVariable;

import java.util.ArrayList;

public class PsiMethodBean {
    private PsiMethod psiMethod;
    private PsiClass psiClass;
    private ArrayList<PsiVariable> initInstanceVariables;
    private ArrayList<PsiVariable> usedInstanceVariables;

    public PsiMethodBean(PsiMethod psiMethod, PsiClass psiClass, ArrayList<PsiVariable> initInstanceVariables, ArrayList<PsiVariable> usedInstanceVariables) {
        this.psiMethod = psiMethod;
        this.psiClass = psiClass;
        this.initInstanceVariables = initInstanceVariables;
        this.usedInstanceVariables = usedInstanceVariables;
    }

    @Override
    public String toString() {
        return "PsiMethodBean{" +
                "psiMethod=" + psiMethod.toString() +
                ", initInstanceVariables=" + initInstanceVariables +
                ", usedInstanceVariables=" + usedInstanceVariables +
                '}';
    }

    /* GETTERS & SETTERS */
    public PsiMethod getPsiMethod() {
        return psiMethod;
    }

    public void setPsiMethod(PsiMethod psiMethod) {
        this.psiMethod = psiMethod;
    }

    public PsiClass getPsiClass() {
        return psiClass;
    }

    public void setPsiClass(PsiClass psiClass) {
        this.psiClass = psiClass;
    }

    public ArrayList<PsiVariable> getInitInstanceVariables() {
        return initInstanceVariables;
    }

    public void setInitInstanceVariables(ArrayList<PsiVariable> initInstanceVariables) {
        this.initInstanceVariables = initInstanceVariables;
    }

    public ArrayList<PsiVariable> getUsedInstanceVariables() {
        return usedInstanceVariables;
    }

    public void setUsedInstanceVariables(ArrayList<PsiVariable> usedInstanceVariables) {
        this.usedInstanceVariables = usedInstanceVariables;
    }
}
