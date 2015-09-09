package com.xuexibao.teacher.model;

import java.util.Date;
import java.io.Serializable;

/**
 * 
 * @author oldlu
 *
 */
public class TeacherRecharge implements Serializable {
	private static final long serialVersionUID = 8286152224720268491L;
	public static final int STATUS_WAIT_STUDENT_CHARGE = 1;
	public static final int STATUS_STUDENT_ALREADY_CHARGE = 2;

	private long id;
	private String teacherId;
	private String studentId;
	private String studentPhoneNumber;
	private int status;
	private Date createTime;
	private Date updateTime;
	private int rechargeMoney;
	private Date rechargeTime;
	private int incomeMoney;

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

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getStudentPhoneNumber() {
		return studentPhoneNumber;
	}

	public void setStudentPhoneNumber(String studentPhoneNumber) {
		this.studentPhoneNumber = studentPhoneNumber;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public int getRechargeMoney() {
		return rechargeMoney;
	}

	public void setRechargeMoney(int rechargeMoney) {
		this.rechargeMoney = rechargeMoney;
	}

	public Date getRechargeTime() {
		return rechargeTime;
	}

	public void setRechargeTime(Date rechargeTime) {
		this.rechargeTime = rechargeTime;
	}

	public int getIncomeMoney() {
		return incomeMoney;
	}

	public void setIncomeMoney(int incomeMoney) {
		this.incomeMoney = incomeMoney;
	}

}
