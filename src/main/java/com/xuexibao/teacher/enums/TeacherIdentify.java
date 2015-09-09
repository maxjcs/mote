package com.xuexibao.teacher.enums;

public enum TeacherIdentify {
	teacher(1), student(2);

	private int code;

	TeacherIdentify(int code) {
		this.code = code;
	}

	public int getValue() {
		return code;
	}
}
