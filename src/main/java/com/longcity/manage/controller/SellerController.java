/**
 * 
 */
package com.longcity.manage.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.longcity.manage.model.param.QuerySellerDetailParamVO;
import com.longcity.manage.model.param.QuerySellerParamVO;
import com.longcity.manage.model.param.QueryTaskDetailParamVO;
import com.longcity.manage.service.SellerService;
import com.longcity.modeler.model.Task;
import com.longcity.modeler.model.User;
import com.longcity.modeler.service.TaskService;
import com.longcity.modeler.service.UserService;

/**
 * @author maxjcs
 *
 */
@Controller
@RequestMapping("back/seller")
public class SellerController extends BaseController{
	
   @Resource
   SellerService sellerService;
   @Resource
   UserService userService;
   @Resource
   TaskService taskService;
	
    @RequestMapping(value = "list")
    protected String list(QuerySellerParamVO paramVO, ModelMap resultMap) {
    	sellerService.querySellerList(paramVO);
    	resultMap.addAttribute("resultVO", paramVO);
        return "seller/list";
    }
    
    @RequestMapping(value = "detail")
    protected String detail(QuerySellerDetailParamVO paramVO, ModelMap resultMap) {
    	sellerService.querySellerDetail(paramVO);
    	//获取用户
    	User user=userService.getUserById(paramVO.getSellerId());
    	
    	resultMap.addAttribute("resultVO", paramVO);
    	resultMap.addAttribute("seller", user);
        return "seller/list";
    }
    
    @RequestMapping(value = "projectDetail")
    protected String projectDetail(QueryTaskDetailParamVO paramVO, ModelMap resultMap) {
    	sellerService.queryTaskDetail(paramVO);
    	//获取用户
    	Task task=taskService.getDetailByTaskId(paramVO.getTaskId());
    	
    	resultMap.addAttribute("resultVO", paramVO);
    	resultMap.addAttribute("task", task);
        return "seller/list";
    }

}
