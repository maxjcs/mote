package com.xuexibao.teacher.model;

public class QuestionList {

    private long id;
    private String latex;
    private String subjectText;
    private int emergencyStatus;
    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getLatex() {
        return latex;
    }
    public void setLatex(String latex) {
        this.latex = latex;
    }
    public String getSubjectText() {
        return subjectText;
    }
    public void setSubjectText(String subjectText) {
        this.subjectText = subjectText;
    }
    public int getEmergencyStatus() {
        return emergencyStatus;
    }
    public void setEmergencyStatus(int emergencyStatus) {
        this.emergencyStatus = emergencyStatus;
    }
}
