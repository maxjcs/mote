package com.xuexibao.teacher.model;

import java.util.Date;

import com.xuexibao.teacher.enums.AudioWbType;

public class Wb2videoProcess {
	private Long id;

	private String teacherId;

	private String videoUrl;

	private int status;

	private Date createTime;

	private Date updateTime;

	private String wbId;

	private String fileType = "video.mp4";

	private Integer wbType = AudioWbType.video.getValue();

	public Integer getWbType() {
		return wbType;
	}

	public void setWbType(Integer wbType) {
		this.wbType = wbType;
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

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId == null ? null : teacherId.trim();
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public String getWbId() {
		return wbId;
	}

	public void setWbId(String wbId) {
		this.wbId = wbId == null ? null : wbId.trim();
	}
}