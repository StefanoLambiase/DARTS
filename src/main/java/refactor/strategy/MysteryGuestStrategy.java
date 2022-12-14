package refactor.strategy;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiNewExpression;
import com.intellij.psi.PsiStatement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.refactoring.extractMethod.PrepareFailedException;
import com.intellij.refactoring.extractclass.ExtractClassProcessor;
import refactor.IRefactor;
import stats.Action;
import stats.Stats;
import testSmellDetection.bean.PsiClassBean;
import testSmellDetection.testSmellInfo.mysteryGuest.MethodWithMysteryGuest;
import testSmellDetection.testSmellInfo.mysteryGuest.MysteryGuestInfo;

import java.util.ArrayList;
import java.util.Date;

public class MysteryGuestStrategy implements IRefactor {
    private MysteryGuestInfo mysteryGuestInfo;
    private MethodWithMysteryGuest methodWithMysteryGuest;
    private Project project;

    private ExtractClassProcessor processor;

    private Editor editor;

    public MysteryGuestStrategy(MethodWithMysteryGuest methodWithMysteryGuest, Project project, MysteryGuestInfo mysteryGuestInfo) {
        this.methodWithMysteryGuest = methodWithMysteryGuest;
        this.project = project;
        this.mysteryGuestInfo = mysteryGuestInfo;
    }

    @Override
    public void doRefactor() throws PrepareFailedException {
        ArrayList<PsiNewExpression> newFileExpressions = methodWithMysteryGuest.getListOfNewFile();
        for (PsiNewExpression psiNewExpression : newFileExpressions) {
            try {
                VirtualFile virtualTextFile = LocalFileSystem.getInstance().findFileByPath(psiNewExpression.getArgumentList().getExpressions()[0].getText().replaceAll("\"", ""));
                String textFile = VfsUtil.loadText(virtualTextFile);
                ApplicationManager.getApplication().invokeLater(() -> {
                    WriteCommandAction.runWriteCommandAction(project, () -> {

                        String variableName = Messages.showInputDialog(project, "Inserisci il nome della nuova variabile",
                                "Variabile", Messages.getQuestionIcon());

                        PsiElementFactory psiElementFactory = JavaPsiFacade.getInstance(project).getElementFactory();
                        PsiStatement psiStatement = psiElementFactory.createStatementFromText("String " + variableName + " = \"" + textFile + "\";", null);
                        methodWithMysteryGuest.getMethodWithMysteryGuest().getPsiMethod().getBody().addBefore(psiStatement, methodWithMysteryGuest.getMethodWithMysteryGuest().getPsiMethod().getBody().getFirstBodyElement());
                        PsiTreeUtil.getParentOfType(psiNewExpression, PsiStatement.class).delete();

//                        Codice per eliminare statements legati al file
//                        Collection<PsiElement> psiElements;
//                        boolean tryAgain = false;
//                        while (!tryAgain) {
//                            tryAgain = true;
//                            psiElements = PsiTreeUtil.findChildrenOfType(methodWithMysteryGuest.getMethodWithMysteryGuest().getPsiMethod().getBody(), PsiElement.class);
//                            for (PsiElement psiElement : psiElements) {
//                                if (psiElement.isValid()) {
//                                    if (psiElement.getReference() != null) {
//                                        if (psiElement.getReference().resolve() == null) {
//                                            if (psiElement instanceof PsiStatement) psiElement.delete();
//                                            else {
//                                                PsiStatement deleteMe = PsiTreeUtil.getParentOfType(psiElement, PsiStatement.class);
//                                                if (deleteMe != null) deleteMe.delete();
//                                            }
//                                            tryAgain = false;
//                                        }
//                                    }
//                                }
//                            }
//                        }
                    });
                });
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    @Override
    public void doAfterRefactor() {
        PsiClassBean originalClassBean = mysteryGuestInfo.getClassWithSmell();
        getActionForStats(originalClassBean);
    }

    public void getActionForStats(PsiClassBean psiClassBean){
        Action action = new Action();
        action.setClassName(psiClassBean.getPsiClass().getName());
        action.setMethodName(methodWithMysteryGuest.getMethodWithMysteryGuest().getPsiMethod().getName());
        action.setPackageName(psiClassBean.getPsiPackage().getName());
        action.setSmellKind(Action.SmellKindEnum.MYSTERY_GUEST);
        action.setActionKind(Action.ActionKindEnum.REFACTORING_PREVIEW);
        action.setTimestamp(new Date().getTime());
        action.setActionCanceled(false);
        action.setActionDone(true);

        Stats.getInstance().getLastSession().getActions().add(action);
        System.out.println("adding action:" + action);
    }
}