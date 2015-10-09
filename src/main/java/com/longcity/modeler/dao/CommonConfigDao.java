package com.longcity.modeler.dao;

import org.springframework.stereotype.Repository;

import com.longcity.modeler.core.MybatisMapper;

@MybatisMapper
@Repository
public interface CommonConfigDao {
	
	public String getValueByKey(String key);

}
