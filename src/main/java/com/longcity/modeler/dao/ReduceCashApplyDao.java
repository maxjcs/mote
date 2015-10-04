package com.longcity.modeler.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.longcity.manage.model.param.QueryCashParamVO;
import com.longcity.manage.model.vo.CashApplyVO;
import com.longcity.modeler.core.MybatisMapper;
import com.longcity.modeler.model.ReduceCashApply;

@MybatisMapper
@Repository
public interface ReduceCashApplyDao {
    int deleteByPrimaryKey(Integer id);

    int insert(ReduceCashApply record);

    ReduceCashApply selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(ReduceCashApply record);
    
    void finishPay(Integer id,String alipayNo);
    
    @SuppressWarnings("rawtypes")
	List<ReduceCashApply> queryApplyList(Map paramMap);
    
    @SuppressWarnings("rawtypes")
	int countApplyList(Map paramMap);
    
    int countReduceCashList(QueryCashParamVO vo);
    
    List<CashApplyVO> reduceCashList(QueryCashParamVO vo);
    
}