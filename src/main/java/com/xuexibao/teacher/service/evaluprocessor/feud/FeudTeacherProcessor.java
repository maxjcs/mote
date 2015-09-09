package com.xuexibao.teacher.service.evaluprocessor.feud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.xuexibao.teacher.model.Audio;
import com.xuexibao.teacher.model.AudioApprove;
import com.xuexibao.teacher.model.Teacher;
import com.xuexibao.teacher.service.evaluprocessor.EvaluProcessor;

/**
 * @author oldlu
 */
@Component
public class FeudTeacherProcessor extends FeudEvaluProcessor implements EvaluProcessor {
	private static Logger logger = LoggerFactory.getLogger(FeudTeacherProcessor.class);

	/**
	 * 支付如题费用，进入音频库,默认好评
	 */
	public void postSubmitAudio(Audio audio, Teacher teacher) {
		logger.info("FeudTeacherProcessor post submit audio");
		super.addAudioToPayClub(audio.getId(), teacher);

		super.giveFeudFee(audio.getId(), teacher);// 获取录题费用
		// 1.3版本不在赠送积分
		// 赠送积分
//		StarPoint starPoint = teacherService.getStarPointByTeacherId(teacher.getId());
//		if (starPoint.getStar() > 2) {
//			super.giveFeudPoint(FeudEvaluateStatus.good.getValue(), teacher);
//
//		}
		
	}

	public void postAuditAudio(AudioApprove audio, Teacher teacher) {

	}

}
