package main.testSmellDetection.structuralRules;

import com.intellij.psi.PsiVariable;
import main.testSmellDetection.bean.PsiClassBean;
import main.testSmellDetection.bean.PsiMethodBean;

import java.util.ArrayList;

public abstract class LackOfCohesionOfTestSmellStructural {

    public boolean isLackOfCohesion(PsiClassBean testClass) {
        int i = getLCOM(testClass);
        if(i > 1) {
            return true;
        }
        return false;
    }

    public static int getLCOM(PsiClassBean testClass){
        int share = 0;
        int notShare = 0;
        ArrayList<PsiMethodBean> methods = new ArrayList<>();
        for(PsiMethodBean psiMethodBeanInside : testClass.getPsiMethodBeans()) {
            String methodName = psiMethodBeanInside.getPsiMethod().getName();
            if (!methodName.equals(testClass.getPsiClass().getName()) &&
                    !methodName.toLowerCase().equals("setup") &&
                    !methodName.toLowerCase().equals("teardown")) {
                methods.add(psiMethodBeanInside);
            }
        }
        for(int i = 0; i < methods.size(); i++){
            for (int j = i + 1; j < methods.size(); j++){
                if(shareAnInstanceVariable(methods.get(i), methods.get(j))){
                    share++;
                } else {
                    notShare++;
                }
            }
        }
        if(share > notShare){
            return 0;
        } else {
            return (notShare - share);
        }
    }

    /**
     * Metodo di supporto che verifica se due metodi usano entrambi le stesse variabili di istanza.
     * @param m1 il primo metodo.
     * @param m2 il secondo metodo.
     * @return vero se condividono tutte le variabili
     */
    private static boolean shareAnInstanceVariable(PsiMethodBean m1, PsiMethodBean m2){
        ArrayList<PsiVariable> m1Variables = m1.getUsedInstanceVariables();
        ArrayList<PsiVariable> m2Variables = m2.getUsedInstanceVariables();
        for(PsiVariable var : m1Variables){
            if(m2Variables.contains(var))
                return true;
        }
        return false;
    }

}
