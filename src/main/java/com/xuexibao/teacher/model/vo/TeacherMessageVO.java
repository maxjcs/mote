package com.xuexibao.teacher.model.vo;

import java.util.Date;
import java.io.Serializable;

/**
 * 
 * @author yingmingbao
 *
 */
public class TeacherMessageVO implements Serializable {

	private static final long serialVersionUID = -4377535776349918370L;
	
	private long id;
	private String name;
	private String imageUrl;
	private Integer gender;
	private String content;
	private Integer type;
	private Date createTime;
	private Date updateTime;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
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