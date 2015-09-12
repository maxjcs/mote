/**
 * 
 */
package com.longcity.modeler.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.longcity.modeler.constant.PageConstant;
import com.longcity.modeler.dao.MoteCardDao;
import com.longcity.modeler.dao.MoteTaskDao;
import com.longcity.modeler.dao.TaskDao;
import com.longcity.modeler.dao.TradeFlowDao;
import com.longcity.modeler.dao.UserDao;
import com.longcity.modeler.enums.MoteTaskStatus;
import com.longcity.modeler.enums.TaskStatus;
import com.longcity.modeler.enums.TradeFlowType;
import com.longcity.modeler.model.MoteCard;
import com.longcity.modeler.model.MoteTask;
import com.longcity.modeler.model.Task;
import com.longcity.modeler.model.TradeFlow;
import com.longcity.modeler.model.User;
import com.longcity.modeler.model.vo.MoteTaskVO;
import com.longcity.modeler.model.vo.TaskVO;

/**
 * @author maxjcs
 *
 */
@Service
public class TaskService {
	
	private static Logger logger = LoggerFactory.getLogger(TaskService.class);
	
	@Resource
	private TaskDao taskDao;
	
	@Resource
	private MoteTaskDao moteTaskDao;
	
	@Resource
	private TradeFlowDao tradeFlowDao;
	
	@Resource
	private MoteCardDao moteCardDao;
	
	@Resource
	private UserDao userDao;
	
	
	
	/**
	 * 获取任务详细信息
	 * @param taskId
	 */
	public Task  getDetailByTaskId(Integer taskId){
		Task task=taskDao.selectByPrimaryKey(taskId);
		return task;
	}
	
	/**
	 * 申请客服
	 * @param moteId
	 * @param taskId
	 */
	public void  applyKefu(Integer moteTaskId){
		moteTaskDao.updateStatus(moteTaskId,MoteTaskStatus.ApplyKefu.getValue());
	}
	
	/**
	 * 退还商品
	 * @param moteId
	 * @param taskId
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public void  returnItem(Integer moteTaskId,Integer expressCompanyId,String expressNo){
		//获取对象
		MoteTask moteTask=moteTaskDao.selectByPrimaryKey(moteTaskId);
		Task task=taskDao.selectByPrimaryKey(moteTask.getTaskId());
		//更新快递信息
		Map paramMap=new HashMap();
		paramMap.put("moteTaskId", moteTaskId);
		paramMap.put("expressCompanyId", expressCompanyId);
		paramMap.put("expressNo", expressNo);
		Integer fee= task.getPrice()+task.getShotFee();
		paramMap.put("fee",fee );//单位为分
		moteTaskDao.returnItem(paramMap);
		
		//mote记录流水
		TradeFlow tradeFlow=new TradeFlow();
		tradeFlow.setMoney(fee);
		tradeFlow.setReferId(moteTaskId);
		tradeFlow.setUserId(moteTask.getUserId());
		tradeFlow.setType(TradeFlowType.itemAccept.getValue()); //自购商品
		tradeFlowDao.insert(tradeFlow);
		
		//商家记录流水
		TradeFlow tradeFlow2=new TradeFlow();
		tradeFlow2.setMoney(fee);
		tradeFlow2.setReferId(moteTaskId);
		tradeFlow2.setUserId(task.getUserId());
		tradeFlow2.setType(TradeFlowType.taskDeduct.getValue()); //任务完成后，余额减少。
		tradeFlowDao.insert(tradeFlow2);
		
		//商家减余额
		userDao.updateRemindFee(task.getUserId(), -1*fee);
		//mote增加余额
		userDao.updateRemindFee(moteTask.getUserId(), fee);
		
	}
	
	/**
	 * 自购商品
	 * @param moteId
	 * @param taskId
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public void  selfBuy(Integer moteTaskId){
		//获取对象
		MoteTask moteTask=moteTaskDao.selectByPrimaryKey(moteTaskId);
		Task task=taskDao.selectByPrimaryKey(moteTask.getTaskId());
		//更新快递信息
		Map paramMap=new HashMap();
		paramMap.put("moteTaskId", moteTaskId);
		Integer fee= task.getPrice()*(100-task.getSelfBuyOff())/100+task.getShotFee();
		paramMap.put("fee",fee );//单位为分
		moteTaskDao.selfBuy(paramMap);
		
		//mote记录流水
		TradeFlow tradeFlow=new TradeFlow();
		tradeFlow.setMoney(fee);
		tradeFlow.setReferId(moteTaskId);
		tradeFlow.setUserId(moteTask.getUserId());
		tradeFlow.setType(TradeFlowType.itemAccept.getValue()); //自购商品
		tradeFlowDao.insert(tradeFlow);
		
		//商家记录流水
		TradeFlow tradeFlow2=new TradeFlow();
		tradeFlow2.setMoney(fee);
		tradeFlow2.setReferId(moteTaskId);
		tradeFlow2.setUserId(task.getUserId());
		tradeFlow2.setType(TradeFlowType.taskDeduct.getValue()); //任务完成后，余额减少。
		tradeFlowDao.insert(tradeFlow2);
		
		//商家减余额
		userDao.updateRemindFee(task.getUserId(), -1*fee);
		//mote增加余额
		userDao.updateRemindFee(moteTask.getUserId(), fee);
		
	}
	
	
	
	/**
	 * 完成晒图
	 * @param moteId
	 * @param taskId
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void  finishShowPic(Integer moteTaskId){
		Map paramMap=new HashMap();
		paramMap.put("moteTaskId", moteTaskId);
		moteTaskDao.finishShowPic(paramMap);
	}
	
	/**
	 * 完成上传图片
	 * @param moteId
	 * @param taskId
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void  uploadImg(Integer moteTaskId){
		Map paramMap=new HashMap();
		paramMap.put("moteTaskId", moteTaskId);
		moteTaskDao.uploadImg(paramMap);
	}
	
	/**
	 * 录入淘宝订单号
	 * @param moteId
	 * @param taskId
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void  addOrderNo(Integer moteTaskId,String orderNo){
		Map paramMap=new HashMap();
		paramMap.put("moteTaskId", moteTaskId);
		paramMap.put("orderNo", orderNo);
		moteTaskDao.addOrderNo(paramMap);
	}
	
	/**
	 * 新接订单
	 * @param moteId
	 * @param taskId
	 */
	public void  newMoteTask(Integer moteId,Integer taskId){
		MoteTask moteTask=new MoteTask();
		moteTask.setTaskId(taskId);
		moteTask.setUserId(moteId);
		moteTask.setStatus(MoteTaskStatus.newAccept.getValue());
		moteTaskDao.insert(moteTask);
	}
	
