package com.xuexibao.teacher.service.evaluprocessor.feud;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xuexibao.teacher.model.FeudPointFeeConf;
import com.xuexibao.teacher.model.StarPoint;
import com.xuexibao.teacher.model.Teacher;
import com.xuexibao.teacher.service.FeudPointFeeConfService;
import com.xuexibao.teacher.service.TeacherService;
import com.xuexibao.teacher.service.evaluprocessor.BaseEvaluProcessor;

/**
 * @author oldlu
 */
public class FeudEvaluProcessor extends BaseEvaluProcessor {
	private static Logger logger = LoggerFactory.getLogger(FeudEvaluProcessor.class);
	@Resource
	protected FeudPointFeeConfService feudPointFeeConfService;
	@Autowired
	protected TeacherService teacherService;

	// 赠送抢答积分 
	public void giveFeudPoint(int starEvaluate, Teacher teacher) {
		FeudPointFeeConf feudPointFee = feudPointFeeConfService.getFeudPointByStarAndTeacherIdentify(starEvaluate,
		teacher.getTeacherIdentify());
		pointLogService.addPoint(teacher.getId(), feudPointFee.getValue().intValue(), feudPointFee.getDescription());
	}

	// 支付抢答费用
	public void giveFeudFee(String audioId, Teacher teacher) {
		StarPoint starPoint = teacherService.getStarPointByTeacherId(teacher.getId());

		FeudPointFeeConf feudPointFee = feudPointFeeConfService.getFeudFeeByStarAndTeacherIdentify(starPoint.getStar(),
		teacher.getTeacherIdentify());
		if (feudPointFee != null) {
			logger.info("获得抢题[" + audioId + "]收入[" + teacher.getId() + "]" + feudPointFee.getValue());
			feeService.addFee(teacher.getId(), feudPointFee.getValue().intValue(), audioId, "抢题费用");
		}
	}
}
