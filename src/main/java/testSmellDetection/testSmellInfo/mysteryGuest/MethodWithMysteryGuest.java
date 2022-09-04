package testSmellDetection.testSmellInfo.mysteryGuest;

import com.intellij.psi.PsiNewExpression;
import testSmellDetection.bean.PsiMethodBean;

import java.util.ArrayList;

public class MethodWithMysteryGuest {
    private PsiMethodBean methodWithMysteryGuest;
    private ArrayList<PsiNewExpression> listOfNewFile;

    public MethodWithMysteryGuest(PsiMethodBean methodWithMysteryGuest, ArrayList<PsiNewExpression> listOfNewFile) {
        this.methodWithMysteryGuest = methodWithMysteryGuest;
        this.listOfNewFile = listOfNewFile;
    }

    public PsiMethodBean getMethodWithMysteryGuest() {
        return methodWithMysteryGuest;
    }

    public void setMethodWithMysteryGuest(PsiMethodBean methodWithMysteryGuest) {
        this.methodWithMysteryGuest = methodWithMysteryGuest;
    }

    public ArrayList<PsiNewExpression> getListOfNewFile() {
        return listOfNewFile;
    }

    public void setListOfNewFile(ArrayList<PsiNewExpression> listOfNewFile) {
        this.listOfNewFile = listOfNewFile;
    }
}
