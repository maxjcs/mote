package com.xuexibao.teacher.model.vo;

import java.util.Date;


public class WaitFeudListVO  {
	

	private long feudQuestId;
	private String questRealId;
	private Date createTime;
	private String subject;
	private String grade;
	private String content;
	private String answer;
	private String studentNick;
	private String studentArea;
	private String studentAvatarUrl;
	private String knowledge;
	private String latex;
	private String studentId;
	private boolean isFeuding;
	private String sourceTeacher;
	
	
	
	public String getSourceTeacher() {
		return sourceTeacher;
	}
	public void setSourceTeacher(String sourceTeacher) {
		this.sourceTeacher = sourceTeacher;
	}
	public boolean isFeuding() {
		return isFeuding;
	}
	public void setFeuding(boolean isFeuding) {
		this.isFeuding = isFeuding;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getLatex() {
		return latex;
	}
	public void setLatex(String latex) {
		this.latex = latex;
	}
	public String getKnowledge() {
		return knowledge;
	}
	public void setKnowledge(String knowledge) {
		this.knowledge = knowledge;
	}
	public String getQuestRealId() {
		return questRealId;
	}
	public void setQuestRealId(String questRealId) {
		this.questRealId = questRealId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public long getFeudQuestId() {
		return feudQuestId;
	}
	public void setFeudQuestId(long feudQuestId) {
		this.feudQuestId = feudQuestId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
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
	public String getStudentAvatarUrl() {
		return studentAvatarUrl;
	}
	public void setStudentAvatarUrl(String studentAvatarUrl) {
		this.studentAvatarUrl = studentAvatarUrl;
	}
	
	
	

}
