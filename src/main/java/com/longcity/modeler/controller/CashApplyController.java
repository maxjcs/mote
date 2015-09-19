/**
 * 
 */
package com.longcity.modeler.controller;

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
import com.longcity.modeler.model.AddCashApply;
import com.longcity.modeler.model.CashRecord;
import com.longcity.modeler.service.CashApplyService;

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
	
	
	/**
     * 提现申请记录
     */
	@ResponseBody
    @RequestMapping(value = "queryList")
    public Object queryList(HttpServletRequest request,Integer userId,Integer status,Integer pageNo,Integer pageSize) throws Exception{
        try{
        	cashApplyService.queryList(userId, status,pageNo==null?1:pageNo,pageSize==null?10:pageSize);
            return dataJson(true, request);
        }catch(Exception e){
            logger.error("上传多张照片失败.", e);
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
    public Object reduceCashApply(HttpServletRequest request,Integer money) throws Exception{
        try{
        	Integer userId=AppContext.getUserId();
        	cashApplyService.reduceCashApply(userId, money);
            return dataJson(true, request);
        }catch(Exception e){
            logger.error("提现申请失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }

}
