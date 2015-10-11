/**
 * 
 */
package com.longcity.manage.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.longcity.manage.model.param.QuerySellerDetailParamVO;
import com.longcity.manage.model.param.QuerySellerParamVO;
import com.longcity.manage.model.param.QueryTaskDetailParamVO;
import com.longcity.manage.service.SellerService;
import com.longcity.modeler.model.MoteCard;
import com.longcity.modeler.model.MoteTask;
import com.longcity.modeler.model.Task;
import com.longcity.modeler.model.User;
import com.longcity.modeler.service.TaskService;
import com.longcity.modeler.service.UserService;
import com.longcity.modeler.util.MoneyUtil;

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
	
    @RequestMapping(value = "manage")
    protected String manage(QuerySellerParamVO paramVO, ModelMap resultMap) {
    	sellerService.querySellerList(paramVO);
    	resultMap.addAttribute("resultVO", paramVO);
        return "seller/manage";
    }
    
    @RequestMapping(value = "sellerDetail")
    protected String sellerDetail(QuerySellerDetailParamVO paramVO, ModelMap resultMap) {
    	sellerService.querySellerDetail(paramVO);
    	//获取用户
    	User user=userService.getUserById(paramVO.getSellerId());
    	user.setRemindFee(MoneyUtil.fen2Yuan(user.getRemindFee()));
    	user.setFreezeFee(MoneyUtil.fen2Yuan(user.getFreezeFee()));
    	
    	resultMap.addAttribute("resultVO", paramVO);
    	resultMap.addAttribute("seller", user);
        return "seller/sellerDetail";
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
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "moteTaskDetail")
    protected String moteTaskDetail(Integer moteTaskId, ModelMap resultMap) {
    	Map map=taskService.getMoteTaskProcess(moteTaskId);
    	resultMap.addAttribute("user", (User)map.get("user"));
    	resultMap.addAttribute("task", (Task)map.get("task"));
    	resultMap.addAttribute("moteTask", (MoteTask)map.get("moteTask"));
    	resultMap.addAttribute("cardList", (List<MoteCard>)map.get("cardList"));
        return "seller/moteTaskDetail";
    }

}
