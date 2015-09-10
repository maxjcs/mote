/**
 * 
 */
package com.longcity.modeler.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
	private static final String BUCKET_NAME = "xuexibao1";
	private static final String OPERATOR_NAME = "xxbadmin";
	private static final String OPERATOR_PWD = "XxbmdkJ1509";
	
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
				return URL + filePath + "!"; //默认保存带感叹号的url，使用时只要加上版本号就行。比如:http://*****.jpg!v6
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return "";
	}
	
	public static void main(String[] args) {
		//getFileDir();
		System.out.println(uploadAvartImg("http://xfsr.91xuexibao.com/21,24570ebac9aab5"));
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
	
	/**
	 * 上传教师历史头像到Upyun
	 * @param imgUrl
	 * @return
	 */
	public static String uploadAvartImg(String imgUrl){
		
		try{
		
		URL url = new URL(imgUrl); 
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();    
        //设置超时间为3秒  
        conn.setConnectTimeout(3*1000);  
        //得到输入流  
        InputStream inputStream = conn.getInputStream();    
        //获取自己数组  
        byte[] getData = readInputStream(inputStream); 
		// 初始化空间
		upyun = new UpYun(BUCKET_NAME, OPERATOR_NAME, OPERATOR_PWD);
		upyun.setDebug(false);
		
		String filename=UUID.randomUUID().toString();
		String extendName="jpg";
		// 要传到upyun后的文件路径,原文件的路径
		String filePath = DIR_ROOT + getFileDir()+"/"+filename+"."+extendName;
		
		boolean success = upyun.writeFile(filePath, getData, true);
		if(success){
			return URL + filePath + "!";
		}else{
			System.out.println("--error:"+imgUrl);
		}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return "";
	}
	
	 /** 
     * 从输入流中获取字节数组 
     * @param inputStream 
     * @return 
     * @throws IOException 
     */  
    public static  byte[] readInputStream(InputStream inputStream) throws IOException {    
        byte[] buffer = new byte[1024];    
        int len = 0;    
        ByteArrayOutputStream bos = new ByteArrayOutputStream();    
        while((len = inputStream.read(buffer)) != -1) {    
            bos.write(buffer, 0, len);    
        }    
        bos.close();    
        return bos.toByteArray();    
    }
	

}
