/**
 * 
 */
package com.longcity.manage.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.longcity.manage.model.MoteStatistics;
import com.longcity.manage.model.param.QueryMoteParamVO;
import com.longcity.manage.model.vo.MoteVO;
import com.longcity.modeler.core.MybatisMapper;

/**
 * @author maxjcs
 *
 */
@MybatisMapper
@Repository
public interface MoteDao {
	
    List<MoteVO>	queryMoteList(QueryMoteParamVO vo);
    
    int countMoteList(QueryMoteParamVO vo);
    
    MoteStatistics selectByMoteId(Integer moteId);
    
    void insert(MoteStatistics moteSta);
    
    void update(MoteStatistics moteSta);

}
