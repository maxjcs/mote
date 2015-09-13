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
import com.longcity.modeler.enums.TaskStatus;
import com.longcity.modeler.model.Task;
import com.longcity.modeler.model.vo.MoteTaskVO;
import com.longcity.modeler.model.vo.TaskVO;
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
     * 获取任务详细信息
     */
	@ResponseBody
    @RequestMapping(value = "getDetailByTaskId")
    public Object getDetailByTaskId(HttpServletRequest request,Integer taskId) throws Exception{
        try{
        	Task task=taskService.getDetailByTaskId(taskId);
            return dataJson(task, request);
        }catch(Exception e){
            logger.error("获取任务详细信息失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }
	
	
	
	/**
     * 申请客服介入
     */
	@ResponseBody
    @RequestMapping(value = "applyKefu")
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
            logger.error("自购商品失败.", e);
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
            logger.error("完成收货并晒图失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }
	
    /**
     * 录入淘宝订单号
     */
	@ResponseBody
    @RequestMapping(value = "addOrderNo")
    public Object addOrderNo(HttpServletRequest request,Integer moteTaskId,String orderNo) throws Exception{
        try{
        	taskService.addOrderNo(moteTaskId,orderNo);
            return dataJson(true, request);
        }catch(Exception e){
            logger.error("录入订单号失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }
	
    /**
     * 接单
     */
	@ResponseBody
    @RequestMapping(value = "newMoteTask")
    public Object newMoteTask(HttpServletRequest request,Integer moteId,Integer taskId) throws Exception{
        try{
        	//Integer moteId=AppContext.getUserId();
        	taskService.newMoteTask(moteId,taskId);
            return dataJson(true, request);
        }catch(Exception e){
            logger.error("接单失败.", e);
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
        	Integer userId=AppContext.getUserId();
        	task.setUserId(userId);
        	task.setTotalFee(task.getPrice()*task.getNumber()+task.getShotFee());
        	taskService.save(task);
            return dataJson(true, request);
        }catch(Exception e){
            logger.error("发布项目需求失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }
	
    /**
     * 发布任务
     */
	@ResponseBody
    @RequestMapping(value = "publish")
    public Object publish(HttpServletRequest request,Task task) throws Exception{
        try{
        	Integer userId=AppContext.getUserId();
        	task.setUserId(userId);
        	task.setTotalFee(task.getPrice()*task.getNumber()+task.getShotFee());
        	taskService.publish(task);
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
            logger.error("更新状态失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }
	
	 /**
     * 获取创建中的项目
     */
	@ResponseBody
    @RequestMapping(value = "getNewTaskList")
    public Object getNewTaskList(HttpServletRequest request,Integer pageNo,Integer pageSize) throws Exception{
        try{
        	Integer userId=AppContext.getUserId();
        	List<Task> taskList=taskService.getNewTaskList(userId,pageNo,pageSize);
            return dataJson(taskList, request);
        }catch(Exception e){
            logger.error("更新状态失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }
	
	 /**
     * 获取执行中的项目
     */
	@ResponseBody
    @RequestMapping(value = "getPerformTaskList")
    public Object getPerformTaskList(HttpServletRequest request,Integer pageNo,Integer pageCount) throws Exception{
        try{
        	Integer userId=AppContext.getUserId();
        	List<TaskVO> taskVOList=taskService.getStasticTaskList(userId,TaskStatus.passed.getValue(),pageNo,pageCount);
            return dataJson(taskVOList, request);
        }catch(Exception e){
            logger.error("获取执行中的项目失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }
	
	 /**
     * 获取已经完成的项目
     */
	@ResponseBody
    @RequestMapping(value = "getFinishedTaskList")
    public Object getFinishedTaskList(HttpServletRequest request,Integer pageNo,Integer pageCount) throws Exception{
        try{
        	Integer userId=AppContext.getUserId();
        	List<TaskVO> taskVOList=taskService.getStasticTaskList(userId,TaskStatus.finished.getValue(),pageNo,pageCount);
            return dataJson(taskVOList, request);
        }catch(Exception e){
            logger.error("获取已经完成的项目失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }
	
	
	 /**
     * 获取接单的模特列表
     */
	@ResponseBody
    @RequestMapping(value = "getMoteListByTaskId")
    public Object getMoteListByTaskId(HttpServletRequest request,Integer taskId,Integer pageNo,Integer pageCount) throws Exception{
        try{
        	List<MoteTaskVO> moteVOList=taskService.getMoteListByTaskId(taskId,pageNo,pageCount);
            return dataJson(moteVOList, request);
        }catch(Exception e){
            logger.error("获取接单的模特列表失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }
	
	 /**
     * 获取模特接单后的进程
     */
	@ResponseBody
    @RequestMapping(value = "getMoteTaskProcess")
    public Object getMoteTaskProcess(HttpServletRequest request,Integer moteTaskId) throws Exception{
        try{
        	Map resutlMap=taskService.getMoteTaskProcess(moteTaskId);
            return dataJson(resutlMap, request);
        }catch(Exception e){
            logger.error("获取模特接单后的进程失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }
	

}
