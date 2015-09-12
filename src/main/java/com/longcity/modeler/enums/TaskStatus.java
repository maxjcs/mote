/**
 * 
 */
package com.longcity.modeler.enums;

/**
 * @author maxjcs
 *
 */
public enum TaskStatus {
	
   no_payed(0),wait_approve(1), passed(2), not_passed(3), finished(4);
    
    private int status;
    
    TaskStatus(int status){
        this.status = status;
    }
    
    public int getValue(){
        return status;
    }
}
