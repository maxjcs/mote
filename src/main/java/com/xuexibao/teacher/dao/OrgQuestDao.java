package com.xuexibao.teacher.dao;
import java.util.List;

import com.xuexibao.teacher.core.MybatisMapper;
import com.xuexibao.teacher.model.OrgQuest;

/**
 * 
 * @author oldlu
 *
 */
@MybatisMapper
public interface OrgQuestDao {

	List<Integer> listInsertOrgId(long realId, List<Integer> orgIds);

	void batchInsert(List<OrgQuest> batchInsert);
}
