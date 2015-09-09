package com.xuexibao.teacher.model;

import java.util.Date;
import java.io.Serializable;

/**
 * 
 * @author oldlu
 *
 */
public class DynamicUpvote implements Serializable {
	private static final long serialVersionUID = 8286152224720268491L;

	private Long id;
	private Long dynamicId;
	private Date createTime;
	private String votorId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDynamicId() {
		return dynamicId;
	}

	public void setDynamicId(Long dynamicId) {
		this.dynamicId = dynamicId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getVotorId() {
		return votorId;
	}

	public void setVotorId(String votorId) {
		this.votorId = votorId;
	}

}
