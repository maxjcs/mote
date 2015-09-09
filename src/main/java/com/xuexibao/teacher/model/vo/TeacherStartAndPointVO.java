package com.xuexibao.teacher.model.vo;

import com.xuexibao.teacher.model.Teacher;

public class TeacherStartAndPointVO extends Teacher{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2485098522278808012L;

	int star;
	
	int point;

	public int getStar() {
		return star;
	}

	public void setStar(int star) {
		this.star = star;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}
	
	

}
