package com.xuexibao.teacher.model;

import java.sql.Timestamp;

/**
 * 作者：付国庆
 * 时间：15/5/27－上午9:49
 * 描述：recom_teacher_order 表pojo
 */

public class RecommendTeacherOrder{
	
	private Long id;
	
    //教师id
    private String teacherId;
    //排序位置，升序排序
    private Integer orderId;
    private Timestamp createTime;
    private Timestamp updateTime;
    //操作人
    private String operator;
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
