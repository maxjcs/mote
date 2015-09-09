package com.xuexibao.teacher.model;

import java.io.Serializable;
import java.util.Date;

import com.xuexibao.teacher.model.iter.IDynamicMessageSendor;
import com.xuexibao.teacher.model.iter.IStudent;
import com.xuexibao.teacher.model.iter.ITeacher;
import com.xuexibao.teacher.util.DateUtils;

/**
 * 
 * @author oldlu
 *
 */
public class DynamicMessage implements Serializable, IDynamicMessageSendor, IStudent, ITeacher {
	private static final long serialVersionUID = 8286152224720268491L;

	public static final int TYPE_TEACHER_SEND = 1;
	public static final int TYPE_STUDENT_SEND = 2;

	private long id;
	private String teacherId;
	private String studentId;
	private Long dynamicId;
	private int type;
	private int status;
	private Date createTime;
	private Date updateTime;
	private String content;
	private String imageUrl;
	private Long replyId;
	private int repliedType;
	private String repliedUserId;
	private String repliedUserName;

	private String teacherNickname;
	private String teacherAvatarUrl;
	private Integer teacherGender;

	private String studentName;
	private String studentImageUrl;
	private Integer studentGender;

	private boolean canDelete;

	public boolean isCanDelete() {
		return canDelete;
	}

	public void setCanDelete(boolean canDelete) {
		this.canDelete = canDelete;
	}

	public String getRepliedUserName() {
		return repliedUserName;
	}

	public void setRepliedUserName(String repliedUserName) {
		this.repliedUserName = repliedUserName;
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Long getDynamicId() {
		return dynamicId;
	}

	public void setDynamicId(Long dynamicId) {
		this.dynamicId = dynamicId;
	}

	public Long getReplyId() {
		return replyId;
	}

	public void setReplyId(Long replyId) {
		this.replyId = replyId;
	}

	public int getRepliedType() {
		return repliedType;
	}

	public void setRepliedType(int repliedType) {
		this.repliedType = repliedType;
	}

	public String getRepliedUserId() {
		return repliedUserId;
	}

	public void setRepliedUserId(String repliedUserId) {
		this.repliedUserId = repliedUserId;
	}

	public String getCreateTimeListStr() {
		return DateUtils.formatList(createTime);
	}

	public String getCreateTimeDetailStr() {
		return DateUtils.formatDetail(createTime);
	}

	public String getTeacherNickname() {
		return teacherNickname;
	}

	public void setTeacherNickname(String teacherNickname) {
		this.teacherNickname = teacherNickname;
	}

	public String getTeacherAvatarUrl() {
		return teacherAvatarUrl;
	}

	public void setTeacherAvatarUrl(String teacherAvatarUrl) {
		this.teacherAvatarUrl = teacherAvatarUrl;
	}

	public Integer getTeacherGender() {
		return teacherGender;
	}

	public void setTeacherGender(Integer teacherGender) {
		this.teacherGender = teacherGender;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getStudentImageUrl() {
		return studentImageUrl;
	}

	public void setStudentImageUrl(String studentImageUrl) {
		this.studentImageUrl = studentImageUrl;
	}

	public Integer getStudentGender() {
		return studentGender;
	}

	public void setStudentGender(Integer studentGender) {
		this.studentGender = studentGender;
	}

	@Override
	public void setTeacherName(String name) {
		this.teacherNickname = name;
	}

	@Override
	public void setPhoneNumber(String phoneNumber) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTeacherCourseYear(Integer courseYear) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTeacherStar(Integer star) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setStudentNickname(String nickname) {
		this.studentName = nickname;
	}

	@Override
	public void setStudentAvatarUrl(String avatarUrl) {
		this.studentImageUrl = avatarUrl;
	}
}
