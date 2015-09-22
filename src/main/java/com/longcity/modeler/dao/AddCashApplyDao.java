/**
 * 
 */
package com.longcity.modeler.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.longcity.manage.model.param.QueryCashParamVO;
import com.longcity.manage.model.vo.CashApplyVO;
import com.longcity.modeler.core.MybatisMapper;
import com.longcity.modeler.model.AddCashApply;

/**
 * @author maxjcs
 *
 */
@MybatisMapper
@Repository
public interface AddCashApplyDao {
	
	void insert(AddCashApply record);
	
	void delete(Integer id);
	
	List<AddCashApply> queryList(Map paramMap);
	
	void finish(Integer id);
	
	int countAddCashList(QueryCashParamVO paramVO);
	
	List<CashApplyVO> addCashList(QueryCashParamVO paramVO);

}
