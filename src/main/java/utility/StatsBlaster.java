package utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import stats.Stats;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class StatsBlaster {

  public static boolean blast(Stats stats) {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    try {
      URL url = new URL("https://dartsstats.herokuapp.com/api/stat");
      URLConnection con = url.openConnection();
      HttpURLConnection http = (HttpURLConnection) con;
      http.setRequestMethod("POST");
      http.setDoOutput(true);

      http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
      http.setRequestProperty("Accept", "application/json");
      http.connect();

      OutputStream os = http.getOutputStream();
      byte[] out = gson.toJson(stats).getBytes("utf-8");
      os.write(out, 0, out.length);

      BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
      StringBuilder response = new StringBuilder();
      String responseLine = null;
      while ((responseLine = br.readLine()) != null) {
        response.append(responseLine.trim());
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }
}
