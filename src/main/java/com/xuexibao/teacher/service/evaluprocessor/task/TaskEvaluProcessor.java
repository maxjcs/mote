package com.xuexibao.teacher.service.evaluprocessor.task;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xuexibao.teacher.model.RatingIncomeConfig;
import com.xuexibao.teacher.model.Teacher;
import com.xuexibao.teacher.service.EvaluationService;
import com.xuexibao.teacher.service.RatingIncomeConfigService;
import com.xuexibao.teacher.service.evaluprocessor.BaseEvaluProcessor;
import com.xuexibao.teacher.util.GsonUtil;

/**
 * @author oldlu
 */
public class TaskEvaluProcessor extends BaseEvaluProcessor {
	private static Logger logger = LoggerFactory.getLogger(TaskEvaluProcessor.class);

	@Resource
	protected RatingIncomeConfigService ratingIncomeConfigService;
	@Resource
	protected EvaluationService evaluationService;

	// 赠送积分
	// public void givePoint(AudioApprove approve, Teacher teacher) {
	// Evaluation evalution =
	// evaluationService.getEvaluationByLevel(approve.getEvalution(),
	// teacher.getTeacherIdentify());
	//
	// pointLogService.addPoint(teacher.getId(), evalution.getPoint(),
	// evalution.getDescription());
	// }

	// 支付录题费用
	public void giveAudioFee(String audioId, Teacher teacher) {
		RatingIncomeConfig cfg = ratingIncomeConfigService.getRatingByTeacher(teacher);
		logger.info("教师身份、星级" + teacher.getPhoneNumber() + "," + teacher.getTeacherIdentify() + "," + GsonUtil.obj2Json(cfg));
		if (cfg != null) {
			logger.info("获得录题[" + audioId + "]收入[" + teacher.getId() + "]" + cfg.getDescription());

			feeService.addFee(teacher.getId(), cfg.getFee(), audioId, cfg.getDescription() + "[" + cfg.getFee() + "]");
		}
	}
}
