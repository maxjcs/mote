/**
 * 
 */
package com.xuexibao.teacher.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author maxjcs
 *
 */
public class TeacherPayDailyRank implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6078214883940145669L;
	
	String teacherId;
	
	Integer incomeRank;
	
	Integer messageNum;
	
	Integer followedNum;
	
	Integer dynamicNumWeekly;
	
	Date createTime;

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public Integer getIncomeRank() {
		return incomeRank;
	}

	public void setIncomeRank(Integer incomeRank) {
		this.incomeRank = incomeRank;
	}

	public Integer getMessageNum() {
		return messageNum;
	}

	public void setMessageNum(Integer messageNum) {
		this.messageNum = messageNum;
	}

	public Integer getFollowedNum() {
		return followedNum;
	}

	public void setFollowedNum(Integer followedNum) {
		this.followedNum = followedNum;
	}

	public Integer getDynamicNumWeekly() {
		return dynamicNumWeekly;
	}

	public void setDynamicNumWeekly(Integer dynamicNumWeekly) {
		this.dynamicNumWeekly = dynamicNumWeekly;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
