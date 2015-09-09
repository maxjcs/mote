package com.xuexibao.teacher.model;

import java.util.Date;

public class FeudQuest {
    private Long id;

    private Long questionRealId;

    private String studentId;

    private String imageId;

    private int status;

    private String feudAnswerTeacherId;

    private Long feudAnswerDetailId;

    private Date createTime;

    private Date updateTime;
    /**
     * 代表定向请求老师或者请求所有老师。common为请求所有老师,teacher_id为某一老师
     */
    private String sourceTeacher;
    
    public String getSourceTeacher() {
		return sourceTeacher;
	}

	public void setSourceTeacher(String sourceTeacher) {
		this.sourceTeacher = sourceTeacher;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuestionRealId() {
        return questionRealId;
    }

    public void setQuestionRealId(Long questionRealId) {
        this.questionRealId = questionRealId ;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId == null ? null : studentId.trim();
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId == null ? null : imageId.trim();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getFeudAnswerTeacherId() {
        return feudAnswerTeacherId;
    }

    public void setFeudAnswerTeacherId(String feudAnswerTeacherId) {
        this.feudAnswerTeacherId = feudAnswerTeacherId == null ? null : feudAnswerTeacherId.trim();
    }

    public Long getFeudAnswerDetailId() {
        return feudAnswerDetailId;
    }

    public void setFeudAnswerDetailId(Long feudAnswerDetailId) {
        this.feudAnswerDetailId = feudAnswerDetailId;
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
}