package com.longcity.modeler.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.longcity.modeler.model.TaskPic;
import com.xuexibao.teacher.core.MybatisMapper;

@MybatisMapper
@Repository
public interface TaskPicDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TaskPic record);

    TaskPic selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(TaskPic record);
    
    void addImageUrl(Integer moteTaskId,String url);
    
    List<TaskPic> queryMoteTask(Integer moteTaskId,Integer userId);
}