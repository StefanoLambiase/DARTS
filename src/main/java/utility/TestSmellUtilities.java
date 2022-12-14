package utility;

import com.intellij.psi.PsiClass;
import testSmellDetection.bean.PsiClassBean;

import java.util.ArrayList;

public abstract class TestSmellUtilities {

    /**
     * Metodo utilizzato per ottenere tutte le classi di test da un insieme di classi. Aggiunge anche la production class.
     * @param allClasses tutte le classi del progetto sotto forma di PsiClass.
     * @return la lista di tutte le classi di test.
     */
    public static ArrayList<PsiClassBean> getAllTestClasses(ArrayList<PsiClassBean> allClasses){
        ArrayList<PsiClassBean> allTestClasses = new ArrayList<>();
        for(PsiClassBean psiClassBean : allClasses){
            if(TestSmellUtilities.isTestClass(psiClassBean)){
                psiClassBean.setProductionClass(findProductionClass(psiClassBean, allClasses));
                allTestClasses.add(psiClassBean);
            }
        }
        return allTestClasses;
    }

    /**
     * Metodo utilizzato per ottenere tutte le production classes in un insieme di classi.
     * @param allClasses tutte le classi del progetto.
     * @param allTestClasses tutte le classi di test del progetto.
     * @return la lista di tutte le production classes.
     */
    public static ArrayList<PsiClassBean> getAllProductionClasses(ArrayList<PsiClassBean> allClasses, ArrayList<PsiClassBean> allTestClasses){
        ArrayList<PsiClassBean> allProductionClasses = new ArrayList<>();
        for(PsiClassBean psiClassBean : allTestClasses){
            PsiClassBean productionClass = TestSmellUtilities.findProductionClass(psiClassBean, allClasses);
            if(productionClass != null){
                psiClassBean.setProductionClass(productionClass);
                allProductionClasses.add(productionClass);
            }
        }
        return allProductionClasses;
    }

    /**
     * Metodo utilizzato per verificare se una classe è una classe di test.
     * @param psiClassBean la classe in esame.
     * @return true se la classe è una classe di test, false altrimenti.
     */
    public static boolean isTestClass(PsiClassBean psiClassBean){
        PsiClass psiClass = psiClassBean.getPsiClass();
        if(psiClass.getName().endsWith("Test") || psiClass.getName().contains("test") || psiClass.getText().contains(" extends TestCase")){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Metodo utilizzato per trovare la production class di una data classe di test.
     * @param psiTestClassBean la classe di test.
     * @param allClasses tutte le classi presenti nel progetto.
     * @return la production class.
     */
    public static PsiClassBean findProductionClass(PsiClassBean psiTestClassBean, ArrayList<PsiClassBean> allClasses){
        for(PsiClassBean psiClassBean : allClasses){
            PsiClass psiClass = psiClassBean.getPsiClass();
            PsiClass psiTestClass = psiTestClassBean.getPsiClass();
            // Creo stringhe contenenti tutti i possibili nomi che potrebbe avere la classe di test della classe in esame.
            String candidateTestName = psiClass.getName()+"Test";
            String candidateTestName2 = "Test"+psiClass.getName();
            String candidateTestName3 = "Tests"+psiClass.getName();
            String candidateTestName4 = psiClass.getName()+"Tests";
            String candidateTestName5 = "TC_"+psiClass.getName();
            String candidateTestName6 = psiClass.getName()+"_TC";
            String candidateTestName7 = psiClass.getName().replaceAll("_", "")+"Test";
            String candidateTestName8 = "Test"+psiClass.getName().replaceAll("_", "");
            String candidateTestName9 = psiClass.getName()+"TestCase";
            String candidateTestName10 = "TestCase"+psiClass.getName();
            // Eseguo la verifica
            if(candidateTestName.toLowerCase().equals(psiTestClass.getName().toLowerCase())) {
                return psiClassBean;
            } else if(candidateTestName2.toLowerCase().equals(psiTestClass.getName().toLowerCase())) {
                return psiClassBean;
            } else if(candidateTestName3.toLowerCase().equals(psiTestClass.getName().toLowerCase())) {
                return psiClassBean;
            } else if(candidateTestName4.toLowerCase().equals(psiTestClass.getName().toLowerCase())) {
                return psiClassBean;
            } else if(candidateTestName5.toLowerCase().equals(psiTestClass.getName().toLowerCase())) {
                return psiClassBean;
            } else if(candidateTestName6.toLowerCase().equals(psiTestClass.getName().toLowerCase())) {
                return psiClassBean;
            } else if(candidateTestName7.toLowerCase().equals(psiTestClass.getName().toLowerCase())) {
                return psiClassBean;
            } else if(candidateTestName8.toLowerCase().equals(psiTestClass.getName().toLowerCase())) {
                return psiClassBean;
            } else if(candidateTestName9.toLowerCase().equals(psiTestClass.getName().toLowerCase())) {
                return psiClassBean;
            } else if(candidateTestName10.toLowerCase().equals(psiTestClass.getName().toLowerCase())) {
                return psiClassBean;
            }
        }
        return null;
    }

    public static String red(String wrapped){
        return ANSI_RED+wrapped+ANSI_RESET;
    }

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";


}
