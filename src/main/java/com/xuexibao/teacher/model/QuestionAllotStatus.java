package com.xuexibao.teacher.model;

public enum QuestionAllotStatus {

    releaseAllot(0), allot(1);
    
    private int status;
    
    QuestionAllotStatus(int status){
        this.status = status;
    }
    
    public int getValue(){
        return status;
    }
}
