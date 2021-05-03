package testSmellDetection.testSmellInfo.mysteryGuest;

import testSmellDetection.bean.PsiClassBean;
import testSmellDetection.testSmellInfo.TestSmellInfo;

import java.util.ArrayList;

public class MysteryGuestInfo extends TestSmellInfo {
    private ArrayList<MethodWithMysteryGuest> methodsThatCauseMysteryGuest;

    public MysteryGuestInfo(PsiClassBean classWithMysteryGuest, ArrayList<MethodWithMysteryGuest> methodsThatCauseMysteryGuest) {
        super(classWithMysteryGuest);
        this.methodsThatCauseMysteryGuest = methodsThatCauseMysteryGuest;
    }

    public ArrayList<MethodWithMysteryGuest> getMethodsThatCauseMysteryGuest() {
        return methodsThatCauseMysteryGuest;
    }

    public void setMethodsThatCauseMysteryGuest(ArrayList<MethodWithMysteryGuest> methodsThatCauseMysteryGuest) {
        this.methodsThatCauseMysteryGuest = methodsThatCauseMysteryGuest;
    }

    @Override
    public String toString() {
        return "MysteryGuestInfo{" +
                "classWithSmell=" + classWithSmell +
                ", methodsThatCauseMysteryGuest=" + methodsThatCauseMysteryGuest +
                '}';
    }
}
