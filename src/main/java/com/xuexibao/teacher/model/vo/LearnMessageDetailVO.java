package com.xuexibao.teacher.model.vo;

import java.util.Date;

import com.xuexibao.teacher.util.DateUtils;

public class LearnMessageDetailVO {

	private long id;
	private String teacherId; // teacher_id
	private String studentId;// student_id
	private int sendMsgUserType; // send_msg_user_type 消息发送者 1 teacher,2 student
	private int msgType; // msg_type 消息类型 1 txt ,2 img
	private String content;
	private Date createTime;// create_time
	
	public String getCreateTimeStr() {
		return DateUtils.formatDetail(createTime);
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
}
