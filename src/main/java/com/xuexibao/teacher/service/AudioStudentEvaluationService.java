package com.xuexibao.teacher.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xuexibao.teacher.dao.AudioEvalApproveDao;
import com.xuexibao.teacher.dao.AudioStudentEvaluationDao;
import com.xuexibao.teacher.dao.AudioUploadDao;
import com.xuexibao.teacher.model.AudioEvalApprove;
import com.xuexibao.teacher.model.AudioStudentEvaluation;
import com.xuexibao.teacher.model.AudioUpload;
import com.xuexibao.teacher.model.StarPoint;
import com.xuexibao.teacher.model.Teacher;

/**
 * 
 * 音频评价积分
 * @author maxjcs
 *
 */
@Service
public class AudioStudentEvaluationService {
	
	@Resource
	AudioStudentEvaluationDao audioStudentEvaluationDao;
	
	@Resource
	AudioUploadDao audioUploadDao;
	
	@Resource
	TeacherService teacherService;
	
    @Resource
    PointLogService pointLogService;
	
	@Resource
	EvaluationPointConfService evaluationPointConfService;
	
	@Resource
	AudioEvalApproveService audioEvalApproveService;
	
	@Resource
	AudioEvalApproveDao audioEvalApproveDao;
	
    public int insert(AudioStudentEvaluation record){
    	AudioStudentEvaluation oldEvaluation=audioStudentEvaluationDao.queryByUserIdAudioId(record.getUserId(),record.getAudioId());
    	if(oldEvaluation==null){
    		AudioUpload audioUpload=audioUploadDao.selectByPrimaryKey(record.getAudioId());
    		if(audioUpload==null){
    			return -1;
    		}
    		record.setTeacherId(audioUpload.getTeacherId());
    		//教师积分
    		StarPoint starPoint= teacherService.getStarPointByTeacherId(audioUpload.getTeacherId());
    		Teacher teacher=teacherService.getRequiredTeacher(audioUpload.getTeacherId());
    		//获取对应的积分
    		Integer point=evaluationPointConfService.getAudioEvalPoint(teacher.getTeacherIdentify(), record.getEvaluation());
    		Integer remainPoint=starPoint.getPoint()+point;
    		record.setPoint(point);
    		record.setRemainPoint(remainPoint);
        	audioStudentEvaluationDao.insert(record);
        	//更新老师积分及星级
        	pointLogService.addPoint(audioUpload.getTeacherId(), point,"");
        	
        	//更新音频评价审核表
        	audioEvalApproveService.upateAudioEvalApprove(record.getAudioId(), audioUpload.getQuestionId(), teacher.getId(), record.getEvaluation(), point);
        	return 1;
    	}
    	return 2;
    }
    
    //获取音频的总积分
    public int getPointByAudioId(String audioId){
    	AudioEvalApprove approve=audioEvalApproveDao.selectByAudioId(audioId);
    	if(approve==null){
    		return 0;
    	}else{
    		return approve.getTotalPoint()==null?0:approve.getTotalPoint().intValue()+approve.getDeductPoint().intValue();
    	}
    }
    
    //批量获取音频的总积分
    public List<AudioEvalApprove> getPointByAudioIds(Map map){
    	return audioEvalApproveDao.selectByAudioIds(map);
  
    }

}
