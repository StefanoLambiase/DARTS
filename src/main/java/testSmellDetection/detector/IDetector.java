package testSmellDetection.detector;

import testSmellDetection.bean.PsiMethodBean;
import testSmellDetection.testSmellInfo.eagerTest.EagerTestInfo;
import testSmellDetection.testSmellInfo.generalFixture.GeneralFixtureInfo;
import testSmellDetection.testSmellInfo.lackOfCohesion.LackOfCohesionInfo;

import java.util.ArrayList;

public interface IDetector {
    ArrayList<GeneralFixtureInfo> executeDetectionForGeneralFixture();

    ArrayList<EagerTestInfo> executeDetectionForEagerTest();

    ArrayList<LackOfCohesionInfo> executeDetectionForLackOfCohesion();

    int getClassBeansNumber();
    int getMethodBeansNumber();
}
