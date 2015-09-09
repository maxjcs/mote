package com.xuexibao.teacher.model.vo;

public class LearnMessageTeacherVO {

	private String teacherId; // teacher_id
	private String teacherName;
	private String teacherImgUrl;
	private Integer teacherGender;

	public Integer getTeacherGender() {
		return teacherGender;
	}

	public void setTeacherGender(Integer teacherGender) {
		this.teacherGender = teacherGender;
	}

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
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

}
