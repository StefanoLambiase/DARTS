package testSmellDetection.structuralRules;

import com.intellij.psi.PsiMethod;
import testSmellDetection.bean.PsiClassBean;
import testSmellDetection.bean.PsiMethodBean;
import utility.PsiTestSmellUtilities;

import java.util.ArrayList;

public abstract class EagerTestStructural {

    public static boolean isEagerTest(PsiClassBean testClass,
                               ArrayList<PsiClassBean> testClasses,
                               ArrayList<PsiClassBean> productionClasses){
        for(PsiMethodBean methodBean : testClass.getPsiMethodBeans()){
            String methodName = methodBean.getPsiMethod().getName();
            if(!methodName.equals(testClass.getPsiClass().getName()) &&
                    !methodName.toLowerCase().equals("setup") &&
                    !methodName.toLowerCase().equals("teardown")){
                int i = getNumberOfProductionTypeUses(methodBean, testClasses, productionClasses);
                if(i > 2) {
                    return true;
                }
            }
        }
        return false;
    }

    private static int getNumberOfProductionTypeUses(PsiMethodBean testMethod,
                                                     ArrayList<PsiClassBean> testClasses,
                                                     ArrayList<PsiClassBean> productionClasses){
        ArrayList<PsiMethod> listOfProductionMethodFind = new ArrayList<>();
        // Mi prendo la lista di tutti i metodi per cercare test helpers.
        ArrayList<PsiMethod> testMethods = new ArrayList<>();
        for(PsiClassBean classBean : testClasses)
            for(PsiMethodBean methodBean : classBean.getPsiMethodBeans())
                testMethods.add(methodBean.getPsiMethod());
        // Mi prendo la lista di tutti i metodi in production classes.
        ArrayList<PsiMethod> productionMethods = new ArrayList<>();
        for(PsiClassBean classBean : productionClasses)
            for(PsiMethodBean methodBean : classBean.getPsiMethodBeans())
                productionMethods.add(methodBean.getPsiMethod());
        recorsivePTU(listOfProductionMethodFind, testMethod.getPsiMethod(), testMethods, productionMethods);

        return listOfProductionMethodFind.size();
    }

    private static void recorsivePTU(ArrayList<PsiMethod> list, PsiMethod m,
                                     ArrayList<PsiMethod> testMethods, ArrayList<PsiMethod> productionMethods){
        ArrayList<PsiMethod> invokedTestHelperMethods = new ArrayList<>();
        // Mi prendo la lista di metodi chiamati dal metodo in esame.
        ArrayList<PsiMethod> methodsCalled = PsiTestSmellUtilities.getPsiMethodCalledFromMethod(m);
        if(methodsCalled.size() > 1){
            // Conto il numero di production methods chiamati e test helper.
            for(PsiMethod method : methodsCalled){
                if(productionMethods.contains(method)){
                    if(!list.contains(method))
                        list.add(method);
                } else if(testMethods.contains(method)){
                    if(!invokedTestHelperMethods.contains(method))
                        invokedTestHelperMethods.add(method);
                }
            }
        }
        if(!invokedTestHelperMethods.isEmpty()){
            for(PsiMethod method : invokedTestHelperMethods)
                recorsivePTU(list, method, testMethods, productionMethods);
        }
    }

}
