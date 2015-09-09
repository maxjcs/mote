package com.xuexibao.teacher.model;

import java.io.Serializable;

public class EvaluationPointConf implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7152111787282665713L;
	Integer id;
	Integer type;
	Integer good;
	Integer middle;
	Integer bad;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getGood() {
		return good;
	}
	public void setGood(Integer good) {
		this.good = good;
	}
	public Integer getMiddle() {
		return middle;
	}
	public void setMiddle(Integer middle) {
		this.middle = middle;
	}
	public Integer getBad() {
		return bad;
	}
	public void setBad(Integer bad) {
		this.bad = bad;
	}
	
	

}
