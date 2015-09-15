/**
 * 
 */
package com.longcity.modeler.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.longcity.modeler.dao.MoteTaskDao;
import com.longcity.modeler.enums.MoteTaskStatus;
import com.longcity.modeler.model.MoteTask;

/**
 * 用于定时任务
 * @author maxjcs
 *
 */
@Service
public class ScheduleService {
	
	private static Logger logger = LoggerFactory.getLogger(ScheduleService.class);
	
	@Resource
	MoteTaskDao moteTaskDao;
	
	private static Integer acceptedTimeOut=30;//分钟
	
	private static Boolean IS_MOTETASK_RUNNING=false;
	
	/**
	 * 处理任务超时
	 */
	public void handleMoteTaskTimeOut(){
		
		if(IS_MOTETASK_RUNNING){
			return;
		}
		try{
			IS_MOTETASK_RUNNING=true;
			
			Integer maxId=0;
			while (true) {
				List<MoteTask> moteTaskList=moteTaskDao.getAcceptList(maxId);
				if(moteTaskList.size()==0){
					break;
				}
				for(MoteTask moteTask:moteTaskList){
					Date acceptedTime=moteTask.getAcceptedTime();
					//超过30分钟未淘宝下单，状态改为超时
					if((new Date().getTime()-acceptedTime.getTime())>acceptedTimeOut*60*1000){
						moteTaskDao.updateStatus(moteTask.getId(), MoteTaskStatus.TimeOut.getValue());//状态改为超时
					}
					maxId=moteTask.getId();
				}
			}
		}catch (Exception e) {
			logger.error("处理任务超时出错",e);
		}finally{
			IS_MOTETASK_RUNNING=false;
		}
		
	}

}
