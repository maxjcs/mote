/**
 * 
 */
package com.xuexibao.teacher.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author maxjcs
 *
 */
public class OcrUtil {
	
	private static Logger logger = LoggerFactory.getLogger(OcrUtil.class);
	
    
    public static String uploadToBatch(File file) throws HttpException, IOException {
		String url = CommonConstant.UPLOAD_RECOG_URL; //"http://imgapi02.91xuexibao.com/imgapi02";
        PostMethod filePost = new PostMethod(url);
        Part[] parts = {
            new FilePart(file.getName(), file),
            new StringPart("pushapi", "pushapi3.91xuexibao.com"),
            new StringPart("ver_client", "2")
        };
        
        filePost.setRequestEntity(new MultipartRequestEntity(parts, filePost.getParams()));

        filePost.setRequestHeader("cookie", "liveaa_club=b906b62db68edc6326deba4e10d8999e335824a780fa9b7aee347ce24c27d3a88b002fdc068611c835be46471b29b420d2c241eeb481485d441c5cdf5b52e7d45b360032e430d6e1952165ea0a7f0f2d600a3c07536d060ec9564f66faf370415de354f3a46c48de7009aa80b276c0e92be97552971e8bf642f3db427dda7f04; Max-Age=2592000; Path=/; Expires=Tue, 19 Aug 2014 05: 24:43 GMT");
           
        HttpClient client = new HttpClient();
        client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
        int status = client.executeMethod(filePost);
        if (200 == status) {
            String responseStr = filePost.getResponseBodyAsString();
            if(StringUtils.isNotBlank(responseStr)){
                Map responseMap = GsonUtil.json2Map(responseStr);
                return responseMap.get("image_id").toString();
            }
        }
        return null;
	}
    
    private static Map  getRecogeResult(String image_id){
		PostMethod postMethod = new UTF8PostMethod(CommonConstant.RECOG_URL);//"http://service02.91xuexibao.com/api/srv/search/qimage2"
		 Map responseMap=new HashMap();
		try{
			HttpClient httpclient = new HttpClient();
			NameValuePair[] data = {new NameValuePair("image_id", image_id)};
			postMethod.setRequestBody(data);
			postMethod.getParams().setParameter(
					"http.protocol.cookie-policy",CookiePolicy.BROWSER_COMPATIBILITY);
			
		    int statusCode = httpclient.executeMethod(postMethod);
		    if(statusCode != HttpStatus.SC_OK){
		    	logger.error("httpclient status code error,statusCode:", statusCode);
		    	return null;
		    }

		    InputStream responseBody = postMethod.getResponseBodyAsStream();
		    BufferedReader reader = new BufferedReader(new InputStreamReader(responseBody,"utf-8"));
		    String line = reader.readLine();
		    String all_line = "";
		    while(line != null){
		    	all_line += line;
		    	line = reader.readLine();		 
		    }
		    System.out.println(all_line);
		    responseMap = GsonUtil.json2Map(all_line);
		}
		catch (Exception e) {
			  logger.error("getRecogeResult exception, e: ", e);
			  e.printStackTrace();
		}finally{
			  postMethod.releaseConnection();
		}
		
		return responseMap;
	}
    
    public static void main(String[] args) {
		File file= new File("/Users/maxjcs/Desktop/test.png");
		String imgId="5cd3b650-0433-11e5-810a-110d4593dc02";
		try{
			imgId=uploadToBatch(file);
			System.out.println(imgId);
			Map map = getRecogeResult(imgId);
			System.out.println(map);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}


}
