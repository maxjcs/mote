package com.xuexibao.teacher.dao;

import com.xuexibao.teacher.core.MybatisMapper;
import com.xuexibao.teacher.model.AudioStudentEvaluation;

@MybatisMapper
public interface AudioStudentEvaluationDao {
	
    int insert(AudioStudentEvaluation record);
    
    //获取音频的总积分
    Integer getPointByAudioId(String audioId);
    
    AudioStudentEvaluation queryByUserIdAudioId(String userId,String audioId);

}

