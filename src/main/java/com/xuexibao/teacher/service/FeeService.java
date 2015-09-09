package com.xuexibao.teacher.service;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xuexibao.teacher.dao.FeeLogDao;
import com.xuexibao.teacher.model.FeeLog;

/**
 * 
 * @author oldlu
 * 
 */
@Service
public class FeeService {
	private static Logger logger = LoggerFactory.getLogger(FeeService.class);

	@Resource
	private FeeLogDao feeLogDao;
	@Resource
	private StudentApiService studentApiService;

	@Transactional
	public void addFee(String teacherId, int money, String audioId, String description) {
		if (money == 0) {
			logger.info("[" + teacherId + "]对应星级录题费是0");
			return;
		}
		FeeLog feelog = new FeeLog();
		feelog.setMoney(money);
		feelog.setDescription(description+"["+audioId+"]");
		feelog.setTeacherId(teacherId);
		feeLogDao.addFeeLog(feelog);
		studentApiService.updateTeacherBalance(teacherId, money, audioId);

		logger.info("[" + teacherId + "]钱包变化，" + description);
	}

}
