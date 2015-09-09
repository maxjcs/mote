package com.xuexibao.teacher.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.vdurmont.emoji.EmojiParser;
import com.xuexibao.teacher.model.iter.IStudent;
import com.xuexibao.teacher.model.iter.ITeacher;
import com.xuexibao.teacher.model.iter.ITeacherMessageReplyCount;
import com.xuexibao.teacher.util.DateUtils;

/**
 * 
 * @author yingmingbao
 * 
 */
public class TeacherMessage implements Serializable, IStudent, ITeacher, ITeacherMessageReplyCount {
	private static final long serialVersionUID = 3731251221370385264L;
	public final static int TEACHER_SEND = 2;
	public final static int STUDENT_SEND = 1;
	public final static long LAST_ITEM_ID=10000000000000L;
	private long id;
	private String studentId;
	private String studentName;
	private String studentImageUrl;
	private Integer studentGender;
	private String teacherId;
	private String teacherName;
	private String teacherImageUrl;
	private Integer teacherGender;
	private String content;
	private Integer type;
	private Date createTime;
	private Date updateTime;

	private int floor;
	private long replyId;
	private int replyFloor;
	private Integer replyCount;
	private boolean canDelete;
	private TeacherMessage parentMessage;
	
	public TeacherMessage getParentMessage() {
		return parentMessage;
	}


	public void setParentMessage(TeacherMessage parentMessage) {
		this.parentMessage = parentMessage;
	}


	public boolean isCanDelete() {
		return canDelete;
	}


	public void setCanDelete(boolean canDelete) {
		this.canDelete = canDelete;
	}


	public Integer getReplyCount() {
		return replyCount;
	}

	
	public void setReplyCount(Integer replyCount) {
		this.replyCount = replyCount;
	}

	public String getCreateTimeListStr() {
		return DateUtils.formatList(createTime);
	}

	public String getCreateTimeDetailStr() {
		return DateUtils.formatDetail(createTime);
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public long getReplyId() {
		return replyId;
	}

	public void setReplyId(long replyId) {
		this.replyId = replyId;
	}

	public int getReplyFloor() {
		return replyFloor;
	}

	public void setReplyFloor(int replyFloor) {
		this.replyFloor = replyFloor;
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

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getEmojiContent() {
		if (StringUtils.isEmpty(content)) {
			return "";
		}
		return EmojiParser.parseToUnicode(content);
	}

	@JsonIgnore
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getTeacherImageUrl() {
		return teacherImageUrl;
	}

	public void setTeacherImageUrl(String teacherImageUrl) {
		this.teacherImageUrl = teacherImageUrl;
	}

	public Integer getTeacherGender() {
		return teacherGender;
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
	public void setPhoneNumber(String phoneNumber) {

	}

	@Override
	public void setTeacherNickname(String nickname) {
		this.teacherName = nickname;
	}

	public void setTeacherGender(Integer gender) {
		this.teacherGender = gender;
	}

	@Override
	public void setTeacherAvatarUrl(String avatarUrl) {
		this.teacherImageUrl = avatarUrl;
	}

	@Override
	public void setStudentNickname(String nickname) {
		this.studentName = nickname;
	}

	@Override
	public void setStudentAvatarUrl(String avatarUrl) {
		this.studentImageUrl = avatarUrl;
	}
	@JsonIgnore
	public Long getMsgId() {
		return id;
	}


	@Override
	public void setTeacherCourseYear(Integer courseYear) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setTeacherStar(Integer star) {
		// TODO Auto-generated method stub
		
	}
}