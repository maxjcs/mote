package com.xuexibao.teacher.model;

public enum CorrectionStatus {
    
    needCheck(0), checked(1);
    
    private int status;
    
    CorrectionStatus(int status){
        this.status = status;
    }
    
    public int getValue(){
        return status;
    }
    
}
