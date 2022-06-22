package utility;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import stats.Stats;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.*;


public class StatsBlasterTest {
    Stats stats;
    @BeforeEach
    public void setUp(){
        stats = Stats.getInstance();
    }

    @Test
    public void blastTrueTest(){
        assertTrue(StatsBlaster.blast(stats));
    }

    @Test
    public void blastFalseTest() {
        assertFalse(StatsBlaster.blast(null));
    }

}
