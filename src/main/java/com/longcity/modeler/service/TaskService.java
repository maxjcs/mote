/**
 * 
 */
package com.longcity.modeler.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.longcity.modeler.dao.MoteTaskDao;
import com.longcity.modeler.dao.TaskDao;
import com.longcity.modeler.dao.TradeFlowDao;
import com.longcity.modeler.dao.UserDao;
import com.longcity.modeler.enums.MoteTaskStatus;
import com.longcity.modeler.enums.TaskStatus;
import com.longcity.modeler.enums.TradeFlowType;
import com.longcity.modeler.model.MoteTask;
import com.longcity.modeler.model.Task;
import com.longcity.modeler.model.TradeFlow;

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
	private UserDao userDao;
	
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
	public void  finishShowPic(Integer moteTaskId){
		moteTaskDao.updateStatus(moteTaskId,MoteTaskStatus.showPicOK.getValue());
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
		task.setStatus(TaskStatus.newadd.getValue());
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

}
