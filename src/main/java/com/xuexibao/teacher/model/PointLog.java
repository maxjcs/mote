package com.xuexibao.teacher.model;

import java.util.Date;
import java.io.Serializable;

/**
 * 
 * @author oldlu
 * 
 */
public class PointLog implements Serializable {
	private static final long serialVersionUID = 8286152224720268491L;

	private long id;
	private int point;
	private int remainPoint;
	private String teacherId;
	private String description;
	private Date createTime;
	private Date updateTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public int getRemainPoint() {
		return remainPoint;
	}

	public void setRemainPoint(int remainPoint) {
		this.remainPoint = remainPoint;
	}

}
