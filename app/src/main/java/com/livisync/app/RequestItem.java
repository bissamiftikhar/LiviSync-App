package com.livisync.app;

public class RequestItem {
    private String requestId;
    private String otherUid;
    private String otherName;
    private String status;
    private boolean isIncoming;

    public RequestItem(String requestId, String otherUid, String otherName,
                       String status, boolean isIncoming) {
        this.requestId = requestId;
        this.otherUid = otherUid;
        this.otherName = otherName;
        this.status = status;
        this.isIncoming = isIncoming;
    }

    public String getRequestId() { return requestId; }
    public String getOtherUid() { return otherUid; }
    public String getOtherName() { return otherName; }
    public String getStatus() { return status; }
    public boolean isIncoming() { return isIncoming; }
}