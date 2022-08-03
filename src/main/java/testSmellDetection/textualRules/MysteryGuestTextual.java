package testSmellDetection.textualRules;

import com.intellij.psi.PsiNewExpression;
import com.intellij.psi.util.PsiTreeUtil;
import testSmellDetection.bean.PsiClassBean;
import testSmellDetection.bean.PsiMethodBean;
import testSmellDetection.testSmellInfo.mysteryGuest.MethodWithMysteryGuest;

import java.util.ArrayList;
import java.util.Collection;

public class MysteryGuestTextual {

    public static ArrayList<MethodWithMysteryGuest> checkMethodsThatCauseMysteryGuest(PsiClassBean testClass) {
        ArrayList<MethodWithMysteryGuest> methodWithMysteryGuests = new ArrayList<>();
        for(PsiMethodBean psiMethodBeanInside : testClass.getPsiMethodBeans()){
            String methodName = psiMethodBeanInside.getPsiMethod().getName();
            if(!methodName.equals(testClass.getPsiClass().getName()) &&
                    !methodName.toLowerCase().equals("setup") &&
                    !methodName.toLowerCase().equals("teardown")){
                Collection<PsiNewExpression> newExpressions = PsiTreeUtil.findChildrenOfType(psiMethodBeanInside.getPsiMethod().getBody(), PsiNewExpression.class);
                ArrayList<PsiNewExpression> newFileExpressions = new ArrayList<>();
                for(PsiNewExpression psiNewExpression : newExpressions) {
                    if(psiNewExpression.getClassReference().getReferenceNameElement().getText().equals("File")) {
                        newFileExpressions.add(psiNewExpression);
                    }
                }
                if(!newFileExpressions.isEmpty()) {
                    methodWithMysteryGuests.add(new MethodWithMysteryGuest(psiMethodBeanInside, newFileExpressions));
                }
            }
        }
        if(methodWithMysteryGuests.isEmpty()){
            return null;
        } else {
            return methodWithMysteryGuests;
        }
    }
}
