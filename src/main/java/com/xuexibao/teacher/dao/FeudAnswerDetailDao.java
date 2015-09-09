package com.xuexibao.teacher.dao;

import java.util.List;
import java.util.Map;

import com.xuexibao.teacher.core.MybatisMapper;
import com.xuexibao.teacher.model.Audio;
import com.xuexibao.teacher.model.FeudAnswerDetail;
import com.xuexibao.teacher.model.vo.FinishFeudAnswerDetailVO;
@MybatisMapper
public interface FeudAnswerDetailDao {
    long deleteByPrimaryKey(Long id);

    long insert(FeudAnswerDetail record);
    
    long selectLastInsertId();

    FeudAnswerDetail selectByPrimaryKey(Long id);

    List<FeudAnswerDetail> selectAll();
    
    List<FinishFeudAnswerDetailVO> completeDirectFeudList(Map<String, Object> paramMap);
    int completeDirectFeudListCount(Map<String, Object> paramMap);
    
    List<FinishFeudAnswerDetailVO> completeFeudList(Map<String, Object> paramMap);
    int completeFeudListCount(Map<String, Object> paramMap);
    
    
    
    FinishFeudAnswerDetailVO completeFeudDetail(String feudAnswerDetailId);
    
    int updateByPrimaryKey(FeudAnswerDetail record);
    int updateExpireByTeacherId(String teacherId);
    
    List<FeudAnswerDetail> selectPrepareByFeudQuestIdAndTeacherId(FeudAnswerDetail record);
    
    
    List<FeudAnswerDetail> seletExpireByTeacherId(String teacherId);
    
    
    FeudAnswerDetail getFeudAnswerDetailByWhiteBoardId(String whiteBoardId);
    int hasCommitQuestWithTeacher(FeudAnswerDetail record);


}