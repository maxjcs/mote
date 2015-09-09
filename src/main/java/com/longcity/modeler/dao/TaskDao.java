package com.longcity.modeler.dao;

import org.springframework.stereotype.Repository;

import com.longcity.modeler.model.Task;
import com.xuexibao.teacher.core.MybatisMapper;

@MybatisMapper
@Repository
public interface TaskDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Task record);

    Task selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(Task record);
    
    void updateStatus(Integer taskId,Integer status);
}