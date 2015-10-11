/**
 * 
 */
package com.longcity.manage.model.vo;

import java.util.Date;

/**
 * @author maxjcs
 *
 */
public class MoteDetailVO {
	
	Integer id;
	
	String title;
	
    private Double price;

    private Double shotFee;
    
    private Integer selfBuyOff;
    
    Integer status;
    
    Date createTime;
    
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getShotFee() {
		return shotFee;
	}

	public void setShotFee(Double shotFee) {
		this.shotFee = shotFee;
	}

	public Integer getSelfBuyOff() {
		return selfBuyOff;
	}

	public void setSelfBuyOff(Integer selfBuyOff) {
		this.selfBuyOff = selfBuyOff;
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

}
