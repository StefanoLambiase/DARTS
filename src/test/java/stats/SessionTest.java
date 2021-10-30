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