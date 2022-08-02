package stats;

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
  private int nOfTotalMethods = 0;
  private String kind;

  public Session() {
    this.userId = StatsUtility.md5(new Date().getTime() + System.getProperty("user.name"));
    this.ID = StatsUtility.md5(new Date().getTime() + System.getProperty("user.name"));
    this.actions = new ArrayList<Action>();
  }

  public int getnOfTotalClasses() {
    return nOfTotalClasses;
  }

  public void setnOfTotalClasses(int nOfTotalClasses) {
    this.nOfTotalClasses = nOfTotalClasses;
  }

  //for eager test smell
  public void setnOfTotalMethod(int nOfTotalMethods) { this.nOfTotalMethods = nOfTotalMethods; }

  //for eager test smell
  public int getnOfTotalMethod(){ return nOfTotalMethods;}

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

  public float densityET(){
    if(this.getnOfTotalMethod() == 0){
      return -1;
    }else{
      if(this.getNOfET() != 0){
        float res = (float)getNOfET()/(float)getnOfTotalMethod();
        return res;
      }else{
        return 0;
      }
    }
  }

  public float densityLOC(){
    if(getnOfTotalClasses() == 0){
      return -1;
    }else{
      if(getNOfLOC() != 0){
        float res = (float)getNOfLOC()/(float)getnOfTotalClasses();
        return res;
      }else{
        return 0;
      }
    }
  }

  public float densityGF(){
    if(getnOfTotalClasses() == 0){
      return -1;
    }else{
      if(getNOfGF() != 0){
        float res = (float)getNOfGF()/(float)getnOfTotalClasses();
        return res;
      }else{
        return 0;
      }
    }
  }

  public ArrayList<Action> getActions() {
    return actions;
  }

  public void setActions(ArrayList<Action> actions) {
    this.actions = actions;
  }
}
