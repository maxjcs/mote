package com.xuexibao.teacher.dao;
import java.util.List;
import java.util.Map;

import com.xuexibao.teacher.core.MybatisMapper;
import com.xuexibao.teacher.model.ErrorQuest;
import com.xuexibao.teacher.model.QuestReasonType;
import com.xuexibao.teacher.model.Question;

/**
 * 
 * @author oldlu
 *
 */
@MybatisMapper
public interface ErrorQuestDao {
	List<ErrorQuest> listErrorQuest(Map<String,Object> paramMap);

	void addErrorQuest(Question question);
	
	List<QuestReasonType> getQuestReasonTypeList(List<ErrorQuest> list);

	void updateQuestReadStatus(String[] str);

	void updateQuestAuditStatus(List<ErrorQuest> list);

	void updateQuestion(ErrorQuest item);
}
  