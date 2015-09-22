/**
 * 
 */
package com.longcity.modeler.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.longcity.modeler.core.AppContext;
import com.longcity.modeler.model.Task;
import com.longcity.modeler.service.TradeFlowService;

/**
 * @author maxjcs
 *
 */
@Controller
@RequestMapping("trade")
public class TradeFlowController extends AbstractController {
	
	private static Logger logger = LoggerFactory.getLogger(TradeFlowController.class);
	
	@Resource
	TradeFlowService tradeFlowService;
	
	/**
     * 获取任务收益记录
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ResponseBody
    @RequestMapping(value = "getTaskIncomeList")
    public Object getTaskIncomeList(HttpServletRequest request,Integer pageNo,Integer pageSize) throws Exception{
        try{
        	Integer userId=AppContext.getUserId();
        	Map resutlMap=tradeFlowService.getTaskIncomeList(userId, pageNo, pageSize);
        	resutlMap.put("pageNo", pageNo);
        	resutlMap.put("pageSize", pageSize);
            return dataJson(resutlMap, request);
        }catch(Exception e){
            logger.error("获取任务详细信息失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }
	
	/**
     * 获取任务收益记录
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ResponseBody
    @RequestMapping(value = "getItemMoneyList")
    public Object getItemMoneyList(HttpServletRequest request,Integer pageNo,Integer pageSize) throws Exception{
        try{
        	Integer userId=AppContext.getUserId();
        	Map resutlMap=tradeFlowService.getItemMoneyList(userId, pageNo, pageSize);
        	resutlMap.put("pageNo", pageNo);
        	resutlMap.put("pageSize", pageSize);
            return dataJson(resutlMap, request);
        }catch(Exception e){
            logger.error("获取任务详细信息失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }

}
