package com.xuexibao.teacher.dao;

import java.util.List;

import com.xuexibao.teacher.core.MybatisMapper;
import com.xuexibao.teacher.model.TeacherSubject;

/**
 * 
 * @author czl
 * 
 */
@MybatisMapper
public interface TeacherSubjectDao {

	void deleteByTeacherId(String teacherId);

	void batchInsert(List<TeacherSubject> list);

	List<Integer> querySubjectIdByTeacherId(String teacherId);
}
