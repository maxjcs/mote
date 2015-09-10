package com.longcity.modeler.dao;

import org.springframework.stereotype.Repository;

import com.longcity.modeler.core.MybatisMapper;
import com.longcity.modeler.model.TradeFlow;

@MybatisMapper
@Repository
public interface TradeFlowDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TradeFlow record);

    TradeFlow selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(TradeFlow record);
}