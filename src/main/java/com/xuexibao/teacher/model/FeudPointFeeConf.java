package com.xuexibao.teacher.model;

public class FeudPointFeeConf implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private Integer starEvaluate;

    private Integer teacherIdentify;

    private Integer value;

    private Integer confType;

    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStarEvaluate() {
        return starEvaluate;
    }

    public void setStarEvaluate(Integer starEvaluate) {
        this.starEvaluate = starEvaluate;
    }

    public Integer getTeacherIdentify() {
        return teacherIdentify;
    }

    public void setTeacherIdentify(Integer teacherIdentify) {
        this.teacherIdentify = teacherIdentify;
    }

    public Integer getValue() {
        return value;
    }
    
    public Integer getFee() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getConfType() {
        return confType;
    }

    public void setConfType(Integer confType) {
        this.confType = confType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}