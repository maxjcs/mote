/**
 * 
 */
package com.longcity.modeler.enums;

/**
 * @author maxjcs
 *
 */
public enum CashRecordType {
	
	add(1), reduce(2),freeze(3),unfreeze(4);
    
    private int type;
    
    CashRecordType(int type){
        this.type = type;
    }
    
    public int getValue(){
        return type;
    }

}
