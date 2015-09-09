/**
 * @author oldlu
 */
package com.xuexibao.teacher.constant;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Administrator
 * 
 */
public class AppVersion {
	public final static String VERSION_11 = "v1.1";
	public final static String VERSION_13 = "v1.3";
	public final static String VERSION_14 = "v1.4";

	public static boolean greatEqualThan13(String version) {
		return !StringUtils.isEmpty(version) && VERSION_13.compareTo(version) <= 0;
	}
	
	public static boolean greatEqualThan14(String version) {
		return !StringUtils.isEmpty(version) && VERSION_14.compareTo(version) <= 0;
	}


	public static boolean isVersion13(String version) {
		return StringUtils.equals(version, VERSION_13);
	}

	public static void main(String args[]) {
		System.out.println(greatEqualThan13(null));
		System.out.println(greatEqualThan13("v1.1"));
		System.out.println(greatEqualThan13("v1.3"));
		System.out.println(greatEqualThan13("v1.4"));
	}
}
