package com.xuexibao.teacher.enums;

public enum FeudPointFeeConfType {
point(1), fee(2);
    
    private int status;
    
    FeudPointFeeConfType(int status){
        this.status = status;
    }
    
    public int getValue(){
        return status;
    }

}
