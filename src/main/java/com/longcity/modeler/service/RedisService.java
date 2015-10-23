/**
 * 
 */
package com.longcity.modeler.service;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.longcity.modeler.constant.RedisContstant;
import com.longcity.modeler.dao.TaskDao;
import com.longcity.modeler.enums.UserType;
import com.longcity.modeler.model.Task;
import com.longcity.modeler.model.User;
import com.longcity.modeler.util.DateUtils;
import com.longcity.modeler.util.MoneyUtil;

/**
 * @author maxjcs
 *
 */
@Service
public class RedisService {
	
	private static Logger logger = LoggerFactory.getLogger(RedisService.class);
	
	@Resource
	private TaskDao taskDao;
	
	@SuppressWarnings("rawtypes")
   @Resource
   private RedisTemplate stringRedisTemplate;
	
	public void registerUser(User newUser){
		try{
			//mote
			if(UserType.mote.getValue()==newUser.getType()){
				String totalNum=getString(RedisContstant.MOTE_HKEY,RedisContstant.MOTE_TOTAL_NUM_KEY);
				String dayNumKey=RedisContstant.MOTE_DAY_NUM_KEY+DateUtils.getDateString();
				String dayNum=getString(RedisContstant.MOTE_HKEY,dayNumKey);
				if(totalNum==null){
					putString(RedisContstant.MOTE_HKEY,RedisContstant.MOTE_TOTAL_NUM_KEY, 1);
				}else{
					putString(RedisContstant.MOTE_HKEY,RedisContstant.MOTE_TOTAL_NUM_KEY, Integer.parseInt(totalNum)+1);
				}
				if(dayNum==null){
					putString(RedisContstant.MOTE_HKEY,dayNumKey, 1);
				}else{
					putString(RedisContstant.MOTE_HKEY,dayNumKey, Integer.parseInt(dayNum)+1);
				}
			}else if(UserType.seller.getValue()==newUser.getType()){
				String totalNum=getString(RedisContstant.SELLER_HKEY,RedisContstant.SELLER_TOTAL_NUM_KEY);
				String dayNumKey=RedisContstant.SELLER_DAY_NUM_KEY+DateUtils.getDateString();
				String dayNum=getString(RedisContstant.SELLER_HKEY,dayNumKey);
				if(totalNum==null){
					putString(RedisContstant.SELLER_HKEY,RedisContstant.SELLER_TOTAL_NUM_KEY, 1);
				}else{
					putString(RedisContstant.SELLER_HKEY,RedisContstant.SELLER_TOTAL_NUM_KEY, Integer.parseInt(totalNum)+1);
				}
				if(dayNum==null){
					putString(RedisContstant.SELLER_HKEY,dayNumKey, 1);
				}else{
					putString(RedisContstant.SELLER_HKEY,dayNumKey, Integer.parseInt(dayNum)+1);
				}
			}
			
		}catch (Exception e) {
			logger.error("register redis error!",e);
		}
	}
	
