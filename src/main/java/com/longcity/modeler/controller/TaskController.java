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
import com.longcity.modeler.model.Task;
import com.longcity.modeler.service.TaskService;

/**
 * @author maxjcs
 *
 */
@Controller
@RequestMapping("task")
public class TaskController extends AbstractController {
	
	private static Logger logger = LoggerFactory.getLogger(TaskController.class);
	
	@Resource
	TaskService taskService;
	
	
	
	/**
     * 申请客服介入
     */
	@ResponseBody
    @RequestMapping(value = "ApplyKefu")
    public Object ApplyKefu(HttpServletRequest request,Integer moteTaskId) throws Exception{
        try{
        	taskService.applyKefu(moteTaskId);
            return dataJson(true, request);
        }catch(Exception e){
            logger.error("申请客服介入失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }
	
	/**
     * 退还商品
     */
	@ResponseBody
    @RequestMapping(value = "returnItem")
    public Object returnItem(HttpServletRequest request,Integer moteTaskId,Integer expressCompanyId,String expressNo) throws Exception{
        try{
        	taskService.returnItem(moteTaskId,expressCompanyId,expressNo);
            return dataJson(true, request);
        }catch(Exception e){
            logger.error("退还商品失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }
	
	/**
     * 自购商品
     */
	@ResponseBody
    @RequestMapping(value = "selfBuy")
    public Object selfBuy(HttpServletRequest request,Integer moteTaskId) throws Exception{
        try{
        	taskService.selfBuy(moteTaskId);
            return dataJson(true, request);
        }catch(Exception e){
            logger.error("发布项目需求失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }
	
	/**
     * 完成收货并晒图
     */
	@ResponseBody
    @RequestMapping(value = "finishShowPic")
    public Object finishShowPic(HttpServletRequest request,Integer moteTaskId) throws Exception{
        try{
        	taskService.finishShowPic(moteTaskId);
            return dataJson(true, request);
        }catch(Exception e){
            logger.error("发布项目需求失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }
	
    /**
     * 接单
     */
	@ResponseBody
    @RequestMapping(value = "addOrderNo")
    public Object addOrderNo(HttpServletRequest request,Integer moteTaskId,String orderNo) throws Exception{
        try{
        	taskService.addOrderNo(moteTaskId,orderNo);
            return dataJson(true, request);
        }catch(Exception e){
            logger.error("发布项目需求失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }
	
    /**
     * 接单
     */
	@ResponseBody
    @RequestMapping(value = "newMoteTask")
    public Object newMoteTask(HttpServletRequest request,Integer taskId) throws Exception{
        try{
        	Integer moteId=AppContext.getUserId();
        	taskService.newMoteTask(moteId,taskId);
            return dataJson(true, request);
        }catch(Exception e){
            logger.error("发布项目需求失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }
	
	
    /**
     * 录入任务
     */
	@ResponseBody
    @RequestMapping(value = "save")
    public Object save(HttpServletRequest request,Task task) throws Exception{
        try{
        	taskService.save(task);
            return dataJson(true, request);
        }catch(Exception e){
            logger.error("发布项目需求失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }
	
    /**
     * 更新状态
     */
	@ResponseBody
    @RequestMapping(value = "updateStatus")
    public Object applayOK(HttpServletRequest request,Integer id,Integer status) throws Exception{
        try{
        	taskService.updateStatus(id,status);
            return dataJson(true, request);
        }catch(Exception e){
            logger.error("发布项目需求失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }

}
