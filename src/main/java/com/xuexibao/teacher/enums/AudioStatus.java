package com.xuexibao.teacher.enums;

public enum AudioStatus {

	waitAudio(-1), needCheck(0), checked(1), notPassCheck(2),offline(3);
    
    private int status;
    
    AudioStatus(int status){
        this.status = status;
    }
    
    public int getValue(){
        return status;
    }
    
}
