package main.contextualAnalysis;

import main.contextualAnalysis.messageChecker.StringChecker;
import org.repodriller.domain.Commit;
import org.repodriller.domain.Modification;
import org.repodriller.persistence.PersistenceMechanism;
import org.repodriller.scm.CommitVisitor;
import org.repodriller.scm.SCMRepository;

public class DevelopersVisitor implements CommitVisitor {
    private String javaClass;

    public DevelopersVisitor(String productionClass) {
        javaClass = productionClass;
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
}
