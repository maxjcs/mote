package com.xuexibao.teacher.dao;

import java.util.List;

import com.xuexibao.teacher.core.MybatisMapper;
import com.xuexibao.teacher.model.CommonConfig;

/**
 * 
 * @author oldlu
 * 
 */
@MybatisMapper
public interface CommonConfigDao {

	List<CommonConfig> allCfg();

}
