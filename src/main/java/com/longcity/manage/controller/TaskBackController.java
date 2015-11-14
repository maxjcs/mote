/**
 * 
 */
package com.longcity.manage.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.longcity.manage.model.param.QueryTaskDetailParamVO;
import com.longcity.manage.model.param.QueryTaskParamVO;
import com.longcity.modeler.dao.TaskDao;
import com.longcity.modeler.dao.UserDao;
import com.longcity.modeler.enums.TaskStatus;
import com.longcity.modeler.model.Task;
import com.longcity.modeler.model.User;
import com.longcity.modeler.service.RedisService;
import com.longcity.modeler.service.TaskService;
import com.longcity.modeler.service.UserService;
import com.longcity.modeler.util.MoneyUtil;

/**
 * @author maxjcs
 *
 */
@Controller
@RequestMapping("back/task")
public class TaskBackController extends BaseController{
	
	   @Resource
	   TaskService taskService;
	   
	   @Resource
	   TaskDao taskDao;
	   
	   @Resource
	   UserDao userDao;
	   
	   @Resource
	   UserService userService;
	   
		@Resource
		private RedisService redisService;
	   
	   
	    @RequestMapping(value = "manage")
	    protected String manage(QueryTaskParamVO paramVO, ModelMap resultMap) {
	    	taskService.queryTaskList(paramVO);
	    	resultMap.addAttribute("resultVO", paramVO);
	        return "task/manage";
	    }
	    
	    @RequestMapping(value = "detail")
	    protected String detail(QueryTaskDetailParamVO paramVO, ModelMap resultMap) {
	    	taskService.queryTaskDetail(paramVO);
	    	Task task=taskService.getDetailByTaskId(paramVO.getTaskId());
	    	User seller=userService.getUserById(task.getUserId());
	    	
	    	resultMap.addAttribute("resultVO", paramVO);
	    	resultMap.addAttribute("task", task);
	    	resultMap.addAttribute("seller", seller);
	        return "task/taskDetail";
	    }
	    
	    @RequestMapping(value = "updateTaskUrl")
	    protected void updateTaskUrl(QueryTaskDetailParamVO paramVO,String url,HttpServletResponse response) {
	    	taskDao.updateTaskUrl(paramVO.getTaskId(), url);
	    	try{
	    		response.sendRedirect("./detail?taskId="+paramVO.getTaskId());
	    	}catch (Exception e) {
	    		
			}
	    }
	    
	    @RequestMapping(value = "approve")
	    @Transactional
	    protected void approve(Integer id, Integer status,HttpServletResponse response) {
	    	taskDao.updateStatus(id, status);
	    	//返回冻结的金额
	    	if(status==TaskStatus.not_passed.getValue()){
	    		Task task=taskDao.selectByPrimaryKey(id);
	    		//打印用户余额
	    		userService.printlogUser(task.getUserId());
	    		Double fee=(task.getPrice()+task.getShotFee())*task.getNumber();
	        	userService.freezeFee(task.getUserId(), -1*MoneyUtil.double2Int(fee));
	        	//打印用户余额
	        	userService.printlogUser(task.getUserId());
	    	}
	    	//更新缓存中的数量
	    	if(TaskStatus.passed.getValue()==status){
				redisService.redisApplyOk(id);
			}
	    	
	    	try{
	    		response.sendRedirect("./detail?taskId="+id);
	    	}catch (Exception e) {
	    		//
			}
	    }
	    
	    

}
