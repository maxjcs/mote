/**
 * 
 */
package com.longcity.modeler.enums;

/**
 * @author maxjcs
 *
 */
public enum MoteTaskStatus {
	
	follow(0), newAccept(1), taobaoOrder(2), showPicOK(3),uploadImg(4), selfBuy(5), returnItem(6), ApplyKefu(7), verifyReturnItem(8), TimeOut(9);
    
    private int status;
    
    MoteTaskStatus(int status){
        this.status = status;
    }
    
    public int getValue(){
        return status;
    }

}