	/**
	 * 任务审核通过
	 * @param taskId
	 */
	@SuppressWarnings("unchecked")
	public void redisApplyOk(Integer taskId){
		Task task=taskDao.selectByPrimaryKey(taskId);
		try{
			String totalNum=getString(RedisContstant.TASK_HKEY,RedisContstant.TASK_TOTAL_NUM_KEY);
			String totalMoney=getString(RedisContstant.TASK_HKEY,RedisContstant.TASK_TOTAL_MONEY_KEY);
			String totalShotFee=getString(RedisContstant.TASK_HKEY,RedisContstant.TASK_TOTAL_SHOT_FEE_KEY);
			String dayNumKey=RedisContstant.TASK_DAY_NUM_KEY+DateUtils.getDateString();
			String moteDayNumKey=RedisContstant.MOTETASK_DAY_NUM_KEY+DateUtils.getDateString();
			String dayNum=getString(RedisContstant.TASK_HKEY,dayNumKey);
			String moteDayNum=getString(RedisContstant.TASK_HKEY,moteDayNumKey);
			//总任务数
			if(totalNum==null){
				putString(RedisContstant.TASK_HKEY,RedisContstant.TASK_TOTAL_NUM_KEY, 1);
			}else{
				putString(RedisContstant.TASK_HKEY,RedisContstant.TASK_TOTAL_NUM_KEY, Integer.parseInt(totalNum)+1);
			}
			//每天任务数
			if(dayNum==null){
				putString(RedisContstant.TASK_HKEY,dayNumKey, 1);
				putString(RedisContstant.TASK_HKEY,moteDayNumKey, task.getNumber());
			}else{
				putString(RedisContstant.TASK_HKEY,dayNumKey, Integer.parseInt(moteDayNum)+task.getNumber());
			}
			//商品总金额
			if(totalMoney==null){
				putString(RedisContstant.TASK_HKEY,RedisContstant.TASK_TOTAL_MONEY_KEY, MoneyUtil.double2Int(task.getNumber()*task.getPrice()));
			}else{
				putString(RedisContstant.TASK_HKEY,RedisContstant.TASK_TOTAL_MONEY_KEY, Integer.parseInt(totalMoney)+ MoneyUtil.double2Int(task.getNumber()*task.getPrice()));
			}
			//总拍摄费用
			if(totalShotFee==null){
				putString(RedisContstant.TASK_HKEY,RedisContstant.TASK_TOTAL_SHOT_FEE_KEY, MoneyUtil.double2Int(task.getNumber()*task.getShotFee()));
			}else{
				putString(RedisContstant.TASK_HKEY,RedisContstant.TASK_TOTAL_SHOT_FEE_KEY, Integer.parseInt(totalShotFee)+MoneyUtil.double2Int(task.getNumber()*task.getShotFee()));
			}
		}catch (Exception e) {
			logger.error("redisApplyOk error!",e);
		}
	}
	
	/**
	 * 总任务量
	 * @param taskId
	 */
	@SuppressWarnings("unchecked")
	public void redisNewMoteTask(){
		try{
			String moteTaskTotalNum=getString(RedisContstant.TASK_HKEY,RedisContstant.MOTETASK_TOTAL_NUM_KEY);
			//总任务量
			if(moteTaskTotalNum==null){
				putString(RedisContstant.TASK_HKEY,RedisContstant.MOTETASK_TOTAL_NUM_KEY, 1);
			}else{
				putString(RedisContstant.TASK_HKEY,RedisContstant.MOTETASK_TOTAL_NUM_KEY, Integer.parseInt(moteTaskTotalNum)+1);
			}
		}catch (Exception e) {
			logger.error("redisNewMoteTask error!",e);
		}
	}
	
	/**
	 * 增加正在执行的总任务量
	 * @param taskId
	 */
	public void redisPerformTask(){
		try{
			String moteTaskPerformNum=getString(RedisContstant.TASK_HKEY,RedisContstant.MOTETASK_PERFORM_NUM_KEY);
			//总任务量
			if(moteTaskPerformNum==null){
				putString(RedisContstant.TASK_HKEY,RedisContstant.MOTETASK_PERFORM_NUM_KEY, 1);
			}else{
				putString(RedisContstant.TASK_HKEY,RedisContstant.MOTETASK_PERFORM_NUM_KEY, Integer.parseInt(moteTaskPerformNum)+1);
			}
		}catch (Exception e) {
			logger.error("redisPerformTask error!",e);
		}
	}
	
	/**
	 * 增加已经完成的总任务量
	 * @param taskId
	 */
	public void redisFinishTask(){
		try{
			String moteTaskPerformNum=getString(RedisContstant.TASK_HKEY,RedisContstant.MOTETASK_PERFORM_NUM_KEY);
			String moteTaskFinishNum=getString(RedisContstant.TASK_HKEY,RedisContstant.MOTETASK_FINISH_NUM_KEY);
			
			//已经完成的任务量+1
			if(moteTaskFinishNum==null){
				putString(RedisContstant.TASK_HKEY,RedisContstant.MOTETASK_FINISH_NUM_KEY, 1);
			}else{
				putString(RedisContstant.TASK_HKEY,RedisContstant.MOTETASK_FINISH_NUM_KEY, Integer.parseInt(moteTaskFinishNum)+1);
			}
			//正在执行的任务量-1
			if(moteTaskPerformNum==null||Integer.parseInt(moteTaskPerformNum)<=0){
				putString(RedisContstant.TASK_HKEY,RedisContstant.MOTETASK_PERFORM_NUM_KEY, 0);
			}else{
				putString(RedisContstant.TASK_HKEY,RedisContstant.MOTETASK_PERFORM_NUM_KEY, Integer.parseInt(moteTaskPerformNum)-1);
			}
		}catch (Exception e) {
			logger.error("redisFinishTask error!",e);
		}
	}
	
