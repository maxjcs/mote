package com.longcity.modeler.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.longcity.modeler.model.User;
import com.xuexibao.teacher.core.MybatisMapper;

@MybatisMapper
@Repository
public interface UserDao {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    User selectByPrimaryKey(Integer id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);
    
    void updateRemindFee(Integer userId,Integer fee);
}