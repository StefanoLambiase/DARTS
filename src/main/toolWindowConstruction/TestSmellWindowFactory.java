package main.toolWindowConstruction;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import it.unisa.testSmellDiffusion.testSmellInfo.eagerTest.EagerTestInfo;
import it.unisa.testSmellDiffusion.testSmellInfo.generalFixture.GeneralFixtureInfo;
import it.unisa.testSmellDiffusion.testSmellInfo.lackOfCohesion.LackOfCohesionInfo;

import javax.swing.*;
import java.util.ArrayList;

public class TestSmellWindowFactory {
    private JPanel generalFixturePanel;
    private JPanel eagerTestPanel;
    private JPanel lackOfCohesionPanel;


    public TestSmellWindowFactory(){

    }


    /**
     * Qeusto metodo si occupa di decidere quale tool window registrare sulla base del tipo di analisi
     * @param textual indica che l'analisi è testuale
     * @param structural indica che l'analisi è strutturale
     * @param project il progetto attivo
     * @param listGFI la lista di info su GeneralFixture
     * @param listETI la lista di info su EagerTest
     * @param listLOCI la lista di info su LackOfCohesion
     */
    public void registerToolWindow(Boolean textual, Boolean structural,
                                   Project project,
                                   ArrayList<GeneralFixtureInfo> listGFI,
                                   ArrayList<EagerTestInfo> listETI,
                                   ArrayList<LackOfCohesionInfo> listLOCI){
        System.out.println("\nTOOL WINDOW: Inizio del processo per registrare la ToolWindow: TestWindow\n");

        //Verifico che tipo di toolWindow devo creare
        if(textual){
            createToolWindow("Textual Test Window", project, listGFI, listETI, listLOCI);
        }
        if(structural){
            createToolWindow("Structural Test Window", project, listGFI, listETI, listLOCI);
        }
    }


    private void createToolWindow(String idWindow,
                                 Project project,
                                 ArrayList<GeneralFixtureInfo> listGFI,
                                 ArrayList<EagerTestInfo> listETI,
                                 ArrayList<LackOfCohesionInfo> listLOCI) {
        //Creo la ToolWindow
        ToolWindowManager twm = ToolWindowManager.getInstance(project);
        System.out.println("Ho preso il ToolWindowManager");

        //Questa parte serve a cancellare una eventuale ToolWindow precedentemente presente
        ToolWindow toolWindow = ToolWindowManager.getInstance(project).getToolWindow(idWindow);
        if (toolWindow != null) {
            twm.unregisterToolWindow(idWindow);
            System.out.println("Ho dovuto disattivare una precedente istanza della ToolWindow");
        }

        ToolWindow testWindow = twm.registerToolWindow(idWindow, false, ToolWindowAnchor.BOTTOM, true);
        testWindow.setTitle(idWindow);
        System.out.println("Ho registrato la ToolWindow");

        //Inizio ad occuparmi della formattazione della ToolWindow

        if (listGFI != null) {
            generalFixturePanel = new GeneralFixturePanel(listGFI, project);
        }
        if (listETI != null) {
            eagerTestPanel = new EagerTestPanel(listETI, project);
        }
        if (listLOCI != null){
            lackOfCohesionPanel = new LackOfCohesionPanel(listLOCI, project);
        }

        //Questo metodo si occupa di creare la formattazione interna della ToolWindow e anche di aggiungervela
        if(listETI != null || listGFI != null || listLOCI != null){
            ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();

            //In questa parte costruisco i Content da mettere nella ToolWindow
            if(listGFI != null){
                JBScrollPane scroll = new JBScrollPane(generalFixturePanel);
                Content contentGeneralFixture = contentFactory.createContent(scroll, "GeneralFixture", true);
                testWindow.getContentManager().addContent(contentGeneralFixture);
            }
            if (listETI != null){
                JBScrollPane scroll = new JBScrollPane(eagerTestPanel);
                Content contentEagerTest = contentFactory.createContent(scroll, "EagerTest", true);
                testWindow.getContentManager().addContent(contentEagerTest);
            }
            if (listLOCI != null){
                JBScrollPane scroll = new JBScrollPane(lackOfCohesionPanel);
                Content contentLackOfCohesion = contentFactory.createContent(scroll, "LackOfCohesion", true);
                testWindow.getContentManager().addContent(contentLackOfCohesion);
            }
            testWindow.show(null);
        }

        System.out.println("Ho completato le operazioni riguardanti la ToolWindow");
    }



}
