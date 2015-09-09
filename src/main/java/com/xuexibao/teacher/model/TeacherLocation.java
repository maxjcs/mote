/**
 * 
 */
package com.xuexibao.teacher.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 教师经纬度坐标对象
 * @author maxjcs
 *
 */
public class TeacherLocation implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8711825860398852782L;

	private String _id;
	 
    private double[] position;
    
    //与学生的距离
    private Double distance;
    
    private Date updateTime;


	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public double[] getPosition() {
		return position;
	}

	public void setPosition(double[] position) {
		this.position = position;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	

}
