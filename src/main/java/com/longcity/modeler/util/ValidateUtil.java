package com.longcity.modeler.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class ValidateUtil {
    /**
     * 手机号校验
     */
    public static boolean isMobile(String phoneNumber){
        String reg = "^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1})|(14[0-9]{1}))+\\d{8})$";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(phoneNumber);
        return m.matches();
    }
    
    /**
     * 身份证号校验
     */
    public static boolean isIDCard(String idNumber){
        String reg = "^(\\d{15,15}|\\d{17,17}(x|X|\\d))$";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(idNumber);
        return m.matches();
    }
    
    /**
     * 中文校验
     */
    public static boolean isChinese(String str){
        if(StringUtils.isBlank(str)){
            return false;
        }
        
        String reg = "[\u4e00-\u9fa5]";
        Pattern p = Pattern.compile(reg);
        for(int i=0; i<str.length(); i++){
            String c = str.substring(i, i+1); 
            Matcher m = p.matcher(c);
            if(!m.matches()){
                return false;
            }
        }
        return true;
    }
    
    public static boolean equals(final Object object1, final Object object2) {
        if (object1 == object2) {
            return true;
        }
        if (object1 == null) {
        	if(object2 == null){
        		return true;
        	}else{
        		return false;
        	}
        }else{
        	return object1.equals(object2);
    	}
    }
    
    public static void main(String[] args){
//        String phone = "10810788180";
//        System.out.println(isMobile(phone));
//        
//        String idNumber = "150402188806242424";
//        System.out.println(isIDCard(idNumber));
//        
//        idNumber = "15040218880624242a";
//        System.out.println(isIDCard(idNumber));
//        
//        String str = "中国人";
//        System.out.println(isChinese(str));
    }
}
