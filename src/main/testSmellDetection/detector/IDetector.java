package main.testSmellDetection.detector;

import main.testSmellDetection.testSmellInfo.eagerTest.EagerTestInfo;
import main.testSmellDetection.testSmellInfo.generalFixture.GeneralFixtureInfo;
import main.testSmellDetection.testSmellInfo.lackOfCohesion.LackOfCohesionInfo;

import java.util.ArrayList;

public interface IDetector {
    ArrayList<GeneralFixtureInfo> executeDetectionForGeneralFixture();

    ArrayList<EagerTestInfo> executeDetectionForEagerTest();

    ArrayList<LackOfCohesionInfo> executeDetectionForLackOfCohesion();
}
