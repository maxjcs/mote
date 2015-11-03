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

import com.google.zxing.common.detector.MathUtils;
import com.longcity.modeler.core.AppContext;
import com.longcity.modeler.dao.MoteTaskDao;
import com.longcity.modeler.dao.TaskDao;
import com.longcity.modeler.enums.MoteTaskStatus;
import com.longcity.modeler.enums.TaskStatus;
import com.longcity.modeler.enums.UserType;
import com.longcity.modeler.model.MoteTask;
import com.longcity.modeler.model.Task;
import com.longcity.modeler.model.User;
import com.longcity.modeler.service.RedisService;
import com.longcity.modeler.service.TaskService;
import com.longcity.modeler.service.UserService;
import com.longcity.modeler.util.JsonUtil;
import com.longcity.modeler.util.MoneyUtil;
import com.longcity.modeler.validator.Validator;

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
	
	@Resource
	UserService userService;
	
	@Resource
	TaskDao taskDao;
	
	@Resource
	private MoteTaskDao moteTaskDao;
	
	/**
     * 获取模特接单的未完成的数量
     */
	@ResponseBody
    @RequestMapping(value = "getUnFinishNumByMoteId")
    public Object getUnFinishNumByTaskId() throws Exception{
        try{
        	Integer moteId=AppContext.getUserId();
        	Integer count=taskService.getUnFinishNumByMoteId(moteId);
            return dataJson(count);
        }catch(Exception e){
            logger.error("获取模特接单的未完成的数量失败.", e);
            return errorJson("服务器异常，请重试.");
        }
    }
	
	/**
     * 获取任务详细信息
     */
	@SuppressWarnings({"rawtypes" })
	@ResponseBody
    @RequestMapping(value = "search")
    public Object searchTask(String keywords,Integer fee,Integer pageNo,Integer pageSize) throws Exception{
        try{
        	Integer moteId=AppContext.getUserId();
        	Map resutlMap=taskService.searchTask(moteId,keywords,fee,pageNo,pageSize);
            return dataJson(resutlMap);
        }catch(Exception e){
            logger.error("获取任务详细信息失败.", e);
            return errorJson("服务器异常，请重试.");
        }
    }
	
	
	
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
    public Object returnItem(HttpServletRequest request,Integer moteTaskId,String expressCompanyId,String expressNo) throws Exception{
		Validator.validateBlank(expressCompanyId, "快递公司不能为空.");
		Validator.validateBlank(expressNo, "快递单号不能为空.");
		try{
        	taskService.returnItem(moteTaskId,expressCompanyId,expressNo);
            return dataJson(true, request);
        }catch(Exception e){
            logger.error("退还商品失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }
	
	/**
     * 确认退还商品收货
     */
	@ResponseBody
    @RequestMapping(value = "verifyReturnItem")
    public Object verifyReturnItem(HttpServletRequest request,Integer moteTaskId) throws Exception{
        try{
        	taskService.verifyReturnItem(moteTaskId);
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
    public Object newMoteTask(HttpServletRequest request,Integer id) throws Exception{
        try{
        	int code=taskService.newMoteTask(id);
        	if(code==-1){
        		return errorJson("项目不存在！", request);
        	}
        	if(code==-2){
        		return errorJson("你已经接过单！", request);
        	}
        	if(code==-3){
        		return errorJson("15天内不能对同一商家接单！", request);
        	}
        	if(code==0){
        		return errorJson("该项目接单已满，不能接单！", request);
        	}
        	if(code==1){
        		return errorJson("您已经达到当天的接单量，不能接单！", request);
        	}
        	if(code==3){
        		return errorJson("已经接单！", request);
        	}
            return dataJson(true, request);
        }catch(Exception e){
            logger.error("接单失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }
	
    /**
     * 关注
     */
	@ResponseBody
    @RequestMapping(value = "follow")
    public Object follow(HttpServletRequest request,Integer taskId) throws Exception{
        try{
        	Integer moteId=AppContext.getUserId();
        	int code=taskService.followTask(moteId,taskId);
        	if(code==3){
        		return errorJson("已经关注了该任务！", request);
        	}
            return dataJson(true, request);
        }catch(Exception e){
            logger.error("关注该任务失败.", e);
            return errorJson("关注该任务失败.", request);
        }
    }
	
    /**
     * 取消关注
     */
	@ResponseBody
    @RequestMapping(value = "cancelFollow")
    public Object cancelFollow(HttpServletRequest request,Integer id) throws Exception{
        try{
        	int code=taskService.cancelFollowTask(id);
        	if(code==3){
        		return errorJson("已经下单，不能删除！", request);
        	}
            return dataJson(true, request);
        }catch(Exception e){
            logger.error("取消关注失败.", e);
            return errorJson("取消关注失败.", request);
        }
    }
	
	
    /**
     * 录入任务
     */
	@ResponseBody
    @RequestMapping(value = "save")
    public Object save(HttpServletRequest request,Integer id,String title,String url,Double price,Double shotFee,
    		String imgUrl,Double selfBuyOff,String shotDesc,Integer gender,Integer shape,
    		Integer heightMin,Integer heightMax,Integer ageMin,Integer ageMax,Integer number) throws Exception{
        try{
        	Integer userId=AppContext.getUserId();
        	Task task=new Task();
        	task.setId(id);
        	task.setTitle(title);
        	task.setUrl(url);
        	task.setPrice(price);
        	task.setShotFee(shotFee);
        	task.setImgUrl(imgUrl);
        	task.setSelfBuyOff(selfBuyOff);
        	task.setShotDesc(shotDesc);
        	task.setGender(gender);
        	task.setShape(shape);
        	task.setHeightMin(heightMin);
        	task.setHeightMax(heightMax);
        	task.setAgeMin(ageMin);
        	task.setAgeMax(ageMax);
        	task.setNumber(number);
        	
        	System.out.println(JsonUtil.obj2Json(task));
        	
        	//
        	task.setUserId(userId);
        	User user=userService.getUserById(userId);
        	if(user.getType()==UserType.mote.getValue()){
        		return errorJson("只有商家才能发布项目", request);
        	}
        	task.setPriceFen(MoneyUtil.yuan2Fen(task.getPrice()));//转换成分
        	task.setShotFeeFen(MoneyUtil.yuan2Fen(task.getShotFee()));//转换成分
        	task.setTotalFeeFen(MoneyUtil.yuan2Fen((task.getPrice()+task.getShotFee())*task.getNumber()));//转换成分
        	taskService.save(task);
            return dataJson(true, request);
        }catch(Exception e){
            logger.error("发布项目需求失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }
	
	
    /**
     * 任务付费
     */
	@ResponseBody
    @RequestMapping(value = "payTask")
    public Object payTask(HttpServletRequest request,Integer taskId) throws Exception{
        try{
        	Integer userId=AppContext.getUserId();
        	
            User user= userService.getUserById(userId);
            
            Task task=taskDao.selectByPrimaryKey(taskId);
            
            if(user.getRemindFee()<task.getTotalFee()){
            	 return errorJson("预存款不足，请充值！", request);
            }
            if(userId.intValue()!=task.getUserId().intValue()){
        		return errorJson("只有给自己的项目付费", request);
        	}
        	task.setPriceFen(MoneyUtil.double2Int(task.getPrice()));//转换成分
        	task.setShotFeeFen(MoneyUtil.double2Int(task.getShotFee()));//转换成分
        	task.setTotalFeeFen(MoneyUtil.double2Int(task.getTotalFee()));//转换成分
        	taskService.publish(task);
        	//冻结金额
        	userService.freezeFee(userId, MoneyUtil.double2Int(task.getTotalFee()));
            return dataJson(true, request);
        }catch(Exception e){
            logger.error("发布项目需求失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }
	
    /**
     * 获取任务详情
     */
	@ResponseBody
    @RequestMapping(value = "getTaskById")
    public Object getTaskById(HttpServletRequest request,Integer taskId) throws Exception{
        try{
            Task task=taskDao.selectByPrimaryKey(taskId);
            MoneyUtil.convertTaskMoney(task);
            return dataJson(task, request);
        }catch(Exception e){
            logger.error("获取任务详情失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }
	
    /**
     * 任务完成确认
     */
	@ResponseBody
    @RequestMapping(value = "finishMoteTask")
    public synchronized Object finishMoteTask(HttpServletRequest request,Integer moteTaskId) throws Exception{
		try{
			MoteTask moteTask=moteTaskDao.selectByPrimaryKey(moteTaskId);
			if(moteTask.getFinishStatus()!=null&&moteTask.getFinishStatus().intValue()==1){
				return errorJson("已经完成.", request);
			}
			//自购商品
			if(moteTask.getStatus()==MoteTaskStatus.selfBuy.getValue()||moteTask.getStatus()==MoteTaskStatus.verifyReturnItem.getValue()){
				taskService.finishMoteTask(moteTask);
				return dataJson(true, request);
			}else{
				return errorJson("状态不正确！", request);
			}
        }catch(Exception e){
            logger.error("任务完成确认失败.", e);
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
        	
            User user= userService.getUserById(userId);
            Double totalFee=(task.getPrice()+task.getShotFee())*task.getNumber();
            if(user.getRemindFee()-totalFee*100<0){
            	 return errorJson("预存款不足，请充值！", request);
            }
            if(user.getType()==UserType.mote.getValue()){
        		return errorJson("只有商家才能发布项目", request);
        	}
        	//新建任务
        	task.setUserId(userId);
        	task.setPriceFen(MoneyUtil.yuan2Fen(task.getPrice()));//转换成分
        	task.setShotFeeFen(MoneyUtil.yuan2Fen(task.getShotFee()));//转换成分
        	task.setTotalFeeFen(MoneyUtil.yuan2Fen(totalFee));//转换成分
        	taskService.publish(task);
        	//冻结金额
        	userService.freezeFee(userId, MoneyUtil.double2Int(totalFee*100));
        	
            return dataJson(true, request);
        }catch(Exception e){
            logger.error("发布项目需求失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }
	
    /**
     * 审核通过
     */
	@ResponseBody
    @RequestMapping(value = "applayOK")
    public Object applayOK(HttpServletRequest request,Integer id) throws Exception{
        try{
        	taskService.updateStatus(id,TaskStatus.passed.getValue());
            return dataJson(true, request);
        }catch(Exception e){
            logger.error("更新状态失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }
	
    /**
     * 审核不通过
     */
	@ResponseBody
    @RequestMapping(value = "applayReject")
    public Object applayReject(HttpServletRequest request,Integer id) throws Exception{
        try{
        	taskService.updateStatus(id,TaskStatus.not_passed.getValue());
            return dataJson(true, request);
        }catch(Exception e){
            logger.error("更新状态失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }
	
	 /**
     * 获取创建中的项目
     */
	@SuppressWarnings("unchecked")
	@ResponseBody
    @RequestMapping(value = "getNewTaskList")
    public Object getNewTaskList(HttpServletRequest request,Integer pageNo,Integer pageSize) throws Exception{
        try{
        	Integer userId=AppContext.getUserId();
        	Map resultMap=taskService.getNewTaskList(userId,pageNo,pageSize);
        	resultMap.put("pageNo", pageNo);
        	resultMap.put("pageSize", pageSize);
            return dataJson(resultMap, request);
        }catch(Exception e){
            logger.error("更新状态失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }
	
	 /**
     * 获取执行中的项目
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ResponseBody
    @RequestMapping(value = "getPerformTaskList")
    public Object getPerformTaskList(HttpServletRequest request,Integer pageNo,Integer pageSize) throws Exception{
        try{
        	Integer userId=AppContext.getUserId();
        	Map resultMap=taskService.getStasticTaskList(userId,TaskStatus.passed.getValue(),pageNo,pageSize);
        	resultMap.put("pageNo", pageNo);
        	resultMap.put("pageSize", pageSize);
        	return dataJson(resultMap, request);
        }catch(Exception e){
            logger.error("获取执行中的项目失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }
	
	 /**
     * 获取已经完成的项目
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ResponseBody
    @RequestMapping(value = "getFinishedTaskList")
    public Object getFinishedTaskList(HttpServletRequest request,Integer pageNo,Integer pageSize) throws Exception{
        try{
        	Integer userId=AppContext.getUserId();
        	Map resultMap=taskService.getStasticTaskList(userId,TaskStatus.finished.getValue(),pageNo,pageSize);
        	resultMap.put("pageNo", pageNo);
        	resultMap.put("pageSize", pageSize);
        	return dataJson(resultMap, request);
        }catch(Exception e){
            logger.error("获取已经完成的项目失败.", e);
            return errorJson("服务器异常，请重试.", request);
        }
    }
	
	
	 /**
     * 获取接单的模特列表
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ResponseBody
    @RequestMapping(value = "getMoteListByTaskId")
    public Object getMoteListByTaskId(HttpServletRequest request,Integer taskId,Integer pageNo,Integer pageSize) throws Exception{
        try{
        	Map resultMap=taskService.getMoteListByTaskId(taskId,pageNo,pageSize);
        	resultMap.put("pageNo", pageNo);
        	resultMap.put("pageSize", pageSize);
            return dataJson(resultMap, request);
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
