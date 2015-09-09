package com.xuexibao.teacher.service.evaluprocessor.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.xuexibao.teacher.model.Audio;
import com.xuexibao.teacher.model.AudioApprove;
import com.xuexibao.teacher.model.Evaluation;
import com.xuexibao.teacher.model.Teacher;
import com.xuexibao.teacher.service.evaluprocessor.EvaluProcessor;

/**
 * @author oldlu
 */
@Component
public class TaskStudentLessNStarProcessor extends TaskEvaluProcessor implements EvaluProcessor {
	private static Logger logger = LoggerFactory.getLogger(TaskStudentLessNStarProcessor.class);

	/**
	 * 小于3星学生
	 */
	public void postSubmitAudio(Audio audio, Teacher teacher) {
		// super.giveAudioFee(audio.getId(), teacher);// 支付录题费用
	}

	/**
	 * 小于3星学生，运营审核后赠送积分，支付录题费用，进入音频库
	 */
	public void postAuditAudio(AudioApprove approve, Teacher teacher) {
		super.addAudioToPayClub(approve, teacher);// 进入音频库

//		super.givePoint(approve, teacher);// 赠送积分 v1.3版本，积分变化仅来源于学生评价

		if (approve.getEvalution() == Evaluation.LEVEL_GOOD) {
			super.giveAudioFee(approve.getAudioId(), teacher);// 支付录题费用
		}
	}

}
