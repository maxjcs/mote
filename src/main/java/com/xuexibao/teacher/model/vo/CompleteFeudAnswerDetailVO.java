package com.xuexibao.teacher.model.vo;

public class CompleteFeudAnswerDetailVO {

	private String questRealId;
	private String subject;
	private String grade;
	private String content;
	private String answer;
	private String knowledge;
	private int feudType;
	private String audioWbUrl;
	private String studentAvatarUrl;
	private String studentNick;
	private int score;
	private int evaluate;
	private int revenue;
	private int feudAnswerDetailId;
	private String studentComment;
	private String solution;
	
	//抢答状态: 1待提交，2已提交，3已失效
	private int status;
	
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getSolution() {
		return solution;
	}
	public void setSolution(String solution) {
		this.solution = solution;
	}
	public String getQuestRealId() {
		return questRealId;
	}
	public void setQuestRealId(String questRealId) {
		this.questRealId = questRealId;
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
	public String getAudioWbUrl() {
		return audioWbUrl;
	}
	public void setAudioWbUrl(String audioWbUrl) {
		this.audioWbUrl = audioWbUrl;
	}
	public String getStudentAvatarUrl() {
		return studentAvatarUrl;
	}
	public void setStudentAvatarUrl(String studentAvatarUrl) {
		this.studentAvatarUrl = studentAvatarUrl;
	}
	public String getStudentNick() {
		return studentNick;
	}
	public void setStudentNick(String studentNick) {
		this.studentNick = studentNick;
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
	public String getStudentComment() {
		return studentComment;
	}
	public void setStudentComment(String studentComment) {
		this.studentComment = studentComment;
	}
	
	
	
	
	
	


	

}
