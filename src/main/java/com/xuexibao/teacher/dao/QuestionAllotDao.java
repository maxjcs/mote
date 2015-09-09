package com.xuexibao.teacher.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xuexibao.teacher.core.MybatisMapper;
import com.xuexibao.teacher.model.QuestionAllot;

@MybatisMapper
@Repository
public interface QuestionAllotDao {

	public List<QuestionAllot> queryExpiredId(Map<String, Object> paramMap);

	public void deleteQuestionAllotById(long id);

	public void releaseQuestionAllotByRealId(long id);
}
