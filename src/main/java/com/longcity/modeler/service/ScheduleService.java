/**
 * 
 */
package com.longcity.modeler.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.longcity.manage.model.MoteStatistics;
import com.longcity.manage.model.SellerStatistics;
import com.longcity.manage.service.MoteService;
import com.longcity.manage.service.SellerService;
import com.longcity.modeler.constant.RedisContstant;
import com.longcity.modeler.dao.MoteTaskDao;
import com.longcity.modeler.dao.TaskDao;
import com.longcity.modeler.dao.UserDao;
import com.longcity.modeler.enums.MoteTaskStatus;
import com.longcity.modeler.model.MoteTask;
import com.longcity.modeler.model.User;
import com.longcity.modeler.model.vo.MoteTaskVO;
import com.longcity.modeler.util.MoneyUtil;

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
	TaskService taskService;
	
	@Resource
	MoteService moteService;
	
	@Resource
	MoteTaskDao moteTaskDao;
	
	@Resource
	UserDao userDao;
	
	@Resource
	TaskDao taskDao;
	
   @Resource
   private RedisTemplate redisTemplate;
	
	private static Long acceptedTimeOut=30L;//分钟
	
	private static Long returnItemTimeOut=7L;//天
	
	private static Boolean IS_MOTETASK_RUNNING=false;
	
	private static Boolean IS_COUNT_SELLER_RUNNING=false;
	
	private static Boolean IS_COUNT_MOTE_RUNNING=false;
	
	private static Boolean IS_TOP1_MOTE_RUNNING=false;
	
	private static Boolean IS_RETURN_ITEM_RUNNING=false;
	
	
	/**
	 * 处理任务超时
	 */
	public void handleReturnItemTimeOut(){
		
		if(IS_RETURN_ITEM_RUNNING){
			return;
		}
		try{
			IS_RETURN_ITEM_RUNNING=true;
			
			Integer maxId=0;
			while (true) {
				List<MoteTask> moteTaskList=moteTaskDao.getReturnItemList(maxId);
				if(moteTaskList.size()==0){
					break;
				}
				Integer redisTimeOut=(Integer)redisTemplate.opsForValue().get(RedisContstant.MOTE_VERIFY_RETURNITEM_TIMEOUT_KEY);
				if(redisTimeOut!=null){
					returnItemTimeOut=new Long(redisTimeOut);
				}
				
				Calendar calendar=Calendar.getInstance();
				calendar.setTime(new Date());
				for(MoteTask moteTask:moteTaskList){
					Date returnItemTime=moteTask.getReturnItemTime();
					if(returnItemTime!=null){
						//超过7天未确认收货，默认直接收货。
						Calendar accCalendar=Calendar.getInstance();
						accCalendar.setTime(returnItemTime);
						Long timeOut=calendar.getTimeInMillis()-accCalendar.getTimeInMillis();
						//判断是否超时
						if(returnItemTime==null||timeOut>returnItemTimeOut*24*60*60*1000){
							taskService.verifyReturnItem(moteTask.getId());
						}
					}
					maxId=moteTask.getId();
				}
			}
		}catch (Exception e) {
			logger.error("处理任务超时出错",e);
		}finally{
			IS_RETURN_ITEM_RUNNING=false;
		}
		
	}
	
	
	
	
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
				String redisTimeOut=(String)redisTemplate.opsForValue().get(RedisContstant.MOTE_ACCEPT_TIMEOUT_KEY);
				if(StringUtils.isNotBlank(redisTimeOut)){
					acceptedTimeOut=new Long(redisTimeOut);
				}
				
				Calendar calendar=Calendar.getInstance();
				calendar.setTime(new Date());
				for(MoteTask moteTask:moteTaskList){
					Date acceptedTime=moteTask.getAcceptedTime();
					if(acceptedTime!=null){
						//超过30分钟未淘宝下单，状态改为超时
						Calendar accCalendar=Calendar.getInstance();
						accCalendar.setTime(acceptedTime);
						Long timeOut=calendar.getTimeInMillis()-accCalendar.getTimeInMillis();
						//判断是否超时
						if(acceptedTime==null||timeOut>acceptedTimeOut*60*1000){
							moteTaskDao.updateStatus(moteTask.getId(), MoteTaskStatus.follow.getValue());//状态改为关注
							//任务接单数减1
							taskDao.updateAcceptNumber(moteTask.getTaskId(),-1);
						}
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
					Double remindFee=user.getRemindFee();
					//酬金花费
					Integer taskFee=moteTaskDao.getMoteTaskTotalFeeBySellerId(user.getId());
					
					SellerStatistics sellerSta=new SellerStatistics();
					sellerSta.setSellerId(user.getId());
					sellerSta.setProjectNum(taskNum);
					sellerSta.setTaskNum(moteTaskNum);
					sellerSta.setTotalFee(MoneyUtil.double2Int(remindFee));
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
					Double remindFee=user.getRemindFee();
					//收益
					Integer taskFee=moteTaskDao.getMoteTaskTotalFeeByMoteId(user.getId());
					
					MoteStatistics moteSta=new MoteStatistics();
					moteSta.setMoteId(user.getId());
					moteSta.setTaskNum(moteTaskNum);
					moteSta.setRemindFee(MoneyUtil.double2Int(remindFee));
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
