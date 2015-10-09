/**
 * 
 */
package com.longcity.modeler.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.longcity.modeler.dao.CommonConfigDao;
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
	
	@Resource
	CommonConfigDao commonConfigDao;
	
	/**
     * 上传图片
     */
	@ResponseBody
    @RequestMapping(value = "upload")
    public Object upload(HttpServletRequest request,HttpServletResponse response,MultipartFile image) throws Exception{
		Validator.validateImageFile(image);
        try{
        	String url = UpYunUtil.upload(image);
        	response.setContentType("text/html;charset=UTF-8");
            return dataJson(url, request);
        }catch(Exception e){
            logger.error("上传图片失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }
	
	/**
     *    返回h5地址
     */
	@ResponseBody
    @RequestMapping(value = "h5Url")
    public void h5Url(HttpServletRequest request,HttpServletResponse response) throws Exception{
        try{
        	String url = commonConfigDao.getValueByKey("h5Url");
        	response.setContentType("text/html;charset=UTF-8");
        	response.getWriter().println(url);
        }catch(Exception e){
            logger.error("上传图片失败.", e);
        }
    }

}
