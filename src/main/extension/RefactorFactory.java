package main.extension;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.ClassUtil;
import com.intellij.refactoring.listeners.RefactoringElementAdapter;
import com.intellij.refactoring.listeners.RefactoringElementListener;
import com.intellij.refactoring.listeners.RefactoringElementListenerProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RefactorFactory implements RefactoringElementListenerProvider{

    @Nullable
    @Override
    public RefactoringElementListener getListener(PsiElement element) {
        if (element instanceof PsiClass) {
            PsiClass psiClass = (PsiClass) element;
            final String oldName = ClassUtil.getJVMClassName(psiClass);
            if (oldName != null) {
                return new MyRefactoringElementListener(psiClass);
            }
        }
        return null;
    }


    private static class MyRefactoringElementListener extends RefactoringElementAdapter {
        private PsiClass myClass;

        MyRefactoringElementListener(PsiClass psiClass) {
            myClass = psiClass;
        }

        @Override
        public void elementRenamedOrMoved(@NotNull PsiElement newElement) {
            PsiClass psiClass = (PsiClass) newElement;
            final String qName = ClassUtil.getJVMClassName(psiClass);
            System.out.println("FUNZIONAAAAAAAAAAAAAAAAAAAAAAAAA");
            }

        @Override
        public void undoElementMovedOrRenamed(@NotNull PsiElement psiElement, @NotNull String s) {
            System.out.println("FUNZIONAAAAAAAAAAAAAAAAAAAAAAAAA");
        }
    }

}
