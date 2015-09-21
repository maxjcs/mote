package com.longcity.modeler.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.longcity.modeler.core.MybatisMapper;
import com.longcity.modeler.model.TradeFlow;
import com.longcity.modeler.model.vo.TradeFlowVO;

@MybatisMapper
@Repository
public interface TradeFlowDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TradeFlow record);

    TradeFlow selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(TradeFlow record);
    
    @SuppressWarnings("rawtypes")
	List<TradeFlowVO> getTaskIncomeList(Map paramMap);
    
    @SuppressWarnings("rawtypes")
	Integer countTaskIncomeList(Map paramMap);
}