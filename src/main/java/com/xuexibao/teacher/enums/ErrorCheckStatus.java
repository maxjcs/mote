package com.xuexibao.teacher.enums;

public enum ErrorCheckStatus {

    noCheck(0), checked(1);
    
    private int status;
    
    ErrorCheckStatus(int status){
        this.status = status;
    }
    
    public int getValue(){
        return status;
    }
    
}
