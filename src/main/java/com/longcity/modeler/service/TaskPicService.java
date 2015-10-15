package com.longcity.modeler.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.longcity.modeler.dao.MoteCardDao;
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

	@Resource
	private TaskService taskService;
	
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
	public void  addImageUrl(Integer moteTaskId,String imgUrl){
		MoteTask moteTask=moteTaskDao.selectByPrimaryKey(moteTaskId);
		TaskPic tasckPic=new TaskPic();
		tasckPic.setImgUrl(imgUrl);
		tasckPic.setMoteTaskId(moteTaskId);
		tasckPic.setSort(0);
		tasckPic.setUserId(moteTask.getUserId());
		tasckPic.setTaskId(moteTask.getTaskId());
		taskPicDao.insert(tasckPic);
		int count=taskPicDao.getPicNumByMoteTaskId(moteTaskId);
		if(count>=6){
			taskService.uploadImg(moteTaskId);
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
