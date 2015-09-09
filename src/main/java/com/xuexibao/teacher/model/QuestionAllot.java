package com.xuexibao.teacher.model;

public class QuestionAllot {
	private long id;
	private long questionId;
	private String allotUser;
	private long orgId;

	public long getOrgId() {
		return orgId;
	}

	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}

	public String getAllotUser() {
		return allotUser;
	}

	public void setAllotUser(String allotUser) {
		this.allotUser = allotUser;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}
}
