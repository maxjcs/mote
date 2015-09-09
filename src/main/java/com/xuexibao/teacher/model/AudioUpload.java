package com.xuexibao.teacher.model;

import java.util.Date;

import com.xuexibao.teacher.model.iter.IAudioGoodTag;
import com.xuexibao.teacher.model.iter.IExplanation;
import com.xuexibao.teacher.model.iter.IFeudAnswer;
import com.xuexibao.teacher.model.iter.ILatex;

public class AudioUpload implements ILatex, IFeudAnswer, IExplanation, IAudioGoodTag {
	public static final Integer DURATION_2_MIN = 120;

	public static final Integer BELONG_AUDUI_SET_LIMIT = 3;

	public static final String SELECT_TEXT_DURATION = "讲解时长低于2分钟，不允许添加到习题集";

	public static final String SELECT_TEXT_LIMIT = "该题您已收录于3个习题集，不允许再使用";

	private String id;

	private Long questionId;

	private Integer duration;

	private String name;

	private String url;

	private Integer subject;

	private Integer status;

	private Date approveTime;

	private String teacherId;

	private Date createTime;

	private Date updateTime;

	private Integer type;

	private Integer source;
	private int orgId;
	private String orgTeacherId;
	private String latex;
	private long feudAnswerDetailId;
	private String imageId;

	private String planType;

	private boolean goodTag;

	private int feudType;
	//白板url类型 1,白板 2 视频
	private int wbType;

	private String selectMessage;// 不可选提示消息
	private Boolean canSelect;// 是否可选标识
	
	

	public int getWbType() {
		return wbType;
	}

	public void setWbType(int wbType) {
		this.wbType = wbType;
	}

	public String getSelectMessage() {
		return selectMessage;
	}

	public void setSelectMessage(String selectMessage) {
		this.selectMessage = selectMessage;
	}

	public Boolean getCanSelect() {
		return canSelect;
	}

	public void setCanSelect(Boolean canSelect) {
		this.canSelect = canSelect;
	}

	public int getFeudType() {
		return feudType;
	}

	public void setFeudType(int feudType) {
		this.feudType = feudType;
	}

	public String getPlanType() {
		return planType;
	}

	public void setPlanType(String planType) {
		this.planType = planType;
	}

	public boolean isGoodTag() {
		return goodTag;
	}

	public void setGoodTag(boolean goodTag) {
		this.goodTag = goodTag;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public long getFeudAnswerDetailId() {
		return feudAnswerDetailId;
	}

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	public String getOrgTeacherId() {
		return orgTeacherId;
	}

	public void setOrgTeacherId(String orgTeacherId) {
		this.orgTeacherId = orgTeacherId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url == null ? null : url.trim();
	}

	public Integer getSubject() {
		return subject;
	}

	public void setSubject(Integer subject) {
		this.subject = subject;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId == null ? null : teacherId.trim();
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public void setLatex(String name) {
		this.latex = name;
	}

	public String getLatex() {
		return latex;
	}

	public String getAudioId() {
		return id;
	}

	public void setFeudAnswerDetailId(long feudAnswerDetailId) {
		this.feudAnswerDetailId = feudAnswerDetailId;
	}
}