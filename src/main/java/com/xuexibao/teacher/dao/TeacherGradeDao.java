package com.xuexibao.teacher.dao;

import java.util.List;

import com.xuexibao.teacher.core.MybatisMapper;
import com.xuexibao.teacher.model.TeacherGrade;

/**
 * 
 * @author czl
 * 
 */
@MybatisMapper
public interface TeacherGradeDao {

	void deleteByTeacherId(String teacherId);

	void batchInsert(List<TeacherGrade> list);

	List<Integer> queryGradeIdByTeacherId(String teacherId);
}
