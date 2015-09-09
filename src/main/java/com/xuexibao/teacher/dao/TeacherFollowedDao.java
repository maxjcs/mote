package com.xuexibao.teacher.dao;

import java.util.List;
import java.util.Map;

import com.xuexibao.teacher.core.MybatisMapper;
import com.xuexibao.teacher.model.TeacherFollowed;
@MybatisMapper
public interface TeacherFollowedDao {
    int deleteByPrimaryKey(Long id);

    Long insert(TeacherFollowed record);

    TeacherFollowed selectByPrimaryKey(Long id);

    List<TeacherFollowed> selectAll();

    int updateByPrimaryKey(TeacherFollowed record);
    
    int deleteByTeacherIdAndUserId(Map map);
    
    int getTotalFollowedByTid(String teacherId);
    
    int getTotalFollowedByUserId(String userId);
    
    List<TeacherFollowed> queryByTeacherIdsAndUserId(Map map);
    
    List<TeacherFollowed> isFollowed(Map map);
    
    List<TeacherFollowed> getFollowListByUserId(Map map);
    
    List<String> getFollowedTeacherIdsByUserId(String userId);
    
    List<TeacherFollowed> getFollowedNumByTeacherIds(Map map); 

}
