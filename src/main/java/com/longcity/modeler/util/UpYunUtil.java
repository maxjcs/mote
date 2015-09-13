/**
 * 
 */
package com.longcity.modeler.util;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author maxjcs
 *
 */
public class UpYunUtil {
	
	// 运行前先设置好以下三个参数
	private static final String BUCKET_NAME = "qmmt2015";
	private static final String OPERATOR_NAME = "root";
	private static final String OPERATOR_PWD = "Mote2015";
	
	/** 绑定的域名 */
	private static final String URL = "http://" + BUCKET_NAME + ".b0.upaiyun.com";

	/** 根目录 */
	private static final String DIR_ROOT = "/";
	
	private static UpYun upyun = null;
	
	/**
	 * 上传图片到又拍云
	 * @param file
	 * @return
	 */
	public static String upload(MultipartFile file){
		
		// 初始化空间
		upyun = new UpYun(BUCKET_NAME, OPERATOR_NAME, OPERATOR_PWD);
		upyun.setDebug(false);
		
		String filename=UUID.randomUUID().toString();
		
		String extendName=FileUtil.getExtendName(file.getOriginalFilename());
		
		// 要传到upyun后的文件路径,原文件的路径
		String filePath = DIR_ROOT + getFileDir()+"/"+filename+"."+extendName;
		
		try{
			boolean success = upyun.writeFile(filePath, file.getBytes(), true);
			if(success){
				return URL + filePath; 
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 获取文件上传路径
	 * @return
	 */
	public static String getFileDir(){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(new Date());
		String fileDir=calendar.get(Calendar.YEAR)+"/"+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.DAY_OF_MONTH);
		System.out.println(fileDir);
		return fileDir;
	}

}
