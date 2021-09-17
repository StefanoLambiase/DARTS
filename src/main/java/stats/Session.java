package stats;

import utility.NetUtility;
import utility.StatsUtility;

import java.util.ArrayList;
import java.util.Date;

public class Session {

  private ArrayList<Action> actions;
  private String userId;
  private final String ID;
  private String projectName;
  private long startTime;
  private long endTime;

  private int nOfGF = 0;
  private int nOfET = 0;
  private int nOfLOC = 0;
  private int nOfTotalClasses = 0;
  private String kind;

  public Session() {
    this.userId = NetUtility.getMACAddress();
    this.ID = StatsUtility.md5(new Date().getTime() + System.getProperty("user.name"));
    this.actions = new ArrayList<Action>();
  }

  public int getnOfTotalClasses() {
    return nOfTotalClasses;
  }

  public void setnOfTotalClasses(int nOfTotalClasses) {
    this.nOfTotalClasses = nOfTotalClasses;
  }

  public String getKind() {
    return kind;
  }

  public void setKind(String kind) { this.kind = kind; }

  public String getID() {
    return ID;
  }

  public long getExecutionTime() {
    return this.endTime - this.startTime;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getUserId() {
    return this.userId;
  }

  public long getStartTime() {
    return startTime;
  }

  public void setStartTime(long startTime) {
    this.startTime = startTime;
  }

  public void setEndTime(long endTime) {
    this.endTime = endTime;
  }

  public long getEndTime() {
    return endTime;
  }

  public String getProjectName() {
    return projectName;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  public int getNOfGF() {
    return nOfGF;
  }

  public void setNOfGF(int nOfGF) {
    this.nOfGF = nOfGF;
  }

  public int getNOfET() {
    return nOfET;
  }

  public void setNOfET(int nOfET) {
    this.nOfET = nOfET;
  }

  public int getNOfLOC() {
    return nOfLOC;
  }

  public void setNOfLOC(int nOfLOC) {
    this.nOfLOC = nOfLOC;
  }

  public ArrayList<Action> getActions() {
    return actions;
  }

  public void setActions(ArrayList<Action> actions) {
    this.actions = actions;
  }
}
