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
	
	Double money;
	
	Integer moneyFen;
	
	Integer type;
	
	String title;
	
	Date createTime;
	
	Double remindMoney;
	
	Integer remindMoneyFen;

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

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Integer getMoneyFen() {
		return moneyFen;
	}

	public void setMoneyFen(Integer moneyFen) {
		this.moneyFen = moneyFen;
	}

	public Double getRemindMoney() {
		return remindMoney;
	}

	public void setRemindMoney(Double remindMoney) {
		this.remindMoney = remindMoney;
	}

	public Integer getRemindMoneyFen() {
		return remindMoneyFen;
	}

	public void setRemindMoneyFen(Integer remindMoneyFen) {
		this.remindMoneyFen = remindMoneyFen;
	}

	
	

}
