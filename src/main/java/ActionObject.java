public class ActionObject {
    String isPerformedBy;
    String hasStatus;
    Integer order;

    String part;
    String name;

    public String getUID() {
        return UID;
    }

    String UID;

    public String getIsPerformedBy() {
        return isPerformedBy;
    }

    public String getHasStatus() {
        return hasStatus;
    }

    public String getPart() {
        return part;
    }

    public String getName() {
        return name;
    }

    public Integer getOrder() {
        return order;
    }

    public String getExecID() {
        return execID;
    }

    String execID;
    public ActionObject(String name, String isPerformedBy, String hasStatus, Integer order, String execID, String part, String UID) {
        this.isPerformedBy = isPerformedBy;
        this.hasStatus = hasStatus;
        this.order = order;
        this.execID = execID;
        this.part = part;
        this.UID = UID;
        this.name = name;
    }

}
