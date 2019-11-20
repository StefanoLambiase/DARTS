package main.testSmellDetection.structuralDetector;

import java.util.ArrayList;
import java.util.Vector;

import it.unisa.testSmellDiffusion.beans.ClassBean;
import it.unisa.testSmellDiffusion.beans.InstanceVariableBean;
import it.unisa.testSmellDiffusion.beans.MethodBean;

public class StructuralDetectorLackOfCohesion {
    /**
     * Metodo che verifica se una classe è effettivamente affetta da LackOfCohesionOfTestMethods.
     * @param testClass la classe da analizzare.
     * @return true se la classe è affetta, false altrimenti.
     */
    public boolean isLackOfCohesion(ClassBean testClass) {
        System.out.println("Sono il detector strutturale per Lack of Cohesion, verifico se la classe " + testClass.getName() + " è affetta da Lack of Cohesion of Test Methods.");
        int i = getLCOM(testClass);
        System.out.println("Numero di variabili non condivise: " + i);
        if(i > 1) {
            System.out.println("La classe " + testClass.getName() + " è affetta da Lack of Cohesion.");
            return true;
        }
        System.out.println("La classe " + testClass.getName() + " NON è affetta da Lack of Cohesion.");
        return false;
    }

    /**
     * Metodo usato per calcolare quanto una classe è coesa.
     * Nello specifico esso fa un confronto tra le variabili di instanza condivise e quelle non condivise tra i metodi di una classe.
     * @param testClass la classe in esame.
     * @return 0 se le variabili condivise superano quelle non condivise, la differenza tra le non condivise e le condivise altrimenti
     */
    private static int getLCOM(ClassBean testClass){
        System.out.println("Eseguo la ricerca LCOM");
        int share = 0;
        int notShare = 0;
        ArrayList<MethodBean> listOfMethods = new ArrayList<>();

        for(MethodBean mb : testClass.getMethods()) {
            //Controllo se il metodo non è ne un costruttore ne un metodo di setup
            if (!mb.getName().equals(testClass.getName())
                    && !mb.getName().toLowerCase().equals("setup")
                    && !mb.getName().toLowerCase().equals("teardown")) {
                listOfMethods.add(mb);
            }
        }

        for(int i = 0; i < listOfMethods.size(); i++){
            for (int j = i + 1; j < listOfMethods.size(); j++){
                if(shareAnInstanceVariable(listOfMethods.get(i), listOfMethods.get(j))){
                    share++;
                } else {
                    notShare++;
                }
            }
        }
        System.out.println("Numero di variabili condivise: " + share);
        System.out.println("Numero di variabili NON condivise: " + notShare);
        if(share > notShare){
            //La classe è coesa
            return 0;
        } else {
            //La classe non è coesa
            return (notShare-share);
        }
    }

    /**
     * Metodo di supporto per la verifica della condivisione di variabili tra due metodi.
     * @param m1 il primo metodo.
     * @param m2 il secondo metodo.
     * @return un booleano per verificare se la variabile è condivisa.
     */
    private static boolean shareAnInstanceVariable(MethodBean m1, MethodBean m2){
        //Utilizzo il Detector di Eclipse
        Vector<InstanceVariableBean> m1Variables = (Vector<InstanceVariableBean>) m1.getUsedInstanceVariables();
        Vector<InstanceVariableBean> m2Variables = (Vector<InstanceVariableBean>) m2.getUsedInstanceVariables();

        for(InstanceVariableBean i: m1Variables){
            if(m2Variables.contains(i)){
                return true;
            }
        }
        return false;
    }

}
