package refactor.strategy;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.refactoring.extractMethod.ExtractMethodHandler;
import com.intellij.refactoring.extractMethod.ExtractMethodProcessor;
import com.intellij.refactoring.extractMethod.PrepareFailedException;
import refactor.IRefactor;
import testSmellDetection.bean.PsiClassBean;
import testSmellDetection.bean.PsiMethodBean;
import testSmellDetection.testSmellInfo.eagerTest.EagerTestInfo;
import testSmellDetection.testSmellInfo.eagerTest.MethodWithEagerTest;
import testSmellDetection.testSmellInfo.mysteryGuest.MethodWithMysteryGuest;
import testSmellDetection.testSmellInfo.mysteryGuest.MysteryGuestInfo;

import java.util.ArrayList;

public class MysteryGuestStrategy implements IRefactor {
    private MysteryGuestInfo mysteryGuestInfo;
    private MethodWithMysteryGuest methodWithMysteryGuest;
    private Project project;
    private Editor editor;

    public MysteryGuestStrategy(MethodWithMysteryGuest methodWithMysteryGuest, Project project, MysteryGuestInfo mysteryGuestInfo) {
        this.methodWithMysteryGuest = methodWithMysteryGuest;
        this.project = project;
        this.mysteryGuestInfo = mysteryGuestInfo;
    }

    @Override
    public void doRefactor() throws PrepareFailedException {
        ArrayList<PsiNewExpression> newFileExpressions = methodWithMysteryGuest.getListOfNewFile();
        for(PsiNewExpression psiNewExpression : newFileExpressions) {
            try {
                VirtualFile virtualTextFile = LocalFileSystem.getInstance().findFileByPath(psiNewExpression.getArgumentList().getExpressions()[0].getText().replaceAll("\"", ""));
                String textFile = VfsUtil.loadText(virtualTextFile);
                ApplicationManager.getApplication().invokeLater(()->{
                    WriteCommandAction.runWriteCommandAction(project, ()->{
                        PsiElementFactory psiElementFactory = JavaPsiFacade.getInstance(project).getElementFactory();
                        PsiField psiField = psiElementFactory.createFieldFromText("String fileString = \""+textFile+"\";", null);
                        mysteryGuestInfo.getClassWithSmell().getPsiClass().addAfter(psiField, psiNewExpression.getParent().getParent());
                        psiNewExpression.getParent().delete();
                    });
                });
            }catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}