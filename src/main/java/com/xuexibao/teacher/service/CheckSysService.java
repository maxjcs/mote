package com.xuexibao.teacher.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xuexibao.teacher.core.RedisOps;
import com.xuexibao.teacher.dao.FeudPointFeeConfDao;
import com.xuexibao.teacher.model.FeudPointFeeConf;
import com.xuexibao.teacher.util.RedisContstant;
import com.xuexibao.webapi.teacher.client.T_DictService;
import com.xuexibao.webapi.teacher.model.City;

/**
 * 
 * @author feng bin
 *
 */
@Service
public class CheckSysService {
	@Resource
	private RedisOps redisOps;
	@Resource
	T_DictService t_DictService;
	@Resource
	private FeudPointFeeConfDao feudPointFeeConfDao;
	private static Logger logger = LoggerFactory.getLogger(CheckSysService.class);
	public Map<String,String> getSysComponentsStatus(){
		Map<String,String> monitorMap = new HashMap<String,String>();
		
		String redisValue = (String) redisOps.getValue(RedisContstant.SYS_CHECK_KEY);
		logger.info("redisValue:"+redisValue);
		if(redisValue==null){
			redisOps.addKeyValue(RedisContstant.SYS_CHECK_KEY, "ok");
			logger.info("redis set value SYS_CHECK_KEY:");
			redisValue = (String) redisOps.getValue(RedisContstant.SYS_CHECK_KEY);
		}
		if(redisValue ==null){
			redisValue = "error";
		}
		monitorMap.put("redisValue",redisValue);
		String dbValue = null;
		
		 List<FeudPointFeeConf> fc = feudPointFeeConfDao.selectAll();
		 if(fc.size()>0){
			 dbValue = "ok"; 
		 }else{
			 dbValue = "error"; 
		 }
		 monitorMap.put("dbValue",dbValue);
		 String thriftValue = null; 
		List<City> citys =  t_DictService.getCityList();
		if(citys == null){
			thriftValue = "error";
		}else{
			thriftValue = "ok";
		}
		 monitorMap.put("thriftValue",thriftValue);
		return monitorMap;
	}

}
