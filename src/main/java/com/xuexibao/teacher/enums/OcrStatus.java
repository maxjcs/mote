package com.xuexibao.teacher.enums;

public enum OcrStatus {
	
	UN_OCR(1), //未ocr
	OCR(2),//已经ocr
	FINISH(3),//ocr失败
	;
	
	private final Integer value;

	OcrStatus(Integer value) {
        this.value = value;
    }
    
    public Integer getValue() {
        return value;
    }

}
