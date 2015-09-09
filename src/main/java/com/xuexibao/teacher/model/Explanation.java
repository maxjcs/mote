package com.xuexibao.teacher.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.xuexibao.teacher.model.vo.ExplanationVO;

public class Explanation implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -9092735884448212895L;

	private Long id;
	
	private String imgId;

    private Long questionRealId1;
    
    private Long questionRealId2;
    
    private Long questionRealId3;
    @JsonIgnore
    private Date createTime;
    @JsonIgnore
    private Date updateTime;

    private String teacherId;

    private Integer status;

    private String audioWhiteboardId;
    
    private Date liveImgCreateTime;
    
    private Date audioCreateTime;
    
    private Boolean isNew=false;
    
    private Integer dealStatus;
    
    private String planType;
    
    List<ExplanationVO> voList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
	public String getImgId() {
		return imgId;
	}

	public void setImgId(String imgId) {
		this.imgId = imgId;
	}

	public Long getQuestionRealId1() {
		return questionRealId1;
	}

	public void setQuestionRealId1(Long questionRealId1) {
		this.questionRealId1 = questionRealId1;
	}

	public Long getQuestionRealId2() {
		return questionRealId2;
	}

	public void setQuestionRealId2(Long questionRealId2) {
		this.questionRealId2 = questionRealId2;
	}

	public Long getQuestionRealId3() {
		return questionRealId3;
	}

	public void setQuestionRealId3(Long questionRealId3) {
		this.questionRealId3 = questionRealId3;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public String getAudioWhiteboardId() {
		return audioWhiteboardId;
	}

	public void setAudioWhiteboardId(String audioWhiteboardId) {
		this.audioWhiteboardId = audioWhiteboardId;
	}

	public List<ExplanationVO> getVoList() {
		return voList;
	}

	public void setVoList(List<ExplanationVO> voList) {
		this.voList = voList;
	}

	public Date getLiveImgCreateTime() {
		return liveImgCreateTime;
	}

	public void setLiveImgCreateTime(Date liveImgCreateTime) {
		this.liveImgCreateTime = liveImgCreateTime;
	}

	public Boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}

	public Integer getDealStatus() {
		return dealStatus;
	}

	public void setDealStatus(Integer dealStatus) {
		this.dealStatus = dealStatus;
	}

	public Date getAudioCreateTime() {
		return audioCreateTime;
	}

	public void setAudioCreateTime(Date audioCreateTime) {
		this.audioCreateTime = audioCreateTime;
	}

	public String getPlanType() {
		return planType;
	}

	public void setPlanType(String planType) {
		this.planType = planType;
	}
	
}
