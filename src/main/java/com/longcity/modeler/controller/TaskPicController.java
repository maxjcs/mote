/**
 * 
 */
package com.longcity.modeler.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.longcity.modeler.core.AppContext;
import com.longcity.modeler.model.TaskPic;
import com.longcity.modeler.service.TaskPicService;

/**
 * @author maxjcs
 *
 */
@Controller
@RequestMapping("taskPic")
public class TaskPicController extends AbstractController{
	
	private static Logger logger = LoggerFactory.getLogger(TaskPicController.class);
	
	@Resource
	TaskPicService taskPicService;
	
	
	/**
     * 展示多张照片
     */
	@ResponseBody
    @RequestMapping(value = "showImage")
    public Object showImage(HttpServletRequest request,Integer moteTaskId) throws Exception{
        try{
        	Integer userId=AppContext.getUserId();
        	List<TaskPic> picList=taskPicService.showImage(moteTaskId,userId);
            return dataJson(picList, request);
        }catch(Exception e){
            logger.error("上传多张照片失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }
	
	/**
     * 上传多张照片
     */
	@ResponseBody
    @RequestMapping(value = "addImageUrl")
    public Object addImageUrl(HttpServletRequest request,Integer moteTaskId,String imgUrls) throws Exception{
        try{
        	taskPicService.addImageUrl(moteTaskId,imgUrls);
            return dataJson(true, request);
        }catch(Exception e){
            logger.error("上传多张照片失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }
	
	/**
     * 上传多张照片
     */
	@ResponseBody
    @RequestMapping(value = "removeImageUrl")
    public Object removeImageUrl(HttpServletRequest request,String taskPicIds) throws Exception{
        try{
        	taskPicService.removeImageUrl(taskPicIds);
            return dataJson(true, request);
        }catch(Exception e){
            logger.error("上传多张照片失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }

}
