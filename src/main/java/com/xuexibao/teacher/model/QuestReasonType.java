/**
 * @author oldlu
 */
package com.xuexibao.teacher.model;

/**
 * @author Administrator
 * 
 */
public class QuestReasonType {
	private long questionId;
	private String reasonType;
	private long id;
	private String reason;
	private String teacherId;
	private String teacherName;
	private String phoneNumber;
	private String teacherNickName;
	
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getTeacherNickName() {
		return teacherNickName;
	}

	public void setTeacherNickName(String teacherNickName) {
		this.teacherNickName = teacherNickName;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
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

	public String getReasonType() {
		return reasonType;
	}

	public void setReasonType(String reasonType) {
		this.reasonType = reasonType;
	}
}
