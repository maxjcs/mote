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
	
	@SuppressWarnings("unchecked")
	public void registerUser(User newUser){
		try{
			//mote
			if(UserType.mote.getValue()==newUser.getType()){
				Integer totalNum=(Integer)stringRedisTemplate.boundHashOps(RedisContstant.MOTE_HKEY).get(RedisContstant.MOTE_TOTAL_NUM_KEY);
				String dayNumKey=RedisContstant.MOTE_DAY_NUM_KEY+DateUtils.getDateString();
				Integer dayNum=(Integer)stringRedisTemplate.boundHashOps(RedisContstant.MOTE_HKEY).get(dayNumKey);
				if(totalNum==null){
					stringRedisTemplate.boundHashOps(RedisContstant.MOTE_HKEY).put(RedisContstant.MOTE_TOTAL_NUM_KEY, 1);
				}else{
					stringRedisTemplate.boundHashOps(RedisContstant.MOTE_HKEY).put(RedisContstant.MOTE_TOTAL_NUM_KEY, totalNum+1);
				}
				if(dayNum==null){
					stringRedisTemplate.boundHashOps(RedisContstant.MOTE_HKEY).put(dayNumKey, 1);
				}else{
					stringRedisTemplate.boundHashOps(RedisContstant.MOTE_HKEY).put(dayNumKey, dayNum+1);
				}
			}else if(UserType.seller.getValue()==newUser.getType()){
				Integer totalNum=(Integer)stringRedisTemplate.boundHashOps(RedisContstant.SELLER_HKEY).get(RedisContstant.SELLER_TOTAL_NUM_KEY);
				String dayNumKey=RedisContstant.SELLER_DAY_NUM_KEY+DateUtils.getDateString();
				Integer dayNum=(Integer)stringRedisTemplate.boundHashOps(RedisContstant.SELLER_HKEY).get(dayNumKey);
				if(totalNum==null){
					stringRedisTemplate.boundHashOps(RedisContstant.SELLER_HKEY).put(RedisContstant.SELLER_TOTAL_NUM_KEY, 1);
				}else{
					stringRedisTemplate.boundHashOps(RedisContstant.SELLER_HKEY).put(RedisContstant.SELLER_TOTAL_NUM_KEY, totalNum+1);
				}
				if(dayNum==null){
					stringRedisTemplate.boundHashOps(RedisContstant.SELLER_HKEY).put(dayNumKey, 1);
				}else{
					stringRedisTemplate.boundHashOps(RedisContstant.SELLER_HKEY).put(dayNumKey, dayNum+1);
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
			Integer totalNum=(Integer)stringRedisTemplate.boundHashOps(RedisContstant.TASK_HKEY).get(RedisContstant.TASK_TOTAL_NUM_KEY);
			Integer totalMoney=(Integer)stringRedisTemplate.boundHashOps(RedisContstant.TASK_HKEY).get(RedisContstant.TASK_TOTAL_MONEY_KEY);
			Integer totalShotFee=(Integer)stringRedisTemplate.boundHashOps(RedisContstant.TASK_HKEY).get(RedisContstant.TASK_TOTAL_SHOT_FEE_KEY);
			String dayNumKey=RedisContstant.TASK_DAY_NUM_KEY+DateUtils.getDateString();
			String moteDayNumKey=RedisContstant.MOTETASK_DAY_NUM_KEY+DateUtils.getDateString();
			Integer dayNum=(Integer)stringRedisTemplate.boundHashOps(RedisContstant.TASK_HKEY).get(dayNumKey);
			Integer moteDayNum=(Integer)stringRedisTemplate.boundHashOps(RedisContstant.TASK_HKEY).get(moteDayNumKey);
			//总任务数
			if(totalNum==null){
				stringRedisTemplate.boundHashOps(RedisContstant.TASK_HKEY).put(RedisContstant.TASK_TOTAL_NUM_KEY, 1);
			}else{
				stringRedisTemplate.boundHashOps(RedisContstant.TASK_HKEY).put(RedisContstant.TASK_TOTAL_NUM_KEY, totalNum+1);
			}
			//每天任务数
			if(dayNum==null){
				stringRedisTemplate.boundHashOps(RedisContstant.TASK_HKEY).put(dayNumKey, 1);
				stringRedisTemplate.boundHashOps(RedisContstant.TASK_HKEY).put(moteDayNumKey, task.getNumber());
			}else{
				stringRedisTemplate.boundHashOps(RedisContstant.TASK_HKEY).put(dayNumKey, moteDayNum+task.getNumber());
			}
			//商品总金额
			if(totalMoney==null){
				stringRedisTemplate.boundHashOps(RedisContstant.TASK_HKEY).put(RedisContstant.TASK_TOTAL_MONEY_KEY, task.getNumber()*task.getPrice());
			}else{
				stringRedisTemplate.boundHashOps(RedisContstant.TASK_HKEY).put(RedisContstant.TASK_TOTAL_MONEY_KEY, totalMoney+task.getNumber()*task.getPrice());
			}
			//总拍摄费用
			if(totalShotFee==null){
				stringRedisTemplate.boundHashOps(RedisContstant.TASK_HKEY).put(RedisContstant.TASK_TOTAL_MONEY_KEY, task.getNumber()*task.getShotFee());
			}else{
				stringRedisTemplate.boundHashOps(RedisContstant.TASK_HKEY).put(RedisContstant.TASK_TOTAL_MONEY_KEY, totalShotFee+task.getNumber()*task.getShotFee());
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
			Integer moteTaskTotalNum=(Integer)stringRedisTemplate.boundHashOps(RedisContstant.TASK_HKEY).get(RedisContstant.MOTETASK_TOTAL_NUM_KEY);
			//总任务量
			if(moteTaskTotalNum==null){
				stringRedisTemplate.boundHashOps(RedisContstant.TASK_HKEY).put(RedisContstant.MOTETASK_TOTAL_NUM_KEY, 1);
			}else{
				stringRedisTemplate.boundHashOps(RedisContstant.TASK_HKEY).put(RedisContstant.MOTETASK_TOTAL_NUM_KEY, moteTaskTotalNum+1);
			}
		}catch (Exception e) {
			logger.error("redisNewMoteTask error!",e);
		}
	}
	
	/**
	 * 增加正在执行的总任务量
	 * @param taskId
	 */
	@SuppressWarnings("unchecked")
	public void redisPerformTask(){
		try{
			Integer moteTaskPerformNum=(Integer)stringRedisTemplate.boundHashOps(RedisContstant.TASK_HKEY).get(RedisContstant.MOTETASK_PERFORM_NUM_KEY);
			//总任务量
			if(moteTaskPerformNum==null){
				stringRedisTemplate.boundHashOps(RedisContstant.TASK_HKEY).put(RedisContstant.MOTETASK_PERFORM_NUM_KEY, 1);
			}else{
				stringRedisTemplate.boundHashOps(RedisContstant.TASK_HKEY).put(RedisContstant.MOTETASK_PERFORM_NUM_KEY, moteTaskPerformNum+1);
			}
		}catch (Exception e) {
			logger.error("redisPerformTask error!",e);
		}
	}
	
	/**
	 * 增加已经完成的总任务量
	 * @param taskId
	 */
	@SuppressWarnings("unchecked")
	public void redisFinishTask(){
		try{
			Integer moteTaskPerformNum=(Integer)stringRedisTemplate.boundHashOps(RedisContstant.TASK_HKEY).get(RedisContstant.MOTETASK_PERFORM_NUM_KEY);
			Integer moteTaskFinishNum=(Integer)stringRedisTemplate.boundHashOps(RedisContstant.TASK_HKEY).get(RedisContstant.MOTETASK_FINISH_NUM_KEY);
			
			//已经完成的任务量+1
			if(moteTaskFinishNum==null){
				stringRedisTemplate.boundHashOps(RedisContstant.TASK_HKEY).put(RedisContstant.MOTETASK_FINISH_NUM_KEY, 1);
			}else{
				stringRedisTemplate.boundHashOps(RedisContstant.TASK_HKEY).put(RedisContstant.MOTETASK_FINISH_NUM_KEY, moteTaskFinishNum+1);
			}
			//正在执行的任务量-1
			if(moteTaskPerformNum==null||moteTaskPerformNum<=0){
				stringRedisTemplate.boundHashOps(RedisContstant.TASK_HKEY).put(RedisContstant.MOTETASK_FINISH_NUM_KEY, 0);
			}else{
				stringRedisTemplate.boundHashOps(RedisContstant.TASK_HKEY).put(RedisContstant.MOTETASK_FINISH_NUM_KEY, moteTaskPerformNum-1);
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
			Integer moteSelfBuyNum=(Integer)stringRedisTemplate.boundHashOps(RedisContstant.TASK_HKEY).get(RedisContstant.MOTE_ITEM_NUM_KEY);
			//已经完成的任务量+1
			if(moteSelfBuyNum==null){
				stringRedisTemplate.boundHashOps(RedisContstant.TASK_HKEY).put(RedisContstant.MOTE_ITEM_NUM_KEY, 1);
			}else{
				stringRedisTemplate.boundHashOps(RedisContstant.TASK_HKEY).put(RedisContstant.MOTE_ITEM_NUM_KEY, moteSelfBuyNum+1);
			}
		}catch (Exception e) {
			logger.error("redisFinishTask error!",e);
		}
	}
	
	/**
	 * top1mote
	 * @param taskId
	 */
	@SuppressWarnings("unchecked")
	public void redisTop1Mote(String nickname,Integer totalFee){
		try{
			stringRedisTemplate.boundHashOps(RedisContstant.TASK_HKEY).put(RedisContstant.TOP1_MOTE_NAME_KEY, nickname);
			stringRedisTemplate.boundHashOps(RedisContstant.TASK_HKEY).put(RedisContstant.TOP1_MOTE_FEE_KEY, totalFee);
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
	public void put(String hkey,String key,Object value){
		stringRedisTemplate.boundHashOps(hkey).put(key,String.valueOf(value));
	}
	
	/**
	 * 获取redis值
	 * @param hkey
	 * @param key
	 * @param value
	 */
	@SuppressWarnings("unchecked")
	public Integer getInt(String hkey,String key,Integer type){
		return (Integer)stringRedisTemplate.boundHashOps(hkey).get(key);
	}
	
	/**
	 * 获取redis值
	 * @param hkey
	 * @param key
	 * @param value
	 */
	@SuppressWarnings("unchecked")
	public String getInt(String hkey,String key,String type){
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
