package com.longcity.modeler.model;

import java.util.Date;

public class MoteTask {
    private Integer id;

    private Integer userId;

    private Integer taskId;

    private Date createTime;

    private Date updateTime;

    private String orderNo;

    private String expressCompanyId;

    private String expressNo;

    private Integer selfBuyFee;

    private Integer status;
    
    private Integer finishStatus;
    
    private Date acceptedTime;
    
    private Date orderNoTime;
    
    private Date showPicTime;
    
    private Date uploadPicTime;
    
    private Date returnItemTime;
    
    private Date finishStatusTime;
    
    private Date selfBuyTime;
    
    public Date getSelfBuyTime() {
		return selfBuyTime;
	}

	public void setSelfBuyTime(Date selfBuyTime) {
		this.selfBuyTime = selfBuyTime;
	}

	public Date getAcceptedTime() {
		return acceptedTime;
	}

	public void setAcceptedTime(Date acceptedTime) {
		this.acceptedTime = acceptedTime;
	}

	public Date getOrderNoTime() {
		return orderNoTime;
	}

	public void setOrderNoTime(Date orderNoTime) {
		this.orderNoTime = orderNoTime;
	}

	public Date getShowPicTime() {
		return showPicTime;
	}

	public void setShowPicTime(Date showPicTime) {
		this.showPicTime = showPicTime;
	}

	public Date getUploadPicTime() {
		return uploadPicTime;
	}

	public void setUploadPicTime(Date uploadPicTime) {
		this.uploadPicTime = uploadPicTime;
	}

	public Date getReturnItemTime() {
		return returnItemTime;
	}

	public void setReturnItemTime(Date returnItemTime) {
		this.returnItemTime = returnItemTime;
	}

	public Date getFinishStatusTime() {
		return finishStatusTime;
	}

	public void setFinishStatusTime(Date finishStatusTime) {
		this.finishStatusTime = finishStatusTime;
	}

	public Integer getFinishStatus() {
		return finishStatus;
	}

	public void setFinishStatus(Integer finishStatus) {
		this.finishStatus = finishStatus;
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

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getExpressCompanyId() {
		return expressCompanyId;
	}

	public void setExpressCompanyId(String expressCompanyId) {
		this.expressCompanyId = expressCompanyId;
	}

	public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo == null ? null : expressNo.trim();
    }

    public Integer getSelfBuyFee() {
        return selfBuyFee;
    }

    public void setSelfBuyFee(Integer selfBuyFee) {
        this.selfBuyFee = selfBuyFee;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}