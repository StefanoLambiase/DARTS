package main.windowConstruction.testSmellPanel;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiPackageStatement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.refactoring.extractMethod.PrepareFailedException;
import javafx.scene.control.RadioButton;
import main.contextualAnalysis.DataMiner;
import main.refactor.IRefactor;
import main.refactor.strategy.EagerTestStrategy;
import main.testSmellDetection.bean.PsiMethodBean;
import main.testSmellDetection.testSmellInfo.eagerTest.EagerTestInfo;
import main.testSmellDetection.testSmellInfo.eagerTest.MethodWithEagerTest;
import main.windowConstruction.ContextualAnalysisFrame;
import org.repodriller.RepoDriller;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Questa classe rappresenta la GUI nella quale vengono mostrate le informazioni riguardanti una classe affetta da EagerTest
 */
public class ClassWithEagerTestPanel extends JPanel {

    public ClassWithEagerTestPanel(EagerTestInfo eti, Project project){
        //Setto il contorno del JPanel
        Border blackline = BorderFactory.createLineBorder(Color.black);
        this.setBorder(blackline);

        // Costruisco la struttura del JPanel riguardante una specifica classe affetta da EagerTest.
        // Test Class.
        PsiFile parentFile = (PsiFile) PsiTreeUtil.getParentOfType(eti.getClassWithEagerTest().getPsiClass(), PsiFile.class);
        PsiPackageStatement psiPackageStatement = PsiTreeUtil.getChildOfType(parentFile, PsiPackageStatement.class);
        String packageString = psiPackageStatement.getPackageName();
        JLabel classNameLabel = new JLabel("   " + packageString + "." + eti.getClassWithEagerTest().getPsiClass().getName());
        // Production class.
        parentFile = (PsiFile) PsiTreeUtil.getParentOfType(eti.getProductionClass().getPsiClass(), PsiFile.class);
        psiPackageStatement = PsiTreeUtil.getChildOfType(parentFile, PsiPackageStatement.class);
        packageString = psiPackageStatement.getPackageName();
        JLabel pClassNameLabel = new JLabel(packageString + "." + eti.getProductionClass().getPsiClass().getName());

        // parte riguardante i metodi
        JPanel listOfMethodsPanel = new JPanel();
        listOfMethodsPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.BLACK));
        listOfMethodsPanel.setLayout(new GridLayout(eti.getMethodsThatCauseEagerTest().size(), 1));

        for(MethodWithEagerTest mb : eti.getMethodsThatCauseEagerTest()){
            JPanel methodPanel = new JPanel(new GridLayout(1, 3));
            JLabel methodName = new JLabel("   " + mb.getMethodWithEagerTest().getPsiMethod().getName());
            JButton methodButton = new JButton("details");
            JButton refactoringButton = new JButton("Do Refactor");
            JButton contextualAnalysisButton = new JButton("Execute contextual analysis");

            methodButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame detailsFrame = new JFrame(eti.getClassWithEagerTest().getPsiClass().getName());
                    Container cp = detailsFrame.getContentPane();
                    cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));

                    //Parte relativa alla creazione della scritta informativa
                    String methodName = " Method: " + mb.getMethodWithEagerTest().getPsiMethod().getName() + " calls the following methods: ";
                    JLabel methodNameLabel = new JLabel(methodName);
                    methodNameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                    methodNameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                    methodNameLabel.setMinimumSize(new Dimension(500, 35));
                    methodNameLabel.setPreferredSize(new Dimension(500, 35));
                    methodNameLabel.setMaximumSize(new Dimension(500, 35));
                    cp.add(methodNameLabel);

                    for(PsiMethodBean mbCalled : mb.getListOfMethodCalled()){
                        JLabel methodCalledName = new JLabel("   " + mbCalled.getPsiMethod().getName());
                        methodCalledName.setAlignmentX(Component.LEFT_ALIGNMENT);
                        methodCalledName.setMinimumSize(new Dimension(500, 35));
                        methodCalledName.setPreferredSize(new Dimension(500, 35));
                        methodCalledName.setMaximumSize(new Dimension(500, 35));

                        cp.add(methodCalledName);
                    }

                    //Parte relativa alla refactoring tips
                    JLabel refactoringLabel = new JLabel("It can be removed using this refactoring operations:");
                    JLabel firstRefactoringLabel = new JLabel("Extract method: affected method can be splitted into smaller methods, each one testing a specific behavior of the tested object.");

                    refactoringLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                    firstRefactoringLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

                    cp.add(refactoringLabel);
                    cp.add(firstRefactoringLabel);

                    //Parte relativa alla creazione del frame
                    detailsFrame.setSize(500,300);
                    detailsFrame.setVisible(true);
                }
            });

            //parte relativa all'azione del bottone per il refactoring automatico

            refactoringButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    IRefactor refactor = new EagerTestStrategy(mb, project, eti);
                    try {
                        refactor.doRefactor();
                    } catch (PrepareFailedException e1) {
                        e1.printStackTrace();
                    }
                }
            });

            contextualAnalysisButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new ContextualAnalysisFrame(project, eti);
                    //new RepoDriller().start(new DataMiner(eti, project.getBasePath()));
                }
            });

            methodPanel.add(methodName);
            methodPanel.add(methodButton);
            methodPanel.add(refactoringButton);
            methodPanel.add(contextualAnalysisButton);

            listOfMethodsPanel.add(methodPanel);
        }
        this.setLayout(new GridLayout(1,3));
        this.add(classNameLabel);
        this.add(pClassNameLabel);
        this.add(listOfMethodsPanel);
    }
}
