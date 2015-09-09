/**
 * 
 */
package com.xuexibao.teacher.model.vo;

import java.util.List;

import com.xuexibao.teacher.model.FeudDetailWb;

/**
 * @author maxjcs
 *
 */
public class ExplanationVO {
	//拍题Id
	Long explanationId;
	
	//图片Id
	String imgId;
	
	//录制时间
	Long audioCreateTime;
	
	
	Long questionId;
	
	//购买次数
	Integer saleNum;
	
	//分成收入
	Integer income;
	
	//题目
	String latex;
	
	//科目
	String subject;
	
	String content;
	
	String knowledge;
	
	String answer;
	
	String solution;
	
	String audioUrl;
	
	int uploadType;
	
	Long liveImgCreateTime;
	
	//好评数
	Integer goodEvaNum=0;
	//中评数
	Integer mediumEvaNum=0;
	//差评数
	Integer badEvaNum=0;
	//总积分
	Integer totalPoint=0;
	
	Boolean showPlanAPointFee=true;
	
	Boolean  offline=false;
	//下线原因
	String offlineReason="";
	//扣除积分
	Integer deductPoint=0;
	//下线时间
	Long offlineTime=0L;
	
	//下线时间字符串
	String offlineTimeStr;
	
	Boolean isNew=false;
	
	int questionStatus;
	
	//已经录制过音频
	Boolean hasRecorded=false;
	
	//音频积分
	public Integer points=0;
	//机构主账号
	public Boolean isOrgMaster=false;
	//录制老师的名字
	public String nickName="";
	
	List<FeudDetailWb> wbDetailList;
	
	public Long getOfflineTime() {
		return offlineTime;
	}

	public void setOfflineTime(Long offlineTime) {
		this.offlineTime = offlineTime;
	}

	public String getOfflineReason() {
		return offlineReason;
	}

	public void setOfflineReason(String offlineReason) {
		this.offlineReason = offlineReason;
	}

	public Boolean getHasRecorded() {
		return hasRecorded;
	}

	public void setHasRecorded(Boolean hasRecorded) {
		this.hasRecorded = hasRecorded;
	}

	public int getQuestionStatus() {
		return questionStatus;
	}

	public void setQuestionStatus(int questionStatus) {
		this.questionStatus = questionStatus;
	}

	public Boolean getOffline() {
		return offline;
	}

	public void setOffline(Boolean offline) {
		this.offline = offline;
	}

	public Boolean getShowPlanAPointFee() {
		return showPlanAPointFee;
	}

	public void setShowPlanAPointFee(Boolean showPlanAPointFee) {
		this.showPlanAPointFee = showPlanAPointFee;
	}

	public Long getExplanationId() {
		return explanationId;
	}

	public void setExplanationId(Long explanationId) {
		this.explanationId = explanationId;
	}

	public Long getAudioCreateTime() {
		return audioCreateTime;
	}

	public void setAudioCreateTime(Long audioCreateTime) {
		this.audioCreateTime = audioCreateTime;
	}

	public Integer getIncome() {
		return income;
	}

	public void setIncome(Integer income) {
		this.income = income;
	}

	public String getLatex() {
		return latex;
	}

	public void setLatex(String latex) {
		this.latex = latex;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
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

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public String getAudioUrl() {
		return audioUrl;
	}

	public void setAudioUrl(String audioUrl) {
		this.audioUrl = audioUrl;
	}

	public Integer getSaleNum() {
		return saleNum;
	}

	public void setSaleNum(Integer saleNum) {
		this.saleNum = saleNum;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public Long getLiveImgCreateTime() {
		return liveImgCreateTime;
	}

	public void setLiveImgCreateTime(Long liveImgCreateTime) {
		this.liveImgCreateTime = liveImgCreateTime;
	}

	public Boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}

	public String getImgId() {
		return imgId;
	}

	public void setImgId(String imgId) {
		this.imgId = imgId;
	}

	public List<FeudDetailWb> getWbDetailList() {
		return wbDetailList;
	}

	public void setWbDetailList(List<FeudDetailWb> wbDetailList) {
		this.wbDetailList = wbDetailList;
	}

	public int getUploadType() {
		return uploadType;
	}

	public void setUploadType(int uploadType) {
		this.uploadType = uploadType;
	}

	public Integer getGoodEvaNum() {
		return goodEvaNum;
	}

	public void setGoodEvaNum(Integer goodEvaNum) {
		this.goodEvaNum = goodEvaNum;
	}

	public Integer getMediumEvaNum() {
		return mediumEvaNum;
	}

	public void setMediumEvaNum(Integer mediumEvaNum) {
		this.mediumEvaNum = mediumEvaNum;
	}

	public Integer getBadEvaNum() {
		return badEvaNum;
	}

	public void setBadEvaNum(Integer badEvaNum) {
		this.badEvaNum = badEvaNum;
	}

	public Integer getTotalPoint() {
		return totalPoint;
	}

	public void setTotalPoint(Integer totalPoint) {
		this.totalPoint = totalPoint;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Boolean getIsOrgMaster() {
		return isOrgMaster;
	}

	public void setIsOrgMaster(Boolean isOrgMaster) {
		this.isOrgMaster = isOrgMaster;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getDeductPoint() {
		return deductPoint;
	}

	public void setDeductPoint(Integer deductPoint) {
		this.deductPoint = deductPoint;
	}

	public String getOfflineTimeStr() {
		return offlineTimeStr;
	}

	public void setOfflineTimeStr(String offlineTimeStr) {
		this.offlineTimeStr = offlineTimeStr;
	}
	
}
