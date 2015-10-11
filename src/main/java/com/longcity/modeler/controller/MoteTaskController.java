/**
 * 
 */
package com.longcity.modeler.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.longcity.modeler.core.AppContext;
import com.longcity.modeler.dao.TaskDao;
import com.longcity.modeler.model.MoteTask;
import com.longcity.modeler.model.Task;
import com.longcity.modeler.model.TaskPic;
import com.longcity.modeler.model.User;
import com.longcity.modeler.service.MoteTaskService;
import com.longcity.modeler.service.TaskPicService;
import com.longcity.modeler.service.TaskService;
import com.longcity.modeler.service.UserService;
import com.longcity.modeler.util.MoneyUtil;

/**
 * @author maxjcs
 *
 */
@Controller
@RequestMapping("motetask")
public class MoteTaskController extends AbstractController{
	
	private static Logger logger = LoggerFactory.getLogger(MoteTaskController.class);
	
	@Resource
	MoteTaskService moteTaskService;
	
	@Resource
	TaskService taskService;
	
	@Resource
	UserService userService;
	
	@Resource
	TaskPicService taskPicService;
	
	@Resource
	TaskDao taskDao;
	
	/**
     * 获取模特接单列表
     */
	@ResponseBody
    @RequestMapping(value = "getAcceptedTaskList")
    public Object getAcceptedTaskList(HttpServletRequest request,Integer type,Integer pageNo,Integer pageSize) throws Exception{
        try{
        	Integer userId=AppContext.getUserId();
        	Map resultMap=moteTaskService.getAcceptedTaskList(userId,type, pageNo==null?1:pageNo, pageSize);
            return dataJson(resultMap, request);
        }catch(Exception e){
            logger.error("获取模特接单列表失败.", e);
            return errorJson("获取模特接单列表失败", request);
        }
    }
	
	/**
     * 获取退还商品信息
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ResponseBody
    @RequestMapping(value = "getReturnItemInfo")
    public Object getReturnItemInfo(HttpServletRequest request,Integer moteTaskId) throws Exception{
        try{
        			
        	MoteTask moteTask=moteTaskService.selectByPrimarykey(moteTaskId);
        	//获取
        	Task task=taskDao.selectByPrimaryKey(moteTask.getTaskId());
        	MoneyUtil.convertTaskMoney(task);
        	
        	User seller=userService.getUserById(task.getUserId());
        	
        	List<TaskPic> picList= taskPicService.showImage(moteTaskId, moteTask.getUserId());
        	
        	Map resultMap=new HashMap();
        	resultMap.put("task", task);
        	resultMap.put("address", seller.getAddress());
        	resultMap.put("picList", picList);
        	
            return dataJson(resultMap, request);
        }catch(Exception e){
            logger.error("获取模特接单列表失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }

}
