package com.longcity.modeler.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.longcity.modeler.core.MybatisMapper;
import com.longcity.modeler.model.Message;

@MybatisMapper
@Repository
public interface MessageDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Message record);

    Message selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(Message record);
    
    List<Message> list(Map paramMap);
    
    int countMessageByType(String type);
}