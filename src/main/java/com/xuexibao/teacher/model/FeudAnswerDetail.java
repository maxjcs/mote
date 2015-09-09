package com.xuexibao.teacher.model;

import java.util.Date;

public class FeudAnswerDetail {
    private Long id;

    private Long feudQuestId;

    private String teacherId;

    private Long feudType;

    private String audioWhiteboardId;

    private Date createTime;

    private Date updateTime;

    private int status;

    private long questRealId;

    private Integer evaluate;

    private String content;
    private int point;
    private long fee;
    private String audioWhiteboardUrl;
    
    public String getAudioWhiteboardUrl() {
		return audioWhiteboardUrl;
	}

	public void setAudioWhiteboardUrl(String audioWhiteboardUrl) {
		this.audioWhiteboardUrl = audioWhiteboardUrl;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public long getFee() {
		return fee;
	}

	public void setFee(long fee) {
		this.fee = fee;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFeudQuestId() {
        return feudQuestId;
    }

    public void setFeudQuestId(Long feudQuestId) {
        this.feudQuestId = feudQuestId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId == null ? null : teacherId.trim();
    }

    public Long getFeudType() {
        return feudType;
    }

    public void setFeudType(Long feudType) {
        this.feudType = feudType;
    }

    public String getAudioWhiteboardId() {
        return audioWhiteboardId;
    }

    public void setAudioWhiteboardId(String audioWhiteboardId) {
        this.audioWhiteboardId = audioWhiteboardId == null ? null : audioWhiteboardId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getQuestRealId() {
        return questRealId;
    }

    public void setQuestRealId(long questRealId) {
        this.questRealId = questRealId ;
    }

    public Integer getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(Integer evaluate) {
        this.evaluate = evaluate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}