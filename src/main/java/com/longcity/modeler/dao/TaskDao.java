package com.longcity.modeler.dao;

import org.springframework.stereotype.Repository;

import com.longcity.modeler.core.MybatisMapper;
import com.longcity.modeler.model.Task;

@MybatisMapper
@Repository
public interface TaskDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Task record);

    Task selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(Task record);
    
    void updateStatus(Integer taskId,Integer status);
}