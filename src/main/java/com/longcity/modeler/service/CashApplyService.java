/**
 * 
 */
package com.longcity.modeler.service;

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
import com.longcity.modeler.enums.CashApplyStatus;
import com.longcity.modeler.model.AddCashApply;
import com.longcity.modeler.model.CashRecord;
import com.longcity.modeler.model.ReduceCashApply;
import com.longcity.modeler.model.User;

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
	UserService userService;
	
	/**
	 * 增加预存款
	 * @param userId
	 * @param money
	 */
	public void  addCashApply(AddCashApply record){
		record.setStatus(1);//new
		addCashApplyDao.insert(record);
	}
	
	/**
	 * 增加预存款
	 * @param userId
	 * @param money
	 */
	public AddCashApply  addCashApplyDetail(Integer id){
		return addCashApplyDao.selectByPrimaryKey(id);
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
		List<CashRecord> cashRecords = cashRecordDao.getRecordList(paramMap);
		Integer totalSize = cashRecordDao.countRecordList(paramMap);
		
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
		List<ReduceCashApply> list=reduceCashApplyDao.queryApplyList(paramMap);
		Integer totalSize=reduceCashApplyDao.countApplyList(paramMap);
		
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
	public void  reduceCashApply(Integer userId,Integer money){
		ReduceCashApply cashApply= new ReduceCashApply();
		cashApply.setMoney(money);
		cashApply.setUserId(userId);
		cashApply.setStatus(CashApplyStatus.newadd.getValue());
		reduceCashApplyDao.insert(cashApply);
	}
	
	/**
	 * 查询详情
	 * @param cashApplyId
	 */
	public ReduceCashApply  reduceApplyDetail(Integer cashApplyId){
		return reduceCashApplyDao.selectByPrimaryKey(cashApplyId);
	}
	
	
	/**
	 * 完成支付
	 * @param cashApplyId
	 */
	@Transactional
	public Boolean  finishReducePay(Integer cashApplyId,String alipayNo){
		ReduceCashApply reduceCashApply =reduceCashApplyDao.selectByPrimaryKey(cashApplyId);
		User user=userService.getUserById(reduceCashApply.getUserId());
		if(reduceCashApply.getMoney()>user.getRemindFee()){
			return false;
		}else{
			userService.updateRemindFee(reduceCashApply.getUserId(), reduceCashApply.getMoney()*-1);
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
		userService.updateRemindFee(addCashApply.getUserId(), addCashApply.getMoney());
		addCashApplyDao.finish(cashApplyId);
		return true;
	}

}
