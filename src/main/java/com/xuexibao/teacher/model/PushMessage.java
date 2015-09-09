/**
 * @author oldlu
 */
package com.xuexibao.teacher.model;

import java.util.Date;

public class PushMessage {
    private String id = "";
    private String msgTitle = "";
    private String msgUrl = "";
    private long timestamp = new Date().getTime();

    public PushMessage(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    public String getMsgUrl() {
        return msgUrl;
    }

    public void setMsgUrl(String msgUrl) {
        this.msgUrl = msgUrl;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
