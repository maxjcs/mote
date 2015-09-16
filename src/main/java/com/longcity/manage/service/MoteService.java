/**
 * 
 */
package com.longcity.manage.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.longcity.manage.dao.MoteDao;
import com.longcity.manage.model.param.QueryMoteDetailParamVO;
import com.longcity.manage.model.param.QueryMoteParamVO;
import com.longcity.manage.model.vo.MoteVO;
import com.longcity.modeler.dao.MoteTaskDao;
import com.longcity.modeler.model.vo.MoteTaskVO;

/**
 * @author maxjcs
 *
 */
@Service
public class MoteService {
	
	@Resource
	MoteDao moteDao;
	
	@Resource
	MoteTaskDao moteTaskDao;
	
	
	public QueryMoteParamVO queryMoteList(QueryMoteParamVO paramVO){
		Integer total = moteDao.countMoteList(paramVO);
        if (total > 0) {
            List<MoteVO> rows = moteDao.queryMoteList(paramVO);
            paramVO.setTotal(total);
            paramVO.setRows(rows);
        }else{
            paramVO.setTotal(0);
            paramVO.setRows(new ArrayList<MoteVO>());
        }
        return paramVO;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public QueryMoteDetailParamVO queryMoteDetail(QueryMoteDetailParamVO paramVO){
		Integer total = moteTaskDao.countByMoteId(paramVO.getMoteId());
        if (total > 0) {
        	Map paramMap=new HashMap();
        	paramMap.put("moteId",paramVO.getMoteId());
        	paramMap.put("start",paramVO.getStart());
        	paramMap.put("pageSize",paramVO.getPageSize());
            List<MoteTaskVO> rows = moteTaskDao.queryListByMoteId(paramMap);
            paramVO.setTotal(total);
            paramVO.setRows(rows);
        }else{
            paramVO.setTotal(0);
            paramVO.setRows(new ArrayList<MoteTaskVO>());
        }
        return paramVO;
	}
	
}
