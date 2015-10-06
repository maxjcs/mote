/**
 * 
 */
package com.longcity.modeler.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.longcity.manage.model.param.QueryTaskDetailParamVO;
import com.longcity.manage.model.param.QueryTaskParamVO;
import com.longcity.modeler.constant.PageConstant;
import com.longcity.modeler.dao.MoteCardDao;
import com.longcity.modeler.dao.MoteTaskDao;
import com.longcity.modeler.dao.TaskDao;
import com.longcity.modeler.dao.TradeFlowDao;
import com.longcity.modeler.dao.UserDao;
import com.longcity.modeler.enums.MoteTaskStatus;
import com.longcity.modeler.enums.TaskStatus;
import com.longcity.modeler.enums.TradeFlowType;
import com.longcity.modeler.model.MoteTask;
import com.longcity.modeler.model.Task;
import com.longcity.modeler.model.TaskPic;
import com.longcity.modeler.model.TradeFlow;
import com.longcity.modeler.model.User;
import com.longcity.modeler.model.vo.MoteTaskVO;
import com.longcity.modeler.model.vo.TaskVO;
import com.longcity.modeler.util.MoneyUtil;

/**
 * @author maxjcs
 *
 */
@Service
public class TaskService {
	
	private static Logger logger = LoggerFactory.getLogger(TaskService.class);
	
	@Resource
	TaskPicService taskPicService;
	
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
	
	private static Integer moteAcceptedDailyNum=10;
	
	@Resource
	RedisService redisService;
	
	@Resource
	UserService userService;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map searchTask(Integer moteId,String keywords,Integer fee,Integer pageNo,Integer pageSize){
		User mote=userService.getUserById(moteId);
		//15天内已经接单的列表
		List<Integer> acceptedTaskIds = moteTaskDao.get15DaysList(moteId);
		//所有已经接单的任务Id
		List<Integer> allAcceptedTaskIds = moteTaskDao.getAcceptedTaskIdList(moteId);
		
		Map resultMap=new HashMap();
		
		Map paramMap=new HashMap();
		paramMap.put("keywords", keywords);
		paramMap.put("fee", fee);
		paramMap.put("gender", mote.getGender());
		paramMap.put("age", mote.getAge());
		paramMap.put("heigth", mote.getHeight());
		paramMap.put("shape", mote.getShape());
		if(!acceptedTaskIds.isEmpty()){
			paramMap.put("acceptedTaskIds", acceptedTaskIds);
		}
		paramMap.put("start", (pageNo-1)*pageSize);
		paramMap.put("pageSize", pageSize==null?10:pageSize);
		
		resultMap.put("pageNo", pageNo);
		resultMap.put("pageSize", pageSize);
		
		int totalSize= taskDao.countTask(paramMap);
		resultMap.put("totalSize", totalSize);
		if(totalSize>0){
			List<Task> taskList= taskDao.searchTask(paramMap);
			for(Task task:taskList){
				if(allAcceptedTaskIds.contains(task.getId())){
					task.setIsAccepted(true);
				}
			}
			resultMap.put("dataList", taskList);
		}else{
			resultMap.put("dataList", new ArrayList<Task>());
		}
		return resultMap;
	}
	

	public QueryTaskParamVO queryTaskList(QueryTaskParamVO paramVO){
		Integer total = taskDao.countTaskList(paramVO);
        if (total > 0) {
            List<Task> rows = taskDao.queryTaskList(paramVO);
            paramVO.setTotal(total);
            paramVO.setRows(rows);
        }else{
            paramVO.setTotal(0);
            paramVO.setRows(new ArrayList<Task>());
        }
        return paramVO;
	}
	
	public QueryTaskDetailParamVO queryTaskDetail(QueryTaskDetailParamVO paramVO){
		Integer total = taskDao.countMoteTaskByTaskId(paramVO);
        if (total > 0) {
            List<MoteTaskVO> rows = taskDao.queryMoteTaskByTaskId(paramVO);
            paramVO.setTotal(total);
            paramVO.setRows(rows);
        }else{
            paramVO.setTotal(0);
            paramVO.setRows(new ArrayList<MoteTaskVO>());
        }
        return paramVO;
	}
	
	
	
	/**
	 * 获取任务详细信息
	 * @param taskId
	 */
	public Task  getDetailByTaskId(Integer taskId){
		Task task=taskDao.selectByPrimaryKey(taskId);
		//转换金额为元
		convertTaskMoney(task);
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
		Double fee= task.getPrice()+task.getShotFee();
		paramMap.put("fee",MoneyUtil.double2Int(fee));//单位为分
		moteTaskDao.returnItem(paramMap);
		
	}
	
