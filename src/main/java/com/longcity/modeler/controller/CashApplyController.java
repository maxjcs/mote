/**
 * 
 */
package com.longcity.modeler.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.longcity.modeler.core.AppContext;
import com.longcity.modeler.service.CashApplyService;

/**
 * @author maxjcs
 *
 */
@Controller
@RequestMapping("cashApply")
public class CashApplyController extends AbstractController{
	
	private static Logger logger = LoggerFactory.getLogger(CashApplyController.class);
	
	@Resource
	CashApplyService cashApplyService;
	
	
	/**
     * 提现申请记录
     */
	@ResponseBody
    @RequestMapping(value = "add")
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
     * 提现申请
     */
	@ResponseBody
    @RequestMapping(value = "add")
    public Object add(HttpServletRequest request,Integer moteTaskId,Integer money) throws Exception{
        try{
        	Integer userId=AppContext.getUserId();
        	cashApplyService.add(userId, money);
            return dataJson(true, request);
        }catch(Exception e){
            logger.error("上传多张照片失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }

}
