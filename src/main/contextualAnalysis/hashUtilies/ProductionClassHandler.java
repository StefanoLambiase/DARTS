package main.contextualAnalysis.hashUtilies;

import main.testSmellDetection.bean.PsiClassBean;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductionClassHandler {

    private ArrayList<PsiClassBean> allProductionClasses;

    public ProductionClassHandler(ArrayList<PsiClassBean> allProductionClasses){
        this.allProductionClasses = allProductionClasses;
    }
    
}
