/**
 * 
 */
package com.xuexibao.teacher.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xuexibao.teacher.core.MybatisMapper;
import com.xuexibao.teacher.model.AudioEvalApprove;

/**
 * @author maxjcs
 *
 */
@MybatisMapper
@Repository
public interface AudioEvalApproveDao {
	
	public Integer insert(AudioEvalApprove audioEvalApprove);
	
	public Integer updateEvalAndPoint(AudioEvalApprove audioEvalApprove);
	
	public AudioEvalApprove selectByAudioId(String audioId);
	public List<AudioEvalApprove> selectByAudioIds(Map map);
	
	
	
}
