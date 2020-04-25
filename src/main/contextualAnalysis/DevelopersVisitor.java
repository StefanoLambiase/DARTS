package main.contextualAnalysis;

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
            boolean isProductionClass = (fileEditedName.equals(javaClass + ".java")) ? true : false;
            System.out.println(isProductionClass + ", " + fileEditedName + ", " + javaClass);
            if (isProductionClass) {
                persistenceMechanism.write(
                        commit.getHash(),
                        commit.getCommitter().getName()
                );
            }
        }

    }
}
