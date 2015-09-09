/**
 * 
 */
package com.xuexibao.teacher.dao;

import java.util.List;

import com.xuexibao.teacher.core.MybatisMapper;
import com.xuexibao.teacher.model.EvaluationPointConf;

/**
 * @author maxjcs
 *
 */
@MybatisMapper
public interface EvaluationPointConfDao {
	
	public List<EvaluationPointConf> selectAll();

}
