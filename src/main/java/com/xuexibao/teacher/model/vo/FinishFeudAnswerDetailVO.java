package com.xuexibao.teacher.model.vo;

import java.util.Date;
import java.util.List;

public class FinishFeudAnswerDetailVO {
	private String studentId;
	//学生昵称
	private String studentNick;
	//学生地区
	private String studentArea;
	private String StudentAvatarUrl;
	//完成时间，
   private Date completeTime;
	//积分
	private int score;
	//评价
	private int evaluate;
	//答题费
	private int revenue;
	//抢答明细对应ID
	private int feudAnswerDetailId;
	//抢答状态: 1待提交，2已提交，3已失效
	private int status;
	
	private int goodEvaNum;
	
	private int mediumEvaNum;
	private int badEvaNum;
	
	
	
	//是否显示PLAN A 机构老师的积分及分成
	
	private boolean showPlanAPointFee;
	
	//音频是否下线
	
	private boolean isOffline;
	
	
	
	
	
	private String  latex;
	private String solution;
	private String audioWbUrl;
	private String teacherId;
	//题目报错状态 -1正常 0 报错
	private int questionStatus;
	
	//总收入
	private int totalRevenue;
	//总购买数
	private int totalBuyers;
	
	private String audioId;
	@SuppressWarnings("rawtypes")
	
	private int feudType;
	private List wbUrls;
	
	
	private String content;
	private String knowledge;
	
	
	
	private String grade;
	private String subject;
	private Long questRealId;
	
	
	//机构主账号
	public Boolean isOrgMaster=false;
	//录制老师的名字
	public String nickName="";
	//下线原因
	String offlineReason="";
	//扣除积分
	Integer deductPoint=0;
	//下线时间
	Long offlineTime=0L;
	
	String offlineTimeStr = "";
	
	
	
	

	public String getOfflineTimeStr() {
		return offlineTimeStr;
	}
	public void setOfflineTimeStr(String offlineTimeStr) {
		this.offlineTimeStr = offlineTimeStr;
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
	public Long getOfflineTime() {
		return offlineTime;
	}
	public void setOfflineTime(Long offlineTime) {
		this.offlineTime = offlineTime;
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
	public boolean isOffline() {
		return isOffline;
	}
	public void setOffline(boolean isOffline) {
		this.isOffline = isOffline;
	}
	public boolean isShowPlanAPointFee() {
		return showPlanAPointFee;
	}
	public void setShowPlanAPointFee(boolean showPlanAPointFee) {
		this.showPlanAPointFee = showPlanAPointFee;
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
	public int getQuestionStatus() {
		return questionStatus;
	}
	public void setQuestionStatus(int questionStatus) {
		this.questionStatus = questionStatus;
	}
	public String getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}
	public Long getQuestRealId() {
		return questRealId;
	}
	public void setQuestRealId(Long questRealId) {
		this.questRealId = questRealId;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
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
	public int getFeudType() {
		return feudType;
	}
	public void setFeudType(int feudType) {
		this.feudType = feudType;
	}
	public List getWbUrls() {
		return wbUrls;
	}
	public void setWbUrls(List wbUrls) {
		this.wbUrls = wbUrls;
	}
	public String getAudioId() {
		return audioId;
	}
	public void setAudioId(String audioId) {
		this.audioId = audioId;
	}
	public int getTotalRevenue() {
		return totalRevenue;
	}
	public void setTotalRevenue(int totalRevenue) {
		this.totalRevenue = totalRevenue;
	}
	public int getTotalBuyers() {
		return totalBuyers;
	}
	public void setTotalBuyers(int totalBuyers) {
		this.totalBuyers = totalBuyers;
	}
	public String getAudioWbUrl() {
		return audioWbUrl;
	}
	public void setAudioWbUrl(String audioWbUrl) {
		this.audioWbUrl = audioWbUrl;
	}
	public String getStudentAvatarUrl() {
		return StudentAvatarUrl;
	}
	public void setStudentAvatarUrl(String studentAvatarUrl) {
		StudentAvatarUrl = studentAvatarUrl;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public Date getCompleteTime() {
		return completeTime;
	}
	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}
	public String getLatex() {
		return latex;
	}
	public void setLatex(String latex) {
		this.latex = latex;
	}
	public String getSolution() {
		return solution;
	}
	public void setSolution(String solution) {
		this.solution = solution;
	}
	public String getStudentNick() {
		return studentNick;
	}
	public void setStudentNick(String studentNick) {
		this.studentNick = studentNick;
	}
	public String getStudentArea() {
		return studentArea;
	}
	public void setStudentArea(String studentArea) {
		this.studentArea = studentArea;
	}

	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getEvaluate() {
		return evaluate;
	}
	public void setEvaluate(int evaluate) {
		this.evaluate = evaluate;
	}
	public int getRevenue() {
		return revenue;
	}
	public void setRevenue(int revenue) {
		this.revenue = revenue;
	}
	public int getFeudAnswerDetailId() {
		return feudAnswerDetailId;
	}
	public void setFeudAnswerDetailId(int feudAnswerDetailId) {
		this.feudAnswerDetailId = feudAnswerDetailId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	

}
