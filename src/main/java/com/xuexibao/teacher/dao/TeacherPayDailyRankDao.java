/**
 * 
 */
package com.xuexibao.teacher.dao;

import java.util.List;

import com.xuexibao.teacher.core.MybatisMapper;
import com.xuexibao.teacher.model.TeacherPayDailyRank;

/**
 * @author maxjcs
 *
 */
@MybatisMapper
public interface TeacherPayDailyRankDao {
	
	List<TeacherPayDailyRank> getByTeacherIds(List<String> teacherIds);

}
