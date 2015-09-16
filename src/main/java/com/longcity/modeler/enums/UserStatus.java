package com.longcity.modeler.enums;

public enum UserStatus{
   waitApprove(0), normal(1),abnormal(2);
    
    private int code;
    UserStatus(int code){
        this.code = code;
    }
    
    public int getValue(){
        return code;
    }
}
