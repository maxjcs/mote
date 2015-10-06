/**
 * 
 */
package com.longcity.modeler.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.longcity.modeler.dao.MoteTaskDao;
import com.longcity.modeler.dao.TaskDao;
import com.longcity.modeler.model.MoteTask;
import com.longcity.modeler.model.Task;

/**
 * @author maxjcs
 *
 */
@Service
public class MoteTaskService {
	
	private static Logger logger = LoggerFactory.getLogger(MoteTaskService.class);
	
	@Resource
	MoteTaskDao moteTaskDao;
	
	@Resource
	TaskDao taskDao;
	
	
	public MoteTask selectByPrimarykey(Integer id){
		return moteTaskDao.selectByPrimaryKey(id);
	}
	
	/**
	 * 
	 * @param userId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getAcceptedTaskList(Integer userId,Integer pageNo,Integer pageSize){
		Map paramMap=new HashMap();
		paramMap.put("userId", userId);
		paramMap.put("start", (pageNo-1)*pageSize);
		paramMap.put("pageSize", pageSize);
		
		Integer totalSize=moteTaskDao.countAcceptedTaskList(paramMap);
		
		List<Task> taskList=new ArrayList<Task>();
		if(totalSize>0){
			taskList=moteTaskDao.getAcceptedTaskList(paramMap);
		}
		
		Map resultMap=new HashMap();
		resultMap.put("pageNo", pageNo);
		resultMap.put("pageSize", pageSize);
		resultMap.put("totalSize", totalSize);
		resultMap.put("dataList", taskList);
		
		return resultMap;
	}

}
