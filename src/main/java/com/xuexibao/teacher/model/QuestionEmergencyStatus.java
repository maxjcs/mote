package com.xuexibao.teacher.model;

public enum QuestionEmergencyStatus {

	emergency(1), normal(0);
    
    private int status;
    
    QuestionEmergencyStatus(int status){
        this.status = status;
    }
    
    public int getValue(){
        return status;
    }
    
}
