/**
 * 
 */
package com.longcity.modeler.model.vo;

import java.util.Date;

/**
 * 
 * @author maxjcs
 *
 */
public class TaskVO {
	
	Integer id;
	
	String title;
	
	Date createTime;
	
	Integer totalNum=0;
	
	Integer accpetedNum=0;
	
	Integer orderedNum=0;
	
	Integer finishShowPicNum=0;
	
	Integer goodRateNum=0;
	
	Integer selfBuyNum=0;
	
	Integer returnNum=0;
	
	Integer finishedNum=0;
	
	Integer totalMoney=0;
	
	Integer status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public Integer getAccpetedNum() {
		return accpetedNum;
	}

	public void setAccpetedNum(Integer accpetedNum) {
		this.accpetedNum = accpetedNum;
	}

	public Integer getOrderedNum() {
		return orderedNum;
	}

	public void setOrderedNum(Integer orderedNum) {
		this.orderedNum = orderedNum;
	}

	public Integer getFinishShowPicNum() {
		return finishShowPicNum;
	}

	public void setFinishShowPicNum(Integer finishShowPicNum) {
		this.finishShowPicNum = finishShowPicNum;
	}

	public Integer getGoodRateNum() {
		return goodRateNum;
	}

	public void setGoodRateNum(Integer goodRateNum) {
		this.goodRateNum = goodRateNum;
	}

	public Integer getSelfBuyNum() {
		return selfBuyNum;
	}

	public void setSelfBuyNum(Integer selfBuyNum) {
		this.selfBuyNum = selfBuyNum;
	}

	public Integer getReturnNum() {
		return returnNum;
	}

	public void setReturnNum(Integer returnNum) {
		this.returnNum = returnNum;
	}

	public Integer getFinishedNum() {
		return finishedNum;
	}

	public void setFinishedNum(Integer finishedNum) {
		this.finishedNum = finishedNum;
	}

	public Integer getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Integer totalMoney) {
		this.totalMoney = totalMoney;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	

}
