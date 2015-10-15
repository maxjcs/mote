package com.longcity.modeler.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.longcity.manage.model.param.QuerySellerDetailParamVO;
import com.longcity.manage.model.param.QueryTaskDetailParamVO;
import com.longcity.manage.model.param.QueryTaskParamVO;
import com.longcity.modeler.core.MybatisMapper;
import com.longcity.modeler.model.Task;
import com.longcity.modeler.model.vo.MoteTaskVO;

@MybatisMapper
@Repository
public interface TaskDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Task record);

    Task selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(Task record);
    
    void updateStatus(Integer taskId,Integer status);
    
    @SuppressWarnings("rawtypes")
	List<Task> getNewTaskList(Map paramMap);
    
    int countNewTaskList(Map paramMap);
    
    @SuppressWarnings("rawtypes")
	List<Task> getStasticTaskList(Map paramMap);
    
    int countStasticTaskList(Map paramMap);
    
    int countTaskByUserId(QuerySellerDetailParamVO vo);
    
    List<Task> queryTaskByUserId(QuerySellerDetailParamVO vo);
    
    int getTaskNumBySellerId(Integer sellerId);
    
    int countTaskList(QueryTaskParamVO paramVO);
    
    List<Task> queryTaskList(QueryTaskParamVO paramVO);
    
    int countMoteTaskByTaskId(QueryTaskDetailParamVO paramVO);
    
    List<MoteTaskVO> queryMoteTaskByTaskId(QueryTaskDetailParamVO paramVO);
    
    int countTask(Map paramMap);
    
    List<Task> searchTask(Map paramMap);
    
    void updateTaskUrl(Integer id,String url);
    
    void updateAcceptNumber(Integer id,Integer num);
    
}