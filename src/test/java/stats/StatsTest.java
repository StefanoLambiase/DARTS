package stats;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class StatsTest {
    Stats statTest = Stats.getInstance();
    @Test
    public void addSession_nullSession_Test() {
        assertEquals(null, statTest.addSession(null));
    }

    @Test
    public void addSession_NotNullSession_Test(){
        Session session = new Session();
        assertNotEquals(null, statTest.addSession(session));
    }
}