/**
 * 
 */
package com.longcity.modeler.util;

import java.math.BigDecimal;

import com.longcity.modeler.model.Task;

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
		if(value==null){
			return 0.0;
		}
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
	
	/**
	 * 转换成元
	 * @param task
	 */
	public static void convertTaskMoney(Task task){
		if(task.getPrice()!=null&&task.getPrice()>0){
			task.setPrice(MoneyUtil.fen2Yuan(task.getPrice()));
		}
		if(task.getShotFee()!=null&&task.getShotFee()>0){
			task.setShotFee(MoneyUtil.fen2Yuan(task.getShotFee()));
		}
		if(task.getTotalFee()!=null&&task.getTotalFee()>0){
			task.setTotalFee(MoneyUtil.fen2Yuan(task.getTotalFee()));
		}
	}

}
