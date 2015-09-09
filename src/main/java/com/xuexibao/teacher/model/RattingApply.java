package com.xuexibao.teacher.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author oldlu
 * 
 */
public class RattingApply implements Serializable {
	private static final long serialVersionUID = 8286152224720268491L;

	public final static int STATUS_APPING = 0;
	public final static int STATUS_AUDITED = 1;
	public final static int STATUS_UNAUDIT = 2;
	public final static int STATUS_REDUCE_STAR = 3;

	public static final int APPLY_AUDIO_COUNT = 10;
	private static Map<Integer, String> STATUS_MAP = new HashMap<Integer, String>();
	static {
		STATUS_MAP.put(STATUS_APPING, "请求升星，等待专家审核!");
		STATUS_MAP.put(STATUS_AUDITED, "顺利通过升星审核，你真棒!");
		STATUS_MAP.put(STATUS_UNAUDIT, "申请升星，因表现不佳被驳回");
		STATUS_MAP.put(STATUS_REDUCE_STAR, "很遗憾，被降星了");
	}
	private long id;
	private String teacherId;
	private Date createTime;
	private Date updateTime;
	private String auditId;
	private Date auditTime;
	private String auditContent;
	private int status;
	private int star;

	public int getStar() {
		return star;
	}

	public void setStar(int star) {
		this.star = star;
	}

	public String getContent() {
		return STATUS_MAP.get(status);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
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

	public String getAuditId() {
		return auditId;
	}

	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public String getAuditContent() {
		return auditContent;
	}

	public void setAuditContent(String auditContent) {
		this.auditContent = auditContent;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