	/**
	 * 保存发布任务
	 * @param task
	 * @return
	 */
	public Integer save(Task task){
		task.setStatus(TaskStatus.no_payed.getValue());
		Integer id=0;
		if(task.getId()==null){
			id=taskDao.insert(task);
		}else{
			taskDao.updateByPrimaryKey(task);
		}
		return id;
	}
	
	/**
	 * 发布任务
	 * @param task
	 * @return
	 */
	public Integer publish(Task task){
		task.setStatus(TaskStatus.wait_approve.getValue());
		Integer id=0;
		if(task.getId()==null){
			id=taskDao.insert(task);
		}else{
			taskDao.updateByPrimaryKey(task);
		}
		return id;
	}
	
	/**
	 * 更新状态
	 * @param taskId
	 * @param status
	 */
	public void updateStatus(Integer taskId,Integer status){
		taskDao.updateStatus(taskId,status);
	}
	
	
	/**
	 * 获取新建的项目
	 * @param userId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Task> getNewTaskList(Integer userId,Integer pageNo,Integer pageSize){
		if(pageNo==null){
			pageNo=1;
		}
		if(pageSize==null){
			pageSize=PageConstant.PAGE_SIZE_10;
		}
		
		Map paramMap=new HashMap();
		paramMap.put("userId", userId);
		paramMap.put("start", (pageNo-1)*pageSize);
		paramMap.put("pageSize", pageSize);
		List<Task> taskList = taskDao.getNewTaskList(paramMap);
		
		return taskList;
	}
	
	/**
	 * 获取执行中的项目
	 * @param userId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<TaskVO> getStasticTaskList(Integer userId,Integer status,Integer pageNo,Integer pageSize){
		if(pageNo==null){
			pageNo=1;
		}
		if(pageSize==null){
			pageSize=PageConstant.PAGE_SIZE_10;
		}
		
		Map paramMap=new HashMap();
		paramMap.put("userId", userId);
		paramMap.put("start", (pageNo-1)*pageSize);
		paramMap.put("pageSize", pageSize);
		paramMap.put("status", status);
		List<Task> taskList = taskDao.getStasticTaskList(paramMap);
		
		if(taskList.size()==0){
			return new ArrayList<TaskVO>();
		}
		
		List<Integer> taskIds=new ArrayList<Integer>();
		for(Task task:taskList){
			taskIds.add(task.getId());
		}
		Map paramMap2=new HashMap();
		paramMap2.put("taskIds", taskIds);
		paramMap2.put("status", status);
		List<TaskVO> volsit=moteTaskDao.stasticByTaskIds(paramMap2);
		return volsit;
	}
	
	/**
	 * 获取接单的模特列表
	 * @param userId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<MoteTaskVO> getMoteListByTaskId(Integer taskId,Integer pageNo,Integer pageSize){
		if(pageNo==null){
			pageNo=1;
		}
		if(pageSize==null){
			pageSize=PageConstant.PAGE_SIZE_10;
		}
		
		Map paramMap=new HashMap();
		paramMap.put("taskId", taskId);
		paramMap.put("start", (pageNo-1)*pageSize);
		paramMap.put("pageSize", pageSize);
		List<MoteTaskVO> moteTaskList = moteTaskDao.getMoteListByTaskId(paramMap);
		
		return moteTaskList;
	}
	
	/**
	 * 获取模特接单后的进程
	 * @param moteTaskId
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getMoteTaskProcess(Integer moteTaskId){
		Map resutlMap=new HashMap();
		
		MoteTask moteTask=moteTaskDao.selectByPrimaryKey(moteTaskId);
		//获取模特对象
		User user=userDao.selectByPrimaryKey(moteTask.getUserId());
		//获取任务对象
		Task task=taskDao.selectByPrimaryKey(moteTask.getTaskId());
		//获取模卡对象
		List<MoteCard> cardList=moteCardDao.queryByUserId(moteTask.getUserId());
		
		resutlMap.put("user", user);
		resutlMap.put("task", task);
		resutlMap.put("moteTask", moteTask);
		resutlMap.put("cardList", cardList);
		
		return resutlMap;
	}
	
	

}
