/**
 * 
 */
package com.longcity.manage.model.vo;

/**
 * @author maxjcs
 *
 */
public class MoteDetailVO {
	
	String title;
	
    private Integer price;

    private Integer shotFee;
    
    private Integer selfBuyOff;
    
    Integer status;
    
    Integer createTime;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getShotFee() {
		return shotFee;
	}

	public void setShotFee(Integer shotFee) {
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

	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}
    

}
