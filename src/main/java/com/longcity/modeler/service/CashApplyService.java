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

import com.longcity.modeler.dao.CashApplyDao;
import com.longcity.modeler.enums.CashApplyStatus;
import com.longcity.modeler.model.CashApply;

/**
 * @author maxjcs
 *
 */
@Service
public class CashApplyService {
	
	private static Logger logger = LoggerFactory.getLogger(CashApplyService.class);
	
	@Resource
	CashApplyDao cashApplyDao;
	
	
	
	
	/**
	 * 申请提现
	 * @param userId
	 * @param money
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void  queryList(Integer userId,Integer status,Integer pageNo,Integer pageSize){
		Map paramMap=new HashMap();
		paramMap.put("userId", userId);
		paramMap.put("status", status);
		paramMap.put("start", pageSize*(pageNo-1));
		paramMap.put("pageSize", pageSize);
		cashApplyDao.queryList(paramMap);
	}
	
	
	/**
	 * 申请提现
	 * @param userId
	 * @param money
	 */
	public void  add(Integer userId,Integer money){
		CashApply cashApply= new CashApply();
		cashApply.setMoney(money);
		cashApply.setUserId(userId);
		cashApply.setStatus(CashApplyStatus.newadd.getValue());
		cashApplyDao.insert(cashApply);
	}
	
	/**
	 * 查询详情
	 * @param cashApplyId
	 */
	public CashApply  detail(Integer cashApplyId){
		return cashApplyDao.selectByPrimaryKey(cashApplyId);
	}
	
	
	/**
	 * 完成支付
	 * @param cashApplyId
	 */
	public void  finishPay(Integer cashApplyId){
		cashApplyDao.finishPay(cashApplyId);
	}

}
