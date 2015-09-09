package com.xuexibao.teacher.model;

import java.util.Date;

import com.xuexibao.teacher.util.DateUtils;

public class LearnTalkTeacher {
	private long id;
	private String teacherId;
	private String teacherAvatarUrl;
	private String teacherName;
	private int msgType;
	private String content;
	private Date createTime;
	private String teacherOfferId;
	private int teacherOfferType; // 1 音频 2白板 3 课件
	private String teacherOfferDesc;
	private int unReadMessageNumber;
	private Integer dialogType;
	private Long questionId;

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public Integer getDialogType() {
		return dialogType;
	}

	public void setDialogType(Integer dialogType) {
		this.dialogType = dialogType;
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

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public String getTeacherAvatarUrl() {
		return teacherAvatarUrl;
	}

	public void setTeacherAvatarUrl(String teacherAvatarUrl) {
		this.teacherAvatarUrl = teacherAvatarUrl;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherNick) {
		this.teacherName = teacherNick;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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
