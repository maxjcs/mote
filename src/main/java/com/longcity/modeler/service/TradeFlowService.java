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

import com.longcity.modeler.dao.TradeFlowDao;
import com.longcity.modeler.model.vo.TradeFlowVO;

/**
 * @author maxjcs
 *
 */
@Service
public class TradeFlowService {
	
	private static Logger logger = LoggerFactory.getLogger(TradeFlowService.class);
	
	@Resource
	TradeFlowDao tradeFlowDao;
	
	/**
	 * 任务收益
	 * @param userId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getTaskIncomeList(Integer userId,Integer pageNo,Integer pageSize){
		Map resultMap=new HashMap();
		
		Map paramMap=new HashMap();
		paramMap.put("userId", userId);
		paramMap.put("start", (pageNo-1)*pageSize);
		paramMap.put("pageSize", pageSize);
		
		List<TradeFlowVO> voList=tradeFlowDao.getTaskIncomeList(paramMap);
		Integer totalSize=tradeFlowDao.countTaskIncomeList(paramMap);
		resultMap.put("dataList", voList);
		resultMap.put("totalSize", totalSize);
		
		return resultMap;
	}
	
	/**
	 * 商品退还
	 * @param userId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getItemMoneyList(Integer userId,Integer pageNo,Integer pageSize){
		Map resultMap=new HashMap();
		
		Map paramMap=new HashMap();
		paramMap.put("userId", userId);
		paramMap.put("start", (pageNo-1)*pageSize);
		paramMap.put("pageSize", pageSize);
		
		List<TradeFlowVO> voList=tradeFlowDao.getItemMoneyList(paramMap);
		Integer totalSize=tradeFlowDao.countItemMoneyList(paramMap); 
		resultMap.put("dataList", voList);
		resultMap.put("totalSize", totalSize);
		
		return resultMap;
	}
	
	

}
