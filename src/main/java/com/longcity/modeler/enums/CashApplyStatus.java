/**
 * 
 */
package com.longcity.modeler.enums;

/**
 * @author maxjcs
 *
 */
public enum CashApplyStatus {
	
	   newadd(1), finished(2);
	    
	    private int status;
	    
	    CashApplyStatus(int status){
	        this.status = status;
	    }
	    
	    public int getValue(){
	        return status;
	    }

}
