package testSmellDetection.detector;

import testSmellDetection.testSmellInfo.eagerTest.EagerTestInfo;
import testSmellDetection.testSmellInfo.generalFixture.GeneralFixtureInfo;
import testSmellDetection.testSmellInfo.hardCodedTestData.HardCodedTestDataInfo;
import testSmellDetection.testSmellInfo.lackOfCohesion.LackOfCohesionInfo;
import testSmellDetection.testSmellInfo.testCodeDuplication.TestCodeDuplicationInfo;
import testSmellDetection.testSmellInfo.mysteryGuest.MysteryGuestInfo;

import java.util.ArrayList;

public interface IDetector {
    ArrayList<GeneralFixtureInfo> executeDetectionForGeneralFixture();

    ArrayList<EagerTestInfo> executeDetectionForEagerTest();

    ArrayList<LackOfCohesionInfo> executeDetectionForLackOfCohesion();

    ArrayList<HardCodedTestDataInfo> executeDetectionForHardCodedTestData();

    ArrayList<MysteryGuestInfo> executeDetectionForMysteryGuest();
  
    ArrayList<TestCodeDuplicationInfo> executeDetectionForTestCodeDuplication();
}
