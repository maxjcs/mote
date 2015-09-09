package com.xuexibao.teacher.model;

import java.util.Date;

import com.xuexibao.teacher.util.DateUtils;

public class LearnTalk {

	private long id;
	private String studentId;
	private String studentAvatarUrl;
	private String studentNick;
	private int msgType;
	private String content;
	private Date createTime;
	private String teacherOfferId;
	private int teacherOfferType; // 1 音频 2白板 3 课件
	private String teacherOfferDesc;
	private int unReadMessageNumber;
	private Integer dialogType;
	
	// protected String createTimeStr;

	private Long questionId;

	public Integer getDialogType() {
		return dialogType;
	}

	public void setDialogType(Integer dialogType) {
		this.dialogType = dialogType;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public String getCreateTimeStr() {
		return DateUtils.formatList(createTime);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	public int getUnReadMessageNumber() {
		return unReadMessageNumber;
	}

	public void setUnReadMessageNumber(int unReadMessageNumber) {
		this.unReadMessageNumber = unReadMessageNumber;
	}

	public String getTeacherOfferId() {
		return teacherOfferId;
	}

	public void setTeacherOfferId(String teacherOfferId) {
		this.teacherOfferId = teacherOfferId;
	}

	public int getTeacherOfferType() {
		return teacherOfferType;
	}

	public void setTeacherOfferType(int teacherOfferType) {
		this.teacherOfferType = teacherOfferType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getMsgType() {
		return msgType;
	}

	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}

	public String getTeacherOfferDesc() {
		return teacherOfferDesc;
	}

	public void setTeacherOfferDesc(String teacherOfferDesc) {
		this.teacherOfferDesc = teacherOfferDesc;
	}
}
