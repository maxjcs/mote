package com.xuexibao.teacher.enums;

public enum AudioNeedEvaluate {

	need(1), notNeed(2);

	private int status;

	AudioNeedEvaluate(int status) {
		this.status = status;
	}

	public int getValue() {
		return status;
	}

}
