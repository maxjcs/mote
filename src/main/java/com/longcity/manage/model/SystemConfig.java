/**
 * 
 */
package com.longcity.manage.model;

/**
 * @author maxjcs
 *
 */
public class SystemConfig {
	
	private Integer moteAcceptNum;
	
	private Integer acceptTimeOut;
	
	private Integer verifyReturnItemTimeOut;
	
	
	public Integer getVerifyReturnItemTimeOut() {
		return verifyReturnItemTimeOut;
	}

	public void setVerifyReturnItemTimeOut(Integer verifyReturnItemTimeOut) {
		this.verifyReturnItemTimeOut = verifyReturnItemTimeOut;
	}

	public Integer getMoteAcceptNum() {
		return moteAcceptNum;
	}

	public void setMoteAcceptNum(Integer moteAcceptNum) {
		this.moteAcceptNum = moteAcceptNum;
	}

	public Integer getAcceptTimeOut() {
		return acceptTimeOut;
	}

	public void setAcceptTimeOut(Integer acceptTimeOut) {
		this.acceptTimeOut = acceptTimeOut;
	}
	
	

}
