/**
 * 
 */
package com.longcity.modeler.model;

import java.util.Date;

/**
 * @author maxjcs
 *
 */

public class CashRecord {
	
	Integer id;
	
	Integer userId;
	
	Integer money;
	
	Integer type;
	
	String title;
	
	Date createTime;
	
	Integer remindMoney;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getRemindMoney() {
		return remindMoney;
	}

	public void setRemindMoney(Integer remindMoney) {
		this.remindMoney = remindMoney;
	}
	
	

}
