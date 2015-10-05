/**
 * 
 */
package com.longcity.manage.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.longcity.manage.model.param.QueryCashParamVO;
import com.longcity.manage.model.vo.CashApplyVO;
import com.longcity.modeler.dao.AddCashApplyDao;
import com.longcity.modeler.dao.ReduceCashApplyDao;

/**
 * @author maxjcs
 *
 */
@Service
public class CashService {
	
	@Resource
	AddCashApplyDao addCashApplyDao;
	
	@Resource
	ReduceCashApplyDao reduceCashApplyDao;
	
	public QueryCashParamVO addCashList(QueryCashParamVO paramVO){
		
		Integer total = addCashApplyDao.countAddCashList(paramVO);
        if (total > 0) {
            List<CashApplyVO> rows = addCashApplyDao.addCashList(paramVO);
            for(CashApplyVO vo:rows){
            	String orderNo=vo.getOrderNo();
            	if(StringUtils.isNotEmpty(orderNo)&&StringUtils.length(orderNo)>=2){
            		orderNo="**"+orderNo.substring(2, orderNo.length());
            		vo.setOrderNo(orderNo);
            	}
            	//转换成元
            	vo.setMoney(vo.getMoney()/100);
            }
            paramVO.setTotal(total);
            paramVO.setRows(rows);
        }else{
            paramVO.setTotal(0);
            paramVO.setRows(new ArrayList<CashApplyVO>());
        }
        return paramVO;
		
	}
	
    public QueryCashParamVO reduceCashList(QueryCashParamVO paramVO){
    	Integer total = reduceCashApplyDao.countReduceCashList(paramVO); 
        if (total > 0) {  
            List<CashApplyVO> rows = reduceCashApplyDao.reduceCashList(paramVO);
            for(CashApplyVO vo:rows){
            	vo.setMoney(vo.getMoney()/100);
            }
            paramVO.setTotal(total);
            paramVO.setRows(rows);
        }else{
            paramVO.setTotal(0);
            paramVO.setRows(new ArrayList<CashApplyVO>());
        }
        return paramVO;
	}
    
    

}
