/**
 * 
 */
package com.xuexibao.teacher.model;

import java.util.Date;

/**
 * @author maxjcs
 *
 */
public class AudioEvalApprove {
	
	Integer id;
	String audioId;
	Integer goodEvalNum=0;
	Integer middleEvalNum=0;
	Integer badEvalNum=0;
	//获得的积分
	Integer totalPoint=0;
	//扣除的积分
	Integer deductPoint=0;
	String teacherId;
	Long questionId;
	String operator;
	String content;
	Integer status;
	Date createTime;
	Date updateTime;
	Date approveTime;
	
	public Date getApproveTime() {
		return approveTime;
	}
	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAudioId() {
		return audioId;
	}
	public void setAudioId(String audioId) {
		this.audioId = audioId;
	}
	public Integer getGoodEvalNum() {
		return goodEvalNum;
	}
	public void setGoodEvalNum(Integer goodEvalNum) {
		this.goodEvalNum = goodEvalNum;
	}
	public Integer getMiddleEvalNum() {
		return middleEvalNum;
	}
	public void setMiddleEvalNum(Integer middleEvalNum) {
		this.middleEvalNum = middleEvalNum;
	}
	public Integer getBadEvalNum() {
		return badEvalNum;
	}
	public void setBadEvalNum(Integer badEvalNum) {
		this.badEvalNum = badEvalNum;
	}
	public Integer getTotalPoint() {
		return totalPoint;
	}
	public void setTotalPoint(Integer totalPoint) {
		this.totalPoint = totalPoint;
	}
	public String getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}
	public Long getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getDeductPoint() {
		return deductPoint;
	}
	public void setDeductPoint(Integer deductPoint) {
		this.deductPoint = deductPoint;
	}
	
	
	
}
