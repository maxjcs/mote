package com.xuexibao.teacher.dao;

import java.util.List;

import com.xuexibao.teacher.core.MybatisMapper;
import com.xuexibao.teacher.model.AudioApprove;
import com.xuexibao.teacher.model.AudioSetCount;
import com.xuexibao.teacher.model.AudioUpload;
import com.xuexibao.teacher.model.Explanation;
import com.xuexibao.teacher.model.FeudAnswerDetail;
import com.xuexibao.teacher.model.MessageReplyCount;
import com.xuexibao.teacher.model.Question;
import com.xuexibao.teacher.model.Teacher;

/**
 * @author oldlu
 * 
 */
@MybatisMapper
public interface CommonMapper {

	List<Teacher> listTeacherByIds(String[] ids);

	List<Question> listQuestionByRealIds(List<Long> ids);

	List<AudioApprove> listApproveByAudioIds(List<String> ids);

	List<AudioUpload> listAudioByIds(List<String> ids);

	List<Explanation> listExplantationByAudioIds(List<String> ids);

	List<AudioSetCount> listAudioSetDetailCount(List<String> setids);

	List<FeudAnswerDetail> listFeudAnswerDetailByAudioIds(List<String> ids);
	
	List<MessageReplyCount> listTeacherReplyCount(List<Long> ids);
}