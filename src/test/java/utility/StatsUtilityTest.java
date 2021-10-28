package utility;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

public class StatsUtilityTest {
    /**
     * Tested for this day "Thu Oct 28 19:04:39 CEST 2021"
     */
    @Test
    public void md5Test(){
        String dayGetTime = "1635440679790";
        String oracle = "2E4CB449D08B2757D0E8C7FC100C8987";
        assertEquals(oracle, StatsUtility.md5(dayGetTime + System.getProperty("user.name")));
    }
}
