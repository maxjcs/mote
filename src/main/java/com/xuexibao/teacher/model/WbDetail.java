/**
 * 
 */
package com.xuexibao.teacher.model;

import java.util.Date;

/**
 * @author maxjcs
 *
 */
public class WbDetail {
	
	Long id;
	
	String fileUrl;
	
	String fileType;
	
	String detailId;
	
	String wbId;
	
	Date createTime;
	
	Date updateTime;
	
	int detailType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getDetailId() {
		return detailId;
	}

	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}

	public String getWbId() {
		return wbId;
	}

	public void setWbId(String wbId) {
		this.wbId = wbId;
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

	public int getDetailType() {
		return detailType;
	}

	public void setDetailType(int detailType) {
		this.detailType = detailType;
	}

}
