package com.xuexibao.teacher.dao;
import java.util.List;
import java.util.Map;

import com.xuexibao.teacher.core.MybatisMapper;
import com.xuexibao.teacher.model.Dynamic;
import com.xuexibao.teacher.model.DynamicMessage;

/**
 * 
 * @author oldlu
 *
 */
@MybatisMapper
public interface DynamicMessageDao {

	DynamicMessage loadDynamicMessage(Long id);

	List<DynamicMessage> listDynamicMessage(Map<String,Object> paramMap);

	Integer countDynamicMessage(DynamicMessage dynamicmessage);

	void addDynamicMessage(DynamicMessage dynamicmessage);

	void updateDynamicMessage(DynamicMessage dynamicmessage);

	void deleteDynamicMessage(Long id);

	int isExistDynamicMessage(DynamicMessage dynamicmessage);

	List<Dynamic> groupCountMesasgeByDynamicIds(List<Long> noCacheIds);

	void restoreDynamicMessage(Long id);
}
