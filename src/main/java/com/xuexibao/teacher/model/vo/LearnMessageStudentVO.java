package com.xuexibao.teacher.model.vo;

public class LearnMessageStudentVO {

	private String studentId;// student_id
	private String studentName;
	private String studentImgUrl;
	private Integer studentGender;

	public Integer getStudentGender() {
		return studentGender;
	}

	public void setStudentGender(Integer studentGender) {
		this.studentGender = studentGender;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getStudentImgUrl() {
		return studentImgUrl;
	}

	public void setStudentImgUrl(String studentImgUrl) {
		this.studentImgUrl = studentImgUrl;
	}

}
