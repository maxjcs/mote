package com.xuexibao.teacher.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xuexibao.teacher.core.MybatisMapper;
import com.xuexibao.teacher.model.QuestionAllot;
import com.xuexibao.teacher.model.vo.RecordedVO;

/**
 * 
 * @author oldlu
 * 
 */
@MybatisMapper
@Repository
public interface TaskDao {
	List<RecordedVO> getTaskList(Map<String,Object> paramMap);

	void deleteOldTaskLsit(String teacherId);

	void addNewTaskList(List<QuestionAllot> list);

	List<QuestionAllot> fetchNewTaskList(Map<String, Object> paramMap);
	//为抢答获取相关的
	List<QuestionAllot> getTaskListWithFeud(Map<String, Object> paramMap);
	
	
	List<QuestionAllot> fetchOrgNewTaskList(Map<String, Object> paramMap);

//	int countTask(String teacher);

	void deleteByTeacherIdQuestionId(Map<String, Object> paramMap);

	Map<String, Object> getTaskSummary(String teacherId);

	int countTaskAudioOfTeacher(String teacherId);

	int countCompleteTask(String teacherId);

	void releaseQuestionAllotStatus(List<Long> questionids);

	void lockQuestionAllotStatus(List<Long> questionids);

	List<Long> listAllotQuestionId(String teacherId);

	void updateOrgQuestAllot(Map<String, Object> paramMap);

	List<QuestionAllot> listAllotQuest(String teacherId);

	int countTaskAudioOfTeacher(String teacherId, Date lastCompleteTaskTime);
}
