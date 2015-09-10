package com.longcity.modeler.enums;

public enum UserStatus{
    normal(0),abnormal(1);
    
    private int code;
    UserStatus(int code){
        this.code = code;
    }
    
    public int getValue(){
        return code;
    }
}
