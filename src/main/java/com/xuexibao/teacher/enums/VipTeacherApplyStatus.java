/**
 * 
 */
package com.xuexibao.teacher.enums;

/**
 * @author maxjcs
 *
 */
public enum VipTeacherApplyStatus {
	
	APPLYING(1), //提交申请
	APPLYED_YES(2),//审批通过
	APPLYED_NO(3);//审批未通过
	
	private final int value;

	VipTeacherApplyStatus(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }

}
