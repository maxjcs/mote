package com.xuexibao.teacher.pay.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xuexibao.teacher.pay.dao.PayAudioDao;
import com.xuexibao.teacher.pay.model.PayAudio;

/**
 * 
 * @author chenzl
 *
 */
@Service
public class PayAudioService {
	@Resource
	private PayAudioDao payAudioDao;
	
	@Transactional
//	(propagation=Propagation.REQUIRES_NEW)
	public void addPayAudio(PayAudio audio){
		audio.setStatus(PayAudio.ONLINE);//上线
		payAudioDao.addPayAudio(audio);
	}
	@Transactional
	public void updateWbTypeUrlPayAudio(PayAudio audio){
		payAudioDao.updateWbTypeUrlPayAudio(audio);
	}
}
