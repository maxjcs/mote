package com.xuexibao.teacher.model.iter;

import com.xuexibao.teacher.model.rpcvo.AudioSetStudentComment;

/**
 * @author oldlu
 * 
 */
public interface IAudioSetStudentComment {
	String getSetId();

	void setStudentComment(AudioSetStudentComment.Item item);
}
