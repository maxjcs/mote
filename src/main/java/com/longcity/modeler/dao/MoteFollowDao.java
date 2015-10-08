package com.longcity.modeler.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.longcity.modeler.core.MybatisMapper;
import com.longcity.modeler.model.MoteFollow;

@MybatisMapper
@Repository
public interface MoteFollowDao {
	
	int insert(MoteFollow record);
	
	void delete(Integer userId,Integer taskId);
	
	List<MoteFollow> queryByMoteIdAndTaskId(Integer userId,Integer taskId);

}
