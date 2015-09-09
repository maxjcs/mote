package com.xuexibao.teacher.service.evaluprocessor;

import java.util.Map;

import com.xuexibao.teacher.model.Audio;
import com.xuexibao.teacher.model.AudioApprove;
import com.xuexibao.teacher.model.Teacher;

/**
 * @author oldlu
 */
public interface EvaluProcessor {
	// 提交音频后处理
	void postSubmitAudio(Audio audio, Teacher teacher);

	// 运营审核后处理
	void postAuditAudio(AudioApprove audio, Teacher teacher);

	//获取音频分成
	Map<String,Object> getAudioIncome(Audio audio, Teacher teacher);
}
