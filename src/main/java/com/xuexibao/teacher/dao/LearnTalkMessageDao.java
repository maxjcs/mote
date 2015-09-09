package com.xuexibao.teacher.dao;

import java.util.List;
import java.util.Map;

import com.xuexibao.teacher.core.MybatisMapper;
import com.xuexibao.teacher.model.FeudAnswerDetail;
import com.xuexibao.teacher.model.LearnMessage;
import com.xuexibao.teacher.model.LearnTalkCount;

@MybatisMapper
public interface LearnTalkMessageDao {
	public List<Long> learnTalkListSubIds(Map<String,Object> param);
	
	public List<LearnMessage> learnTalkformIds(Map<String,Object> param);
	
	public List<Long> learnTalkStudentListSubIds(Map<String,Object> param);
	
	public List<LearnMessage> learnTalkDetail(Map<String,Object> param) ;
	
	public String learnTalkImageId(Map<String,Object> param) ;
	
	public int insertLearnMsg(LearnMessage learnMessage);
	
	public LearnTalkCount countTeacherUnReadMessage(Map<String,Object> param);
	
	public LearnTalkCount countStudentUnReadMessage(Map<String,Object> param);

	public int learnTalkUpdate(Map<String,Object> param);
	
	public int learnTalkStudentUpdate(Map<String,Object> param);
	
	public List<Long>  studentNumber(String teacherId);

	public List<LearnMessage> learnTalkStudentRecentMessage(Map<String, Object> param);
	
	public FeudAnswerDetail getFeudAnswerDetailByWhiteBoardId(String whiteBoardId);

	public List<String> queryIsHudong(Map<String, Object> paramMap);

}
