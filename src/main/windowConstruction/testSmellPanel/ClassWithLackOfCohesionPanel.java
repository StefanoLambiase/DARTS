package main.windowConstruction.testSmellPanel;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiPackageStatement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.refactoring.extractMethod.PrepareFailedException;
import main.refactor.IRefactor;
import main.refactor.strategy.LackOfCohesionStrategy;
import main.testSmellDetection.testSmellInfo.lackOfCohesion.LackOfCohesionInfo;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClassWithLackOfCohesionPanel extends JPanel {

    public ClassWithLackOfCohesionPanel(LackOfCohesionInfo loci, Project project){
        //Setto il contorno del JPanel
        Border blackline = BorderFactory.createLineBorder(Color.black);
        this.setBorder(blackline);

        //Costruisco la struttura del JPanel riguardante una specifica classe affetta da LackOfCohesion
        // Test Class.
        PsiFile parentFile = (PsiFile) PsiTreeUtil.getParentOfType(loci.getClassWithLackOfCohesion().getPsiClass(), PsiFile.class);
        PsiPackageStatement psiPackageStatement = PsiTreeUtil.getChildOfType(parentFile, PsiPackageStatement.class);
        String packageString = psiPackageStatement.getPackageName();
        JLabel classNameLabel = new JLabel("   " + packageString + "." + loci.getClassWithLackOfCohesion().getPsiClass().getName());
        // Production class.
        parentFile = (PsiFile) PsiTreeUtil.getParentOfType(loci.getProductionClass().getPsiClass(), PsiFile.class);
        psiPackageStatement = PsiTreeUtil.getChildOfType(parentFile, PsiPackageStatement.class);
        packageString = psiPackageStatement.getPackageName();
        JLabel pClassNameLabel = new JLabel(packageString + "." + loci.getProductionClass().getPsiClass().getName());

        JButton refactoringButton = new JButton("Do Refactor");
        JButton methodButton = new JButton("details");
        methodButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame detailsFrame = new JFrame(loci.getClassWithLackOfCohesion().getPsiClass().getName());
                Container cp = detailsFrame.getContentPane();
                cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));

                //Parte relativa alla refactoring tips
                JLabel refactoringLabel = new JLabel("The Smell can be removed using one of this refactoring operations:");
                JLabel firstRefactoringLabel = new JLabel("Extract method: the method can be move in an other class");
                JLabel secondRefactoringLabel = new JLabel("Extract class: the test class can be split into two separated classes");

                refactoringLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                firstRefactoringLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                secondRefactoringLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

                cp.add(refactoringLabel);
                cp.add(firstRefactoringLabel);
                cp.add(secondRefactoringLabel);

                //Parte relativa alla creazione del frame
                detailsFrame.setSize(500,300);
                detailsFrame.setVisible(true);
            }
        });

        //parte relativa all'azione del bottone per il refactoring automatico

        refactoringButton.addActionListener(e -> {

            IRefactor refactor = new LackOfCohesionStrategy(loci, project);
            try {
                refactor.doRefactor();
            } catch (PrepareFailedException e1) {
                e1.printStackTrace();
            }
        });

        this.setLayout(new GridLayout(1,3));
        this.add(classNameLabel);
        this.add(pClassNameLabel);
        this.add(methodButton);
        this.add(refactoringButton);
    }



}
