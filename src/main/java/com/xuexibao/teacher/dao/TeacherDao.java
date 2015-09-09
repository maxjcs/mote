package com.xuexibao.teacher.dao;

import java.util.List;
import java.util.Map;

import com.xuexibao.teacher.core.MybatisMapper;
import com.xuexibao.teacher.model.RecommendTeacherOrder;
import com.xuexibao.teacher.model.StarPoint;
import com.xuexibao.teacher.model.Teacher;
import com.xuexibao.teacher.model.vo.TeacherStartAndPointVO;

@MybatisMapper
public interface TeacherDao {

	void addTeacher(Teacher teacher);

	void updateTeacher(Teacher teacher);

	boolean isExistTeacher(String phoneNumber);

	String getIdByPhoneNumber(String phoneNumber);

	void updatePassword(Teacher teacher);

	void updateSelfDescription(Teacher teacher);

	Teacher loadTeacherById(String teacherId);

	StarPoint getPointAndRatingByTeacherId(String teacherId);

	Teacher getRatingAndUndoTaskDaysByTeacherId(String teacherId);

	List<String> queryTeacherIdByMap(Map<String, Object> paramMap);
	
	List<String> queryUnFollowedTeacherIdByMap(Map<String, Object> paramMap);

	void updatePoint(String teacherId, int point);

	void updateStar(String teacherId, int star);
	
	void updatePoint2(String teacherId, int star);
	
	List<Teacher> getTeachersByIds(List<String> teacherIds);
	
	List<TeacherStartAndPointVO> getTeachersWithStarAndPointByIds(List<String> teacherIds);
	
	void setOnlineTeacher(Teacher teacher);
	
	void setOfflineTeacher(String teacherId);
	
	List<String> listTeacherIdByDeviceId(String deviceId);

	void completeNewTask(String teacherId);

	void updateLastTaskTime(String teacherId);

	void unjoinCapacityTest(String teacherId);

	void updateIosToken(Teacher teacher);
	
	List<RecommendTeacherOrder> getRecommTeacher(Map paramMap);

	void bindBank(Teacher teacher);

	boolean isExistIdNumber(Teacher teacher);

	boolean isExistBankCard(Teacher teacher);

	void updateTeacherPlanType(Teacher teacher);

	void unbindBank(Teacher teacher);
	
	//临时性需求，更新头像地址
	List<Teacher> getTeachersForAvartImg();
	//更新头像url
	void updateAvartImg(String teacherId,String avartUrl);
	
}
