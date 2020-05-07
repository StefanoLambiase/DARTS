package main.contextualAnalysis;

import main.testSmellDetection.testSmellInfo.TestSmellInfo;
import org.repodriller.RepoDriller;
import org.repodriller.RepositoryMining;
import org.repodriller.Study;
import org.repodriller.filter.commit.OnlyModificationsWithFileTypes;
import org.repodriller.filter.diff.OnlyDiffsWithFileTypes;
import org.repodriller.filter.range.Commits;
import org.repodriller.persistence.csv.CSVFile;
import org.repodriller.scm.CollectConfiguration;
import org.repodriller.scm.GitRepository;

import java.io.File;
import java.util.Arrays;
import java.util.GregorianCalendar;

//class able to manage the data extraction
public class DataMiner implements Study{

    private TestSmellInfo smell;
    private String productionClass;
    private String projectPath;
    private GregorianCalendar commitSinceDate;

    public DataMiner(TestSmellInfo info, String projectPath, GregorianCalendar commitSinceDate){
        smell = info;
        productionClass = info.getClassWithSmell().getProductionClass().getName();
        this.projectPath = projectPath;
        this.commitSinceDate = commitSinceDate;
    }

    @Override
    public void execute() {
        String userDesktop = System.getProperty("user.home") + File.separator + "Desktop";
        new RepositoryMining()
                .in(GitRepository.singleProject(projectPath))
                .through(Commits.since(commitSinceDate))
                .filters(
                        new OnlyModificationsWithFileTypes(Arrays.asList(".java"))
                )
                .collect( new CollectConfiguration().sourceCode().diffs(new OnlyDiffsWithFileTypes(Arrays.asList(".java"))))
                .process(new DevelopersVisitor(productionClass), new CSVFile(userDesktop + File.separator + "devs.csv"))
                .mine();
    }
}
