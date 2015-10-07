/**
 * 
 */
package com.longcity.modeler.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.longcity.modeler.core.MybatisMapper;
import com.longcity.modeler.model.CashRecord;

/**
 * @author maxjcs
 *
 */
@MybatisMapper
@Repository
public interface CashRecordDao {
	
	@SuppressWarnings("rawtypes")
	List<CashRecord> getRecordList(Map parmamap);
	
	@SuppressWarnings("rawtypes")
	int countRecordList(Map parmamap);
	
	void insert(CashRecord record);

}
