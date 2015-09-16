package com.longcity.manage.model.vo;

import java.util.Date;

public class MoteVO {
	
	Integer userId;
	
	String nickname;
	
	Integer gender;
	
	Integer age;
	
	Integer heigth;
	
	Integer shape;
	
	Integer status;
	
	Integer areaId;
	
	Integer remindFee;
	
	Integer taskFee;
	
	Integer taskNum;

	Date createTime;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getHeigth() {
		return heigth;
	}

	public void setHeigth(Integer heigth) {
		this.heigth = heigth;
	}

	public Integer getShape() {
		return shape;
	}

	public void setShape(Integer shape) {
		this.shape = shape;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public Integer getRemindFee() {
		return remindFee;
	}

	public void setRemindFee(Integer remindFee) {
		this.remindFee = remindFee;
	}

	public Integer getTaskFee() {
		return taskFee;
	}

	public void setTaskFee(Integer taskFee) {
		this.taskFee = taskFee;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	
}
