/**
 * 
 */
package com.longcity.modeler.enums;

/**
 * @author maxjcs
 *
 */
public enum MoteTaskStatus {
	
    newAccept(1), taobaoOrder(2), showPicOK(3),uploadImg(4), selfBuy(5), returnItem(6), ApplyKefu(7), TimeOut(8);
    
    private int status;
    
    MoteTaskStatus(int status){
        this.status = status;
    }
    
    public int getValue(){
        return status;
    }

}
