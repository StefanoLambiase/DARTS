package utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import stats.Stats;
import java.io.File;

import java.io.FileWriter;
import java.io.IOException;

public class StatsSerializator {

  public static boolean serialize(Stats stats, String filePath) {
    if(stats == null || filePath == null){
      return false;
    }
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    try {
      FileWriter writer = new FileWriter(filePath);
      writer.write(gson.toJson(stats));
      writer.close();
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

  public static boolean deleteJsonFile(String filePath){
    File f = new File(filePath);
    if(f.delete()){
      return true;
    }else{
      return false;
    }
  }
  public static boolean fileExist(String filePath){
    File f = new File(filePath);
    if(f.exists()){
      return true;
    }else{
      return false;
    }
  }


}
