package com.longcity.manage.model.vo;

import java.util.Date;

public class MoteVO {
	
	Integer userId;
	
	String nickname;
	
	String phoneNumber;
	
	Integer gender;
	
	Integer age;
	
	Integer heigth;
	
	Integer shape;
	
	Integer status;
	
	Integer areaId;
	
	Double remindFee;
	
	Double taskFee;
	
	Integer taskNum;
	
	Date birdthday;

	Date createTime;
	
	
	public Date getBirdthday() {
		return birdthday;
	}

	public void setBirdthday(Date birdthday) {
		this.birdthday = birdthday;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Integer getTaskNum() {
		return taskNum;
	}

	public void setTaskNum(Integer taskNum) {
		this.taskNum = taskNum;
	}

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

	public Double getRemindFee() {
		return remindFee;
	}

	public void setRemindFee(Double remindFee) {
		this.remindFee = remindFee;
	}

	public Double getTaskFee() {
		return taskFee;
	}

	public void setTaskFee(Double taskFee) {
		this.taskFee = taskFee;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	
}
