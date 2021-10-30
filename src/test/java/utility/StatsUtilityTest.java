package utility;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

public class StatsUtilityTest {
    /**
     * Tested for this day "Thu Oct 28 19:04:39 CEST 2021"
     */
    @Test
    public void md5_FilledParameter_Test(){
        String dayGetTime = "1635440679790";
        String oracle = "2E4CB449D08B2757D0E8C7FC100C8987";
        String userGetName = "UniSa";
        assertEquals(oracle, StatsUtility.md5(dayGetTime + userGetName));
    }

    @Test
    public void md5_NotFilledParameter_Test(){
        String empty = "";
        assertEquals(null, StatsUtility.md5(empty));
    }

    @Test
    public void md5_NullParameter_Test(){
        assertEquals(null, StatsUtility.md5(null));
    }
}
