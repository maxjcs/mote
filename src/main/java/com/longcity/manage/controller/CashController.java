/**
 * 
 */
package com.longcity.manage.controller;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.longcity.manage.model.param.QueryCashParamVO;
import com.longcity.manage.service.CashService;
import com.longcity.modeler.model.AddCashApply;
import com.longcity.modeler.model.ReduceCashApply;
import com.longcity.modeler.model.User;
import com.longcity.modeler.service.CashApplyService;
import com.longcity.modeler.service.UserService;

/**
 * @author maxjcs
 *
 */
@Controller
@RequestMapping("back/cash")
public class CashController extends BaseController{
	
	@Resource
	CashService cashService;
	
	@Resource
	CashApplyService cashApplyService;
	
	@Resource
	UserService userService;
	
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
        return "cash/addCashList";
    } 
    
	/**
	 * 充值申请详情
	 * @param paramVO
	 * @param resultMap
	 * @return
	 */
    @RequestMapping(value = "addCashDetail")
    protected String addCashDetail(Integer id,ModelMap resultMap) {
    	AddCashApply addCashApply=cashApplyService.addCashApplyDetail(id);
    	resultMap.addAttribute("resultVO", addCashApply);
        return "cash/addCashDetail";
    } 
    
	/**
	 * 完成充值
	 * @param paramVO
	 * @param resultMap
	 * @return
	 */
    @RequestMapping(value = "verifyAddCash")
    protected String verifyAddCash(Integer id,String money,String lastSixOrderNo,ModelMap resultMap) {
    	AddCashApply addCashApply=cashApplyService.addCashApplyDetail(id);
    	//完成
    	Double dmoney=Double.parseDouble(money)*100;
    	if(StringUtils.equals(String.valueOf(dmoney.intValue()), String.valueOf(addCashApply.getMoney()))&&StringUtils.equals(lastSixOrderNo, addCashApply.getLastSixOrderNo())){
    		Boolean success=cashApplyService.finishAddCashPay(id);
    		if(success){
    			addCashApply.setStatus(2);//完成
    			resultMap.addAttribute("message", "操作成功");
    		}else{
    			resultMap.addAttribute("message", "操作失败,金额或者订单号不对,请核对！");
    		}
    	}
    	
    	resultMap.addAttribute("resultVO", addCashApply);
        return "cash/addCashDetail";
    } 
    
	/**
	 * 完成充值
	 * @param paramVO
	 * @param resultMap
	 * @return
	 */
    @RequestMapping(value = "verifyReduceCash")
    protected String verifyReduceCash(Integer id,String alipayNo,ModelMap resultMap) {
    	ReduceCashApply apply=cashApplyService.reduceApplyDetail(id);
    	//完成
    	Boolean success=cashApplyService.finishReducePay(id,alipayNo);
		if(success){
			apply.setStatus(2);//完成
			resultMap.addAttribute("message", "操作成功");
		}else{
			resultMap.addAttribute("message", "操作失败,金额不足！");
		}
    	//获取用户相关信息
    	User user=userService.getUserById(apply.getUserId());
    	apply.setNickname(user.getNickname());
    	apply.setPhoneNumber(user.getPhoneNumber());
    	apply.setAlipayId(user.getAlipayId());
    	apply.setAlipayName(user.getAlipayName());
    	apply.setAlipayNo(alipayNo);
    	resultMap.addAttribute("resultVO", apply);
        return "cash/reduceCashDetail";
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
        return "cash/reduceCashList";
    } 
    
    /**
     * 提现申请详情
     * @param paramVO
     * @param resultMap
     * @return
     */
    @RequestMapping(value = "reduceCashDetail")
    protected String reduceCashDetail(Integer applyId,ModelMap resultMap) {
    	ReduceCashApply apply=cashApplyService.reduceApplyDetail(applyId);
    	//获取用户相关信息
    	User user=userService.getUserById(apply.getUserId());
    	apply.setNickname(user.getNickname());
    	apply.setPhoneNumber(user.getPhoneNumber());
    	apply.setAlipayId(user.getAlipayId());
    	apply.setAlipayName(user.getAlipayName());
    	resultMap.addAttribute("resultVO", apply);
        return "cash/reduceCashDetail";
    } 

}
