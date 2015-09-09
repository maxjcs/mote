package com.xuexibao.teacher.dao;
import com.xuexibao.teacher.core.MybatisMapper;
import com.xuexibao.teacher.model.TeacherReport;

/**
 * 
 * @author oldlu
 *
 */
@MybatisMapper
public interface TeacherReportDao {

	TeacherReport loadTeacherReport(String id);


	void addTeacherReport(TeacherReport teacherreport);

	void deleteTeacherReport(String id);

}
