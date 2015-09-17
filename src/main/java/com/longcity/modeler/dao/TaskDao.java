package com.longcity.modeler.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.longcity.manage.model.param.QuerySellerDetailParamVO;
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
    
    int getTotalAcceptedNum(Integer taskId);
    
    int getMoteAcceptedNumDaily(Integer moteId);
    
    @SuppressWarnings("rawtypes")
	List<Task> getNewTaskList(Map paramMap);
    
    @SuppressWarnings("rawtypes")
	List<Task> getStasticTaskList(Map paramMap);
    
    int countTaskByUserId(QuerySellerDetailParamVO vo);
    
    List<Task> queryTaskByUserId(QuerySellerDetailParamVO vo);
    
    int getTaskNumBySellerId(Integer sellerId);
    
}