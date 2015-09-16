/**
 * 
 */
package com.longcity.manage.model;

import java.util.Date;

/**
 * @author maxjcs
 *
 */
public class MoteStatistics {
	
	Integer id;
	
	Integer moteId;
	
	Integer taskFee;
	
	Integer remindFee;
	
	Integer taskNum;
	
	Date createTime;
	
	Date updateTime;
	
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getTaskNum() {
		return taskNum;
	}

	public void setTaskNum(Integer taskNum) {
		this.taskNum = taskNum;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMoteId() {
		return moteId;
	}

	public void setMoteId(Integer moteId) {
		this.moteId = moteId;
	}

	public Integer getTaskFee() {
		return taskFee;
	}

	public void setTaskFee(Integer taskFee) {
		this.taskFee = taskFee;
	}

	public Integer getRemindFee() {
		return remindFee;
	}

	public void setRemindFee(Integer remindFee) {
		this.remindFee = remindFee;
	}
	
	

}
