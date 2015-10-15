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
import org.springframework.web.multipart.MultipartFile;

import com.longcity.modeler.core.AppContext;
import com.longcity.modeler.dao.MoteTaskDao;
import com.longcity.modeler.model.MoteTask;
import com.longcity.modeler.model.TaskPic;
import com.longcity.modeler.service.TaskPicService;
import com.longcity.modeler.util.FileUtil;
import com.longcity.modeler.util.UpYunUtil;
import com.longcity.modeler.validator.Validator;

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
	
	@Resource
	MoteTaskDao moteTaskDao;
	
	
	/**
     * 展示多张照片
     */
	@ResponseBody
    @RequestMapping(value = "showImage")
    public Object showImage(HttpServletRequest request,Integer moteTaskId) throws Exception{
        try{
        	MoteTask moteTask=moteTaskDao.selectByPrimaryKey(moteTaskId);
        	List<TaskPic> picList=taskPicService.showImage(moteTaskId,moteTask.getUserId());
            return dataJson(picList, request);
        }catch(Exception e){
            logger.error("上传多张照片失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }
	
	/**
	 * 模特上传图片
	 * @param moteTaskId
	 * @param image1
	 * @param image2
	 * @param image3
	 * @param image4
	 * @param image5
	 * @param image6
	 */
	@ResponseBody
    @RequestMapping(value = "uploadImage")
	public Object uploadImage(HttpServletRequest request,Integer moteTaskId,MultipartFile image1)throws Exception {
		Validator.validateImageFile(image1);
		
		try{
			if (!FileUtil.isEmpty(image1)) {
				String url = UpYunUtil.upload(image1);
				taskPicService.addImageUrl(moteTaskId,url);
			}
			 return dataJson(true,request);
		}
		catch (Exception e) {
			logger.error("上传多张照片失败.", e);
            return errorJson("服务器异常，请重试.", request);
		}
	}
	
	/**
     * 上传多张照片
     */
	@ResponseBody
    @RequestMapping(value = "addImageUrl")
    public Object addImageUrl(HttpServletRequest request,Integer moteTaskId,String imgUrl) throws Exception{
        try{
        	taskPicService.addImageUrl(moteTaskId,imgUrl);
            return dataJson(true, request);
        }catch(Exception e){
            logger.error("上传多张照片失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }
	
	/**
     * 删除多张照片
     */
	@ResponseBody
    @RequestMapping(value = "removeImageUrl")
    public Object removeImageUrl(HttpServletRequest request,String taskPicIds) throws Exception{
        try{
        	taskPicService.removeImageUrl(taskPicIds);
            return dataJson(true, request);
        }catch(Exception e){
            logger.error("删除多张照片失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }

}