	/**
	 * 确认退还商品收货
	 * @param moteId
	 * @param taskId
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public void  verifyReturnItem(Integer moteTaskId){
		//获取对象
		MoteTask moteTask=moteTaskDao.selectByPrimaryKey(moteTaskId);
		Task task=taskDao.selectByPrimaryKey(moteTask.getTaskId());
		//更新快递信息
		Map paramMap=new HashMap();
		paramMap.put("moteTaskId", moteTaskId);
		Double fee= task.getPrice()+task.getShotFee();
		paramMap.put("fee",MoneyUtil.double2Int(fee) );//单位为分
		moteTaskDao.verifyReturnItem(paramMap);
		
		//mote记录流水
		TradeFlow tradeFlow=new TradeFlow();
		tradeFlow.setMoney(MoneyUtil.double2Int(fee));
		tradeFlow.setReferId(moteTaskId);
		tradeFlow.setUserId(moteTask.getUserId());
		tradeFlow.setType(TradeFlowType.itemReturn.getValue()); //退还商品
		tradeFlowDao.insert(tradeFlow);
		
		//商家记录流水
		TradeFlow tradeFlow2=new TradeFlow();
		tradeFlow2.setMoney(MoneyUtil.double2Int(fee));
		tradeFlow2.setReferId(moteTaskId);
		tradeFlow2.setUserId(task.getUserId());
		tradeFlow2.setType(TradeFlowType.taskDeduct.getValue()); //任务完成后，余额减少。
		tradeFlowDao.insert(tradeFlow2);
		
		//商家减余额
		userDao.updateFreezeFee(task.getUserId(), -1*MoneyUtil.double2Int(fee));
		//mote增加余额
		userDao.updateRemindFee(moteTask.getUserId(), MoneyUtil.double2Int(fee));
		
		//增加缓存中已经完成的任务量
		redisService.redisFinishTask();
		
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
		Double fee= task.getPrice()*(100-task.getSelfBuyOff())/100+task.getShotFee();
		paramMap.put("fee",MoneyUtil.double2Int(fee) );//单位为分
		moteTaskDao.selfBuy(paramMap);
		
		//mote记录流水
		TradeFlow tradeFlow=new TradeFlow();
		tradeFlow.setMoney(MoneyUtil.double2Int(fee));
		tradeFlow.setReferId(moteTaskId);
		tradeFlow.setUserId(moteTask.getUserId());
		tradeFlow.setType(TradeFlowType.itemAccept.getValue()); //自购商品
		tradeFlowDao.insert(tradeFlow);
		
		//商家记录流水
		TradeFlow tradeFlow2=new TradeFlow();
		tradeFlow2.setMoney(MoneyUtil.double2Int(fee));
		tradeFlow2.setReferId(moteTaskId);
		tradeFlow2.setUserId(task.getUserId());
		tradeFlow2.setType(TradeFlowType.taskDeduct.getValue()); //任务完成后，余额减少。
		tradeFlowDao.insert(tradeFlow2);
		
		//商家减冻结金额
		userDao.updateFreezeFee(task.getUserId(), -1*MoneyUtil.double2Int(fee));
		//mote增加余额
		userDao.updateRemindFee(moteTask.getUserId(), MoneyUtil.double2Int(fee));
		
		//增加缓存中已经完成的任务量
		redisService.redisFinishTask();
		redisService.redisSelfBuyTask();//自购的商品数
		
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
		//增加缓存中正执行的任务量
		redisService.redisPerformTask();
	}
	
	/**
	 * 新接订单
	 * @param moteId
	 * @param taskId
	 */
	public int  newMoteTask(Integer moteId,Integer taskId){
		
		MoteTask existMoteTask=moteTaskDao.queryByMoteIdAndTaskId(moteId,taskId);
		if(existMoteTask!=null){
			return 3;
		}
		
		Integer acceptedNum=moteTaskDao.getTotalAcceptedNum(taskId);
		Task task=taskDao.selectByPrimaryKey(taskId);
		if(task==null){
			return -1;
		}
		if(acceptedNum>=task.getNumber()){
			return 0;//已经达到当量
		}
		//模特当天的接单量
		Integer moteAcceptDaily=moteTaskDao.getMoteAcceptedNumDaily(moteId);
		if(moteAcceptDaily>=moteAcceptedDailyNum){
			return 1;//模特当天的接单量超出
		}
		
		MoteTask moteTask=new MoteTask();
		moteTask.setTaskId(taskId);
		moteTask.setUserId(moteId);
		moteTask.setStatus(MoteTaskStatus.newAccept.getValue());
		moteTask.setAcceptedTime(new Date());
		moteTaskDao.insert(moteTask);
		//增加缓存中总的任务量
		redisService.redisNewMoteTask();
		
		return 2; //接单成功
	}
	
