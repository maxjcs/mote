package com.xuexibao.teacher.enums;

public enum AudioEvalApproveStatus {
	
	waitApprove(0), beVerified(1), NotBeVerified(2);
    
    private int source;
    
    AudioEvalApproveStatus(int source){
        this.source = source;
    }
    
    public int getValue(){
        return source;
    }

}
