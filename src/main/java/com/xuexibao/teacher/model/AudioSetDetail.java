package com.xuexibao.teacher.model;

import java.io.Serializable;
import java.util.Date;

import com.xuexibao.teacher.model.iter.IAudio;
import com.xuexibao.teacher.model.iter.IAudioGoodTag;
import com.xuexibao.teacher.model.iter.IExplanation;
import com.xuexibao.teacher.model.iter.IFeudAnswer;
import com.xuexibao.teacher.model.iter.ILatex;

/**
 * 
 * @author oldlu
 * 
 */
public class AudioSetDetail implements Serializable, ILatex, IAudio, IExplanation, IFeudAnswer, IAudioGoodTag {
	private static final long serialVersionUID = 8286152224720268491L;

	private long id;
	private String setId;
	private String audioId;
	private Date createTime;
	private Date updateTime;
	private int orderNo;
	private long questionId;
	private String latex;
	private int audioType;
	private String imageId;
	private boolean goodTag;// 好评价标记

	private long feudAnswerDetailId;

	public boolean isGoodTag() {
		return goodTag;
	}

	public void setGoodTag(boolean goodTag) {
		this.goodTag = goodTag;
	}

	public long getFeudAnswerDetailId() {
		return feudAnswerDetailId;
	}

	public void setFeudAnswerDetailId(long feudAnswerDetailId) {
		this.feudAnswerDetailId = feudAnswerDetailId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getAudioType() {
		return audioType;
	}

	public void setAudioType(int audioType) {
		this.audioType = audioType;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}

	public String getLatex() {
		return latex;
	}

	public void setLatex(String latex) {
		this.latex = latex;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSetId() {
		return setId;
	}

	public void setSetId(String setId) {
		this.setId = setId;
	}

	public String getAudioId() {
		return audioId;
	}

	public void setAudioId(String audioId) {
		this.audioId = audioId;
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

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
}
