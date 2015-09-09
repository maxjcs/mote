/**
 * 
 */
package com.longcity.modeler.enums;

/**
 * @author maxjcs
 *
 */
public enum MoteTaskStatus {
	
    newAccept(1), taobaoOrder(2), showPicOK(3), selfBuy(4), returnItem(5), ApplyKefu(6), TimeOut(7);
    
    private int status;
    
    MoteTaskStatus(int status){
        this.status = status;
    }
    
    public int getValue(){
        return status;
    }

}
