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
import com.longcity.modeler.dao.AddCashApplyDao;
import com.longcity.modeler.dao.CashRecordDao;
import com.longcity.modeler.dao.ReduceCashApplyDao;
import com.longcity.modeler.dao.UserDao;
import com.longcity.modeler.enums.CashApplyStatus;
import com.longcity.modeler.enums.CashRecordType;
import com.longcity.modeler.model.AddCashApply;
import com.longcity.modeler.model.CashRecord;
import com.longcity.modeler.model.ReduceCashApply;
import com.longcity.modeler.model.User;
import com.longcity.modeler.util.MoneyUtil;

/**
 * @author maxjcs
 *
 */
@Service
public class CashApplyService {
	
	private static Logger logger = LoggerFactory.getLogger(CashApplyService.class);
	
	@Resource
	ReduceCashApplyDao reduceCashApplyDao;
	
	@Resource
	AddCashApplyDao addCashApplyDao;
	
	@Resource
	CashRecordDao cashRecordDao;
	
	@Resource
	UserDao userDao;
	
	@Resource
	UserService userService;
	
	/**
	 * 增加预存款
	 * @param userId
	 * @param money
	 */
	public void  addCashApply(AddCashApply record){
		record.setStatus(1);//new
		record.setMoneyFen(MoneyUtil.yuan2Fen(record.getMoney()));//以分保存
		addCashApplyDao.insert(record);
	}
	
	/**
	 * 增加预存款
	 * @param userId
	 * @param money
	 */
	public AddCashApply  addCashApplyDetail(Integer id){
		AddCashApply addApply= addCashApplyDao.selectByPrimaryKey(id);
		addApply.setMoney(MoneyUtil.fen2Yuan(addApply.getMoney()));
		return addApply;
	}
	
	/**
	 * 预付款使用情况
	 * @param userId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getRecordList(Integer userId,Integer pageNo,Integer pageSize){
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
		
		Integer totalSize = cashRecordDao.countRecordList(paramMap);
		List<CashRecord> cashRecords = null;
		if(totalSize>0){
			cashRecords = cashRecordDao.getRecordList(paramMap);
			for(CashRecord record:cashRecords){
				if(record.getMoney()!=null){
					record.setMoney(MoneyUtil.fen2Yuan(record.getMoney()));
				}
				if(record.getRemindMoney()!=null){
				   record.setRemindMoney(MoneyUtil.fen2Yuan(record.getRemindMoney()));
				}
			}
		}else{
			cashRecords = new ArrayList<CashRecord>();
		}
		
		Map resultMap = new HashMap();
		resultMap.put("dataList", cashRecords);
		resultMap.put("totalSize", totalSize);
		
		return resultMap;
	}
	
	
	
	
	/**
	 * 申请提现
	 * @param userId
	 * @param money
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map  queryApplyList(Integer userId,Integer status,Integer pageNo,Integer pageSize){
		Map paramMap=new HashMap();
		paramMap.put("userId", userId);
		paramMap.put("status", status);
		paramMap.put("start", pageSize*(pageNo-1));
		paramMap.put("pageSize", pageSize);
		
		Integer totalSize=reduceCashApplyDao.countApplyList(paramMap);
		List<ReduceCashApply> list=null;
		
		User user=userService.getUserById(userId);
		
		if(totalSize>0){
		     list=reduceCashApplyDao.queryApplyList(paramMap);
		     for(ReduceCashApply apply:list){
		    	 apply.setMoney(MoneyUtil.fen2Yuan(apply.getMoney()));
		    	 apply.setAlipayId(user.getAlipayId());
		    	 apply.setAlipayName(user.getAlipayName());
		     }
		}else{
			list =new ArrayList<ReduceCashApply>();
		}
		
		Map resultlMap=new HashMap();
		resultlMap.put("dataList", list);
		resultlMap.put("totalSize", totalSize);
		
		return resultlMap;
	}
	
	
	/**
	 * 申请提现
	 * @param userId
	 * @param money
	 */
	public  void  reduceCashApply(Integer userId,Double money){
		ReduceCashApply cashApply= new ReduceCashApply();
		cashApply.setMoneyFen(MoneyUtil.yuan2Fen(money));
		cashApply.setUserId(userId);
		cashApply.setStatus(CashApplyStatus.newadd.getValue());
		reduceCashApplyDao.insert(cashApply);
		//冻结金额
		userService.freezeFee(userId, MoneyUtil.yuan2Fen(money));
	}
	
	/**
	 * 查询详情
	 * @param cashApplyId
	 */
	public ReduceCashApply  reduceApplyDetail(Integer cashApplyId){
		ReduceCashApply cashApply= reduceCashApplyDao.selectByPrimaryKey(cashApplyId);
		if(cashApply.getMoney()!=null){
			cashApply.setMoney(MoneyUtil.fen2Yuan(cashApply.getMoney()));
		}
		return cashApply;
	}
	
	
	/**
	 * 完成支付
	 * @param cashApplyId
	 */
	@Transactional
	public Boolean  finishReducePay(Integer cashApplyId,String alipayNo){
		ReduceCashApply reduceCashApply =reduceCashApplyDao.selectByPrimaryKey(cashApplyId);
		User user=userService.getUserById(reduceCashApply.getUserId());
		if(reduceCashApply.getMoney()>user.getFreezeFee()){
			return false;
		}else{
			userDao.updateFreezeFee(reduceCashApply.getUserId(), MoneyUtil.double2Int(reduceCashApply.getMoney())*-1);
		}
		reduceCashApplyDao.finishPay(cashApplyId,alipayNo);
		
		return true;
	}
	
	/**
	 * 确认充值
	 * @param cashApplyId
	 */
	@Transactional
	public Boolean  finishAddCashPay(Integer cashApplyId){
		AddCashApply addCashApply=addCashApplyDao.selectByPrimaryKey(cashApplyId);
		userService.updateRemindFee(addCashApply.getUserId(),  MoneyUtil.double2Int(addCashApply.getMoney()));
		addCashApplyDao.finish(cashApplyId);
		//充值记录
		addCashRecord(addCashApply.getUserId(),addCashApply.getMoney(),"充值",CashRecordType.add.getValue());
		return true;
	}
	
	/**
	 * 增加充值记录
	 * @param userId
	 * @param money
	 */
	public void addCashRecord(Integer userId,Double money,String title,Integer type){
		//充值记录
		User user=userService.getUserById(userId);
		CashRecord cashRecord = new CashRecord();
		cashRecord.setMoneyFen(MoneyUtil.double2Int(money));
		cashRecord.setRemindMoneyFen(MoneyUtil.double2Int(user.getRemindFee()));
		cashRecord.setTitle(title);
		cashRecord.setUserId(userId);
		cashRecord.setType(type);
		cashRecordDao.insert(cashRecord);
	}

}
