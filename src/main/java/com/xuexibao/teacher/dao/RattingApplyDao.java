package com.xuexibao.teacher.dao;

import java.util.List;
import java.util.Map;

import com.xuexibao.teacher.core.MybatisMapper;
import com.xuexibao.teacher.model.RattingApply;

/**
 * 
 * @author oldlu
 * 
 */
@MybatisMapper
public interface RattingApplyDao {

	List<RattingApply> listRattingApply(Map<String, Object> map);

	void addRattingApply(RattingApply rattingapply);

	RattingApply getLastAuditRattingApply(String teacherId);
	
	RattingApply getLastUnAuditRattingApply(String teacherId);
	
	RattingApply getLastRattingApply(String teacherId);

	List<String> listAudioIdByTime(Map<String, Object> paramMap);
}
