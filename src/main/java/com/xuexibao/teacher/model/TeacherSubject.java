/**
 * @author oldlu
 */
package com.xuexibao.teacher.model;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;

public class TeacherSubject implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4653925332739640397L;
	private int id;
	private int subjectId;
	private String teacherId;
	@JsonIgnore
	private Date createTime;
	@JsonIgnore
	private Date updateTime;

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
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
