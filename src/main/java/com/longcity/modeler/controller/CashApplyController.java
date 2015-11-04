/**
 * 
 */
package com.longcity.modeler.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.bson.NewBSONDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.longcity.modeler.core.AppContext;
import com.longcity.modeler.model.AddCashApply;
import com.longcity.modeler.model.ReduceCashApply;
import com.longcity.modeler.model.User;
import com.longcity.modeler.service.CashApplyService;
import com.longcity.modeler.service.UserService;
import com.longcity.modeler.service.VerifyCodeService;
import com.longcity.modeler.util.MoneyUtil;

/**
 * @author maxjcs
 *
 */
@Controller
@RequestMapping("cash")
public class CashApplyController extends AbstractController{
	
	private static Logger logger = LoggerFactory.getLogger(CashApplyController.class);
	
	@Resource
	CashApplyService cashApplyService;
	
	@Resource
	VerifyCodeService verifyCodeService;
	
	@Resource
	UserService userService;
	
	
	/**
     * 提现申请记录
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ResponseBody
    @RequestMapping(value = "queryApplyList")
    public Object queryApplyList(HttpServletRequest request,Integer pageNo,Integer pageSize) throws Exception{
        try{
        	Integer userId=AppContext.getUserId();
        	Map resultMap=cashApplyService.queryApplyList(userId, null,pageNo==null?1:pageNo,pageSize==null?10:pageSize);
        	resultMap.put("pageNo", pageNo);
        	resultMap.put("pageSize", pageSize);
        	//模特余额
        	User user=userService.getUserById(userId);
        	if(user.getRemindFee()!=null){
        		resultMap.put("remindFee", MoneyUtil.fen2Yuan(user.getRemindFee()));
        	}else{
        		resultMap.put("remindFee", 0);
        	}
        	
            return dataJson(resultMap, request);
        }catch(Exception e){
            logger.error("提现申请记录.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }
	
	/**
     * 提现申请记录详情
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ResponseBody
    @RequestMapping(value = "getReduceApplyDetail")
    public Object getReduceApplyDetail(HttpServletRequest request,Integer id) throws Exception{
        try{
        	Integer userId=AppContext.getUserId();
        	User user=userService.getUserById(userId);
        	Map resultMap=new HashMap();
        	ReduceCashApply apply=cashApplyService.reduceApplyDetail(id);
        	resultMap.put("apply", apply);
            return dataJson(resultMap, request);
        }catch(Exception e){
            logger.error("提现申请记录.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }
	
	/**
     * 预付款使用情况
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ResponseBody
    @RequestMapping(value = "getRecordList")
    public Object getRecordList(HttpServletRequest request,Integer pageNo,Integer pageSize) throws Exception{
        try{
        	Integer userId=AppContext.getUserId();
        	Map resultMap=cashApplyService.getRecordList(userId,pageNo,pageSize);
        	resultMap.put("pageNo", pageNo);
        	resultMap.put("pageSize", pageSize);
            return dataJson(resultMap, request);
        }catch(Exception e){
            logger.error("获取预付款使用情况失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }
	
	/**
     * 增加预存款申请
     */
	@ResponseBody
    @RequestMapping(value = "addCashApply")
    public Object addCashApply(HttpServletRequest request,AddCashApply addCashApply) throws Exception{
        try{
        	Integer userId=AppContext.getUserId();
        	addCashApply.setUserId(userId);
        	cashApplyService.addCashApply(addCashApply);
            return dataJson(true, request);
        }catch(Exception e){
            logger.error("增加预存款申请失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }
	
	/**
     * 提现申请
     */
	@ResponseBody
    @RequestMapping(value = "reduceCashApply")
    public synchronized Object reduceCashApply(HttpServletRequest request,Double money,String password,String smsCode) throws Exception{
        try{
        	Integer userId=AppContext.getUserId();
        	
        	//验证短信验证码
        	User user=userService.getUserById(userId);
        	verifyCodeService.validateVerifyCode(user.getPhoneNumber(), smsCode);
        	//校验密码
        	User userParam=new User();
        	userParam.setId(userId);
        	userParam.setPhoneNumber(user.getPhoneNumber());
        	userParam.setPassword(password);
        	userService.login(userParam);
        	
    		if(MoneyUtil.double2Int(user.getRemindFee())-money*100>=0){
    			//提交申请
            	cashApplyService.reduceCashApply(userId, money);
                return dataJson(true, request);
    		}else{
    			return errorJson("余额不足，不能体现.", request);
    		}
        }catch(Exception e){
            logger.error("提现申请失败.", e);
            return errorJson("提现申请失败.", request);
        }
    }

}
