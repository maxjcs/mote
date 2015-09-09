package com.xuexibao.teacher.pay.model;

import java.util.Date;

import com.xuexibao.teacher.model.Audio;

public class PayAudio extends Audio {
	public static final int ONLINE = 1;// 1--上线，
	public static final int OFFLINE = 0;// 0--下线
	private Integer recheckStatus;
	private Integer orderId;
	private String approvor;
	private Date approvorTime;
	private Integer gold;
	private int orgId;
	private String orgTeacherId;
	
	
  	

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	public String getOrgTeacherId() {
		return orgTeacherId;
	}

	public void setOrgTeacherId(String orgTeacherId) {
		this.orgTeacherId = orgTeacherId;
	}

	public Integer getRecheckStatus() {
		return recheckStatus;
	}

	public void setRecheckStatus(Integer recheckStatus) {
		this.recheckStatus = recheckStatus;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getApprovor() {
		return approvor;
	}

	public void setApprovor(String approvor) {
		this.approvor = approvor;
	}

	public Date getApprovorTime() {
		return approvorTime;
	}

	public void setApprovorTime(Date approvorTime) {
		this.approvorTime = approvorTime;
	}

	public Integer getGold() {
		return gold;
	}

	public void setGold(Integer gold) {
		this.gold = gold;
	}

}
