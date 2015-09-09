package com.xuexibao.teacher.model;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vdurmont.emoji.EmojiParser;

public class LearnMessage {
	public static final int TEACHER_SEND = 1;
	public static final int STUDENT_SEND = 2;

	public static final int DIALOG_TYPE_PAITI = 1;
	public static final int DIALOG_TYPE_AUDIO_SET = 2;
	
	public static final int MSG_TYPE_CONTENT = 1;
	public static final int MSG_TYPE_IMAGE = 2;
	
	public static final List<Integer> DIALOG_TYPES = Arrays.asList(DIALOG_TYPE_PAITI, DIALOG_TYPE_AUDIO_SET);

	private long id;
	private String teacherId; // teacher_id
	private String studentId;// student_id
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
	private int dialogType;
	
	private String msg;
	private String img;
	
	@JsonIgnore
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@JsonIgnore
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public int getDialogType() {
		return dialogType;
	}

	public void setDialogType(int dialogType) {
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

	public String getEmojiContent() {
		if (StringUtils.isEmpty(content)) {
			return "";
		}
		return EmojiParser.parseToUnicode(content);
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

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

}
