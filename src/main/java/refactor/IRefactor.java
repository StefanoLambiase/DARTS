package refactor;

import com.intellij.refactoring.extractMethod.PrepareFailedException;
import testSmellDetection.bean.PsiClassBean;

public interface IRefactor {
    void doRefactor() throws PrepareFailedException;

    void doAfterRefactor();

}
