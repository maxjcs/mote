/**
 * 
 */
package com.longcity.manage.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.longcity.manage.dao.MoteDao;
import com.longcity.manage.model.MoteStatistics;
import com.longcity.manage.model.param.QueryMoteDetailParamVO;
import com.longcity.manage.model.param.QueryMoteParamVO;
import com.longcity.manage.model.vo.MoteDetailVO;
import com.longcity.manage.model.vo.MoteVO;
import com.longcity.modeler.dao.MoteTaskDao;
import com.longcity.modeler.util.MoneyUtil;

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
            for(MoteVO vo:rows){
            	if(vo.getBirdthday()!=null){
        			Calendar calendar=Calendar.getInstance();
        			calendar.setTime(vo.getBirdthday());
        			//计算年龄
        			Calendar calendar2=Calendar.getInstance();
        			calendar2.setTime(new Date());
        			vo.setAge((calendar2.get(Calendar.YEAR)-calendar.get(Calendar.YEAR))+1);
        		}
            	vo.setRemindFee(MoneyUtil.fen2Yuan(vo.getRemindFee()));
            	vo.setTaskFee(MoneyUtil.fen2Yuan(vo.getTaskFee()));
            }
        }else{
            paramVO.setTotal(0);
            paramVO.setRows(new ArrayList<MoteVO>());
        }
        return paramVO;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public QueryMoteDetailParamVO queryMoteDetail(QueryMoteDetailParamVO paramVO){
		Integer total = moteTaskDao.getAcceptNumByMoteId(paramVO.getMoteId());
        if (total > 0) {
        	Map paramMap=new HashMap();
        	paramMap.put("moteId",paramVO.getMoteId());
        	paramMap.put("start",paramVO.getStart());
        	paramMap.put("pageSize",paramVO.getPageSize());
            List<MoteDetailVO> rows = moteTaskDao.queryListByMoteId(paramMap);
            for(MoteDetailVO vo:rows){
            	vo.setPrice(MoneyUtil.fen2Yuan(vo.getPrice()));
            	vo.setShotFee(MoneyUtil.fen2Yuan(vo.getShotFee()));
            }
            paramVO.setTotal(total);
            paramVO.setRows(rows);
        }else{
            paramVO.setTotal(0);
            paramVO.setRows(new ArrayList<MoteDetailVO>());
        }
        return paramVO;
	}
	
	public void save(MoteStatistics moteSta){
		MoteStatistics oldMoteStatistics=moteDao.selectByMoteId(moteSta.getMoteId());
		if(oldMoteStatistics==null){
			moteDao.insert(moteSta);
		}else{
			moteDao.update(moteSta);
		}
	}
	
}
