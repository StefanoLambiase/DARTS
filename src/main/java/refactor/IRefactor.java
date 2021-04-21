package refactor;

import com.intellij.refactoring.extractMethod.PrepareFailedException;

public interface IRefactor {
    void doRefactor() throws PrepareFailedException;
}
