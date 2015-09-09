package com.xuexibao.teacher.model;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Question {
	// '1：每日热题 2：抢答题 3：机构题';
	public static final String SOURCE_TASK = "1";
	public static final String SOURCE_REUD = "2";
	public static final String SOURCE_ORG = "3";

	private Long id;

	private String subject;

	private String knowledge;

	private String learnPhase;

	private int realSubject;

	private Long realId;

	private Integer errorNumber;

	private Integer recordNumber;

	private Date createTime;

	private int emergencyStatus;

	private Integer emergencyCount;

	private int allotCount;

	private Date updateTime;

	private int audioUploadStatus;

	private String latex;

	private String content;

	private String answer;

	private String solution;

	private String source;
	private int allotStatus;

	public int getAllotStatus() {
		return allotStatus;
	}

	public void setAllotStatus(int allotStatus) {
		this.allotStatus = allotStatus;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject == null ? null : subject.trim();
	}

	public String getKnowledge() {
		return knowledge;
	}

	public void setKnowledge(String knowledge) {
		this.knowledge = knowledge == null ? null : knowledge.trim();
	}

	public String getLearnPhase() {
		return learnPhase;
	}

	public void setLearnPhase(String learnPhase) {
		this.learnPhase = learnPhase == null ? null : learnPhase.trim();
	}

	public int getRealSubject() {
		return realSubject;
	}

	public void setRealSubject(int realSubject) {
		this.realSubject = realSubject;
	}

	public Long getRealId() {
		return realId;
	}

	public void setRealId(Long realId) {
		this.realId = realId;
	}

	public Integer getErrorNumber() {
		return errorNumber;
	}

	public void setErrorNumber(Integer errorNumber) {
		this.errorNumber = errorNumber;
	}

	public Integer getRecordNumber() {
		return recordNumber;
	}

	public void setRecordNumber(Integer recordNumber) {
		this.recordNumber = recordNumber;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getEmergencyStatus() {
		return emergencyStatus;
	}

	public void setEmergencyStatus(int emergencyStatus) {
		this.emergencyStatus = emergencyStatus;
	}

	public Integer getEmergencyCount() {
		return emergencyCount;
	}

	public void setEmergencyCount(Integer emergencyCount) {
		this.emergencyCount = emergencyCount;
	}

	public int getAllotCount() {
		return allotCount;
	}

	public void setAllotCount(int allotCount) {
		this.allotCount = allotCount;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public int getAudioUploadStatus() {
		return audioUploadStatus;
	}

	public void setAudioUploadStatus(Byte audioUploadStatus) {
		this.audioUploadStatus = audioUploadStatus;
	}

	public String getLatex() {
		return latex;
	}

	@JsonIgnore
	public String getSub50Latex() {
		if (!StringUtils.isEmpty(latex)) {
			if (latex.length() > 50) {
				return latex.substring(0, 50);
			}
			return latex;
		}
		return "";
	}

	public void setLatex(String latex) {
		this.latex = latex == null ? null : latex.trim();
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content == null ? null : content.trim();
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer == null ? null : answer.trim();
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution == null ? null : solution.trim();
	}

	public boolean isEmpty() {
		return StringUtils.isEmpty(knowledge) || realId == null | realId <= 0 || StringUtils.isEmpty(latex)
				|| StringUtils.isEmpty(content) || StringUtils.isEmpty(answer) || StringUtils.isEmpty(solution)
				|| StringUtils.isEmpty(source);
	}
}