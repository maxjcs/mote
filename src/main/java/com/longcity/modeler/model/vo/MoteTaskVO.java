/**
 * 
 */
package com.longcity.modeler.model.vo;


/**
 * @author maxjcs
 *
 */
public class MoteTaskVO {
	
	Integer id;
	
	String nickname;
	
	String orderNo;
	
	Integer status;
	
	Integer finishStatus;
	
	Integer totalTaskFee;
	
	public Integer getTotalTaskFee() {
		return totalTaskFee;
	}

	public void setTotalTaskFee(Integer totalTaskFee) {
		this.totalTaskFee = totalTaskFee;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getFinishStatus() {
		return finishStatus;
	}

	public void setFinishStatus(Integer finishStatus) {
		this.finishStatus = finishStatus;
	}
	
	
	

}
