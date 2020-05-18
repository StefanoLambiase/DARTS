package main.contextualAnalysis;

import main.contextualAnalysis.hashUtilies.ProductionClassesSingleton;
import main.contextualAnalysis.messageChecker.StringChecker;
import main.testSmellDetection.bean.PsiClassBean;
import org.repodriller.domain.Commit;
import org.repodriller.domain.Modification;
import org.repodriller.persistence.PersistenceMechanism;
import org.repodriller.scm.CommitVisitor;
import org.repodriller.scm.SCMRepository;

import java.util.HashMap;

public class DevelopersVisitor implements CommitVisitor {
    private String javaClass;
    private HashMap<String, Integer> fixingActivities;

    public DevelopersVisitor(String productionClass) {
        javaClass = productionClass;
        this.fixingActivities = new HashMap<>();
        initializeHashMap();
    }

    @Override
    public void process(SCMRepository scmRepository, Commit commit, PersistenceMechanism persistenceMechanism) {

        for (Modification modification : commit.getModifications()) {
            String[] fileEditedPath = modification.getFileName().split("/");
            String fileEditedName = fileEditedPath[fileEditedPath.length - 1];

            //check if the modify belongs to the production class
            boolean isProductionClass = fileEditedName.equals(javaClass + ".java");
            System.out.println(isProductionClass + ", " + fileEditedName + ", " + javaClass);

            if(StringChecker.isBugFixingMessage(commit.getMsg())) {
                updateActivities(fileEditedName);
                if (isProductionClass) {
                    persistenceMechanism.write(
                            commit.getHash(),
                            commit.getCommitter().getName(),
                            commit.getMsg(),
                            modification.getFileName()
                    );
                }
            }
        }
    }

    private void updateActivities (String className) {
        if (fixingActivities.containsKey(className)) {
            int activities = fixingActivities.get(className);
            activities++;
            System.out.println("Update activities: " + fixingActivities.get(className)); // testing purposes
            fixingActivities.put(className, activities);
        }
    }

    private void initializeHashMap () {
        ProductionClassesSingleton productionClassesSingleton = ProductionClassesSingleton.getIstance();
        for (PsiClassBean productionClass : productionClassesSingleton.getProductionClasses()) {
            String productionClassName = productionClass.getName();
            if (!fixingActivities.containsKey(productionClassName)) {
                fixingActivities.put(productionClassName, 0);
            }
        }
    }

    public HashMap<String, Integer> getFixingActivities() {
        return fixingActivities;
    }
}
