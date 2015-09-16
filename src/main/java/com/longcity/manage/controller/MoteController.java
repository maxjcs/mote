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

/**
 * @author maxjcs
 *
 */
@Controller
@RequestMapping("mote")
public class MoteController extends BaseController{
	
   @Resource
   MoteService moteService;
   @Resource
   UserService userService;
	
    @RequestMapping(value = "list")
    protected String list(QueryMoteParamVO paramVO, ModelMap resultMap) {
    	moteService.queryMoteList(paramVO);
    	resultMap.addAttribute("resultVO", paramVO);
        return "mote/list";
    }
    
    @RequestMapping(value = "detail")
    protected String detail(QueryMoteDetailParamVO paramVO, ModelMap resultMap) {
    	moteService.queryMoteDetail(paramVO);
    	//获取用户
    	User user=userService.getUserById(paramVO.getMoteId());
    	resultMap.addAttribute("resultVO", paramVO);
    	resultMap.addAttribute("mote", user);
        return "mote/list";
    }

}
