package com.xuexibao.teacher.dao;

import java.util.List;
import java.util.Map;

import com.xuexibao.teacher.core.MybatisMapper;
import com.xuexibao.teacher.model.Explanation;
@MybatisMapper
public interface ExplanationDao{
	
    int deleteByPrimaryKey(Long id);

    int insert(Explanation record);

    Explanation selectByPrimaryKey(Long id);

    int updateByPrimaryKey(Explanation record);
    
    @SuppressWarnings("rawtypes")
	List<Explanation> getUnRecordedList(Map paramMap);
    
    @SuppressWarnings("rawtypes")
	List<Explanation> getRecordedList(Map paramMap);
    
    int getRecordedCount(String teachreId);
    
    Explanation queryByImgId(String imgId);
    
    Explanation getLastRecord(String teacherId);
    
}