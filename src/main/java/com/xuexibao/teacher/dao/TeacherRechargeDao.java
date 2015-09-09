package com.xuexibao.teacher.dao;
import com.xuexibao.teacher.core.MybatisMapper;
import com.xuexibao.teacher.model.TeacherRecharge;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author oldlu
 *
 */
@MybatisMapper
public interface TeacherRechargeDao {

	TeacherRecharge loadTeacherRecharge(String id);

	List<TeacherRecharge> listTeacherRecharge(Map<String,Object> paramMap);

	Integer countTeacherRecharge(TeacherRecharge teacherrecharge);

	void addTeacherRecharge(TeacherRecharge teacherrecharge);

	void deleteTeacherRecharge(String id);

	int isExistTeacherRecharge(TeacherRecharge teacherrecharge);

	TeacherRecharge getRechargeByTeacherIdStudentId(Map<String, Object> map);

	void updateStudentRecharge(TeacherRecharge exsits);
}
