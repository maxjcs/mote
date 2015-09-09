package com.xuexibao.teacher.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xuexibao.teacher.dao.FeedbackDao;
import com.xuexibao.teacher.model.Feedback;


/**
 * 
 * @author oldlu
 *
 */
@Service
public class FeedbackService {
 	@Resource
	private FeedbackDao feedbackDao;

	public void addFeedback(Feedback feedback) {
		feedbackDao.addFeedback(feedback);
	}
}
