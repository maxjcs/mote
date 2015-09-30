/**
 * 
 */
package test;

import com.longcity.modeler.util.VerifyCodeUtil;

/**
 * @author maxjcs
 *
 */
public class SmsRegister {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			String verifyCode = VerifyCodeUtil.getRandNum(6);
			VerifyCodeUtil.sendMessage("15888838217", verifyCode);
		} catch (Exception e) {
			
		}
		
	}

}
