package contextualAnalysis;

import contextualAnalysis.hashUtilies.ProductionClassesSingleton;
import contextualAnalysis.messageChecker.StringChecker;
import testSmellDetection.bean.PsiClassBean;
import org.repodriller.domain.Commit;
import org.repodriller.domain.Modification;
import org.repodriller.persistence.PersistenceMechanism;
import org.repodriller.scm.CommitVisitor;
import org.repodriller.scm.SCMRepository;

import java.util.HashMap;
import java.util.Vector;

public class DevelopersVisitor implements CommitVisitor {
    private String javaClass;
    private HashMap<String, Integer> fixingActivities;
    private Vector<Commit> commitsAnalyzed;
    private HashMap<String, Integer> commitsPerAuthor;

    public DevelopersVisitor(String productionClass) {
        javaClass = productionClass;
        this.commitsAnalyzed = new Vector<>();
        this.commitsPerAuthor = new HashMap<>();
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
                String className = fileEditedName.replace(".java", "");
                updateActivities(className);
                if (isProductionClass) {
                    // Add the commit inside the list of the commit analyzed
                    commitsAnalyzed.add(commit);
                    // Tracking authors and commits done
                    if (commitsPerAuthor.containsKey(commit.getAuthor().getName())) {
                        int count = commitsPerAuthor.get(commit.getAuthor().getName());
                        count++;
                        commitsPerAuthor.put(commit.getAuthor().getName(), count);
                    } else {
                        commitsPerAuthor.put(commit.getAuthor().getName(), 1);
                    }
                    // Writing commit information into the csv file
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

    public Vector<Commit> getCommitsAnalyzed() {
        return commitsAnalyzed;
    }

    public HashMap<String, Integer> getCommitsPerAuthor() {
        return commitsPerAuthor;
    }

    public HashMap<String, Integer> getFixingActivities() {
        return fixingActivities;
    }
}
