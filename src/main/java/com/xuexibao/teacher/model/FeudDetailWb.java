package com.xuexibao.teacher.model;

import java.util.Date;

import com.xuexibao.teacher.enums.AudioWbType;

public class FeudDetailWb {
	private Long id;

	private Long feudDetailId;

	private String fileUrl;

	private Date createTime;

	private Date updateTime;

	private String fileType;

	private String wbId;

	private Long questRealId;
	private int wbVersion;
	private Integer wbType = AudioWbType.whiteboard.getValue();

	public Integer getWbType() {
		return wbType;
	}

	public void setWbType(Integer wbType) {
		this.wbType = wbType;
	}

	public int getWbVersion() {
		return wbVersion;
	}

	public void setWbVersion(int wbVersion) {
		this.wbVersion = wbVersion;
	}

	public String getWbId() {
		return wbId;
	}

	public void setWbId(String wbId) {
		this.wbId = wbId;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFeudDetailId() {
		return feudDetailId;
	}

	public void setFeudDetailId(Long feudDetailId) {
		this.feudDetailId = feudDetailId;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl == null ? null : fileUrl.trim();
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

	public Long getQuestRealId() {
		return questRealId;
	}

	public void setQuestRealId(Long questRealId) {
		this.questRealId = questRealId;
	}
}