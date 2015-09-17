package com.longcity.modeler.enums;

public enum UserStatus{
   waitApprove(1), normal(2),abnormal(3);
    
    private int code;
    UserStatus(int code){
        this.code = code;
    }
    
    public int getValue(){
        return code;
    }
}
