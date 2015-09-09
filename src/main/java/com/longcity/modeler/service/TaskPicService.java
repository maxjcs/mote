package com.longcity.modeler.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.longcity.modeler.dao.MoteTaskDao;
import com.longcity.modeler.dao.TaskPicDao;
import com.longcity.modeler.model.MoteTask;
import com.longcity.modeler.model.TaskPic;

/**
 * 
 * @author maxjcs
 *
 */
@Service
public class TaskPicService {
	
	private static Logger logger = LoggerFactory.getLogger(TaskPicService.class);
	
	@Resource
	TaskPicDao taskPicDao;
	
	@Resource
	private MoteTaskDao moteTaskDao;
	
	/**
	 * 展示图片
	 * @param moteTaskId
	 * @param imgUrls
	 */
	public List<TaskPic>  showImage(Integer moteTaskId,Integer userId){
		List<TaskPic> picList=taskPicDao.queryMoteTask(moteTaskId,userId);
		return picList;
	}
	
	/**
	 * 上传图片
	 * @param moteTaskId
	 * @param imgUrls
	 */
	public void  addImageUrl(Integer moteTaskId,String imgUrls){
		MoteTask moteTask=moteTaskDao.selectByPrimaryKey(moteTaskId);
		if(StringUtils.isNotBlank(imgUrls)){
			String[] urls=imgUrls.split(",");
			for(String url:urls){
				TaskPic tasckPic=new TaskPic();
				tasckPic.setImgUrl(url);
				tasckPic.setMoteTaskId(moteTaskId);
				tasckPic.setSort(0);
				tasckPic.setUserId(moteTask.getUserId());
				tasckPic.setTaskId(moteTask.getTaskId());
				taskPicDao.insert(tasckPic);
			}
		}
		
	}
	
	/**
	 * 删除图片
	 * @param moteTaskId
	 * @param imgUrls
	 */
	public void  removeImageUrl(String taskPicIds){
		if(StringUtils.isNotBlank(taskPicIds)){
			String[] ids=taskPicIds.split(",");
			for(String id:ids){
				taskPicDao.deleteByPrimaryKey(Integer.parseInt(id));
			}
		}
		
	}

}
