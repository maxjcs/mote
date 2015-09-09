package com.xuexibao.teacher.model;

import com.xuexibao.teacher.model.iter.ITeacher;

/**
 * 
 * @author oldlu
 * 
 */
public class AudioSetRank extends AudioSet implements ITeacher {
	private static final long serialVersionUID = 8286152224720268491L;

	private String name;
	private String nickname;
	private Integer gender;
	private String avatarUrl;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setStudentNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getGender() {
		return gender;
	}

	public void setStudentGender(Integer gender) {
		this.gender = gender;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	@Override
	public void setTeacherNickname(String name) {
		
	}

	@Override
	public void setTeacherGender(Integer gender) {

	}

	@Override
	public void setTeacherAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	@Override
	public void setTeacherName(String name) {
		setName(name);
	}

	/* (non-Javadoc)
	 * @see com.xuexibao.teacher.model.iter.ITeacher#setPhoneNumber(java.lang.String)
	 */
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
}
