package com.xuexibao.teacher.enums;

public enum AudioType {

	audio(1), whiteboard(2);

	private int type;

	AudioType(int type) {
		this.type = type;
	}

	public int getValue() {
		return type;
	}

}
