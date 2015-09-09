package com.xuexibao.teacher.model;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;

public class Audio {
	public static final int BUSI_CODE_ALREADY_SUBMIT = 11;
	private String id;
	private long questionId;
	private long duration;
	private String name;
	private String url;
	private String teacherId;
	private Date createTime;
	private int status;
	private int type;
	private int source;
	private int teacherStar;
	
    private int feudType;
	
  //白板url类型 1,白板 2 视频
  	private int wbType;
  	

  	
	
	
	public int getWbType() {
		return wbType;
	}

	public void setWbType(int wbType) {
		this.wbType = wbType;
	}

	public int getFeudType() {
		return feudType;
	}

	public void setFeudType(int feudType) {
		this.feudType = feudType;
	}  
	
	public int getTeacherStar() {
		return teacherStar;
	}

	public void setTeacherStar(int teacherStar) {
		this.teacherStar = teacherStar;
	}

	@JsonIgnore
	private int orgId;
	@JsonIgnore
	private String orgTeacherId;
	
	private String planType;

	private MultipartFile file;
	
	public String getPlanType() {
		return planType;
	}

	public void setPlanType(String planType) {
		this.planType = planType;
	}

	public boolean isOrgAudio() {
		return orgId > 0;
	}

	public String getOrgTeacherId() {
		return orgTeacherId;
	}

	public void setOrgTeacherId(String orgTeacherId) {
		this.orgTeacherId = orgTeacherId;
	}

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	@JsonIgnore
	private int subject;

	public int getSubject() {
		return subject;
	}

	public void setSubject(int subject) {
		this.subject = subject;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}

}
