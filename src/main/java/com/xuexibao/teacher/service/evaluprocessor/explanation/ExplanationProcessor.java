package com.xuexibao.teacher.service.evaluprocessor.explanation;

import org.springframework.stereotype.Component;

import com.xuexibao.teacher.model.Audio;
import com.xuexibao.teacher.model.AudioApprove;
import com.xuexibao.teacher.model.Teacher;
import com.xuexibao.teacher.service.evaluprocessor.BaseEvaluProcessor;
import com.xuexibao.teacher.service.evaluprocessor.EvaluProcessor;

@Component
public class ExplanationProcessor extends BaseEvaluProcessor implements EvaluProcessor{

	@Override
	public void postSubmitAudio(Audio audio, Teacher teacher) {
		super.addAudioToPayClub(audio.getId(), teacher);
	}

	@Override
	public void postAuditAudio(AudioApprove audio, Teacher teacher) {
	}
}
