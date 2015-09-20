package com.longcity.modeler.model;

import java.util.Date;

public class User {
    private Integer id;

    private String phoneNumber;

    private String password;

    private String avartUrl;

    private String nickname;

    private Integer gender;

    private Date birdthday;
    
    private String birdthdayStr;

    private Integer height;

    private Integer weight;

    private Integer areaId;

    private String wangwang;

    private String alipayId;

    private String alipayName;

    private Integer status;

    private Integer type;

    private String shopName;

    private String email;

    private String weixin;

    private String address;

    private String referee;

    private Integer remindFee;//余额
    
    private Integer freezeFee;//冻结金额
    
    private String deviceId="";
    
    private String loginType;//登陆类型 1手机登陆 2pc端登陆
    
    private String ip;//pc端登陆的ip地址
    
    private String smsCode;//短信验证码
    
    public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public Integer getFreezeFee() {
		return freezeFee;
	}

	public void setFreezeFee(Integer freezeFee) {
		this.freezeFee = freezeFee;
	}

	public String getBirdthdayStr() {
		return birdthdayStr;
	}

	public void setBirdthdayStr(String birdthdayStr) {
		this.birdthdayStr = birdthdayStr;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getAvartUrl() {
        return avartUrl;
    }

    public void setAvartUrl(String avartUrl) {
        this.avartUrl = avartUrl == null ? null : avartUrl.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Date getBirdthday() {
        return birdthday;
    }

    public void setBirdthday(Date birdthday) {
        this.birdthday = birdthday;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getWangwang() {
        return wangwang;
    }

    public void setWangwang(String wangwang) {
        this.wangwang = wangwang == null ? null : wangwang.trim();
    }

    public String getAlipayId() {
        return alipayId;
    }

    public void setAlipayId(String alipayId) {
        this.alipayId = alipayId == null ? null : alipayId.trim();
    }

    public String getAlipayName() {
        return alipayName;
    }

    public void setAlipayName(String alipayName) {
        this.alipayName = alipayName == null ? null : alipayName.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName == null ? null : shopName.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin == null ? null : weixin.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getReferee() {
        return referee;
    }

    public void setReferee(String referee) {
        this.referee = referee == null ? null : referee.trim();
    }

    public Integer getRemindFee() {
        return remindFee;
    }

    public void setRemindFee(Integer remindFee) {
        this.remindFee = remindFee;
    }
}