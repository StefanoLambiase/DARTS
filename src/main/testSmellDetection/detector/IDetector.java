package main.testSmellDetection.detector;

import it.unisa.testSmellDiffusion.testSmellInfo.eagerTest.EagerTestInfo;
import it.unisa.testSmellDiffusion.testSmellInfo.generalFixture.GeneralFixtureInfo;
import it.unisa.testSmellDiffusion.testSmellInfo.lackOfCohesion.LackOfCohesionInfo;

import java.util.ArrayList;

public interface IDetector {
    ArrayList<GeneralFixtureInfo> executeDetectionForGeneralFixture();

    ArrayList<EagerTestInfo> executeDetectionForEagerTest();

    ArrayList<LackOfCohesionInfo> executeDetectionForLackOfCohesion();
}
