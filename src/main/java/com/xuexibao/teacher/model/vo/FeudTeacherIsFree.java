package com.xuexibao.teacher.model.vo;

public class FeudTeacherIsFree {
	private String teacherId;
	private boolean isFree;
	private int feudTotal;
	
	public int getFeudTotal() {
		return feudTotal;
	}
	public void setFeudTotal(int feudTotal) {
		this.feudTotal = feudTotal;
	}
	public String getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}
	public boolean isFree() {
		return isFree;
	}
	public void setFree(boolean isFree) {
		this.isFree = isFree;
	}
	

}
