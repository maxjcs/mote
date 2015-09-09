package com.xuexibao.teacher.model;

import java.util.Date;
import java.io.Serializable;

/**
 * 
 * @author oldlu
 *
 */
public class TeacherReport implements Serializable {
	private static final long serialVersionUID = 8286152224720268491L;
	public static final int MSG_TYPE_TEACHER_MESSAGE = 1;
	public static final int MSG_TYPE_DYNAMIC_MESSAGE = 2;
	private Long id;
	private Long msgId;
	private int msgType;
	private String teacherId;
	private Date createTime;
	private Date updateTime;
	private int reportType;
	private String content;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getMsgId() {
		return msgId;
	}

	public void setMsgId(long msgId) {
		this.msgId = msgId;
	}

	public int getMsgType() {
		return msgType;
	}

	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
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

	public int getReportType() {
		return reportType;
	}

	public void setReportType(int reportType) {
		this.reportType = reportType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
