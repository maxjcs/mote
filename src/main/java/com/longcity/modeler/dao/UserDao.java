package com.longcity.modeler.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.longcity.modeler.core.MybatisMapper;
import com.longcity.modeler.model.User;

@MybatisMapper
@Repository
public interface UserDao {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    User selectByPrimaryKey(Integer id);

    int updateMote(User record);
    
    int updateSeller(User record);
    
    void updateRemindFee(Integer userId,Integer fee);
    
    User selectByPhoneNumber(String phoneNumber);
    
    void updatePassword(User record);
    
    @SuppressWarnings("rawtypes")
	void freezeFee(Map praamMap);
    
    void updateFreezeFee(Integer userId,Integer fee);
    
    void approve(Integer userId,Integer status);
    
    List<User> querySellerList(Integer maxId);
    
    List<User> queryMoteList(Integer maxId);
    
    
}