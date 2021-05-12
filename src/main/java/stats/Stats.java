package stats;

import utility.NetUtility;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Stats {

  private static Stats instance = null;

  private String userId = NetUtility.getMACAddress();
  private String projectName;
  private long startTime;
  private long endTime;
  private long executionTime;

  private Stats() {
  }

  public static Stats getInstance() {
    if (instance == null) {
      instance = new Stats();
    }
    return instance;
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
}
