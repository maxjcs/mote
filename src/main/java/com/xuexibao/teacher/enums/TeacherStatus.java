package com.xuexibao.teacher.enums;

public enum TeacherStatus{
    normal(1),abnormal(0);
    
    private int code;
    TeacherStatus(int code){
        this.code = code;
    }
    
    public int getValue(){
        return code;
    }
}
