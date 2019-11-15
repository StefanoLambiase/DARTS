package main.testSmellDetection;

import it.unisa.testSmellDiffusion.testSmellInfo.eagerTest.EagerTestInfo;
import it.unisa.testSmellDiffusion.testSmellInfo.generalFixture.GeneralFixtureInfo;
import it.unisa.testSmellDiffusion.testSmellInfo.lackOfCohesion.LackOfCohesionInfo;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public interface IDetector {
    ArrayList<GeneralFixtureInfo> executeDetectionForGeneralFixture(@NotNull String pFolderPath);

    ArrayList<EagerTestInfo> executeDetectionForEagerTest(@NotNull String pFolderPath);

    ArrayList<LackOfCohesionInfo> executeDetectionForLackOfCohesion(@NotNull String pFolderPath);
}
