package com.xuexibao.teacher.dao;

import org.springframework.stereotype.Repository;

import com.xuexibao.teacher.core.MybatisMapper;
import com.xuexibao.teacher.model.AudioApprove;

@MybatisMapper
@Repository
public interface AudioApproveDao {

	void addApprove(AudioApprove approve);

	AudioApprove getApproveByAudioId(String audioId);
}
