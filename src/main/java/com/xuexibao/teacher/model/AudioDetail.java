package com.xuexibao.teacher.model;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.xuexibao.teacher.enums.PlanFactory;
import com.xuexibao.teacher.model.iter.IAudioBuyCount;
import com.xuexibao.teacher.model.iter.IAudioEvaluateCount;
import com.xuexibao.teacher.model.vo.RecordedVO;
import com.xuexibao.teacher.util.DateUtils;

public class AudioDetail extends RecordedVO implements IAudioEvaluateCount, IAudioBuyCount {
	private String url;
	private String notPassReason;
	private Date approveTime;
	private boolean canRecordFlag;
	private String content;
	private String knowledge;
	private String solution;

	private int goodEvaNum;
	private int mediumEvaNum;
	private int badEvaNum;

	private boolean showPlanAPointFee;
	private String planType;
	private boolean hasRecorded;

	// 下线原因
	private String offlineReason = "";
	// 扣除积分
	private Integer deductPoint = 0;
	// 下线时间
	private Date offlineTime;

	private String offlineTimeStr;

	private boolean orgMaster;
	private String nickName;

	public boolean isOrgMaster() {
		return orgMaster;
	}

	public void setOrgMaster(boolean orgMaster) {
		this.orgMaster = orgMaster;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getOfflineReason() {
		return offlineReason;
	}

	public void setOfflineReason(String offlineReason) {
		this.offlineReason = offlineReason;
	}

	public Integer getDeductPoint() {
		return deductPoint;
	}

	public void setDeductPoint(Integer deductPoint) {
		this.deductPoint = deductPoint;
	}

	public Date getOfflineTime() {
		return offlineTime;
	}

	public void setOfflineTime(Date offlineTime) {
		this.offlineTime = offlineTime;
	}

	public String getOfflineTimeStr() {
		if (offlineTime != null) {
			return DateUtils.formatDetail(offlineTime);
		}
		return "";
	}

	public void setOfflineTimeStr(String offlineTimeStr) {
		this.offlineTimeStr = offlineTimeStr;
	}

	public String getPlanType() {
		return planType;
	}

	public void setPlanType(String planType) {
		this.planType = planType;
	}

	public boolean isHasRecorded() {
		return !StringUtils.isEmpty(getId());
	}

	public void setHasRecorded(boolean hasRecorded) {
		this.hasRecorded = hasRecorded;
	}

	public boolean isShowPlanAPointFee() {
		return !StringUtils.equals(PlanFactory.PLAN_A, planType);
	}

	public void setShowPlanAPointFee(boolean showPlanAPointFee) {
		this.showPlanAPointFee = showPlanAPointFee;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getKnowledge() {
		return knowledge;
	}

	public void setKnowledge(String knowledge) {
		this.knowledge = knowledge;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getNotPassReason() {
		return notPassReason;
	}

	public void setNotPassReason(String notPassReason) {
		this.notPassReason = notPassReason;
	}

	public Date getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}

	public boolean isCanRecordFlag() {
		return canRecordFlag;
	}

	public void setCanRecordFlag(boolean canRecordFlag) {
		this.canRecordFlag = canRecordFlag;
	}

	public int getGoodEvaNum() {
		return goodEvaNum;
	}

	public void setGoodEvaNum(int goodEvaNum) {
		this.goodEvaNum = goodEvaNum;
	}

	public int getMediumEvaNum() {
		return mediumEvaNum;
	}

	public void setMediumEvaNum(int mediumEvaNum) {
		this.mediumEvaNum = mediumEvaNum;
	}

	public int getBadEvaNum() {
		return badEvaNum;
	}

	public void setBadEvaNum(int badEvaNum) {
		this.badEvaNum = badEvaNum;
	}

	@Override
	public String getAudioId() {
		return super.getId();
	}

}
