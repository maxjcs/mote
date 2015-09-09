/**
 * 
 */
package com.xuexibao.teacher.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xuexibao.teacher.dao.TeacherFollowedDao;
import com.xuexibao.teacher.model.TeacherFollowed;

/**
 * @author maxjcs
 *
 */
@Service
public class TeacherFollowedService {
	
	@Resource
	TeacherFollowedDao teacherFollowed;
	
	/**
	 * 学生是否关注老师
	 * @param teacherId
	 * @param userId
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Boolean isFollowed(String teacherId,String userId) {
		
		Map map  = new HashMap();
		map.put("teacherId", teacherId);
		map.put("userId", userId);
		List<TeacherFollowed> list =teacherFollowed.isFollowed(map);
		
		if(list.size()>0){
			return true;
		}
		
		return false;
	}
	
	/**
	 * 学生关注老师的数量
	 * @param teacherId
	 * @param userId
	 * @return
	 */
	public int getTotalFollowedByUserId(String userId) {
		int followedNum =teacherFollowed.getTotalFollowedByUserId(userId);
		return followedNum;
	}

}
