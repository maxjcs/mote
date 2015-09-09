package com.xuexibao.teacher.enums;

public enum FeudAnswerDetailStatus {

	prepare(1),  complete(2), expire(3), giveup(4);
    
    private int status;
    
    FeudAnswerDetailStatus(int status){
        this.status = status;
    }
    
    public int getValue(){
        return status;
    }
    
}
