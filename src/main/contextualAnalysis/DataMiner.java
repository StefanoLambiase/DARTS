package main.contextualAnalysis;

import data.TestClassAnalysis;
import main.testSmellDetection.testSmellInfo.TestSmellInfo;
import main.windowCommitConstruction.contextualAnalysisPanel.ContextualAnalysisResultsFrame;
import org.repodriller.RepositoryMining;
import org.repodriller.Study;
import org.repodriller.filter.commit.OnlyModificationsWithFileTypes;
import org.repodriller.filter.commit.OnlyNoMerge;
import org.repodriller.filter.diff.OnlyDiffsWithFileTypes;
import org.repodriller.filter.range.Commits;
import org.repodriller.persistence.csv.CSVFile;
import org.repodriller.scm.CollectConfiguration;
import org.repodriller.scm.GitRepository;

import java.io.File;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

//class able to manage the data extraction
public class DataMiner implements Study{

    private TestSmellInfo smell;
    private String productionClass;
    private String projectPath;
    private GregorianCalendar commitSinceDate;
    private HashMap<String, Integer> fixingActivities;
    private TestClassAnalysis testClassAnalysis;

    public DataMiner(TestSmellInfo info, TestClassAnalysis testClassAnalysis, String projectPath, GregorianCalendar commitSinceDate){
        smell = info;
        this.testClassAnalysis = testClassAnalysis;
        productionClass = info.getClassWithSmell().getProductionClass().getName();
        this.projectPath = projectPath;
        this.commitSinceDate = commitSinceDate;
        fixingActivities = new HashMap<>();
    }

    @Override
    public void execute() {
        String userDesktop = System.getProperty("user.home") + File.separator + "Desktop";
        DevelopersVisitor devVisitor = new DevelopersVisitor(productionClass);
        new RepositoryMining()
                .in(GitRepository.singleProject(projectPath))
                .through(Commits.since(commitSinceDate))
                .filters(
                        new OnlyNoMerge(),
                        new OnlyModificationsWithFileTypes(Arrays.asList(".java")))
                .collect(new CollectConfiguration().sourceCode().diffs(new OnlyDiffsWithFileTypes(Arrays.asList(".java"))))
                .collect(new CollectConfiguration().commitMessages())
                .process(devVisitor, new CSVFile(userDesktop + File.separator + "devs.csv"))
                .mine();

        // Tracks the number of bug fixing activities done in every production class detected
        fixingActivities = devVisitor.getFixingActivities();
        // Printing the HashMap for debugging purposes
        fixingActivities.entrySet().forEach(entry->{
            System.out.println(entry.getKey() + " " + entry.getValue());
        });
        // Extracting the author with the max numbers of commits
        Map.Entry<String, Integer> authorMaxCommits = null;
        for (Map.Entry<String, Integer> entry : devVisitor.getCommitsPerAuthor().entrySet()) {
            if (authorMaxCommits == null || entry.getValue().compareTo(authorMaxCommits.getValue()) > 0) {
                authorMaxCommits = entry;
            }
        }

        // Showing the results frame to the user
        new ContextualAnalysisResultsFrame(
                productionClass,
                devVisitor.getCommitsAnalyzed(),
                authorMaxCommits.getKey(),
                testClassAnalysis.getCoverage().getLineCoverage(),
                testClassAnalysis.getCoverage().getBranchCoverage(),
                testClassAnalysis.getFlakyTests().getFlakyMethods().size());
    }
}
