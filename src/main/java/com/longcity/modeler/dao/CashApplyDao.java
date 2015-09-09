package com.longcity.modeler.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.longcity.modeler.model.CashApply;
import com.xuexibao.teacher.core.MybatisMapper;

@MybatisMapper
@Repository
public interface CashApplyDao {
    int deleteByPrimaryKey(Integer id);

    int insert(CashApply record);

    CashApply selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(CashApply record);
    
    void finishPay(Integer id);
    
    List<CashApply> queryList(Map paramMap);
}