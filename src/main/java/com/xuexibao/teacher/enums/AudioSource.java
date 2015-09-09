package com.xuexibao.teacher.enums;

public enum AudioSource {
	task(1), explanation(2), feud(3),direct(4),hudong(5),;
    
    private int source;
    
    AudioSource(int source){
        this.source = source;
    }
    
    public int getValue(){
        return source;
    }

}
