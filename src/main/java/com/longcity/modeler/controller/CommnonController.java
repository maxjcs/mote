/**
 * 
 */
package com.longcity.modeler.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.longcity.modeler.util.UpYunUtil;
import com.longcity.modeler.validator.Validator;

/**
 * @author maxjcs
 *
 */
@Controller
@RequestMapping("common")
public class CommnonController extends AbstractController{
	
	private static Logger logger = LoggerFactory.getLogger(CommnonController.class);
	
	/**
     * 上传图片
     */
	@ResponseBody
    @RequestMapping(value = "upload")
    public Object upload(HttpServletRequest request,MultipartFile image) throws Exception{
		Validator.validateImageFile(image);
        try{
        	String url = UpYunUtil.upload(image);
            return dataJson(url, request);
        }catch(Exception e){
            logger.error("上传图片失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }

}
