package com.longcity.manage.model.vo;

public class SellerVO {
	
	Integer userId;
	
	String nickname;
	
	String shopName;
	
	String phoneNumber;
	
	String weixin;
	
	String email;
	
	Integer status;
	
	Integer remainFee;
	
	Integer freezeFee;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getWeixin() {
		return weixin;
	}

	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getRemainFee() {
		return remainFee;
	}

	public void setRemainFee(Integer remainFee) {
		this.remainFee = remainFee;
	}

	public Integer getFreezeFee() {
		return freezeFee;
	}

	public void setFreezeFee(Integer freezeFee) {
		this.freezeFee = freezeFee;
	}

}
