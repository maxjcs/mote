package com.longcity.modeler.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.longcity.modeler.core.MybatisMapper;
import com.longcity.modeler.model.MoteTask;
import com.longcity.modeler.model.vo.TaskVO;

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
	
	@SuppressWarnings("rawtypes")
	void returnItem(Map paramMap);
	
	@SuppressWarnings("rawtypes")
	List<TaskVO> stasticByTaskIds(Map paramMap2);
	
}