package com.xuexibao.teacher.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

public class City implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -7534656769505081908L;

	private Long id;

    private String name;
    
    @JsonIgnore
    private Long sortNumber;
    @JsonIgnore
    private Date createTime;
    @JsonIgnore
    private Date updateTime;
    private Long pid;
    
    private List<City> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(Long sortNumber) {
        this.sortNumber = sortNumber;
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

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

	public List<City> getChildren() {
		return children;
	}

	public void setChildren(List<City> children) {
		this.children = children;
	}
    
    
}