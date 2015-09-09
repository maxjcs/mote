
package com.xuexibao.teacher.model;

import java.io.Serializable;

public class RatingIncomeConfig implements Serializable {
    private Integer id;

    private Integer star;

    private Integer teacherIdentify;

    private Integer fee;
    
    private String description;
    
    public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public Integer getTeacherIdentify() {
        return teacherIdentify;
    }

    public void setTeacherIdentify(Integer teacherIdentify) {
        this.teacherIdentify = teacherIdentify;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

}