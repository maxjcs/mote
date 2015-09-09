package com.xuexibao.teacher.model;

import java.util.List;

import com.xuexibao.teacher.model.iter.ITeacher;

public class AudioBelongSet implements ITeacher {
	private String audioId;
	private String setId;
	private String teacherId;

	private String teacherName;
	private String phoneNumber;
	private String teacherNickname;
	private String teacherAvatarUrl;
	private Integer teacherGender;
	private List<String> grades;
	private String description;
	private Integer price;
	private String name;
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getGrades() {
		return grades;
	}

	public void setGrades(List<String> grades) {
		this.grades = grades;
	}

	public String getAudioId() {
		return audioId;
	}

	public void setAudioId(String audioId) {
		this.audioId = audioId;
	}

	public String getSetId() {
		return setId;
	}

	public void setSetId(String setId) {
		this.setId = setId;
	}

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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

	@Override
	public void setTeacherCourseYear(Integer courseYear) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTeacherStar(Integer star) {
		// TODO Auto-generated method stub
		
	}
}
