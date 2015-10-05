/**
 * 
 */
package com.longcity.manage.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.longcity.manage.model.SystemConfig;
import com.longcity.modeler.constant.RedisContstant;
import com.longcity.modeler.dao.MessageDao;
import com.longcity.modeler.model.Message;
import com.longcity.modeler.util.DateUtils;

/**
 * @author maxjcs
 *
 */
@Controller
@RequestMapping("back/system")
public class SystemController extends BaseController{
	
   @Resource
   private RedisTemplate redisTemplate;
   
   @Resource
   MessageDao messageDao;
	
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "index")
    protected String index( ModelMap resultMap) {
    	//模特相关
    	resultMap.addAttribute("moteTotalNum", redisTemplate.opsForHash().get(RedisContstant.MOTE_HKEY, RedisContstant.MOTE_TOTAL_NUM_KEY));
    	resultMap.addAttribute("moteToDayNum", redisTemplate.opsForHash().get(RedisContstant.MOTE_HKEY, RedisContstant.MOTE_TOTAL_NUM_KEY+DateUtils.getDateString()));
    	resultMap.addAttribute("moteYesterDayNum", redisTemplate.opsForHash().get(RedisContstant.MOTE_HKEY, RedisContstant.MOTE_TOTAL_NUM_KEY+DateUtils.getYesterdayDateString()));
        //商家相关
    	resultMap.addAttribute("sellerTotalNum", redisTemplate.opsForHash().get(RedisContstant.SELLER_HKEY, RedisContstant.SELLER_TOTAL_NUM_KEY));
    	resultMap.addAttribute("sellerToDayNum", redisTemplate.opsForHash().get(RedisContstant.SELLER_HKEY, RedisContstant.SELLER_TOTAL_NUM_KEY+DateUtils.getDateString()));
    	resultMap.addAttribute("sellerYesterDayNum", redisTemplate.opsForHash().get(RedisContstant.SELLER_HKEY, RedisContstant.SELLER_TOTAL_NUM_KEY+DateUtils.getYesterdayDateString()));
    	//项目相关
    	resultMap.addAttribute("taskTotalNum", redisTemplate.opsForHash().get(RedisContstant.TASK_HKEY, RedisContstant.TASK_TOTAL_NUM_KEY));
    	resultMap.addAttribute("taskTotalMoney", redisTemplate.opsForHash().get(RedisContstant.TASK_HKEY, RedisContstant.TASK_TOTAL_MONEY_KEY));
    	resultMap.addAttribute("taskTotalShotFee", redisTemplate.opsForHash().get(RedisContstant.TASK_HKEY, RedisContstant.TASK_TOTAL_SHOT_FEE_KEY));
    	//任务相关
    	resultMap.addAttribute("moteTaskTotalNum", redisTemplate.opsForHash().get(RedisContstant.TASK_HKEY, RedisContstant.MOTETASK_TOTAL_NUM_KEY));
    	resultMap.addAttribute("moteTaskFinishNum", redisTemplate.opsForHash().get(RedisContstant.TASK_HKEY, RedisContstant.MOTETASK_FINISH_NUM_KEY));
    	resultMap.addAttribute("moteTaskPerformNum", redisTemplate.opsForHash().get(RedisContstant.TASK_HKEY, RedisContstant.MOTETASK_PERFORM_NUM_KEY));
    	resultMap.addAttribute("moteItemNum", redisTemplate.opsForHash().get(RedisContstant.TASK_HKEY, RedisContstant.MOTE_ITEM_NUM_KEY));
    	//昨日发布并审核的项目数
    	resultMap.addAttribute("taskTodayNum", redisTemplate.opsForHash().get(RedisContstant.TASK_HKEY, RedisContstant.TASK_DAY_NUM_KEY+DateUtils.getDateString()));
    	resultMap.addAttribute("moteTaskTodayNum", redisTemplate.opsForHash().get(RedisContstant.TASK_HKEY, RedisContstant.MOTETASK_DAY_NUM_KEY+DateUtils.getDateString()));
    	resultMap.addAttribute("taskYesterdayNum", redisTemplate.opsForHash().get(RedisContstant.TASK_HKEY, RedisContstant.TASK_DAY_NUM_KEY+DateUtils.getYesterdayDateString()));
    	resultMap.addAttribute("moteTaskYesterdayNum", redisTemplate.opsForHash().get(RedisContstant.TASK_HKEY, RedisContstant.MOTETASK_DAY_NUM_KEY+DateUtils.getYesterdayDateString()));
    	//top1 mote
    	resultMap.addAttribute("top1_nickname", redisTemplate.opsForHash().get(RedisContstant.TASK_HKEY, RedisContstant.TOP1_MOTE_NAME_KEY));
    	resultMap.addAttribute("top1_total_fee", redisTemplate.opsForHash().get(RedisContstant.TASK_HKEY, RedisContstant.TOP1_MOTE_FEE_KEY));
    	
    	return "system/index";
    }
    
	@RequestMapping(value = "systemControl")
    protected String systemControl( ModelMap resultMap) {
    	Integer moteAcceptNum=(Integer)redisTemplate.opsForValue().get(RedisContstant.MOTE_ACCEPT_NUM_KEY);
    	Integer acceptTimeOut=(Integer)redisTemplate.opsForValue().get(RedisContstant.MOTE_ACCEPT_TIMEOUT_KEY);
    	
    	SystemConfig systemConfig = new SystemConfig();
    	systemConfig.setAcceptTimeOut(acceptTimeOut);
    	systemConfig.setMoteAcceptNum(moteAcceptNum);
    	
    	resultMap.addAttribute("config", systemConfig);
    	return "system/systemControl";
    }
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "save")
    protected void save(SystemConfig systemConfig,HttpServletResponse response) {
		if(systemConfig!=null){
			if(systemConfig.getMoteAcceptNum()!=null){
				redisTemplate.opsForValue().set(RedisContstant.MOTE_ACCEPT_NUM_KEY,systemConfig.getMoteAcceptNum());
			}
			if(systemConfig.getAcceptTimeOut()!=null){
				redisTemplate.opsForValue().set(RedisContstant.MOTE_ACCEPT_TIMEOUT_KEY,systemConfig.getAcceptTimeOut());
			}
			try {
				response.sendRedirect("./systemControl");
			} catch (IOException e) {
			}
		}
    }
    
	@RequestMapping(value = "message")
    protected String message( ModelMap resultMap) {
    	return "system/message";
    }
	
	@RequestMapping(value = "sendMessage")
    protected String sendMessage(Message record, ModelMap resultMap) {
		messageDao.insert(record);
    	return "system/message";
    }
    
}
