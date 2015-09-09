package com.xuexibao.teacher.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xuexibao.teacher.core.MybatisMapper;
import com.xuexibao.teacher.model.Audio;
import com.xuexibao.teacher.model.AudioDetail;
import com.xuexibao.teacher.model.vo.RecordedVO;

/**
 * 
 * @author oldlu
 * 
 */
@MybatisMapper
@Repository
public interface AudioDao {

	void updateAudio(Audio audio);
	void updateWbTypeUrlPayAudio(Audio audio);

	void saveAudio(Audio audio);

	Audio queryAudioById(String audioId);

	Audio queryAudioByTeacherIdQuestId(String teacherId, Long realId);

	List<RecordedVO> getRecordedList(Map<String,Object> paramMap);

	AudioDetail queryAudioDetailById(String audioId);

	AudioDetail getQuestDetail(Map<String, Object> paramMap);

	void updateStatus(String audioId,int status);

	List<Audio> queryAudioByQuestionIdList(Map<String, Object> param);

	int countAudio(String teacherId);

	int countTaskAudio(Map<String, Object> paramMap);
}
