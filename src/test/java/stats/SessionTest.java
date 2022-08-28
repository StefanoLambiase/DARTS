package stats;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

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
     * TC_0_1 : return -1
     */
    @Test
    public void densityET_ZeroET_Test(){
        session.setNOfET(0);
        session.setnOfTotalMethod(1);
        assertEquals(0, session.densityET());
    }
    /**
     * TC_0_2 : return -1
     */
    @Test
    public void densityET_OneToOne_Test(){
        session.setNOfET(1);
        session.setnOfTotalMethod(1);
        assertEquals(1/1, session.densityET());
    }
    /**
     * TC_0_3 : return -1
     */
    @Test
    public void densityET_TwoToOne_Test(){
        session.setNOfET(2);
        session.setnOfTotalMethod(1);
        assertEquals((float)2/1, session.densityET());
    }
    /**
     * TC_0_4 : return -1
     */
    @Test
    public void densityET_OneToTwo_Test(){
        session.setNOfET(1);
        session.setnOfTotalMethod(2);
        assertEquals((float)1/2, session.densityET());
    }
    /**
     * TC_0_5 : return -1
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
    public void densityLOC_ZeroTotalClasses_Test(){
        session.setnOfTotalClasses(0);
        assertEquals(-1, session.densityLOC());
    }
    /**
     * TC_1_1 : return -1
     */
    @Test
    public void densityLOC_ZeroLOC_Test(){
        session.setNOfLOC(0);
        session.setnOfTotalClasses(1);
        assertEquals(0, session.densityLOC());
    }
    /**
     * TC_1_2 : return -1
     */
    @Test
    public void densityLOC_OneToOne_Test(){
        session.setNOfLOC(1);
        session.setnOfTotalClasses(1);
        assertEquals(1/1, session.densityLOC());
    }
    /**
     * TC_1_3 : return -1
     */
    @Test
    public void densityLOC_TwoToOne(){
        session.setNOfLOC(2);
        session.setnOfTotalClasses(1);
        assertEquals((float)2/1, session.densityLOC());
    }
    /**
     * TC_1_4 : return -1
     */
    @Test
    public void densityLOC_OneToTwo_Test(){
        session.setNOfLOC(1);
        session.setnOfTotalClasses(2);
        assertEquals((float)1/2, session.densityLOC());
    }
    /**
     * TC_1_5 : return -1
     */
    @Test
    public void densityLOC_TwoToTwo_Test(){
        session.setNOfLOC(2);
        session.setnOfTotalClasses(2);
        assertEquals((float)2/2, session.densityLOC());
    }
    /**
     * TC_2_0 : return -1
     */
    @Test
    public void densityGF_ZeroTotalClasses_Test(){
        session.setnOfTotalClasses(0);
        assertEquals(-1, session.densityGF());
    }
    /**
     * TC_2_1 : return -1
     */
    @Test
    public void densityGF_ZeroGF_Test(){
        session.setNOfGF(0);
        session.setnOfTotalClasses(1);
        assertEquals(0, session.densityGF());
    }
    /**
     * TC_2_2 : return -1
     */
    @Test
    public void densityGF_OneToOne_Test(){
        session.setNOfGF(1);
        session.setnOfTotalClasses(1);
        assertEquals(1/1, session.densityGF());
    }
    /**
     * TC_2_3 : return -1
     */
    @Test
    public void densityGF_TwoToOne_Test(){
        session.setNOfGF(2);
        session.setnOfTotalClasses(1);
        assertEquals((float)2/1, session.densityGF());
    }
    /**
     * TC_2_4 : return -1
     */
    @Test
    public void densityGF_OneToTwo_Test(){
        session.setNOfGF(1);
        session.setnOfTotalClasses(2);
        assertEquals((float)1/2, session.densityGF());
    }
    /**
     * TC_2_5 : return -1
     */
    @Test
    public void densityGF_TwoToTwo_Test(){
        session.setNOfGF(2);
        session.setnOfTotalClasses(2);
        assertEquals((float)2/2, session.densityGF());
    }

    /**
     * TC_3_0 : return -1
     */
    @Test
    public void densityHCTD_ZeroTotalClasses_Test(){
        session.setnOfTotalClasses(0);
        assertEquals(-1, session.densityHCTD());
    }
    /**
     * TC_3_1 : return -1
     */
    @Test
    public void densityHCTD_ZeroHCTD_Test(){
        session.setNOfHCTD(0);
        session.setnOfTotalClasses(1);
        assertEquals(0, session.densityHCTD());
    }
    /**
     * TC_3_2 : return -1
     */
    @Test
    public void densityHCTD_OneToHCTD_Test(){
        session.setNOfHCTD(1);
        session.setnOfTotalClasses(1);
        assertEquals(1/1, session.densityHCTD());
    }
    /**
     * TC_3_3 : return -1
     */
    @Test
    public void densityHCTD_TwoToOne(){
        session.setNOfHCTD(2);
        session.setnOfTotalClasses(1);
        assertEquals((float)2/1, session.densityHCTD());
    }
    /**
     * TC_3_4 : return -1
     */
    @Test
    public void densityHCTD_OneToTwo_Test(){
        session.setNOfHCTD(1);
        session.setnOfTotalClasses(2);
        assertEquals((float)1/2, session.densityHCTD());
    }
    /**
     * TC_3_5 : return -1
     */
    @Test
    public void densityHCTD_TwoToTwo_Test(){
        session.setNOfHCTD(2);
        session.setnOfTotalClasses(2);
        assertEquals((float)2/2, session.densityHCTD());
    }

    /**
     * TC_4_0 : return -1
     */
    @Test
    public void densityMG_ZeroTotalClasses_Test(){
        session.setnOfTotalClasses(0);
        assertEquals(-1, session.densityMG());
    }
    /**
     * TC_4_1 : return -1
     */
    @Test
    public void densityMG_ZeroMG_Test(){
        session.setNOfMG(0);
        session.setnOfTotalClasses(1);
        assertEquals(0, session.densityMG());
    }
    /**
     * TC_4_2 : return -1
     */
    @Test
    public void densityMG_OneToOne_Test(){
        session.setNOfMG(1);
        session.setnOfTotalClasses(1);
        assertEquals(1/1, session.densityMG());
    }
    /**
     * TC_4_3 : return -1
     */
    @Test
    public void densityMG_TwoToOne(){
        session.setNOfMG(2);
        session.setnOfTotalClasses(1);
        assertEquals((float)2/1, session.densityMG());
    }
    /**
     * TC_4_4 : return -1
     */
    @Test
    public void densityMG_OneToTwo_Test(){
        session.setNOfMG(1);
        session.setnOfTotalClasses(2);
        assertEquals((float)1/2, session.densityMG());
    }
    /**
     * TC_4_5 : return -1
     */
    @Test
    public void densityMG_TwoToTwo_Test(){
        session.setNOfMG(2);
        session.setnOfTotalClasses(2);
        assertEquals((float)2/2, session.densityMG());
    }

    /**
     * TC_5_0 : return -1
     */
    @Test
    public void densityTCD_ZeroTotalClasses_Test(){
        session.setnOfTotalClasses(0);
        assertEquals(-1, session.densityTCD());
    }
    /**
     * TC_5_1 : return -1
     */
    @Test
    public void densityTCD_ZeroTCD_Test(){
        session.setNOfTCD(0);
        session.setnOfTotalClasses(1);
        assertEquals(0, session.densityTCD());
    }
    /**
     * TC_5_2 : return -1
     */
    @Test
    public void densityTCD_OneToOne_Test(){
        session.setNOfTCD(1);
        session.setnOfTotalClasses(1);
        assertEquals(1/1, session.densityTCD());
    }
    /**
     * TC_5_3 : return -1
     */
    @Test
    public void densityTCD_TwoToOne(){
        session.setNOfTCD(2);
        session.setnOfTotalClasses(1);
        assertEquals((float)2/1, session.densityTCD());
    }
    /**
     * TC_5_4 : return -1
     */
    @Test
    public void densityTCD_OneToTwo_Test(){
        session.setNOfTCD(1);
        session.setnOfTotalClasses(2);
        assertEquals((float)1/2, session.densityTCD());
    }
    /**
     * TC_5_5 : return -1
     */
    @Test
    public void densityTCD_TwoToTwo_Test(){
        session.setNOfTCD(2);
        session.setnOfTotalClasses(2);
        assertEquals((float)2/2, session.densityTCD());
    }

    /**
     * TC_6_0: return 1
     */
    @Test
    public void totalClasses_Test(){
        int nOfTotalClasses = 1;
        session.setnOfTotalClasses(nOfTotalClasses);
        assertEquals(1, session.getnOfTotalClasses());
    }

    /**
     * TC_7_0: return 1
     */
    @Test
    public void totalMethod_Test(){
        int nOfTotalMethod = 1;
        session.setnOfTotalMethod(nOfTotalMethod);
        assertEquals(1, session.getnOfTotalMethod());
    }

    /**
     * TC_8_0: return 1
     */
    @Test
    public void nOfET_Test(){
        int nOfEt = 1;
        session.setNOfET(nOfEt);
        assertEquals(1, session.getNOfET());
    }

    /**
     * TC_9_0: return 1
     */
    @Test
    public void nOfGF_Test(){
        int nOfGF = 1;
        session.setNOfGF(nOfGF);
        assertEquals(1, session.getNOfGF());
    }

    /**
     * TC_10_0: return 1
     */
    @Test
    public void nOfLOC_Test(){
        int nOfLOC = 1;
        session.setNOfLOC(nOfLOC);
        assertEquals(1, session.getNOfLOC());
    }

    /**
     * TC_11_0: return 1
     */
    @Test
    public void nOfHCTD_Test(){
        int nOfHCTD = 1;
        session.setNOfHCTD(nOfHCTD);
        assertEquals(1, session.getNOfHCTD());
    }

    /**
     * TC_12_0: return 1
     */
    @Test
    public void nOfMG_Test(){
        int nOfMG = 1;
        session.setNOfMG(nOfMG);
        assertEquals(1, session.getNOfMG());
    }

    /**
     * TC_13_0: return 1
     */
    @Test
    public void nOfTCD_Test(){
        int nOfTCD = 1;
        session.setNOfTCD(nOfTCD);
        assertEquals(1, session.getNOfTCD());
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