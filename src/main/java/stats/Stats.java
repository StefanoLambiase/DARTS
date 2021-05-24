package stats;

import java.util.HashMap;

public class Stats {

  private static Stats instance = null;

  HashMap<String, Session> sessions;
  String lastSessionID;
  private int nOfExecutionTextual = 0;
  private int nOfExecutionStructural = 0;

  private Stats() {
    this.sessions = new HashMap<String, Session>();
    this.lastSessionID = "";
  }

  public static Stats getInstance() {
    if (instance == null) {
      instance = new Stats();
    }
    return instance;
  }

  public HashMap<String, Session> getSessions() {
    return sessions;
  }

  public void setSessions(HashMap<String, Session> sessions) {
    this.sessions = sessions;
  }

  public Session addSession(Session session) {
    this.sessions.put(session.getID(), session);
    this.lastSessionID = session.getID();
    return session;
  }

  public Session getSession(String sessionID) {
    return this.sessions.get(sessionID);
  }

  public Session getLastSession() {
    return this.sessions.get(this.lastSessionID);
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
}
