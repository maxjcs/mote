/**
 * 
 */
package com.longcity.modeler.util;

import java.math.BigDecimal;

/**
 * @author maxjcs
 *
 */
public class MoneyUtil {
	
	/**
	 * 元转换成分
	 * @param value
	 * @return
	 */
	public static Integer yuan2Fen(Double value){
		if(value==null){
			return null;
		}
		long longvalue=Math.round(value*100);
		return new Long(longvalue).intValue();
	}
	
	/**
	 * 分转换成元
	 * @param value
	 * @return
	 */
	public static Double fen2Yuan(Integer value){
		Double dvalue= new Double(value)/100;
        return new BigDecimal(dvalue).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 分转换成元
	 * @param value
	 * @return
	 */
	public static Double fen2Yuan(Double value){
		Double dvalue= value/100;
        return new BigDecimal(dvalue).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * double转换成int
	 * @return
	 */
	public static Integer double2Int(Double value){
		if(value==null){
			return null;
		}
		long longvalue=Math.round(value);
		return new Long(longvalue).intValue();
	}

}
