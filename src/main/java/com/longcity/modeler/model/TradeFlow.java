package com.longcity.modeler.model;

import java.util.Date;

public class TradeFlow {
    private Integer id;

    private Integer referId;

    private Integer type;

    private Double money;
    
    private Integer moneyFen;

    private Date createTime;

    private Integer userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReferId() {
        return referId;
    }

    public void setReferId(Integer referId) {
        this.referId = referId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Integer getMoneyFen() {
		return moneyFen;
	}

	public void setMoneyFen(Integer moneyFen) {
		this.moneyFen = moneyFen;
	}

	public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}