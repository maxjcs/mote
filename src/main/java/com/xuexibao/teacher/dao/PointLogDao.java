package com.xuexibao.teacher.dao;
import com.xuexibao.teacher.core.MybatisMapper;
import com.xuexibao.teacher.model.PointLog;

/**
 * 
 * @author oldlu
 *
 */
@MybatisMapper
public interface PointLogDao {
 
	void addPointLog(PointLog pointlog);

 
}
