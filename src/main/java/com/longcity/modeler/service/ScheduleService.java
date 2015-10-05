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

import com.longcity.manage.model.MoteStatistics;
import com.longcity.manage.model.SellerStatistics;
import com.longcity.manage.service.MoteService;
import com.longcity.manage.service.SellerService;
import com.longcity.modeler.dao.MoteTaskDao;
import com.longcity.modeler.dao.TaskDao;
import com.longcity.modeler.dao.UserDao;
import com.longcity.modeler.enums.MoteTaskStatus;
import com.longcity.modeler.model.MoteTask;
import com.longcity.modeler.model.User;
import com.longcity.modeler.model.vo.MoteTaskVO;

/**
 * 用于定时任务
 * @author maxjcs
 *
 */
@Service
public class ScheduleService {
	
	private static Logger logger = LoggerFactory.getLogger(ScheduleService.class);
	
	@Resource
	SellerService sellerService;
	
	@Resource
	RedisService redisService;
	
	@Resource
	MoteService moteService;
	
	@Resource
	MoteTaskDao moteTaskDao;
	
	@Resource
	UserDao userDao;
	
	@Resource
	TaskDao taskDao;
	
	private static Integer acceptedTimeOut=30*24*60;//分钟
	
	private static Boolean IS_MOTETASK_RUNNING=false;
	
	private static Boolean IS_COUNT_SELLER_RUNNING=false;
	
	private static Boolean IS_COUNT_MOTE_RUNNING=false;
	
	private static Boolean IS_TOP1_MOTE_RUNNING=false;
	
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
					if(acceptedTime==null||(new Date().getTime()-acceptedTime.getTime())>acceptedTimeOut*60*1000){
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
	
	/**
	 * 统计商家信息
	 */
	public void handleCountSeller(){
		
		if(IS_COUNT_SELLER_RUNNING){
			return;
		}
		try{
			IS_COUNT_SELLER_RUNNING=true;
			
			Integer maxId=0;
			while (true) {
				List<User> userList=userDao.querySellerList(maxId);
				if(userList.size()==0){
					break;
				}
				for(User user:userList){
					//项目数
					Integer taskNum=taskDao.getTaskNumBySellerId(user.getId());
					//任务数
					Integer moteTaskNum=moteTaskDao.getMoteTaskNumBySellerId(user.getId());
					//预存款
					Integer remindFee=user.getRemindFee();
					//酬金花费
					Integer taskFee=moteTaskDao.getMoteTaskTotalFeeBySellerId(user.getId());
					
					SellerStatistics sellerSta=new SellerStatistics();
					sellerSta.setSellerId(user.getId());
					sellerSta.setProjectNum(taskNum);
					sellerSta.setTaskNum(moteTaskNum);
					sellerSta.setTotalFee(remindFee);
					sellerSta.setTaskFee(taskFee);
					sellerService.save(sellerSta);
					
					maxId=user.getId();
				}
			}
		}catch (Exception e) {
			logger.error("处理任务超时出错",e);
		}finally{
			IS_COUNT_SELLER_RUNNING=false;
		}
		
	}
	
	/**
	 * 统计模特信息
	 */
	public void handleCountMote(){
		
		if(IS_COUNT_MOTE_RUNNING){
			return;
		}
		try{
			IS_COUNT_MOTE_RUNNING=true;
			
			Integer maxId=0;
			while (true) {
				List<User> userList=userDao.queryMoteList(maxId);
				if(userList.size()==0){
					break;
				}
				for(User user:userList){
					//任务数
					Integer moteTaskNum=moteTaskDao.getMoteTaskNumByMoteId(user.getId());
					//预存款
					Integer remindFee=user.getRemindFee();
					//收益
					Integer taskFee=moteTaskDao.getMoteTaskTotalFeeByMoteId(user.getId());
					
					MoteStatistics moteSta=new MoteStatistics();
					moteSta.setMoteId(user.getId());
					moteSta.setTaskNum(moteTaskNum);
					moteSta.setRemindFee(remindFee);
					moteSta.setTaskFee(taskFee);
					moteService.save(moteSta);
					
					maxId=user.getId();
				}
			}
		}catch (Exception e) {
			logger.error("处理任务超时出错",e);
		}finally{
			IS_COUNT_MOTE_RUNNING=false;
		}
		
	}
	
	/**
	 * top1模特
	 */
	public void handleTop1Mote(){
		if(IS_TOP1_MOTE_RUNNING){
			return;
		}
		try{
			IS_TOP1_MOTE_RUNNING=true;
			MoteTaskVO vo=moteTaskDao.getTop1Mote();
			if(vo!=null){
				redisService.redisTop1Mote(vo.getNickname(),vo.getTotalTaskFee());
			}
		}catch (Exception e) {
			logger.error("处理任务超时出错",e);
		}finally{
			IS_TOP1_MOTE_RUNNING=false;
		}
	}

}
