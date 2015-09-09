package com.xuexibao.teacher.model;

public class LearnTalkDetail {

	private String studentAvatarUrl;
	private String studentNick;
	private String studentId;
	private long createTime;
	private int msgType;
	private String content;

	public String getStudentAvatarUrl() {
		return studentAvatarUrl;
	}

	public void setStudentAvatarUrl(String studentAvatarUrl) {
		this.studentAvatarUrl = studentAvatarUrl;
	}

	public String getStudentNick() {
		return studentNick;
	}

	public void setStudentNick(String studentNick) {
		this.studentNick = studentNick;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

}
