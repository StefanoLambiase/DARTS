package stats;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class SessionTest {
    Session session = new Session();

    @Test
    public void densityET_ZeroTotalMethod_Test(){
        session.setnOfTotalMethod(0);
        assertEquals(-1, session.densityET());
    }

    @Test
    public void densityET_ZeroET_Test(){
        session.setNOfET(0);
        session.setnOfTotalMethod(1);
        assertEquals(0, session.densityET());
    }

    @Test
    public void densityET_OneToOne_Test(){
        session.setNOfET(1);
        session.setnOfTotalMethod(1);
        assertEquals(1/1, session.densityET());
    }

    @Test
    public void densityET_TwoToOne_Test(){
        session.setNOfET(2);
        session.setnOfTotalMethod(1);
        assertEquals((float)2/1, session.densityET());
    }

    @Test
    public void densityET_OneToTwo_Test(){
        session.setNOfET(1);
        session.setnOfTotalMethod(2);
        assertEquals((float)1/2, session.densityET());
    }

    @Test
    public void densityET_TwoToTwo_Test(){
        session.setNOfET(2);
        session.setnOfTotalMethod(2);
        assertEquals((float)2/2, session.densityET());
    }

    @Test
    public void densityLOC_ZeroTotalClasses_Test(){
        session.setnOfTotalClasses(0);
        assertEquals(-1, session.densityLOC());
    }

    @Test
    public void densityLOC_ZeroLOC_Test(){
        session.setNOfLOC(0);
        session.setnOfTotalClasses(1);
        assertEquals(0, session.densityLOC());
    }

    @Test
    public void densityLOC_OneToOne_Test(){
        session.setNOfLOC(1);
        session.setnOfTotalClasses(1);
        assertEquals(1/1, session.densityLOC());
    }

    @Test
    public void densityLOC_TwoToOne(){
        session.setNOfLOC(2);
        session.setnOfTotalClasses(1);
        assertEquals((float)2/1, session.densityLOC());
    }

    @Test
    public void densityLOC_OneToTwo_Test(){
        session.setNOfLOC(1);
        session.setnOfTotalClasses(2);
        assertEquals((float)1/2, session.densityLOC());
    }

    @Test
    public void densityLOC_TwoToTwo_Test(){
        session.setNOfLOC(2);
        session.setnOfTotalClasses(2);
        assertEquals((float)2/2, session.densityLOC());
    }

    @Test
    public void densityGF_ZeroTotalClasses_Test(){
        session.setnOfTotalClasses(0);
        assertEquals(-1, session.densityGF());
    }

    @Test
    public void densityGF_ZeroGF_Test(){
        session.setNOfGF(0);
        session.setnOfTotalClasses(1);
        assertEquals(0, session.densityGF());
    }

    @Test
    public void densityGF_OneToOne_Test(){
        session.setNOfGF(1);
        session.setnOfTotalClasses(1);
        assertEquals(1/1, session.densityGF());
    }

    @Test
    public void densityGF_TwoToOne_Test(){
        session.setNOfGF(2);
        session.setnOfTotalClasses(1);
        assertEquals((float)2/1, session.densityGF());
    }

    @Test
    public void densityGF_OneToTwo_Test(){
        session.setNOfGF(1);
        session.setnOfTotalClasses(2);
        assertEquals((float)1/2, session.densityGF());
    }

    @Test
    public void densityGF_TwoToTwo_Test(){
        session.setNOfGF(2);
        session.setnOfTotalClasses(2);
        assertEquals((float)2/2, session.densityGF());
    }

    @Test
    public void getActionUnitTest(){
        Session sessionMock = Mockito.mock(Session.class);
        when(sessionMock.getActions()).thenReturn(new ArrayList<>());
        assertEquals(new ArrayList<>(), sessionMock.getActions());

    }

    @Test
    public void getActionIntegrationTest(){
        session.setActions(new ArrayList<Action>());
        assertEquals(new ArrayList<Action>(), session.getActions());
    }
}