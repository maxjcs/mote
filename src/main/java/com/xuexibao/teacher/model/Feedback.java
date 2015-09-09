package com.xuexibao.teacher.model;

import java.util.Date;
import java.io.Serializable;

/**
 * 
 * @author oldlu
 *
 */
public class Feedback implements Serializable  {
	private static final long serialVersionUID = 8286152224720268491L;
	
	private long id;
	private String teacherId;
	private String content;
	private String phoneNumber;
	private Date createTime;
	
	public long getId(){
		return id;
	}
	
	public void setId(long id){
		this.id=id;
	}
	
	public String getTeacherId(){
		return teacherId;
	}
	
	public void setTeacherId(String teacherId){
		this.teacherId=teacherId;
	}
	
	public String getContent(){
		return content;
	}
	
	public void setContent(String content){
		this.content=content;
	}
	
	public String getPhoneNumber(){
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber){
		this.phoneNumber=phoneNumber;
	}
	
	public Date getCreateTime(){
		return createTime;
	}
	
	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}
	
}
