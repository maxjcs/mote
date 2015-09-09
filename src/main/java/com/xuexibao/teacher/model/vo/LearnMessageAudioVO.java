package com.xuexibao.teacher.model.vo;

public class LearnMessageAudioVO {

	private String teacherOfferId;
	private int teacherOfferType; // 1 音频 2白板 3 课件
	private String teacherOfferDesc;
	private String imageId;
	private long questionId;

	public long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(long questionId) {
		this.questionId = questionId;
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

	public String getTeacherOfferDesc() {
		return teacherOfferDesc;
	}

	public void setTeacherOfferDesc(String teacherOfferDesc) {
		this.teacherOfferDesc = teacherOfferDesc;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

}
