/**
 * 
 */
package com.longcity.modeler.model.vo;

import java.util.Date;

/**
 * @author maxjcs
 *
 */
public class TradeFlowVO {
	
	Integer referId;
	
	Date createTime;
	
	String url;
	
	Date acceptTime;
	
	Double money;
	
	Integer moneyFen;
	
	Integer price;
	
	Integer selfBuyOff;
	
	Integer type;
	
	public Integer getMoneyFen() {
		return moneyFen;
	}

	public void setMoneyFen(Integer moneyFen) {
		this.moneyFen = moneyFen;
	}

	public Integer getReferId() {
		return referId;
	}

	public void setReferId(Integer referId) {
		this.referId = referId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getAcceptTime() {
		return acceptTime;
	}

	public void setAcceptTime(Date acceptTime) {
		this.acceptTime = acceptTime;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getSelfBuyOff() {
		return selfBuyOff;
	}

	public void setSelfBuyOff(Integer selfBuyOff) {
		this.selfBuyOff = selfBuyOff;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	

}
