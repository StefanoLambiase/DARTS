package testSmellDetection.structuralRules;

import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiVariable;
import testSmellDetection.bean.PsiClassBean;
import testSmellDetection.bean.PsiMethodBean;
import utility.PsiTestSmellUtilities;

import java.util.ArrayList;

public abstract class GeneralFixtureStructural {

    public static boolean isGeneralFixture(PsiClassBean testClass, ArrayList<PsiClassBean> productionClasses, ArrayList<PsiClassBean> testClasses, int numberOfFixtureProductionTypes, int numberOfObjectUsesInSetup){
        if(getNumberOfFixtureProductionTypes(testClass, productionClasses) > numberOfFixtureProductionTypes) {
            if(getNumberOfObjectUsesInSetup(testClass, testClasses) > numberOfObjectUsesInSetup) {
                if(calculateAverageFixtureUsage(testClass) < 1) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * NFPT + NFOB
     * uesto metodo ritorna il numero di oggetti con tipo appartenente ad una classe del progetto presenti nella classe di test.
     * @param testClass la classe in esame.
     * @param productionClasses la lista delle classi del progetto.
     * @return il numero di occorrenze.
     */
    private static int getNumberOfFixtureProductionTypes(PsiClassBean testClass, ArrayList<PsiClassBean> productionClasses){
        ArrayList<PsiVariable> listOfAllInstance = new ArrayList<>();
        for(PsiVariable var : testClass.getPsiClass().getAllFields()) {
            for(PsiClassBean productionClass : productionClasses){
                if(var.getTypeElement().getText().equals(productionClass.getPsiClass().getName())){
                    if(!listOfAllInstance.contains(var)) {
                        listOfAllInstance.add(var);
                    }
                }
            }
        }
        return listOfAllInstance.size();
    }

    /**
     * NOBU
     * Metodo centrale. Conta il numero di oggetti usati nel metodo di setup, sia direttamente che indirettamente.
     * @param testClass
     * @param testClasses
     * @return
     */
    private static int getNumberOfObjectUsesInSetup(PsiClassBean testClass, ArrayList<PsiClassBean> testClasses){
        ArrayList<PsiMethod> listOfMethodFindInNOBU = new ArrayList<>();
        ArrayList<PsiVariable> listOfInstanceFindInNOBU = new ArrayList<>();
        int numberOfObjectUses = 0;

        ArrayList<PsiMethodBean> testMethods = new ArrayList<>();
        for(PsiClassBean classBean : testClasses) {
            for (PsiMethodBean methodBean : classBean.getPsiMethodBeans())
                testMethods.add(methodBean);
        }
        for(PsiMethodBean method : testClass.getPsiMethodBeans()){
            if(method.getPsiMethod().getName().toLowerCase().equals("setup"))
                numberOfObjectUses += recorsiveIOBU(method, testMethods, listOfMethodFindInNOBU, listOfInstanceFindInNOBU);
        }
        return numberOfObjectUses;
    }

    /**
     * Metodo ricorsivo per la ricerca ricorsiva degli usi di oggetti all'interno di metodi helper chiamati dal metodo di setup.
     * @param method
     * @param testMethods
     * @param listOfMethodFindInNOBU
     * @param listOfInstanceFindInNOBU
     * @return
     */
    private static int recorsiveIOBU(PsiMethodBean method, ArrayList<PsiMethodBean> testMethods,
                                     ArrayList<PsiMethod> listOfMethodFindInNOBU,
                                     ArrayList<PsiVariable> listOfInstanceFindInNOBU){
        int directObjectUses = 0;
        ArrayList<PsiMethodBean> invokedTestHelperMethods = new ArrayList<>();

        for(PsiVariable var : method.getInitInstanceVariables()){
            if(!listOfInstanceFindInNOBU.contains(var)){
                listOfInstanceFindInNOBU.add(var);
                directObjectUses ++;
            }
        }
        for(PsiMethod m : PsiTestSmellUtilities.getPsiMethodCalledFromMethod(method.getPsiMethod())){
            if(!listOfMethodFindInNOBU.contains(m)){
                listOfMethodFindInNOBU.add(m);
                directObjectUses ++;
            }
        }
        // Verifico la presenza di test helper methods.
        if(method.getMethodCalls().size() == 0){
            return directObjectUses;
        } else {
            ArrayList<PsiMethod> methodsCalled = PsiTestSmellUtilities.getPsiMethodCalledFromMethod(method.getPsiMethod());
            for(PsiMethod m1 : methodsCalled){
                for(PsiMethodBean m2 : testMethods){
                    if(m1.getName().toLowerCase().equals(m2.getPsiMethod().getName()))
                        invokedTestHelperMethods.add(m2);
                }
            }
            // Chiamata ricorsiva.
            if(invokedTestHelperMethods.size() == 0){
                return directObjectUses;
            } else {
                for (PsiMethodBean calledMethod : invokedTestHelperMethods){
                    int i = 0;
                    i += recorsiveIOBU(calledMethod, testMethods, listOfMethodFindInNOBU, listOfInstanceFindInNOBU);
                    directObjectUses += i;
                }
            }
            return directObjectUses;
        }
    }

    /**
     * AFIU: AVERAGE FIXTURE USAGE
     * Questo metodo calcola l'utilizzo della fixture da parte di un metodo di test.
     * @param testMethod il metodo in esame.
     * @param testClass la classe in esame.
     * @return un valore compreso tra 0 e 1 rappresentante l'utilizzo della fixture da parte di quel metodo.
     */
    private static double getFixtureUsageRateOfMethod(PsiMethodBean testMethod, PsiClassBean testClass){
        if(testClass.getPsiClass().getAllFields().length == 1){
            return 1.0;
        } else {
            return testMethod.getUsedInstanceVariables().size() / testClass.getPsiClass().getAllFields().length;
        }
    }

    /**
     * Questo metodo calcola la media di utilizzo della fixture di una classe da parte dei suoi metodi.
     * @param testClass la classe in esame.
     * @return un valore compreso tra 0 e 1.
     */
    private static double calculateAverageFixtureUsage(PsiClassBean testClass){
        double sumOfFixtureUsageRate = 0.0;
        int numberOfTestMethods = 0;

        for(PsiMethodBean methodBean : testClass.getPsiMethodBeans()){
            String methodName = methodBean.getPsiMethod().getName();
            if(!methodName.equals(testClass.getPsiClass().getName()) &&
                    !methodName.toLowerCase().equals("setup") &&
                    !methodName.toLowerCase().equals("teardown")){
                numberOfTestMethods ++;
                sumOfFixtureUsageRate += getFixtureUsageRateOfMethod(methodBean, testClass);
            }
        }
        return sumOfFixtureUsageRate / numberOfTestMethods;
    }

}
