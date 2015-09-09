/**
 * @author oldlu
 */
package com.xuexibao.teacher.model.iter;

public interface ITeacher {
	String getTeacherId();

	void setTeacherName(String name);

	void setPhoneNumber(String phoneNumber);

	void setTeacherNickname(String nickname);

	void setTeacherGender(Integer gender);

	void setTeacherAvatarUrl(String avatarUrl);
	
	void setTeacherCourseYear(Integer courseYear);
	
	void setTeacherStar(Integer star);
}
