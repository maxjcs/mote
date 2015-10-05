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
import com.longcity.modeler.service.MoteTaskService;

/**
 * @author maxjcs
 *
 */
@Controller
@RequestMapping("motetask")
public class MoteTaskController extends AbstractController{
	
	private static Logger logger = LoggerFactory.getLogger(MoteTaskController.class);
	
	@Resource
	MoteTaskService moteTaskService;
	
	/**
     * 获取模特接单列表
     */
	@ResponseBody
    @RequestMapping(value = "getAcceptedTaskList")
    public Object getAcceptedTaskList(HttpServletRequest request,Integer pageNo,Integer pageSize) throws Exception{
        try{
        	Integer userId=AppContext.getUserId();
        	Map resultMap=moteTaskService.getAcceptedTaskList(userId, pageNo==null?1:pageNo, pageSize);
            return dataJson(resultMap, request);
        }catch(Exception e){
            logger.error("获取模特接单列表失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }

}
