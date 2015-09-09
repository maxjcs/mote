package com.xuexibao.teacher.dao;

import com.xuexibao.teacher.core.MybatisMapper;
import com.xuexibao.teacher.model.Evaluation;
import java.util.List;

/**
 * 
 * @author oldlu
 * 
 */
@MybatisMapper
public interface EvaluationDao {

	List<Evaluation> allEvaluation();

	void updateEvaluation(Evaluation evaluation);

}
