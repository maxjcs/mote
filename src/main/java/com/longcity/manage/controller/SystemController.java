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
   private RedisTemplate stringRedisTemplate;
   
   @Resource
   MessageDao messageDao;
	
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "index")
    protected String index( ModelMap resultMap) {
    	//模特相关
    	resultMap.addAttribute("moteTotalNum", stringRedisTemplate.opsForHash().get(RedisContstant.MOTE_HKEY, RedisContstant.MOTE_TOTAL_NUM_KEY));
    	resultMap.addAttribute("moteToDayNum", stringRedisTemplate.opsForHash().get(RedisContstant.MOTE_HKEY, RedisContstant.MOTE_TOTAL_NUM_KEY+DateUtils.getDateString()));
    	resultMap.addAttribute("moteYesterDayNum", stringRedisTemplate.opsForHash().get(RedisContstant.MOTE_HKEY, RedisContstant.MOTE_TOTAL_NUM_KEY+DateUtils.getYesterdayDateString()));
        //商家相关
    	resultMap.addAttribute("sellerTotalNum", stringRedisTemplate.opsForHash().get(RedisContstant.SELLER_HKEY, RedisContstant.SELLER_TOTAL_NUM_KEY));
    	resultMap.addAttribute("sellerToDayNum", stringRedisTemplate.opsForHash().get(RedisContstant.SELLER_HKEY, RedisContstant.SELLER_TOTAL_NUM_KEY+DateUtils.getDateString()));
    	resultMap.addAttribute("sellerYesterDayNum", stringRedisTemplate.opsForHash().get(RedisContstant.SELLER_HKEY, RedisContstant.SELLER_TOTAL_NUM_KEY+DateUtils.getYesterdayDateString()));
    	//项目相关
    	resultMap.addAttribute("taskTotalNum", stringRedisTemplate.opsForHash().get(RedisContstant.TASK_HKEY, RedisContstant.TASK_TOTAL_NUM_KEY));
    	resultMap.addAttribute("taskTotalMoney", stringRedisTemplate.opsForHash().get(RedisContstant.TASK_HKEY, RedisContstant.TASK_TOTAL_MONEY_KEY));
    	resultMap.addAttribute("taskTotalShotFee", stringRedisTemplate.opsForHash().get(RedisContstant.TASK_HKEY, RedisContstant.TASK_TOTAL_SHOT_FEE_KEY));
    	//任务相关
    	resultMap.addAttribute("moteTaskTotalNum", stringRedisTemplate.opsForHash().get(RedisContstant.TASK_HKEY, RedisContstant.MOTETASK_TOTAL_NUM_KEY));
    	resultMap.addAttribute("moteTaskFinishNum", stringRedisTemplate.opsForHash().get(RedisContstant.TASK_HKEY, RedisContstant.MOTETASK_FINISH_NUM_KEY));
    	resultMap.addAttribute("moteTaskPerformNum", stringRedisTemplate.opsForHash().get(RedisContstant.TASK_HKEY, RedisContstant.MOTETASK_PERFORM_NUM_KEY));
    	resultMap.addAttribute("moteItemNum", stringRedisTemplate.opsForHash().get(RedisContstant.TASK_HKEY, RedisContstant.MOTE_ITEM_NUM_KEY));
    	//昨日发布并审核的项目数
    	resultMap.addAttribute("taskTodayNum", stringRedisTemplate.opsForHash().get(RedisContstant.TASK_HKEY, RedisContstant.TASK_DAY_NUM_KEY+DateUtils.getDateString()));
    	resultMap.addAttribute("moteTaskTodayNum", stringRedisTemplate.opsForHash().get(RedisContstant.TASK_HKEY, RedisContstant.MOTETASK_DAY_NUM_KEY+DateUtils.getDateString()));
    	resultMap.addAttribute("taskYesterdayNum", stringRedisTemplate.opsForHash().get(RedisContstant.TASK_HKEY, RedisContstant.TASK_DAY_NUM_KEY+DateUtils.getYesterdayDateString()));
    	resultMap.addAttribute("moteTaskYesterdayNum", stringRedisTemplate.opsForHash().get(RedisContstant.TASK_HKEY, RedisContstant.MOTETASK_DAY_NUM_KEY+DateUtils.getYesterdayDateString()));
    	//top1 mote
    	resultMap.addAttribute("top1_nickname", stringRedisTemplate.opsForHash().get(RedisContstant.TASK_HKEY, RedisContstant.TOP1_MOTE_NAME_KEY));
    	resultMap.addAttribute("top1_total_fee", stringRedisTemplate.opsForHash().get(RedisContstant.TASK_HKEY, RedisContstant.TOP1_MOTE_FEE_KEY));
    	
    	return "system/index";
    }
    
	@RequestMapping(value = "systemControl")
    protected String systemControl( ModelMap resultMap) {
    	String moteAcceptNum=(String)stringRedisTemplate.opsForValue().get(RedisContstant.MOTE_ACCEPT_NUM_KEY);
    	String acceptTimeOut=(String)stringRedisTemplate.opsForValue().get(RedisContstant.MOTE_ACCEPT_TIMEOUT_KEY);
    	String verifyReturnItemTimeOut=(String)stringRedisTemplate.opsForValue().get(RedisContstant.MOTE_VERIFY_RETURNITEM_TIMEOUT_KEY);
    	
    	SystemConfig systemConfig = new SystemConfig();
    	systemConfig.setAcceptTimeOut(Integer.parseInt(acceptTimeOut));
    	systemConfig.setMoteAcceptNum(Integer.parseInt(moteAcceptNum));
    	systemConfig.setVerifyReturnItemTimeOut(Integer.parseInt(verifyReturnItemTimeOut));
    	resultMap.addAttribute("config", systemConfig);
    	return "system/systemControl";
    }
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "save")
    protected void save(SystemConfig systemConfig,HttpServletResponse response) {
		if(systemConfig!=null){
			if(systemConfig.getMoteAcceptNum()!=null){
				stringRedisTemplate.opsForValue().set(RedisContstant.MOTE_ACCEPT_NUM_KEY,String.valueOf(systemConfig.getMoteAcceptNum()));
			}
			if(systemConfig.getAcceptTimeOut()!=null){
				stringRedisTemplate.opsForValue().set(RedisContstant.MOTE_ACCEPT_TIMEOUT_KEY,String.valueOf(systemConfig.getAcceptTimeOut()));
			}
			if(systemConfig.getVerifyReturnItemTimeOut()!=null){
				stringRedisTemplate.opsForValue().set(RedisContstant.MOTE_VERIFY_RETURNITEM_TIMEOUT_KEY,String.valueOf(systemConfig.getVerifyReturnItemTimeOut()));
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
