package com.xuexibao.teacher.enums;

public enum WhiteBoradVersion {
	one(1), two(2);
	private int version;

	WhiteBoradVersion(int version) {
		this.version = version;
	}

	public int getValue() {
		return version;
	}

}
