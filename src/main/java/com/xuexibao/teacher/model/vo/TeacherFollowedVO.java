/**
 * 
 */
package com.xuexibao.teacher.model.vo;

import com.xuexibao.teacher.model.Teacher;

/**
 * @author maxjcs
 *
 */
public class TeacherFollowedVO extends Teacher {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4152177618902220342L;
	//教师Id
	String teacherId;
	//学生Id
	String userId;
	//是否关注
	Boolean isFollowed = false;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Boolean getIsFollowed() {
		return isFollowed;
	}
	public void setIsFollowed(Boolean isFollowed) {
		this.isFollowed = isFollowed;
	}
	public String getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

}