	/**
	 * 自购的商品数
	 * @param taskId
	 */
	@SuppressWarnings("unchecked")
	public void redisSelfBuyTask(){
		try{
			String moteSelfBuyNum=getString(RedisContstant.TASK_HKEY,RedisContstant.MOTE_ITEM_NUM_KEY);
			//已经完成的任务量+1
			if(moteSelfBuyNum==null){
				putString(RedisContstant.TASK_HKEY,RedisContstant.MOTE_ITEM_NUM_KEY, 1);
			}else{
				putString(RedisContstant.TASK_HKEY,RedisContstant.MOTE_ITEM_NUM_KEY, Integer.parseInt(moteSelfBuyNum)+1);
			}
		}catch (Exception e) {
			logger.error("redisFinishTask error!",e);
		}
	}
	
	/**
	 * top1mote
	 * @param taskId
	 */
	public void redisTop1Mote(String nickname,Integer totalFee){
		try{
			putString(RedisContstant.TASK_HKEY,RedisContstant.TOP1_MOTE_NAME_KEY, nickname);
			putString(RedisContstant.TASK_HKEY,RedisContstant.TOP1_MOTE_FEE_KEY, totalFee);
		}catch (Exception e) {
			logger.error("redisFinishTask error!",e);
		}
	}
	
	
	/**
	 * 保存redis值
	 * @param hkey
	 * @param key
	 * @param value
	 */
	@SuppressWarnings("unchecked")
	public void putString(String hkey,String key,String value){
		stringRedisTemplate.boundHashOps(hkey).put(key,String.valueOf(value));
	}
	
	/**
	 * 保存redis值
	 * @param hkey
	 * @param key
	 * @param value
	 */
	@SuppressWarnings("unchecked")
	public void putString(String hkey,String key,Integer value){
		stringRedisTemplate.boundHashOps(hkey).put(key,String.valueOf(value));
	}
	
//	/**
//	 * 获取redis值
//	 * @param hkey
//	 * @param key
//	 * @param value
//	 */
//	@SuppressWarnings("unchecked")
//	public Integer getInt(String hkey,String key){
//		return (Integer)stringRedisTemplate.boundHashOps(hkey).get(key);
//	}
	
	/**
	 * 获取redis值
	 * @param hkey
	 * @param key
	 * @param value
	 */
	@SuppressWarnings("unchecked")
	public String getString(String hkey,String key){
		return (String)stringRedisTemplate.boundHashOps(hkey).get(key);
	}
	
	
	
	
	/**
	 * redis中记录登陆状态
	 * @param userId
	 * @return
	 */
	public boolean isRedisLogin(Integer userId){
		String value=(String)stringRedisTemplate.opsForValue().get(RedisContstant.USER_LOGIN_IN_KEY+userId);
		if(StringUtils.equals(value, "true")){
			return true;
		}
		return false;
	}
	
	/**
	 * 设置登陆状态
	 * @param userId
	 */
	@SuppressWarnings("unchecked")
	public void redisSetLoginStatus(Integer userId){
		stringRedisTemplate.opsForValue().set(RedisContstant.USER_LOGIN_IN_KEY+userId, "true");
	}
	
	/**
	 * 设置登陆状态
	 * @param userId
	 */
	@SuppressWarnings("unchecked")
	public void redisClearLoginStatus(Integer userId){
		stringRedisTemplate.delete(RedisContstant.USER_LOGIN_IN_KEY+userId);
	}

}
