package com.longcity.modeler.dao;

import org.springframework.stereotype.Repository;

import com.longcity.modeler.core.MybatisMapper;
import com.longcity.modeler.model.User;

@MybatisMapper
@Repository
public interface UserDao {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(User record);
    
    void updateRemindFee(Integer userId,Integer fee);
    
    User selectByPhoneNumber(String phoneNumber);
    
    void updatePassword(User record);
}