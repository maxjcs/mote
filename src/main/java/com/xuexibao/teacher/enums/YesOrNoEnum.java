package com.xuexibao.teacher.enums;

public enum YesOrNoEnum {
	
	Y("Y"), //yes
	N("N");//no
	
	private final String value;

	YesOrNoEnum(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
}
