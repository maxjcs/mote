package com.xuexibao.teacher.model;

import java.util.Date;

public class TeacherFollowed {
    private Long id;

    private String teacherId;

    private String userId;

    private Date createTime;

    private Date updateTime;
    
    //老师被关注的数量
    private Integer teacherFollowedNum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId == null ? null : teacherId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
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

	public Integer getTeacherFollowedNum() {
		return teacherFollowedNum;
	}

	public void setTeacherFollowedNum(Integer teacherFollowedNum) {
		this.teacherFollowedNum = teacherFollowedNum;
	}
    
    
}