package main.testSmellDetection.structuralDetector;

import java.util.ArrayList;

import it.unisa.testSmellDiffusion.beans.*;

/**
 * Questa classe sfrutta metriche strutturali per stabilire se una classe di test è affetta da GeneralFixture.
 * @author Stefano Lambiase
 *
 */
public class StructuralDetectorGeneralFixture {
    //Variabili necessari all'analisi intermedia: NOBU
    private ArrayList<MethodBean> listOfMethodFindInNOBU;
    private ArrayList<InstanceVariableBean> listOfInstanceFindInNOBU;

    /**
     * Questo metodo esegue l'effettiva verifica.
     * @param testClass la classe da analizzare.
     * @param pClasses la lista delle production classes.
     * @param testClasses la lista delle classi di test.
     * @return true se la classe è affetta da General Fixture, false altrimenti.
     */
    public boolean isGeneralFixture(ClassBean testClass, ArrayList<ClassBean> pClasses, ArrayList<ClassBean> testClasses, int numberOfFixtureProductionTypes, int numberOfObjectUsesInSetup) {
        if(getNumberOfFixtureProductionTypes(testClass, pClasses) > numberOfFixtureProductionTypes) {
            if(getNumberOfObjectUsesInSetupNOBU(testClass, testClasses) > numberOfObjectUsesInSetup) {
                if(calculateAverageFixtureUsageAFIU(testClass) < 1) {
                    System.out.println("La classe " + testClass.getName() + " è affetta da General Fixture.");
                    return true;
                }
            }
        }
        System.out.println("La classe " + testClass.getName() + " NON è affetta da General Fixture.");
        return false;
    }

    //METODI PER LA PARTE INIZIALE DELL'ANALISI: NFPT + NFOB
    /**
     * Questo metodo ritorna il numero di oggetti con tipo appartenente ad una classe del progetto presenti nella classe di test.
     * @param tc la classe di test da analizzare
     * @param pClasses la lista della production classes.
     * @return il numero di oggetti con tipo appartenente ad una classe del progetto presenti nella classe di test
     */
    private int getNumberOfFixtureProductionTypes(ClassBean tc, ArrayList<ClassBean> pClasses) {
        System.out.println("\nEseguo la ricerca NFPT + NFOB");
        ArrayList<InstanceVariableBean> listOfAllInstance = new ArrayList<>();

        //Eseguo il confronto tra il tipo delle variabili e il nome delle production classes
        for(InstanceVariableBean var : tc.getInstanceVariables()) {
            for(ClassBean classBean : pClasses) {
                if(var.getType().equals(classBean.getName())) {
                    if(!listOfAllInstance.contains(var)) {
                        listOfAllInstance.add(var);
                    } else {
                        System.out.println("Variabile già trovata in precedenza");
                    }
                }
            }
        }
        System.out.println("Fine della ricerca NFPT + NFOB");
        return listOfAllInstance.size();
    }

    //METODI PER LA PARTE INTERMEDIA DELL'ANALISI: NOBU
    /**
     * Metodo usato per trovare il numero di oggetti utilizzati, direttamente e indirettamente, nel metodo di setup.
     * @param testClass la classe da analizzare.
     * @param testClasses la lista di classi di test del progetto.
     * @return il numero di oggetti usati nel metodo di setup.
     */
    private int getNumberOfObjectUsesInSetupNOBU(ClassBean testClass, ArrayList<ClassBean> testClasses) {
        System.out.println("\nEseguo la ricerca NOBU = DOBU + IOBU");
        int numberOfObjectUses = 0;

        //Inizializzo le variabili utili per l'analisi ricorsiva
        listOfMethodFindInNOBU = new ArrayList<>();
        listOfInstanceFindInNOBU = new ArrayList<>();

        ArrayList<MethodBean> testMethods = new ArrayList<>();
        //Per ogni classe trovate mi prendo i metodi. Questo mi serve per torvare i test helper methods.
        for(ClassBean c : testClasses) {
            testMethods.addAll(c.getMethods());
        }

        //Mi cerco il metodo di setup
        for (MethodBean mb : testClass.getMethods()) {
            if (mb.getName().toLowerCase().equals("setup")) {
                numberOfObjectUses =+ recorsiveIOBU(mb, testMethods);
            }
        }
        System.out.println("Fine ricerca NOBU");
        return numberOfObjectUses;
    }

