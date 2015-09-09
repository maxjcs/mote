package com.xuexibao.teacher.enums;

public enum Wb2videoProcessStatus {

	uploadOk(1), uploadFailed(2), processOk(3), processFailed(4);
    
    private int status;
    
    Wb2videoProcessStatus(int status){
        this.status = status;
    }
    
    public int getValue(){
        return status;
    }

}
