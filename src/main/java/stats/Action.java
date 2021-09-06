package stats;

public class Action {

  public enum ActionKindEnum {
    REFACTORING_PREVIEW,
    CONTEXTUAL_ANALYSIS
  }

  public enum SmellKindEnum {
    GENERAL_FIXTURE,
    EAGER_TEST,
    LACK_OF_COHESION
  }

  private ActionKindEnum actionKind;
  private SmellKindEnum smellKind;
  private long timestamp;
  private String className;
  private String methodName;
  private String packageName;
  private boolean actionCanceled;
  private boolean actionDone;

  public Action() {

  }

  public long getTimestamp() {
    return this.timestamp;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  public String getClassName() {
    return this.className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public String getMethodName() {
    return this.methodName;
  }

  public void setMethodName(String methodName) {
    this.methodName = methodName;
  }

  public ActionKindEnum getActionKind() {
    return actionKind;
  }

  public void setActionKind(ActionKindEnum actionKind) {
    this.actionKind = actionKind;
  }

  public SmellKindEnum getSmellKind() {
    return smellKind;
  }

  public void setSmellKind(SmellKindEnum smellKind) {
    this.smellKind = smellKind;
  }

  public String getPackageName() {
    return packageName;
  }

  public void setPackageName(String packageName) {
    this.packageName = packageName;
  }

  public boolean isActionCanceled() {
    return actionCanceled;
  }

  public void setActionCanceled(boolean actionCanceled) {
    this.actionCanceled = actionCanceled;
  }

  public boolean isActionDone() {
    return actionDone;
  }

  public void setActionDone(boolean actionDone) {
    this.actionDone = actionDone;
  }

  @Override
  public String toString() {
    return "Action{" +
        "actionKind=" + actionKind +
        ", smellKind=" + smellKind +
        ", timestamp=" + timestamp +
        ", className='" + className + '\'' +
        ", methodName='" + methodName + '\'' +
        ", packageName='" + packageName + '\'' +
        '}';
  }
}
