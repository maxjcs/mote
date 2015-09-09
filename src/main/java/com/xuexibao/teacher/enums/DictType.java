package com.xuexibao.teacher.enums;

public enum DictType {
	gender(1), teacherIdentify(2), teachingTime(3);

	private int code;

	DictType(int code) {
		this.code = code;
	}

	public int getValue() {
		return code;
	}
}
