package stats;

import java.util.ArrayList;
import java.util.HashMap;

public class Stats {

  private static Stats instance = null;

  ArrayList<Session> sessions;
  String lastSessionID;
  private int nOfExecutionTextual = 0;
  private int nOfExecutionStructural = 0;
  private String FILE_PATH;

  private Stats() {
    this.sessions = new ArrayList<Session>();
    this.lastSessionID = "";
  }

  public static Stats getInstance() {
    if (instance == null) {
      instance = new Stats();
    }
    return instance;
  }

  public ArrayList<Session> getSessions() {
    return sessions;
  }

  public Session addSession(Session session) {
    this.sessions.add(session);
    this.lastSessionID = session.getID();
    return session;
  }

  public Session getLastSession() {
    return this.sessions.get(this.sessions.size()-1);
  }

  public int getNOfExecutionTextual() {
    return nOfExecutionTextual;
  }

  public int getNOfExecutionStructural() {
    return nOfExecutionStructural;
  }

  public void incrementNOfExecutionTextual() {
    this.nOfExecutionTextual++;
  }

  public void incrementNOfExecutionStructural() {
    this.nOfExecutionStructural++;
  }

  public String getFILE_PATH() {
    return FILE_PATH;
  }

  public void setFILE_PATH(String FILE_PATH) {
    this.FILE_PATH = FILE_PATH;
  }
}
