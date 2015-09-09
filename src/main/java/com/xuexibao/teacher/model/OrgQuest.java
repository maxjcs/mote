package com.xuexibao.teacher.model;

import java.util.Date;
import java.io.Serializable;

/**
 * 
 * @author oldlu
 * 
 */
public class OrgQuest implements Serializable {
	private static final long serialVersionUID = 8286152224720268491L;

	public static final Object STATUS_LOCK = 1;
	public static final Object STATUS_FREE = 0;
	public static final Object STATUS_UPLOAD = 2;

	private int orgId;
	private long realQuestId;
	private Date createTime;
	private Date updateTime;
	private long subjectId;
	private String learnPhase;

	public long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(long subjectId) {
		this.subjectId = subjectId;
	}

	public String getLearnPhase() {
		return learnPhase;
	}

	public void setLearnPhase(String learnPhase) {
		this.learnPhase = learnPhase;
	}

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	public long getRealQuestId() {
		return realQuestId;
	}

	public void setRealQuestId(long realQuestId) {
		this.realQuestId = realQuestId;
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

}
