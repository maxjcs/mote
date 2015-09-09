package com.xuexibao.teacher.dao;

import com.xuexibao.teacher.core.MybatisMapper;
import com.xuexibao.teacher.model.ErrorCorrection;

/**
 * 
 * @author oldlu
 * 
 */
@MybatisMapper
public interface ErrorCorrectionDao {

	void addErrorCorrection(ErrorCorrection params);

	boolean hasErrorCorrection(String teacherId,Long questionId);

	ErrorCorrection getQuestionCorrection(String teacherId, Long questionId);

}
