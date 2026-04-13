package com.livisync.app;

public class MessageItem {
    private String text;
    private String senderUid;
    private long timestamp;

    public MessageItem() {}

    public MessageItem(String text, String senderUid, long timestamp) {
        this.text = text;
        this.senderUid = senderUid;
        this.timestamp = timestamp;
    }

    public String getText() { return text; }
    public String getSenderUid() { return senderUid; }
    public long getTimestamp() { return timestamp; }
}