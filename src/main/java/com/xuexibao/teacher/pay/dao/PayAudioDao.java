package com.xuexibao.teacher.pay.dao;

import java.util.List;
import java.util.Map;

import com.xuexibao.teacher.pay.model.PayAudio;

/**
 * 
 * @author oldlu
 *
 */
public interface PayAudioDao {
	void addPayAudio(PayAudio audio);
	void updateWbTypeUrlPayAudio(PayAudio audio);

	PayAudio queryAudioById(String audioId);

	List<PayAudio> queryAudioByQuestionIdList(Map<String, Object> param);
	
	List<PayAudio> queryAudiosByQuestionId(Integer questionId);
}
