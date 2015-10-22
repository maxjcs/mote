/**
 * 
 */
package com.longcity.manage.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.longcity.manage.model.param.QueryMoteDetailParamVO;
import com.longcity.manage.model.param.QueryMoteParamVO;
import com.longcity.manage.service.MoteService;
import com.longcity.modeler.model.User;
import com.longcity.modeler.service.UserService;
import com.longcity.modeler.util.MoneyUtil;

/**
 * @author maxjcs
 *
 */
@Controller
@RequestMapping("back/mote")
public class MoteController extends BaseController{
	
   @Resource
   MoteService moteService;
   @Resource
   UserService userService;
	
    @RequestMapping(value = "manage")
    protected String manage(QueryMoteParamVO paramVO, ModelMap resultMap) {
    	moteService.queryMoteList(paramVO);
    	resultMap.addAttribute("resultVO", paramVO);
        return "mote/manage";
    }
    
    @RequestMapping(value = "moteDetail")
    protected String detail(QueryMoteDetailParamVO paramVO, ModelMap resultMap) {
    	moteService.queryMoteDetail(paramVO);
    	//获取用户
    	User user=userService.getUserById(paramVO.getMoteId());
    	user.setRemindFee(MoneyUtil.fen2Yuan(user.getRemindFee()));
    	resultMap.addAttribute("resultVO", paramVO);
    	resultMap.addAttribute("mote", user);
        return "mote/moteDetail";
    }

}
