package com.xuexibao.teacher.service;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xuexibao.teacher.dao.AudioEvalApproveDao;
import com.xuexibao.teacher.enums.AudioEvalApproveStatus;
import com.xuexibao.teacher.model.AudioEvalApprove;

/**
 * 
 * 音频评价审核
 * @author maxjcs
 *
 */
@Service
public class AudioEvalApproveService {
	
	@Resource
	AudioEvalApproveDao audioEvalApproveDao;
	
	
	
	/**
	 * 获取音频评价审核
	 * @param audioId
	 * @return
	 */
	public AudioEvalApprove selectByAudioId(String audioId){
		return audioEvalApproveDao.selectByAudioId(audioId);
	}
	
	/**
	 * 更新音频评价审核表
	 * @param audioId
	 * @param evaluation
	 * @param point
	 */
	public void upateAudioEvalApprove(String audioId,Long questionId,String teacherId,Integer evaluation,Integer point){
		AudioEvalApprove approve=audioEvalApproveDao.selectByAudioId(audioId);
		if(approve==null){
			approve = new AudioEvalApprove();
			approve.setAudioId(audioId);
			if(evaluation==1){
				approve.setGoodEvalNum(1);
			}else if(evaluation==2){
				approve.setMiddleEvalNum(1);
			}else if (evaluation==3) {
				approve.setBadEvalNum(1);
			}else{
				return;
			}
			approve.setQuestionId(questionId);
			approve.setTotalPoint(point);
			approve.setTeacherId(teacherId);
			approve.setStatus(AudioEvalApproveStatus.waitApprove.getValue());//待审
			approve.setCreateTime(new Date());
			audioEvalApproveDao.insert(approve);
		}else{
			AudioEvalApprove audioEvalApprove = new AudioEvalApprove();
			audioEvalApprove.setAudioId(audioId);
			if(evaluation==1){
				audioEvalApprove.setGoodEvalNum(1);
			}else if(evaluation==2){
				audioEvalApprove.setMiddleEvalNum(1);
			}else if (evaluation==3) {
				audioEvalApprove.setBadEvalNum(1);
			}else{
				return;
			}
			audioEvalApprove.setTotalPoint(point);
			audioEvalApproveDao.updateEvalAndPoint(audioEvalApprove);
		}
	}

}
