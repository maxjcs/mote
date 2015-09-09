package com.xuexibao.teacher.enums;

public enum AudioWbType {
	 whiteboard(1),video(2);

	private int type;

	AudioWbType(int type) {
		this.type = type;
	}

	public int getValue() {
		return type;
	}
}
