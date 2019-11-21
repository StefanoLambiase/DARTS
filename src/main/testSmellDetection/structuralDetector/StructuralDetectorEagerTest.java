package main.testSmellDetection.structuralDetector;

import java.util.ArrayList;

import it.unisa.testSmellDiffusion.beans.ClassBean;
import it.unisa.testSmellDiffusion.beans.MethodBean;

/**
 * Questa classe sfrutta metriche strutturali per stabilire se una classe di test è affetta da Eager Test.
 * @author Stefano Lambiase
 */
public class StructuralDetectorEagerTest {
    //Array usato per la ricerca ricorsiva.
    private ArrayList<MethodBean> listOfProductionMethodFindInPTU;

    /**
     * Metodo che verifica se una classe è effettivamente affetta da EagerTest.
     * @param testClass la classe in esame.
     * @param testClasses la lista delle classi di test.
     * @param productionClasses la lista delle production classes.
     * @return true se è affetta da EagerTest, false altrimenti.
     */
    public boolean isEagerTest(ClassBean testClass, ArrayList<ClassBean> testClasses, ArrayList<ClassBean> productionClasses) {
        System.out.println("Sono il detector strutturale per Eager Test, verifico se la classe " + testClass.getName() + " è affetta da Eager Test.");
        for (MethodBean mb : testClass.getMethods()) {
            if (!mb.getName().equals(testClass.getName())
                    && !mb.getName().toLowerCase()
                    .equals("setup")
                    && !mb.getName().toLowerCase()
                    .equals("teardown")) {
                int i = getNumberOfProductionTypeUsesPTU(mb, testClasses, productionClasses);
                System.out.println("Numero di usi di Production Type: " + i);
                if(i > 2) {
                    System.out.println("La classe " + testClass.getName() + " è affetta da Eager Test.");
                    return true;
                }
            }
        }
        System.out.println("La classe " + testClass.getName() + " NON è affetta da Eager Test.");
        return false;
    }

    /**
     * Metodo principale che inizializza le risorse per eseguire la ricerca.
     * @param methodAnalyzed Il metodo che sto analizzando.
     * @param testClasses la lista delle classi di test.
     * @param productionClasses la lista delle production classes.
     * @return il numero di usi di production types individuati.
     */
    private int getNumberOfProductionTypeUsesPTU(MethodBean methodAnalyzed, ArrayList<ClassBean> testClasses, ArrayList<ClassBean> productionClasses) {
        System.out.println("Eseguo la ricerca PTU");

        //Inizializzo le variabili utili per l'analisi ricorsiva
        listOfProductionMethodFindInPTU = new ArrayList<>();

        ArrayList<MethodBean> testMethods = new ArrayList<>();
        //Per ogni classe trovate mi prendo i metodi. Questo mi serve per torvare i test helper methods.
        for(ClassBean c : testClasses) {
            testMethods.addAll(c.getMethods());
        }

        ArrayList<MethodBean> productionMethods = new ArrayList<>();
        //Per ogni classe trovate mi prendo i metodi. Questo mi serve per torvare i production methods.
        for(ClassBean c : productionClasses) {
            productionMethods.addAll(c.getMethods());
        }

        //Chiamata ricorsiva per la conta di chiamate a productionMethods
        recorsivePTU(methodAnalyzed, testMethods, productionMethods);

        //Se questo valore è maggiore di un tot allora il metodo è smelly
        return listOfProductionMethodFindInPTU.size();
    }

    /**
     * Metodo ricorsivo per la ricerca ricorsiva degli usi di oggetti all'interno di metodi helper chiamati dal metodo di setup.
     * @param m il metodo che stiamo analizzando.
     * @param testMethods tutti i metodi di test individuati nel progetto.
     */
    private void recorsivePTU(MethodBean m, ArrayList<MethodBean> testMethods, ArrayList<MethodBean> productionMethods) {
        ArrayList<MethodBean> invokedTestHelperMethods = new ArrayList<>();

        //Conta del numero di production methods usati.
        //Occorre aggiungere variabili e metodi all'array per verificare che non siano presenti ripetizioni.
        for(MethodBean method : m.getMethodCalls()) {
            for(MethodBean productionMethod : productionMethods) {
                if(method.getName().equals(productionMethod.getName())) {
                    if(!listOfProductionMethodFindInPTU.contains(method)) {
                        listOfProductionMethodFindInPTU.add(method);
                    }
                }
            }
        }
        //Verifica la presenza di helper test methods.
        if (!m.getMethodCalls().isEmpty()) {
            //Ricerca dei metodi di test chiamati dal metodo in input.
            for (MethodBean calledMethod : m.getMethodCalls()) {
                for (MethodBean testMethod : testMethods) {
                    if (calledMethod.getName().toLowerCase().equals(testMethod.getName())) {
                        invokedTestHelperMethods.add(calledMethod);
                    }
                }
            }
            //Chiamata ricorsiva
            if (!invokedTestHelperMethods.isEmpty()) {
                for (MethodBean calledMethod : invokedTestHelperMethods) {
                    recorsivePTU(calledMethod, testMethods, productionMethods);        //Ricorsione
                }
            }
        }
    }

}
