package com.xuexibao.teacher.enums;

public enum AudioFeudTypeStatus {

	feud(1), direct(2);
    
    private int status;
    
    AudioFeudTypeStatus(int status){
        this.status = status;
    }
    
    public int getValue(){
        return status;
    }
    
}
