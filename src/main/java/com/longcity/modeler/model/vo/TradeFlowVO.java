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
	
	Integer money;
	
	Integer price;
	
	Integer selfBuyOff;
	
	Integer type;
	
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

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
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
