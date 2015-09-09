package com.xuexibao.teacher.service.evaluprocessor.task;

import java.util.Map;

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
public class TaskTeacherLessNStarProcessor extends TaskEvaluProcessor implements EvaluProcessor {
	private static Logger logger = LoggerFactory.getLogger(TaskTeacherLessNStarProcessor.class);

	/**
	 * 小于3星老师，后台评价不做处理
	 */
	public void postSubmitAudio(Audio audio, Teacher teacher) {
		// super.giveAudioFee(audio.getId(), teacher);
	}

	/**
	 * 小于三星老师，运营审核后赠送积分，支付录题费用,进入音频库
	 */
	public void postAuditAudio(AudioApprove approve, Teacher teacher) {
		super.addAudioToPayClub(approve, teacher);

		// super.givePoint(approve, teacher); v1.3版本，积分变化仅来源于学生评价
	}

	public Map<String, Object> getAudioIncome(Audio audio, Teacher teacher) {
		return audioMonyUtil.getAudioIncomeByPlanType(audio, teacher);
	}
}
