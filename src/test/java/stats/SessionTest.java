package stats;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Date;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class SessionTest {
    Session session = new Session();


    /**
     * TC_0_0 : return -1
     */
    @Test
    public void densityET_ZeroTotalMethod_Test(){
        session.setnOfTotalMethod(0);
        assertEquals(-1, session.densityET());
    }
    /**
     * TC_0_1 : return 0
     */
    @Test
    public void densityET_ZeroET_Test(){
        session.setNOfET(0);
        session.setnOfTotalMethod(1);
        assertEquals(0, session.densityET());
    }
    /**
     * TC_0_2 : return NumberEagerTest / NumberTestClasses
     */
    @Test
    public void densityET_OneToOne_Test(){
        session.setNOfET(1);
        session.setnOfTotalMethod(1);
        assertEquals(1/1, session.densityET());
    }
    /**
     * TC_0_3 : return NumberEagerTest / NumberTestClasses
     */
    @Test
    public void densityET_TwoToOne_Test(){
        session.setNOfET(2);
        session.setnOfTotalMethod(1);
        assertEquals((float)2/1, session.densityET());
    }
    /**
     * TC_0_4 : return NumberEagerTest / NumberTestClasses
     */
    @Test
    public void densityET_OneToTwo_Test(){
        session.setNOfET(1);
        session.setnOfTotalMethod(2);
        assertEquals((float)1/2, session.densityET());
    }
    /**
     * TC_0_5 : return NumberEagerTest / NumberTestClasses
     */
    @Test
    public void densityET_TwoToTwo_Test(){
        session.setNOfET(2);
        session.setnOfTotalMethod(2);
        assertEquals((float)2/2, session.densityET());
    }

    /**
     * TC_1_0 : return -1
     */
    @Test
    public void densityGF_ZeroTotalClasses_Test(){
        session.setnOfTotalClasses(0);
        assertEquals(-1, session.densityGF());
    }
    /**
     * TC_1_1 : return 0
     */
    @Test
    public void densityGF_ZeroGF_Test(){
        session.setNOfGF(0);
        session.setnOfTotalClasses(1);
        assertEquals(0, session.densityGF());
    }
    /**
     * TC_1_2 : return NumberEagerTest / NumberTestClasses
     */
    @Test
    public void densityGF_OneToOne_Test(){
        session.setNOfGF(1);
        session.setnOfTotalClasses(1);
        assertEquals(1/1, session.densityGF());
    }
    /**
     * TC_1_3 : return NumberEagerTest / NumberTestClasses
     */
    @Test
    public void densityGF_TwoToOne_Test(){
        session.setNOfGF(2);
        session.setnOfTotalClasses(1);
        assertEquals((float)2/1, session.densityGF());
    }
    /**
     * TC_1_4 : return NumberEagerTest / NumberTestClasses
     */
    @Test
    public void densityGF_OneToTwo_Test(){
        session.setNOfGF(1);
        session.setnOfTotalClasses(2);
        assertEquals((float)1/2, session.densityGF());
    }
    /**
     * TC_1_5 : return NumberEagerTest / NumberTestClasses
     */
    @Test
    public void densityGF_TwoToTwo_Test(){
        session.setNOfGF(2);
        session.setnOfTotalClasses(2);
        assertEquals((float)2/2, session.densityGF());
    }

    /**
     * TC_2_0 : return -1
     */
    @Test
    public void densityLOC_ZeroTotalClasses_Test(){
        session.setnOfTotalClasses(0);
        assertEquals(-1, session.densityLOC());
    }
    /**
     * TC_2_1 : return 0
     */
    @Test
    public void densityLOC_ZeroLOC_Test(){
        session.setNOfLOC(0);
        session.setnOfTotalClasses(1);
        assertEquals(0, session.densityLOC());
    }
    /**
     * TC_2_2 : return NumberEagerTest / NumberTestClasses
     */
    @Test
    public void densityLOC_OneToOne_Test(){
        session.setNOfLOC(1);
        session.setnOfTotalClasses(1);
        assertEquals(1/1, session.densityLOC());
    }
    /**
     * TC_2_3 : return NumberEagerTest / NumberTestClasses
     */
    @Test
    public void densityLOC_TwoToOne(){
        session.setNOfLOC(2);
        session.setnOfTotalClasses(1);
        assertEquals((float)2/1, session.densityLOC());
    }
    /**
     * TC_2_4 : return NumberEagerTest / NumberTestClasses
     */
    @Test
    public void densityLOC_OneToTwo_Test(){
        session.setNOfLOC(1);
        session.setnOfTotalClasses(2);
        assertEquals((float)1/2, session.densityLOC());
    }
    /**
     * TC_2_5 : return NumberEagerTest / NumberTestClasses
     */
    @Test
    public void densityLOC_TwoToTwo_Test(){
        session.setNOfLOC(2);
        session.setnOfTotalClasses(2);
        assertEquals((float)2/2, session.densityLOC());
    }

    /**
     * TC_16_0 : return -1
     */
    @Test
    public void densityHCTD_ZeroTotalClasses_Test(){
        session.setnOfTotalClasses(0);
        assertEquals(-1, session.densityHCTD());
    }
    /**
     * TC_16_1 : return 0
     */
    @Test
    public void densityHCTD_ZeroHCTD_Test(){
        session.setNOfHCTD(0);
        session.setnOfTotalClasses(1);
        assertEquals(0, session.densityHCTD());
    }
    /**
     * TC_16_2 : return NumberEagerTest / NumberTestClasses
     */
    @Test
    public void densityHCTD_OneToHCTD_Test(){
        session.setNOfHCTD(1);
        session.setnOfTotalClasses(1);
        assertEquals(1/1, session.densityHCTD());
    }
    /**
     * TC_16_3 : return NumberEagerTest / NumberTestClasses
     */
    @Test
    public void densityHCTD_TwoToOne(){
        session.setNOfHCTD(2);
        session.setnOfTotalClasses(1);
        assertEquals((float)2/1, session.densityHCTD());
    }
    /**
     * TC_16_4 : return NumberEagerTest / NumberTestClasses
     */
    @Test
    public void densityHCTD_OneToTwo_Test(){
        session.setNOfHCTD(1);
        session.setnOfTotalClasses(2);
        assertEquals((float)1/2, session.densityHCTD());
    }
    /**
     * TC_16_5 : return NumberEagerTest / NumberTestClasses
     */
    @Test
    public void densityHCTD_TwoToTwo_Test(){
        session.setNOfHCTD(2);
        session.setnOfTotalClasses(2);
        assertEquals((float)2/2, session.densityHCTD());
    }

    /**
     * TC_17_0 : return -1
     */
    @Test
    public void densityMG_ZeroTotalClasses_Test(){
        session.setnOfTotalClasses(0);
        assertEquals(-1, session.densityMG());
    }
    /**
     * TC_17_1 : return 0
     */
    @Test
    public void densityMG_ZeroMG_Test(){
        session.setNOfMG(0);
        session.setnOfTotalClasses(1);
        assertEquals(0, session.densityMG());
    }
    /**
     * TC_17_2 : return NumberEagerTest / NumberTestClasses
     */
    @Test
    public void densityMG_OneToOne_Test(){
        session.setNOfMG(1);
        session.setnOfTotalClasses(1);
        assertEquals(1/1, session.densityMG());
    }
    /**
     * TC_17_3 : return NumberEagerTest / NumberTestClasses
     */
    @Test
    public void densityMG_TwoToOne(){
        session.setNOfMG(2);
        session.setnOfTotalClasses(1);
        assertEquals((float)2/1, session.densityMG());
    }
    /**
     * TC_17_4 : return NumberEagerTest / NumberTestClasses
     */
    @Test
    public void densityMG_OneToTwo_Test(){
        session.setNOfMG(1);
        session.setnOfTotalClasses(2);
        assertEquals((float)1/2, session.densityMG());
    }
    /**
     * TC_17_5 : return NumberEagerTest / NumberTestClasses
     */
    @Test
    public void densityMG_TwoToTwo_Test(){
        session.setNOfMG(2);
        session.setnOfTotalClasses(2);
        assertEquals((float)2/2, session.densityMG());
    }

    /**
     * TC_18_0 : return -1
     */
    @Test
    public void densityTCD_ZeroTotalClasses_Test(){
        session.setnOfTotalClasses(0);
        assertEquals(-1, session.densityTCD());
    }
    /**
     * TC_18_1 : return 0
     */
    @Test
    public void densityTCD_ZeroTCD_Test(){
        session.setNOfTCD(0);
        session.setnOfTotalClasses(1);
        assertEquals(0, session.densityTCD());
    }
    /**
     * TC_18_2 : return NumberEagerTest / NumberTestClasses
     */
    @Test
    public void densityTCD_OneToOne_Test(){
        session.setNOfTCD(1);
        session.setnOfTotalClasses(1);
        assertEquals(1/1, session.densityTCD());
    }
    /**
     * TC_18_3 : return NumberEagerTest / NumberTestClasses
     */
    @Test
    public void densityTCD_TwoToOne(){
        session.setNOfTCD(2);
        session.setnOfTotalClasses(1);
        assertEquals((float)2/1, session.densityTCD());
    }
    /**
     * TC_18_4 : return NumberEagerTest / NumberTestClasses
     */
    @Test
    public void densityTCD_OneToTwo_Test(){
        session.setNOfTCD(1);
        session.setnOfTotalClasses(2);
        assertEquals((float)1/2, session.densityTCD());
    }
    /**
     * TC_18_5 : return NumberEagerTest / NumberTestClasses
     */
    @Test
    public void densityTCD_TwoToTwo_Test(){
        session.setNOfTCD(2);
        session.setnOfTotalClasses(2);
        assertEquals((float)2/2, session.densityTCD());
    }

    /**
     * TC_19_0: return 1
     */
    @Test
    public void totalClasses_Test(){
        int nOfTotalClasses = 2;
        session.setnOfTotalClasses(nOfTotalClasses);
        assertEquals(nOfTotalClasses, session.getnOfTotalClasses());
    }

    /**
     * TC_20_0: return 1
     */
    @Test
    public void totalMethod_Test(){
        int nOfTotalMethod = 6;
        session.setnOfTotalMethod(nOfTotalMethod);
        assertEquals(nOfTotalMethod, session.getnOfTotalMethod());
    }

    /**
     * TC_21_0: return 1
     */
    @Test
    public void nOfET_Test(){
        int nOfEt = 1;
        session.setNOfET(nOfEt);
        assertEquals(nOfEt, session.getNOfET());
    }

    /**
     * TC_22_0: return 1
     */
    @Test
    public void nOfGF_Test(){
        int nOfGF = 1;
        session.setNOfGF(nOfGF);
        assertEquals(nOfGF, session.getNOfGF());
    }

    /**
     * TC_23_0: return 1
     */
    @Test
    public void nOfLOC_Test(){
        int nOfLOC = 1;
        session.setNOfLOC(nOfLOC);
        assertEquals(nOfLOC, session.getNOfLOC());
    }

    /**
     * TC_24_0: return 1
     */
    @Test
    public void nOfHCTD_Test(){
        int nOfHCTD = 1;
        session.setNOfHCTD(nOfHCTD);
        assertEquals(nOfHCTD, session.getNOfHCTD());
    }

    /**
     * TC_25_0: return 1
     */
    @Test
    public void nOfMG_Test(){
        int nOfMG = 1;
        session.setNOfMG(nOfMG);
        assertEquals(nOfMG, session.getNOfMG());
    }

    /**
     * TC_26_0: return 1
     */
    @Test
    public void nOfTCD_Test(){
        int nOfTCD = 1;
        session.setNOfTCD(nOfTCD);
        assertEquals(nOfTCD, session.getNOfTCD());
    }

    /**
     * TC_27_0: return difference between endTime and startTime
     */
    @Test
    public void executionTime_Test(){
        long startTime = new Date().getTime();
        long endTime = new Date().getTime();
        long expectedResult = endTime - startTime;
        session.setStartTime(startTime);
        session.setEndTime(endTime);
        assertEquals(expectedResult, session.getExecutionTime());
    }

    @Test
    public void getActionUnitOneElementTest(){
        Session sessionMock = Mockito.mock(Session.class);
        ArrayList<Action> actionList = new ArrayList<>();
        actionList.add(new Action());
        when(sessionMock.getActions()).thenReturn(actionList);
        assertEquals(actionList.size(), sessionMock.getActions().size());

    }

    @Test
    public void getActionUnitZeroElementTest(){
        Session sessionMock = Mockito.mock(Session.class);
        when(sessionMock.getActions()).thenReturn(new ArrayList<Action>());
        assertEquals(new ArrayList<Action>(), sessionMock.getActions());

    }

    @Test
    public void getActionIntegrationOneElementTest(){
        ArrayList<Action> actionList = new ArrayList<>();
        actionList.add(new Action());
        session.setActions(actionList);
        assertEquals(actionList.size(), session.getActions().size());
    }

    @Test
    public void getActionIntegrationZeroElementTest(){
        ArrayList<Action> actionList = new ArrayList<>();
        session.setActions(actionList);
        assertEquals(actionList.size(), session.getActions().size());
    }
}