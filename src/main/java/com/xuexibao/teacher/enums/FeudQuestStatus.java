package com.xuexibao.teacher.enums;

public enum FeudQuestStatus {

    wait(1), ing(2), complete(3),expire(4);
    
    private int status;
    
    FeudQuestStatus(int status){
        this.status = status;
    }
    
    public int getValue(){
        return status;
    }
    
}
