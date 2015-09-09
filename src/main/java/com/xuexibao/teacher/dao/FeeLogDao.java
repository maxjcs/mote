package com.xuexibao.teacher.dao;
import com.xuexibao.teacher.core.MybatisMapper;
import com.xuexibao.teacher.model.FeeLog;

/**
 * 
 * @author oldlu
 *
 */
@MybatisMapper
public interface FeeLogDao {
 
	void addFeeLog(FeeLog feelog);

 
}
