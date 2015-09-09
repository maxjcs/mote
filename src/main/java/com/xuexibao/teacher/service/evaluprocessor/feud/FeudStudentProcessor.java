package com.xuexibao.teacher.service.evaluprocessor.feud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.xuexibao.teacher.enums.FeudEvaluateStatus;
import com.xuexibao.teacher.model.Audio;
import com.xuexibao.teacher.model.AudioApprove;
import com.xuexibao.teacher.model.StarPoint;
import com.xuexibao.teacher.model.Teacher;
import com.xuexibao.teacher.service.evaluprocessor.EvaluProcessor;

/**
 * @author oldlu
 */
@Component
public class FeudStudentProcessor extends FeudEvaluProcessor implements EvaluProcessor {
	private static Logger logger = LoggerFactory.getLogger(FeudStudentProcessor.class);

	/**
	 * 支付如题费用，进入音频库
	 */
	public void postSubmitAudio(Audio audio, Teacher teacher) {
		logger.info("FeudStudentProcessor post submit audio");
		super.addAudioToPayClub(audio.getId(), teacher);
		
		super.giveFeudFee(audio.getId(), teacher);// 获取录题费用
		// 赠送积分
		//1.3版本不再赠送积分
//		StarPoint starPoint = teacherService.getStarPointByTeacherId(teacher.getId());
//		if (starPoint.getStar() > 2) {
//		 super.giveFeudPoint(FeudEvaluateStatus.good.getValue(), teacher);
//		}
	}

	public void postAuditAudio(AudioApprove audio, Teacher teacher) {
	}
}
