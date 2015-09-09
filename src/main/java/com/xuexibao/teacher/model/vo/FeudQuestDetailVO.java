package com.xuexibao.teacher.model.vo;
/**
 * 抢答课件详情
 * @author fengbin
 * @time 2015-03-27
 */
public class FeudQuestDetailVO {
	
	/**
	 * "feudQuestId": "int问题抢答ID", "questRealId": "int问题ID", "subject": "str科目",  "grade": "str年级",
      "content": "str试题内容",
      "answer": "str试题答案",
       "knowledge": "str知识点",
	 */
	
	
	private int subjectId;
	private int gradeId;
	private String grade;
	private String subject;
	//题目具体内容
	private String content;
	//答案
	private String answer;
	//知识点
	private String knowledge;
	//题干
	private String latex;
	private String solution;
	
	private Long feudQuestId;
	private Long questRealId;
	//题目报错状态 -1正常 0 报错
	private int questionStatus;
	
	//已经录制过音频
	Boolean hasRecorded=false;
	
	
	
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
	public String getSolution() {
		return solution;
	}
	public void setSolution(String solution) {
		this.solution = solution;
	}
	public Long getFeudQuestId() {
		return feudQuestId;
	}
	public void setFeudQuestId(Long feudQuestId) {
		this.feudQuestId = feudQuestId;
	}
	public long getQuestRealId() {
		return questRealId;
	}
	public void setQuestRealId(Long questRealId) {
		this.questRealId = questRealId;
	}
	public int getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}
	public int getGradeId() {
		return gradeId;
	}
	public void setGradeId(int gradeId) {
		this.gradeId = gradeId;
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
	public String getLatex() {
		return latex;
	}
	public void setLatex(String latex) {
		this.latex = latex;
	}


}
