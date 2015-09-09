package com.xuexibao.teacher.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 
 * @author oldlu
 * 
 */
@Service
public class ErrorCorrectionService {
	private static Logger logger = LoggerFactory.getLogger(ErrorCorrectionService.class);
//
//	@Resource
//	private ErrorCorrectionDao errorCorrectionDao;
//
//	public void addErrorCorrection(ErrorCorrection params) {
//		if (!errorCorrectionDao.hasErrorCorrection(params)) {
//			params.setCheckStatus(ErrorCheckStatus.noCheck.getValue());
//
//			errorCorrectionDao.addErrorCorrection(params);
//		} else {
//			logger.info("the teacher has correct the quesiton [" + params.getQuestionId() + "]");
//		}
//	}
}
