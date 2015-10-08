package com.longcity.modeler.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.longcity.modeler.core.MybatisMapper;
import com.longcity.modeler.model.MoteFollow;
import com.longcity.modeler.model.Task;

@MybatisMapper
@Repository
public interface MoteFollowDao {
	
	int insert(MoteFollow record);
	
	void delete(Integer userId,Integer taskId);
	
	List<MoteFollow> queryByMoteIdAndTaskId(Integer userId,Integer taskId);
	
	List<Task> getFollowList(Map paramMap);
	
	int countFollowList(Map paramMap);

}
