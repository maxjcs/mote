package com.xuexibao.teacher.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;



/**
 * 类EncodeUtil.java的实现描述：字符串加密解密类
 * 
 */
public class EncryptUtil {

    private static final Logger logger        = Logger.getLogger(EncryptUtil.class);

    private static String       strDefaultKey = "xuexibao";
    
    /**
     * 加密字符串
     * 
     * @param strIn 需加密的字符串
     * @return 加密后的字符串
     * @throws Exception
     */
    public static String encrypt(String strIn) {
        try {
        	SecretKey secretKey = new SecretKeySpec(strDefaultKey.getBytes(), "DES");  
            //encrypt  
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");  
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);          
            byte[] encryptedData = cipher.doFinal(strIn.getBytes());  
            return new String(Hex.encodeHex(encryptedData)); 
        } catch (Exception ex) {
            logger.error("EncodeUtil.encrypt() is error! str=" + strIn, ex);
        }
        return "";
    }
    
    
    /**
     * 解密字节数组
     * 
     * @param arrB 需解密的字节数组
     * @return 解密后的字节数组
     * @throws Exception
     */
    public static String decrypt(String encryptedData) {
        try {
        	SecretKey secretKey = new SecretKeySpec(strDefaultKey.getBytes(), "DES");  
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");  
            cipher.init(Cipher.DECRYPT_MODE, secretKey);  
            byte[] decryptPlainText = cipher.doFinal(Hex.decodeHex(encryptedData.toCharArray()));  
            return new String(decryptPlainText);
        } catch (Exception ex) {
        	ex.printStackTrace();
            logger.error("EncodeUtil.decrypt() is error! encryptedData=" + encryptedData, ex);
        }
        return "";
    }
      
    public static void main(String[] args) {
    	String aaa="91xuexibao.com&t=1000";
    	String encode = EncryptUtil.encrypt(aaa);
    	System.out.println(encode);
    	System.out.println(EncryptUtil.decrypt("bc19b384ff7e1c6b9afe650b6982986a0e01baf061173759a00b96887ab9951b"));
	}
}


