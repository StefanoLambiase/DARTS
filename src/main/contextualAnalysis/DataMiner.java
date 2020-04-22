package main.contextualAnalysis;

import org.repodriller.RepoDriller;
import org.repodriller.Study;

//class able to manage the data extraction
public class DataMiner implements Study{

    public static void main(String[] args) {
        new RepoDriller().start(new DataMiner());
    }

    @Override
    public void execute() {
        //implemnts extraction's logic
    }
}
