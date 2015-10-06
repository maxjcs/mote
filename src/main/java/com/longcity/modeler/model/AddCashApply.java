/**
 * 
 */
package com.longcity.modeler.model;

import java.util.Date;

/**
 * @author maxjcs
 *
 */
public class AddCashApply {
	
	Integer id;
	
	Integer userId;
	
	String alipayName;
	
	Double money;
	
	Integer moneyFen;//分单位
	
	String lastSixOrderNo;
	
	Integer status;
	
	Date createTime;
	
	Date finishTime;
	
	String alipayId;
	
	public String getAlipayId() {
		return alipayId;
	}

	public void setAlipayId(String alipayId) {
		this.alipayId = alipayId;
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

	public String getAlipayName() {
		return alipayName;
	}

	public void setAlipayName(String alipayName) {
		this.alipayName = alipayName;
	}

	public String getLastSixOrderNo() {
		return lastSixOrderNo;
	}

	public void setLastSixOrderNo(String lastSixOrderNo) {
		this.lastSixOrderNo = lastSixOrderNo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
	
	

}
