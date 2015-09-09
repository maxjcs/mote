package com.xuexibao.teacher.enums;

public enum FeudEvaluateStatus {

	good(1), medium(2), bad(3);

	private int status;

	FeudEvaluateStatus(int status) {
		this.status = status;
	}

	public int getValue() {
		return status;
	}

}
