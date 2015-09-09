/**
 * 
 */
package com.xuexibao.teacher.model;

import java.util.Date;

/**
 * 学生对音频的评价
 * @author maxjcs
 *
 */
public class AudioStudentEvaluation {
	
	private Integer id;
	private String teacherId;
	private String userId;
	private String audioId;
	private Integer evaluation;
	private String content;
	private Date createTime;
	private Integer point;
	private Integer remainPoint;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAudioId() {
		return audioId;
	}
	public void setAudioId(String audioId) {
		this.audioId = audioId;
	}
	public Integer getEvaluation() {
		return evaluation;
	}
	public void setEvaluation(Integer evaluation) {
		this.evaluation = evaluation;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getPoint() {
		return point;
	}
	public void setPoint(Integer point) {
		this.point = point;
	}
	public Integer getRemainPoint() {
		return remainPoint;
	}
	public void setRemainPoint(Integer remainPoint) {
		this.remainPoint = remainPoint;
	}
	
	

}
