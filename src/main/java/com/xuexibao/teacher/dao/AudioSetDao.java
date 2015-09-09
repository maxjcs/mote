package com.xuexibao.teacher.dao;

import java.util.List;
import java.util.Map;

import com.xuexibao.teacher.core.MybatisMapper;
import com.xuexibao.teacher.model.AudioBelongSet;
import com.xuexibao.teacher.model.AudioSet;
import com.xuexibao.teacher.model.AudioSetDetail;
import com.xuexibao.teacher.model.AudioSetRank;
import com.xuexibao.teacher.model.AudioUpload;
import com.xuexibao.teacher.model.vo.AudioBelongAudioSetCount;

/**
 * 
 * @author oldlu
 * 
 */
@MybatisMapper
public interface AudioSetDao {

	AudioSet loadAudioSet(String audiosetId);

	List<AudioSet> listAudioSet(String teacherId, int start, int limit);

	Integer countAudioSet(String teacherId);

	void addAudioSet(AudioSet audioset);

	List<AudioSetDetail> listAudioSetDetailBySetId(String setId);

	void batchAddAudioSetDetail(List<AudioSetDetail> list);

	List<AudioUpload> listAudio(Map<String, Object> paramMap);

	List<AudioSet> listAudioSetByIds(String[] aryIds);

	List<String> listAudioDate(Integer source);

	void updateAudioSet(AudioSet set);

	void updateAudioSetDescription(AudioSet set);

	void removeAudioSet(AudioSet set);

	List<AudioSetRank> queryAudioSetBySortType(Map<String, Object> paramMap);

	List<AudioSetRank> queryAudioSetTeacherByIds(String[] data);

	int minOrderNo(String teacherId);

	void setTopAudioSet(AudioSet set);

	void clearTopAudioSet(AudioSet set);

	void addAudioSetGrade(Map<String, Object> paramMap);

	List<Integer> getSubjectIdsByQuesiton(List<AudioSetDetail> items);

	void addAudioSetSubject(Map<String, Object> paramMap);

	String getSubjectIdsBySetId(String setId);

	String getGradeIdsBySetId(String setId);
	
	List<Map<String,String>> getGradeIdsBySetIds(List<AudioBelongSet> ary);

	void deleteGradeBySetId(String id);

	List<AudioBelongSet> queryAudioSetByAudioIds(String[] audioIds);

	void setAudioSetFreeStatus(AudioSet set);

	List<AudioBelongAudioSetCount> listAudioBelongAudioSetCount(List<String> noCacheAudioIds);
}
