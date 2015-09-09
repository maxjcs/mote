package com.xuexibao.teacher.model.vo;

import java.util.Date;

public class LearnMessageVO {

	private long id;
	private String teacherId; // teacher_id
	private String teacherName;
	private String teacherImgUrl;
	private String studentId;// student_id
	private String studentName;
	private int sendMsgUserType; // send_msg_user_type 消息发送者 1 teacher,2 student
	private int msgType; // msg_type 消息类型 1 txt ,2 img
	private String teacherOfferId;
	private int teacherOfferType; // 1 音频 2白板 3 课件
	private String teacherOfferDesc;
	private String isRead;
	private String isStudentRead;
	private String content;
	private Date createTime;// create_time
	private Date updateTime;// update_time
	private String imageId;
	private Integer dialogType;

	public Integer getDialogType() {
		return dialogType;
	}

	public void setDialogType(Integer dialogType) {
		this.dialogType = dialogType;
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

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public int getSendMsgUserType() {
		return sendMsgUserType;
	}

	public void setSendMsgUserType(int sendMsgUserType) {
		this.sendMsgUserType = sendMsgUserType;
	}

	public int getMsgType() {
		return msgType;
	}

	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public String getIsRead() {
		return isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}

	public String getTeacherOfferDesc() {
		return teacherOfferDesc;
	}

	public void setTeacherOfferDesc(String teacherOfferDesc) {
		this.teacherOfferDesc = teacherOfferDesc;
	}

	public String getIsStudentRead() {
		return isStudentRead;
	}

	public void setIsStudentRead(String isStudentRead) {
		this.isStudentRead = isStudentRead;
	}

	public String getTeacherImgUrl() {
		return teacherImgUrl;
	}

	public void setTeacherImgUrl(String teacherImgUrl) {
		this.teacherImgUrl = teacherImgUrl;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

}
