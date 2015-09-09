/**
 * 
 */
package com.xuexibao.teacher.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author maxjcs
 *
 */
public class LiveaaImage implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1191606787583747947L;
	
	Long id;
	String imageId;
	String resSearch;
	String imageUrl;
	String imageUrlDomain;
	Date createTime;
	Date updateTime;
	String searchType;
	String verClient;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	public String getResSearch() {
		return resSearch;
	}
	public void setResSearch(String resSearch) {
		this.resSearch = resSearch;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getImageUrlDomain() {
		return imageUrlDomain;
	}
	public void setImageUrlDomain(String imageUrlDomain) {
		this.imageUrlDomain = imageUrlDomain;
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
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getVerClient() {
		return verClient;
	}
	public void setVerClient(String verClient) {
		this.verClient = verClient;
	}
	
	
}
