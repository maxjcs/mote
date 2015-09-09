/**
 * 
 */
package com.longcity.modeler.enums;

/**
 * @author maxjcs
 *
 */
public enum TradeFlowType {
	
    itemReturn(1), itemAccept(2), cashFetch(3),cashAdd(4),taskDeduct(5);
    
    private int type;
    
    TradeFlowType(int type){
        this.type = type;
    }
    
    public int getValue(){
        return type;
    }

}
