package main.toolWindowConstruction.testSmellPanel;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.refactoring.extractMethod.PrepareFailedException;
import it.unisa.testSmellDiffusion.beans.InstanceVariableBean;
import it.unisa.testSmellDiffusion.testSmellInfo.generalFixture.GeneralFixtureInfo;
import it.unisa.testSmellDiffusion.testSmellInfo.generalFixture.MethodWithGeneralFixture;
import main.refactor.IRefactor;
import main.refactor.strategy.GeneralFixtureStrategy;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Questa classe rappresenta la GUI nella quale vengono mostrate le informazioni riguardanti una classe affetta da GeneralFixture
 */
public class ClassWithGeneralFixturePanel extends JPanel {

    public ClassWithGeneralFixturePanel(GeneralFixtureInfo gfi, Project project){
        //Setto il contorno del JPanel
        Border blackline = BorderFactory.createLineBorder(Color.black);
        this.setBorder(blackline);

        //Costruisco la struttura del JPanel riguardante una specifica classe affetta da GeneralFixture
        JLabel classNameLabel = new JLabel("   " + gfi.getTestClass().getBelongingPackage() + "." + gfi.getTestClass().getName());

        //Parte riguardante i metodi affetti da GeneralFixture
        JPanel listOfMethodsPanel = new JPanel();
        listOfMethodsPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.BLACK));
        listOfMethodsPanel.setLayout(new GridLayout(gfi.getMethodsThatCauseGeneralFixture().size(), 1));

        for(MethodWithGeneralFixture mb : gfi.getMethodsThatCauseGeneralFixture()){
            JPanel methodPanel = new JPanel(new GridLayout(1, 2));
            JLabel methodName = new JLabel("   "+mb.getMethod().getName());
            JButton methodButton = new JButton("details");
            JButton refactoringButton = new JButton("Do Refactor");

            methodButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame detailsFrame = new JFrame(gfi.getTestClass().getName());
                    Container cp = detailsFrame.getContentPane();
                    cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));

                    int windowWidth = detailsFrame.getWidth();
                    int windowHeight = detailsFrame.getHeight();

                    //Parte relativa alla creazione della scritta informativa
                    String methodName = " Method: "+mb.getMethod().getName()+" doesn't use the following variables:";
                    JLabel methodNameLabel = new JLabel(methodName);
                    methodNameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                    methodNameLabel.setMinimumSize(new Dimension(500, 35));
                    methodNameLabel.setPreferredSize(new Dimension(500, 35));
                    methodNameLabel.setMaximumSize(new Dimension(500, 35));
                    cp.add(methodNameLabel);

                    for(InstanceVariableBean instance : mb.getListOfInstances()){
                        JLabel instanceCalledName = new JLabel("   "+instance.getName());

                        instanceCalledName.setAlignmentX(Component.LEFT_ALIGNMENT);
                        instanceCalledName.setMinimumSize(new Dimension(500, 35));
                        instanceCalledName.setPreferredSize(new Dimension(500, 35));
                        instanceCalledName.setMaximumSize(new Dimension(500, 35));

                        cp.add(instanceCalledName);
                    }

                    //Parte relativa alla refactoring tips
                    JLabel refactoringLabel = new JLabel("The Smell can be removed using one of this refactoring operations:");
                    JLabel firstRefactoringLabel = new JLabel("Extract method: setup method can be split into two different methods");
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
            refactoringButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    IRefactor refactor = new GeneralFixtureStrategy(mb, project, gfi);
                    try {
                        refactor.doRefactor();
                        ToolWindow toolWindow = ToolWindowManager.getActiveToolWindow();
                        toolWindow.hide(null);
                    } catch (PrepareFailedException e1) {
                        e1.printStackTrace();
                    }
                }
            });

            methodPanel.add(methodName);
            methodPanel.add(methodButton);
            methodPanel.add(refactoringButton);

            listOfMethodsPanel.add(methodPanel);
        }
        this.setLayout(new GridLayout(1,2));
        this.add(classNameLabel);
        this.add(listOfMethodsPanel);
    }


}
