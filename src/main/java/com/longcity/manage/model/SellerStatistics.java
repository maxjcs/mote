/**
 * 
 */
package com.longcity.manage.model;

import java.util.Date;

/**
 * 商家统计
 * @author maxjcs
 *
 */
public class SellerStatistics {
	
	Integer id;
	
	Integer sellerId;
	
	Integer projectNum;
	
	Integer taskNum;
	
	Integer totalFee;
	
	Integer taskFee;
	
	Date createTime;
	
	Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSellerId() {
		return sellerId;
	}

	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}

	public Integer getProjectNum() {
		return projectNum;
	}

	public void setProjectNum(Integer projectNum) {
		this.projectNum = projectNum;
	}

	public Integer getTaskNum() {
		return taskNum;
	}

	public void setTaskNum(Integer taskNum) {
		this.taskNum = taskNum;
	}

	public Integer getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}
