/**
 * @author oldlu
 */
package com.xuexibao.teacher.enums;

public class PlanFactory {
	public static final String PLAN_A = "A";
	public static final String PLAN_B = "B";
	public static final String PLAN_C = "C";

	public static final Plan planA = new Plan(50, 0);
	public static final Plan planB = new Plan(10, 50);
	public static final Plan planC = new Plan(50, 0);
	public static final Plan planD = new Plan(0, 50);

	public static Plan getPlanA() {
		return planA;
	}

	public static Plan getPlanB() {
		return planB;
	}

	public static Plan getPlanC() {
		return planC;
	}
	
	public static Plan getPlanD() {
		return planD;
	}
}
