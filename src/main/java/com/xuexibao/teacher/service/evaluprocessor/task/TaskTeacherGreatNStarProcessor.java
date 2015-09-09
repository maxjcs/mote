package com.xuexibao.teacher.service.evaluprocessor.task;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.xuexibao.teacher.dao.AudioApproveDao;
import com.xuexibao.teacher.model.Audio;
import com.xuexibao.teacher.model.AudioApprove;
import com.xuexibao.teacher.model.Teacher;
import com.xuexibao.teacher.service.evaluprocessor.EvaluProcessor;

/**
 * @author oldlu
 */
@Component
public class TaskTeacherGreatNStarProcessor extends TaskEvaluProcessor implements EvaluProcessor {
	private static Logger logger = LoggerFactory.getLogger(TaskTeacherGreatNStarProcessor.class);

	@Resource
	private AudioApproveDao audioApproveDao;

	/**
	 * 大于3星老师，默认好品；赠送积分;进入音频库
	 */
	public void postSubmitAudio(Audio audio, Teacher teacher) {
		logger.info("teacher great n star submit audio");
		AudioApprove approve = super.addGoodApprove(audio.getId());

		super.addAudioToPayClub(approve, teacher);

		// super.givePoint(approve, teacher);// 赠送积分 v1.3版本，积分变化仅来源于学生评价
	}

	public void postAuditAudio(AudioApprove audio, Teacher teacher) {
	}

	public Map<String, Object> getAudioIncome(Audio audio, Teacher teacher) {
		return audioMonyUtil.getAudioIncomeByPlanType(audio, teacher);
	}
}
