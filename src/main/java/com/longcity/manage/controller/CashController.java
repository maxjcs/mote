/**
 * 
 */
package com.longcity.manage.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.longcity.manage.model.param.QueryCashParamVO;
import com.longcity.manage.service.CashService;

/**
 * @author maxjcs
 *
 */
@Controller
@RequestMapping("back/cash")
public class CashController extends BaseController{
	
	@Resource
	CashService cashService;
	
	/**
	 * 充值申请的list
	 * @param paramVO
	 * @param resultMap
	 * @return
	 */
    @RequestMapping(value = "addCashList")
    protected String addCashList(QueryCashParamVO paramVO,ModelMap resultMap) {
    	cashService.addCashList(paramVO);
    	resultMap.addAttribute("resultVO", paramVO);
        return "mote/manage";
    } 
    
    /**
     * 提现申请
     * @param paramVO
     * @param resultMap
     * @return
     */
    @RequestMapping(value = "reduceCashList")
    protected String reduceCashList(QueryCashParamVO paramVO,ModelMap resultMap) {
    	cashService.reduceCashList(paramVO);
    	resultMap.addAttribute("resultVO", paramVO);
        return "mote/manage";
    } 

}
