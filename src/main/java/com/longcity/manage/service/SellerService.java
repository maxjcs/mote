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

import com.longcity.manage.dao.SellerDao;
import com.longcity.manage.model.param.QuerySellerDetailParamVO;
import com.longcity.manage.model.param.QuerySellerParamVO;
import com.longcity.manage.model.param.QueryTaskDetailParamVO;
import com.longcity.manage.model.vo.SellerVO;
import com.longcity.modeler.dao.MoteTaskDao;
import com.longcity.modeler.dao.TaskDao;
import com.longcity.modeler.model.Task;
import com.longcity.modeler.model.vo.MoteTaskVO;

/**
 * @author maxjcs
 *
 */
@Service
public class SellerService {
	
	@Resource
	SellerDao sellerDao;
	
	@Resource
	TaskDao taskDao;
	
	@Resource
	MoteTaskDao moteTaskDao;
	
	public QuerySellerParamVO querySellerList(QuerySellerParamVO paramVO){
		Integer total = sellerDao.countSellerList(paramVO);
        if (total > 0) {
            List<SellerVO> rows = sellerDao.querySellerList(paramVO);
            paramVO.setTotal(total);
            paramVO.setRows(rows);
        }else{
            paramVO.setTotal(0);
            paramVO.setRows(new ArrayList<SellerVO>());
        }
        return paramVO;
	}
	
	public QuerySellerDetailParamVO querySellerDetail(QuerySellerDetailParamVO paramVO){
		Integer total = taskDao.countTaskByUserId(paramVO);
        if (total > 0) {
            List<Task> rows = taskDao.queryTaskByUserId(paramVO);
            paramVO.setTotal(total);
            paramVO.setRows(rows);
        }else{
            paramVO.setTotal(0);
            paramVO.setRows(new ArrayList<Task>());
        }
        return paramVO;
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public QueryTaskDetailParamVO queryTaskDetail(QueryTaskDetailParamVO paramVO){
		Integer total = moteTaskDao.countMoteListByTaskId(paramVO.getTaskId());
        if (total > 0) {
        	Map paramMap=new HashMap();
        	paramMap.put("taskId",paramVO.getTaskId());
        	paramMap.put("start",paramVO.getStart());
        	paramMap.put("pageSize",paramVO.getPageSize());
            List<MoteTaskVO> rows = moteTaskDao.getMoteListByTaskId(paramMap);
            paramVO.setTotal(total);
            paramVO.setRows(rows);
        }else{
            paramVO.setTotal(0);
            paramVO.setRows(new ArrayList<MoteTaskVO>());
        }
        return paramVO;
	}
	

}
