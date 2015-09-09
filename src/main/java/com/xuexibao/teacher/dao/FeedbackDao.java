package com.xuexibao.teacher.dao;

import java.util.List;

import com.xuexibao.teacher.core.MybatisMapper;
import com.xuexibao.teacher.model.Feedback;

/**
 * 
 * @author oldlu
 * 
 */
@MybatisMapper
public interface FeedbackDao {

	List<Feedback> listFeedback(Feedback feedback);

	void addFeedback(Feedback feedback);
}
