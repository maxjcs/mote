package com.xuexibao.teacher.dao;

import java.util.List;
import java.util.Map;

import com.xuexibao.teacher.core.MybatisMapper;
import com.xuexibao.teacher.model.TeacherMessage;

/**
 * 
 * @author yingmingbao
 * 
 */
@MybatisMapper
public interface TeacherMessageDao {

	int addTeacherMessage(TeacherMessage teacherMessage);

	List<TeacherMessage> getTeacherMessage(Map<String, Object> paramMap);

	int countMessageByTeacherId(String teacherId);
	
	int getMessageNumByTeacherId(String teacherId);

	TeacherMessage getLastMessageByTeacherId(String teacherId);

	TeacherMessage getMessageById(Long replyId);

	Map<String, Object> summaryTeacherMessage(String teacherId);

	void removeTeacherMessage(Long messgseId);

	List<TeacherMessage> listMesageByIds(List<Long> ids);

	List<TeacherMessage> nextPageTeacherMessage(Map<String, Object> paramMap);

	Map<String, Object> summaryReplyMessage(Map<String, Object> paramMap);

	List<TeacherMessage> nextPageReplyMessage(Map<String, Object> paramMap);
 
	Long getHotMessageIdByTeacherId(String teacherId);
}

