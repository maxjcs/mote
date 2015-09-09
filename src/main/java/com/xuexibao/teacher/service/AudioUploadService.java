package com.xuexibao.teacher.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xuexibao.teacher.dao.AudioUploadDao;
import com.xuexibao.teacher.model.AudioUpload;

/**
 * 
 * @author feng bin
 *
 */
@Service
@Transactional
public class AudioUploadService {
    @Resource
    private AudioUploadDao audioUploadDao;
    
    public int saveFeudAudio(AudioUpload au){
    	return  audioUploadDao.insert(au);
    }
    
    /**
     * 根据音频ids获取音频对象
     * @param audioIds
     * @return
     */
    public List<AudioUpload> queryByAudioIds(List<String> audioIds){
    	Map paramMap=new HashMap();
    	paramMap.put("audioIds", audioIds);
    	return audioUploadDao.queryByAudioIds(paramMap);
    }

}