    /**
     * Metodo ricorsivo per la ricerca ricorsiva degli usi di oggetti all'interno di metodi helper chiamati dal metodo di setup.
     * @param methodAnalyzed il metodo da analizzare.
     * @param testMethods la lista di tutti i metodi di test del progetto.
     * @return il numero di oggetti usati dal metodo.
     */
    private int recorsiveIOBU(MethodBean methodAnalyzed, ArrayList<MethodBean> testMethods) {
        int directObjectUses = 0;
        ArrayList<MethodBean> invokedTestHelperMethods = new ArrayList<>();

        //Conta del numero di oggetti usati
        //Occorre aggiungere variabili e metodi all'array per verificare la non presenza di ripetizioni
        for(InstanceVariableBean var : methodAnalyzed.getUsedInstanceVariables()) {
            if(!listOfInstanceFindInNOBU.contains(var)) {
                listOfInstanceFindInNOBU.add(var);
                directObjectUses ++;
            }
        }
        for(MethodBean method : methodAnalyzed.getMethodCalls()) {
            if(!listOfMethodFindInNOBU.contains(method)) {
                listOfMethodFindInNOBU.add(method);
                directObjectUses ++;
            }
        }

        //Verifica la presenza di helper test methods
        if(methodAnalyzed.getMethodCalls().isEmpty()) {
            return directObjectUses;
        } else {
            //Ricerca dei metodi di test chiamati dal metodo in input
            for(MethodBean calledMethod : methodAnalyzed.getMethodCalls()) {
                for(MethodBean testMethod : testMethods) {
                    if(calledMethod.getName().toLowerCase().equals(testMethod.getName())) {
                        invokedTestHelperMethods.add(calledMethod);
                    }
                }
            }

            //Chiamata ricorsiva
            if(invokedTestHelperMethods.isEmpty()) {
                return directObjectUses;
            } else {
                for(MethodBean calledMethod : invokedTestHelperMethods) {
                    int i =+ recorsiveIOBU(calledMethod, testMethods);        //Ricorsione
                    directObjectUses =+ i;
                }
            }
            return directObjectUses;
        }
    }

    //METODI PER LA PARTE FINALE DELL'ANALISI AFIU: Average Fixture Usage
    /**
     * Questo metodo va a calcolare la media di utilizzo della fixture da parte di tutti i metodi della test class.
     * @param testClass la classe di test da analizzare.
     * @return la media di utilizzo della fixture.
     */
    private double calculateAverageFixtureUsageAFIU(ClassBean testClass) {
        System.out.println("\nInizio la ricerca AFIU");
        double sumOfFixtureUsageRate = 0.0;
        int numberOfTestMethods = 0;

        for (MethodBean mbInside : testClass.getMethods()) {
            //Controllo per accertarmi che il metodo in analisi non sia ne un costruttore ne un setup
            if (!mbInside.getName().equals(testClass.getName())
                    && !mbInside.getName().toLowerCase().equals("setup")
                    && !mbInside.getName().toLowerCase().equals("teardown")) {
                numberOfTestMethods ++;
                sumOfFixtureUsageRate += getFixtureUsageRateOfMethod(mbInside, testClass);
            }
        }
        //Se questo valore supera un tot allora la classe è smelly
        System.out.println("La somma degli usi della fixture è: " + sumOfFixtureUsageRate);
        System.out.println("La media totale di utilizzo è: " + sumOfFixtureUsageRate / numberOfTestMethods);
        System.out.println("Fine della ricerca AFIU");
        return sumOfFixtureUsageRate / numberOfTestMethods;
    }

    /**
     * Questo metodo calcola l'utilizzo dela fixture da parte di un metodo di una test class.
     * @param methodAnalyzed il metodo in esame.
     * @param testClass la classe in esame.
     * @return un valore rappresentate l'utilizzo della fixture.
     */
    private double getFixtureUsageRateOfMethod(MethodBean methodAnalyzed, ClassBean testClass) {
        System.out.println("\nInizio il calcolo dell'uso della Fixture per il metodo: " + methodAnalyzed + ".");
        if(testClass.getInstanceVariables().size() == 1) {
            return 1.0;
        } else {
            return methodAnalyzed.getUsedInstanceVariables().size() / testClass.getInstanceVariables().size();
        }
    }

}
