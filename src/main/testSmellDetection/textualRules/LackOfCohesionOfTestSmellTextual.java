package main.testSmellDetection.textualRules;

import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiMethodCallExpression;
import main.testSmellDetection.bean.PsiClassBean;
import main.testSmellDetection.bean.PsiMethodBean;
import main.testSmellDetection.testSmellMetrics.CosineSimilarity;
import main.utility.PsiTestSmellUtilities;

import java.io.IOException;
import java.util.ArrayList;

public abstract class LackOfCohesionOfTestSmellTextual {

    /**
     * Questo metodo verifica se una classe è affetta da LackOfCohesionTestMethods o meno.
     * @param testClass la classe da analizzare.
     * @return vero se la classe è smelly, falso altrimenti.
     */
    public static boolean isLackOfCohesionTestMethods(PsiClassBean testClass) {
        for(PsiMethodBean testMethod : testClass.getPsiMethodBeans()) {
            String methodText = null;
            //Aggiungo la stringa di controllo al testo del metodo
            methodText = alterTestCase(testMethod);
            double smelliness = 0.0;

            try {
                smelliness = computeSmelliness(methodText);
            } catch(IOException e) {
                e.printStackTrace();
            }

            if(smelliness >= 0.4) {
                return true;
            }
        }
        return false;
    }

    /* METODI DI SUPPORTO */
    private static double computeSmelliness(String methodString) throws IOException {
        CosineSimilarity cosineSimilarity = new CosineSimilarity();
        String[] blocks = methodString.split("_____");
        ArrayList<String> filteredBlocks = new ArrayList<String>();
        Double smellyness=0.0;

        for(int k=0; k<blocks.length;k++) {
            if(! blocks[k].isEmpty()) {
                filteredBlocks.add(blocks[k]);
            }
        }

        if(filteredBlocks.size() == 1) {
            return 0.0;
        }

        int comparison = 0;
        for(int k=0; k<filteredBlocks.size(); k++) {
            for(int i=1; i<filteredBlocks.size(); i++) {

                String[] document1 = new String[2];
                document1[0] = "block_"+k;

                String[] document2 = new String[2];
                document2[0] = "block_"+i;

                document1[1] = filteredBlocks.get(k);
                document2[1] = filteredBlocks.get(i);

                smellyness+=cosineSimilarity.computeSimilarity(document1, document2);
                comparison++;
            }
        }

        smellyness = 1 - (smellyness/comparison);

        if (smellyness.isNaN()) {
            return 0.0;
        } else if(smellyness.isInfinite()) {
            return 0.0;
        } else return smellyness;

    }

    private static String alterTestCase(PsiMethodBean testMethod) {
        String mutatedTestCase = "";
        //System.out.println("public void " + methodBean.getName() + "{");
        String[] methodLines = testMethod.getPsiMethod().getBody().getText().split("\n");
        mutatedTestCase += methodLines[0] + "\n";

        ArrayList<PsiMethodCallExpression> methodCallExpressions = testMethod.getMethodCalls();
        ArrayList <PsiMethod> calledMethods = PsiTestSmellUtilities.getPsiMethodFromReferences(methodCallExpressions);
        for(PsiMethod calledMethod : calledMethods) {
            // <method> can be null since the method can call an external method.
            if(calledMethod != null) {
                String[] calledMethodLines = calledMethod.getBody().getText().split("\n");
                for(int i = 1; i < calledMethodLines.length - 1; i++) {
                    mutatedTestCase += calledMethodLines[i] + "\n";
                }
                mutatedTestCase += "_____\n";
            }
        }
        mutatedTestCase += "}";
        return mutatedTestCase;
    }

}
