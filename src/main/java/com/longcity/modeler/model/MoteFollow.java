/**
 * 
 */
package com.longcity.modeler.model;

import java.util.Date;

/**
 * @author maxjcs
 *
 */
public class MoteFollow {
	
	Integer id;
	
	Integer moteId;
	
	Integer taskId;
	
	Date createTime;

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

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	

}
