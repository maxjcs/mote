/**
 * 
 */
package com.longcity.modeler.enums;

/**
 * @author maxjcs
 *
 */
public enum UserType {
	
	mote(1), seller(2);
    
    private int code;
    
    UserType(int code){
        this.code = code;
    }
    
    public int getValue(){
        return code;
    }
}
