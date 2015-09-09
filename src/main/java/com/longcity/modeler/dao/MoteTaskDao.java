package com.longcity.modeler.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.longcity.modeler.model.MoteTask;
import com.xuexibao.teacher.core.MybatisMapper;

@MybatisMapper
@Repository
public interface MoteTaskDao {
	
    int deleteByPrimaryKey(Integer id);

    int insert(MoteTask record);

    MoteTask selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(MoteTask record);
    
    void addOrderNo(Map paramMap);
    
    void updateStatus(Integer id,Integer status);
    
	void selfBuy(Map paramMap);
	
	void returnItem(Map paramMap);
}