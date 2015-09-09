package com.xuexibao.teacher.dao;

import com.xuexibao.teacher.core.MybatisMapper;
import com.xuexibao.teacher.model.AudioUpload;

import java.util.List;
import java.util.Map;
@MybatisMapper
public interface AudioUploadDao {
    int deleteByPrimaryKey(String id);

    int insert(AudioUpload record);

    AudioUpload selectByPrimaryKey(String id);

    List<AudioUpload> selectAll();

    int updateByPrimaryKey(AudioUpload record);
    
    void updateAudioNameById(Map  map);
    
	List<AudioUpload> queryByTeacherId(Map map);
	
	List<AudioUpload> queryByAudioIds(Map map);
	
	AudioUpload queryByTeacherIdAndQuestionId(Map map);
}