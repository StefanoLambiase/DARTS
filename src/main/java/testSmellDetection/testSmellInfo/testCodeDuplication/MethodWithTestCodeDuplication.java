package testSmellDetection.testSmellInfo.testCodeDuplication;

import com.harukizaemon.simian.Block;
import testSmellDetection.bean.PsiMethodBean;

import java.util.ArrayList;
import java.util.Map;

public class MethodWithTestCodeDuplication {
    private PsiMethodBean methodWithTestCodeDuplication;
    private Map<String, ArrayList<Block>> blocksOfDuplicatedCode;

    public MethodWithTestCodeDuplication(PsiMethodBean methodWithTestCodeDuplication, Map<String, ArrayList<Block>> blocksOfDuplicatedCode) {
        this.methodWithTestCodeDuplication = methodWithTestCodeDuplication;
        this.blocksOfDuplicatedCode = blocksOfDuplicatedCode;
    }

    public PsiMethodBean getMethodWithTestCodeDuplication() {
        return methodWithTestCodeDuplication;
    }

    public void setMethodWithTestCodeDuplication(PsiMethodBean methodWithTestCodeDuplication) {
        this.methodWithTestCodeDuplication = methodWithTestCodeDuplication;
    }

    public Map<String, ArrayList<Block>> getBlocksOfDuplicatedCode() {
        return blocksOfDuplicatedCode;
    }

    public void setBlocksOfDuplicatedCode(Map<String, ArrayList<Block>> blocksOfDuplicatedCode) {
        this.blocksOfDuplicatedCode = blocksOfDuplicatedCode;
    }
}
