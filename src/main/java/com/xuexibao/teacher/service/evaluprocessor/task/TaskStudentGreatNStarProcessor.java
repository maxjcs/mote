package com.xuexibao.teacher.service.evaluprocessor.task;

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
public class TaskStudentGreatNStarProcessor extends TaskEvaluProcessor implements EvaluProcessor {
	private static Logger logger = LoggerFactory.getLogger(TaskStudentGreatNStarProcessor.class);

	@Resource
	private AudioApproveDao audioApproveDao;

	/**
	 * 大于3星学生，默认好评；怎送积分，支付如题费用，进入音频库
	 */
	public void postSubmitAudio(Audio audio, Teacher teacher) {
		logger.info("student great n star submit audio");

		AudioApprove approve = super.addGoodApprove(audio.getId());// 默认好品

		super.addAudioToPayClub(approve, teacher);// 进入音频库

//		super.givePoint(approve, teacher);// 赠送积分 v1.3版本，积分变化仅来源于学生评价

		super.giveAudioFee(audio.getId(), teacher);// 获取录题费用
	}

	public void postAuditAudio(AudioApprove audio, Teacher teacher) {
	}
}
