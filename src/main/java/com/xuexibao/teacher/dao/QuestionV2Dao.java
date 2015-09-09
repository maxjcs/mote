package com.xuexibao.teacher.dao;

import java.util.List;

import com.xuexibao.teacher.core.MybatisMapper;
import com.xuexibao.teacher.model.Question;

/**
 * 
 * @author fengbin
 * @date 2015-03-26 新版question表 dao 类
 */
@MybatisMapper
public interface QuestionV2Dao {
	int deleteByPrimaryKey(Long id);

	int insert(Question record);

	Question selectByPrimaryKey(Long id);

	List<Question> selectAll();

	int updateByPrimaryKey(Question record);

	Question queryByRealId(Long realId);

	void updateQuestionStatus(long questionId, int value);

	void updateErrorNumber(long questionId);
	
	List<Question> getQuestionsByRealIds(List realIds);
	
	void updateAnswerByRealId(Question record);
}