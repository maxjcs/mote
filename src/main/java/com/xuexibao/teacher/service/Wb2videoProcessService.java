package com.xuexibao.teacher.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xuexibao.teacher.dao.Wb2videoProcessDao;
import com.xuexibao.teacher.model.Wb2videoProcess;
@Transactional
@Service
public class Wb2videoProcessService {
	@Resource
	private Wb2videoProcessDao wb2videoProcessDao;
	public void insert(Wb2videoProcess record){
		wb2videoProcessDao.insert(record);
	}
	public Wb2videoProcess selectByWbId(String wbId){
		return wb2videoProcessDao.selectByWbId(wbId);
	}
	public void updateByPrimaryKey(Wb2videoProcess record){
		wb2videoProcessDao.updateByPrimaryKey(record);
	}
	
	

}
