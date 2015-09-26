/**
 * 
 */
package com.longcity.manage.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.longcity.manage.model.param.QueryTaskDetailParamVO;
import com.longcity.manage.model.param.QueryTaskParamVO;
import com.longcity.modeler.model.Task;
import com.longcity.modeler.service.TaskService;

/**
 * @author maxjcs
 *
 */
@Controller
@RequestMapping("back/task")
public class TaskBackController extends BaseController{
	
	   @Resource
	   TaskService taskService;
	   
	   
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
	    	
	    	resultMap.addAttribute("resultVO", paramVO);
	    	resultMap.addAttribute("task", task);
	        return "task/taskDetail";
	    }
	    
	    

}
