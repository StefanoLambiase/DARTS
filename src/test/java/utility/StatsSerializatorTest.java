package utility;

import junit.framework.TestCase;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;
import stats.Stats;

import static org.junit.jupiter.api.Assertions.*;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class StatsSerializatorTest {
    String filePath = "./testFileToDelete";
    @BeforeEach
    public void setUp() throws IOException {
        File file = new File(filePath);
        if(!file.exists()){
            file.createNewFile();
        }

    }
    @AfterAll
    public static void tearDown(){
        File file = new File("./testFileToDelete");
        if(!file.exists()){
            file.delete();
        }
    }

    @Test
    public void deleteJsonTrueFileTest(){
        assertTrue(StatsSerializator.deleteJsonFile(filePath), "Il file viene cancellato correttamente");
    }

    @Test
    public void deleteJsonFalseFileTest(){
        StatsSerializator.deleteJsonFile(filePath);
        assertFalse(StatsSerializator.deleteJsonFile(filePath), "Il file non viene cancellato correttamente!");

    }

    @Test
    public void fileExistTrueTest(){
        assertTrue(StatsSerializator.fileExist(filePath), "Il file esiste!");
    }

    @Test
    public void fileExistFalseTest(){
        StatsSerializator.deleteJsonFile(filePath);
        assertFalse(StatsSerializator.fileExist(filePath), "Il file non esiste!");
    }

    @Test
    public void serializeTrueTest(){
        Stats stats = Stats.getInstance();
        assertTrue(StatsSerializator.serialize(stats, filePath));
    }

    @Test
    public void serializeNullPointerTest(){
        Stats stats = Stats.getInstance();
        assertThrows(NullPointerException.class, () -> {
            StatsSerializator.serialize(stats, null);
        });
    }


}
