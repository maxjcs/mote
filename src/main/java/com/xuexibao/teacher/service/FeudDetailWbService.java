package com.xuexibao.teacher.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xuexibao.teacher.dao.FeudDetailWbDao;
import com.xuexibao.teacher.model.FeudDetailWb;

@Service
@Transactional
public class FeudDetailWbService {
	
	@Resource
	private FeudDetailWbDao feudDetailWbDao;
	public List<FeudDetailWb> queryWbDownloadUrls(String wbId){
		List<FeudDetailWb> wbs = feudDetailWbDao.selectAllByWbId(wbId);
		for(FeudDetailWb fwb:wbs){
			fwb.setFileUrl(fwb.getFileUrl());
		}
		return wbs;
	}

}
