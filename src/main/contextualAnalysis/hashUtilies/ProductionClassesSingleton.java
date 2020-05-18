package main.contextualAnalysis.hashUtilies;

import main.testSmellDetection.bean.PsiClassBean;

import java.util.ArrayList;

public class ProductionClassesSingleton {
    private static ProductionClassesSingleton istance = null;
    private ArrayList<PsiClassBean> productionClasses;

    public ProductionClassesSingleton() {}

    public static ProductionClassesSingleton getIstance() {
        if (istance == null) {
            istance = new ProductionClassesSingleton();
        }

        return istance;
    }

    public ArrayList<PsiClassBean> getProductionClasses() {
        return productionClasses;
    }

    public void setProductionClasses(ArrayList<PsiClassBean> productionClasses) {
        this.productionClasses = productionClasses;
    }
}
