package com.xuexibao.teacher.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author oldlu
 * 
 */
public class ErrorQuest implements Serializable {
	private static final long serialVersionUID = 8286152224720268491L;
	public static final int AUDIT_RESULT_WRONG_UPDATE = 1;// 有错可以修改
	public static final int AUDIT_RESULT_WRONG_READONLY = 2; // 有错无法修改
	public static final int AUDIT_RESULT_RIGTH = 3;// 没有错误不用修改
	private long realId;
	private String subject;
	private String latex;
	private String content;
	private String knowledge;
	private String answer;
	private String solution;
	private String learnPhase;
	private int realSubject;
	private Date createTime;
	private Date updateTime;
	private String source;
	private int status;
	private Date auditTime;

	private int audioResult;
	private QuestReasonType questReason;

	public int getAudioResult() {
		return audioResult;
	}

	public void setAudioResult(int audioResult) {
		this.audioResult = audioResult;
	}

	public QuestReasonType getQuestReason() {
		return questReason;
	}

	public void setQuestReason(QuestReasonType questReason) {
		this.questReason = questReason;
	}

	public long getRealId() {
		return realId;
	}

	public void setRealId(long realId) {
		this.realId = realId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getLatex() {
		return latex;
	}

	public void setLatex(String latex) {
		this.latex = latex;
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

	public String getLearnPhase() {
		return learnPhase;
	}

	public void setLearnPhase(String learnPhase) {
		this.learnPhase = learnPhase;
	}

	public int getRealSubject() {
		return realSubject;
	}

	public void setRealSubject(int realSubject) {
		this.realSubject = realSubject;
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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

}
