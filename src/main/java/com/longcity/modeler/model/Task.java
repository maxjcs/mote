package com.longcity.modeler.model;

import java.util.Date;

public class Task {
    private Integer id;

    private Integer userId;

    private String title;

    private String url;

    private Integer price;

    private Integer shotFee;

    private String imgUrl;

    private Integer selfBuyOff;

    private String shotDesc;

    private Integer gender;

    private Integer shape;

    private Integer heightMin;

    private Integer heightMax;

    private Integer areaid;

    private Integer modelerLevel;

    private Integer selfBuyRate;

    private Integer number;

    private Integer shotAreaId;

    private Integer status;
    
    private Date createTime;
    
    private Date updateTime;
    
    private Integer totalFee;
    
    public Integer getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    public Integer getSelfBuyOff() {
        return selfBuyOff;
    }

    public void setSelfBuyOff(Integer selfBuyOff) {
        this.selfBuyOff = selfBuyOff;
    }

    public String getShotDesc() {
        return shotDesc;
    }

    public void setShotDesc(String shotDesc) {
        this.shotDesc = shotDesc == null ? null : shotDesc.trim();
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getShape() {
        return shape;
    }

    public void setShape(Integer shape) {
        this.shape = shape;
    }

    public Integer getHeightMin() {
        return heightMin;
    }

    public void setHeightMin(Integer heightMin) {
        this.heightMin = heightMin;
    }

    public Integer getHeightMax() {
        return heightMax;
    }

    public void setHeightMax(Integer heightMax) {
        this.heightMax = heightMax;
    }

    public Integer getAreaid() {
        return areaid;
    }

    public void setAreaid(Integer areaid) {
        this.areaid = areaid;
    }

    public Integer getModelerLevel() {
        return modelerLevel;
    }

    public void setModelerLevel(Integer modelerLevel) {
        this.modelerLevel = modelerLevel;
    }

    public Integer getSelfBuyRate() {
        return selfBuyRate;
    }

    public void setSelfBuyRate(Integer selfBuyRate) {
        this.selfBuyRate = selfBuyRate;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getShotAreaId() {
        return shotAreaId;
    }

    public void setShotAreaId(Integer shotAreaId) {
        this.shotAreaId = shotAreaId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}