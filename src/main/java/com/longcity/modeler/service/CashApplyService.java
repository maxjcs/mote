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

import com.longcity.modeler.constant.PageConstant;
import com.longcity.modeler.dao.AddCashApplyDao;
import com.longcity.modeler.dao.CashRecordDao;
import com.longcity.modeler.dao.ReduceCashApplyDao;
import com.longcity.modeler.enums.CashApplyStatus;
import com.longcity.modeler.model.AddCashApply;
import com.longcity.modeler.model.CashRecord;
import com.longcity.modeler.model.ReduceCashApply;

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
	CashRecordDao caashRecordDao;
	
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
	 * 提现申请详情
	 * @param userId
	 * @param money
	 */
	public ReduceCashApply  getApplyDetail(Integer id){
		ReduceCashApply apply=reduceCashApplyDao.selectByPrimaryKey(id);
		return apply;
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
		List<CashRecord> cashRecords = caashRecordDao.getRecordList(paramMap);
		Integer totalSize = caashRecordDao.countRecordList(paramMap);
		
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
	public ReduceCashApply  detail(Integer cashApplyId){
		return reduceCashApplyDao.selectByPrimaryKey(cashApplyId);
	}
	
	
	/**
	 * 完成支付
	 * @param cashApplyId
	 */
	public void  finishPay(Integer cashApplyId){
		reduceCashApplyDao.finishPay(cashApplyId);
	}

}