	/**
	 * 保存发布任务
	 * @param task
	 * @return
	 */
	public Integer save(Task task){
		task.setStatus(TaskStatus.no_payed.getValue());
		task.setOldUrl(task.getUrl());
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
			task.setOldUrl(task.getUrl());
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
		if(TaskStatus.passed.getValue()==status){
			redisService.redisApplyOk(taskId); 
		}
	}
	
	
	/**
	 * 获取新建的项目
	 * @param userId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getNewTaskList(Integer userId,Integer pageNo,Integer pageSize){
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
		
		Integer totalSize=taskDao.countNewTaskList(paramMap);
		List<Task> taskList = taskDao.getNewTaskList(paramMap);
		//转换金额为元
		for(Task task:taskList){
			convertTaskMoney(task);
		}
		
		Map resultMap=new HashMap();
		resultMap.put("totalSize", totalSize);
		resultMap.put("dataList", taskList);
		
		return resultMap;
	}
	
	/**
	 * 获取执行中的项目
	 * @param userId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getStasticTaskList(Integer userId,Integer status,Integer pageNo,Integer pageSize){
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
		
		Map resultMap=new HashMap();
		
		if(taskList.size()==0){
			resultMap.put("totalSize", 0);
			resultMap.put("voList", new ArrayList<TaskVO>());
			return resultMap;
		}
		
		Integer totalSize=taskDao.countStasticTaskList(paramMap);
		
		List<Integer> taskIds=new ArrayList<Integer>();
		for(Task task:taskList){
			taskIds.add(task.getId());
		}
		Map paramMap2=new HashMap();
		paramMap2.put("taskIds", taskIds);
		paramMap2.put("status", status);
		List<TaskVO> volist=moteTaskDao.stasticByTaskIds(paramMap2);
		
		resultMap.put("totalSize", totalSize);
		resultMap.put("dataList", volist);
		return resultMap;
	}
	
	/**
	 * 获取接单的模特列表
	 * @param userId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getMoteListByTaskId(Integer taskId,Integer pageNo,Integer pageSize){
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
		Integer totalSize = moteTaskDao.countMoteListByTaskId(taskId);
		Task task=taskDao.selectByPrimaryKey(taskId);
		
		Map resultMap = new HashMap();
		resultMap.put("totalSize", totalSize);
		resultMap.put("dataList", moteTaskList);
		resultMap.put("taskTitle", task.getTitle());
		
		return resultMap;
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
		User mote=userDao.selectByPrimaryKey(moteTask.getUserId());
		//获取任务对象
		Task task=taskDao.selectByPrimaryKey(moteTask.getTaskId());
		//获取模卡对象
		List<TaskPic> taskPicList=taskPicService.showImage(moteTaskId, moteTask.getUserId());
		
		resutlMap.put("user", mote);
		resutlMap.put("task", task);
		resutlMap.put("moteTask", moteTask);
		resutlMap.put("picNum", taskPicList.size());//上传的图片数
		
		 //商家收货地址
		User seller=userDao.selectByPrimaryKey(task.getUserId());
		resutlMap.put("address", seller.getAddress());
		
		return resutlMap;
	}
	
	public Integer getUnFinishNumByMoteId(Integer moteId){
		Integer count=moteTaskDao.getUnFinishNumByMoteId(moteId);
		return count;
	}
	
	
	/**
	 * 转换成元
	 * @param task
	 */
	public void convertTaskMoney(Task task){
		if(task.getPrice()!=null&&task.getPrice()>0){
			task.setPrice(MoneyUtil.fen2Yuan(task.getPrice()));
		}
		if(task.getShotFee()!=null&&task.getShotFee()>0){
			task.setShotFee(MoneyUtil.fen2Yuan(task.getShotFee()));
		}
		if(task.getTotalFee()!=null&&task.getTotalFee()>0){
			task.setTotalFee(MoneyUtil.fen2Yuan(task.getTotalFee()));
		}
	}
	
	
	
	
	

}
