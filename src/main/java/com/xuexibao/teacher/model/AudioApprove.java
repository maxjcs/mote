/**
 * @author oldlu
 */
package com.xuexibao.teacher.model;

import java.util.Date;

public class AudioApprove {
	public static final int STATUS_WAIT = 0;//待审
	public static final int STATUS_Y = 1;//审核通过
	public static final int STATUS_N = 2;//审核不通过
	private String id;
	private String audioId;
	private String approvor;
	private Date approveTime;
	private String reason;
	private int status;
	private int evalution;

	public int getEvalution() {
		return evalution;
	}

	public void setEvalution(int evalution) {
		this.evalution = evalution;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAudioId() {
		return audioId;
	}

	public void setAudioId(String audioId) {
		this.audioId = audioId;
	}

	public String getApprovor() {
		return approvor;
	}

	public void setApprovor(String approvor) {
		this.approvor = approvor;
	}

	public Date getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
