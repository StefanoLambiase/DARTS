package utility;

import com.google.gson.Gson;
import stats.Stats;

import java.io.FileWriter;
import java.io.IOException;

public class StatsSerializator {

  public static boolean serialize(Stats stats, String filePath) {
    try {
      FileWriter writer = new FileWriter(filePath);
      writer.write(new Gson().toJson(stats));
      writer.close();
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

}
