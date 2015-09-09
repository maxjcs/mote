/**
 * @author oldlu
 */
package com.xuexibao.teacher.enums;


public class Plan {
	private double orgProfit;
	private double teacherProfit;

	Plan(double orgProfit, double teacherProfit) {
		this.orgProfit = orgProfit;
		this.teacherProfit = teacherProfit;
	}

	public double getOrgProfit() {
		return orgProfit;
	}

	public double getTeacherProfit() {
		return teacherProfit;
	}
}