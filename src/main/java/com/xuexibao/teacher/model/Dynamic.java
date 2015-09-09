package com.xuexibao.teacher.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.xuexibao.teacher.common.GradeUtil;
import com.xuexibao.teacher.model.iter.IDynmicCommentCount;
import com.xuexibao.teacher.model.iter.IDynmicUpVoteCount;
import com.xuexibao.teacher.model.iter.IGradeStr;
import com.xuexibao.teacher.model.iter.ITeacher;
import com.xuexibao.teacher.util.DateUtils;

/**
 * 
 * @author oldlu
 *
 */
public class Dynamic implements Serializable, ITeacher, IGradeStr, IDynmicUpVoteCount, IDynmicCommentCount {
	private static final long serialVersionUID = 8286152224720268491L;

	public static final int DYNAMIC_TYPE_ADUIO_SET = 1;
	public static final int DYNAMIC_TYPE_PUBLISH = 2;

	private long id;
	private String teacherId;
	private String setId;
	private int dynamicType;
	private String gradeIds;
	private int status;
	private Date createTime;
	private Date updateTime;
	private String description;
	private String imageUrl1;
	private String imageUrl2;
	private String imageUrl3;
	private String imageUrl4;

	private String teacherNickname;
	private String teacherAvatarUrl;
	private Integer teacherGender;
	private Integer teacherCourseYear;
	private Integer teacherStar;

	private String gradeStr;
	private AudioSet audioSet;

	private Integer countUpVote;
	private Integer countComment;
	private boolean canUpVote = true;
	private List<String> imageUrls = new ArrayList<String>();

	public List<String> getImageUrls() {
		return imageUrls;
	}

	public void setImageUrls() {
		if (!StringUtils.isEmpty(imageUrl1)) {
			imageUrls.add(imageUrl1);
		}
		if (!StringUtils.isEmpty(imageUrl2)) {
			imageUrls.add(imageUrl2);
		}
		if (!StringUtils.isEmpty(imageUrl3)) {
			imageUrls.add(imageUrl3);
		}
		if (!StringUtils.isEmpty(imageUrl4)) {
			imageUrls.add(imageUrl4);
		}

		imageUrl1 = null;
		imageUrl2 = null;
		imageUrl3 = null;
		imageUrl4 = null;
	}

	public boolean isCanUpVote() {
		return canUpVote;
	}

	public void setCanUpVote(boolean canUpVote) {
		this.canUpVote = canUpVote;
	}

	public Integer getCountUpVote() {
		return countUpVote;
	}

	public void setCountUpVote(Integer countUpVote) {
		this.countUpVote = countUpVote;
	}

	public Integer getCountComment() {
		return countComment;
	}

	public void setCountComment(Integer countComment) {
		this.countComment = countComment;
	}

	public AudioSet getAudioSet() {
		return audioSet;
	}

	public void setAudioSet(AudioSet audioSet) {
		this.audioSet = audioSet;
	}

	public String getGradeStr() {
		return gradeStr;
	}

	@JsonIgnore
	public String getGradeIdsStr() {
		return GradeUtil.reverseGrade(gradeIds);
	}

	public void setGradeStr(String gradeStr) {
		this.gradeStr = gradeStr;
	}

	public void setTeacherName(String teacherName) {
		this.teacherNickname = teacherName;
	}

	public void setPhoneNumber(String phoneNumber) {
	}

	public String getTeacherNickname() {
		return teacherNickname;
	}

	public void setTeacherNickname(String teacherNickname) {
		this.teacherNickname = teacherNickname;
	}

	public String getTeacherAvatarUrl() {
		return teacherAvatarUrl;
	}

	public void setTeacherAvatarUrl(String teacherAvatarUrl) {
		this.teacherAvatarUrl = teacherAvatarUrl;
	}

	public Integer getTeacherGender() {
		return teacherGender;
	}

	public void setTeacherGender(Integer teacherGender) {
		this.teacherGender = teacherGender;
	}

	public Integer getTeacherCourseYear() {
		return teacherCourseYear;
	}

	public void setTeacherCourseYear(Integer teacherCourseYear) {
		this.teacherCourseYear = teacherCourseYear;
	}

	public Integer getTeacherStar() {
		return teacherStar;
	}

	public void setTeacherStar(Integer teacherStar) {
		this.teacherStar = teacherStar;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public String getSetId() {
		return setId;
	}

	public void setSetId(String setId) {
		this.setId = setId;
	}

	public int getDynamicType() {
		return dynamicType;
	}

	public void setDynamicType(int dynamicType) {
		this.dynamicType = dynamicType;
	}

	public String getGradeIds() {
		return GradeUtil.reverseGrade(gradeIds);
	}

	public void setGradeIds(String gradeIds) {
		this.gradeIds = gradeIds;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageUrl1() {
		return imageUrl1;
	}

	public void setImageUrl1(String imageUrl1) {
		this.imageUrl1 = imageUrl1;
	}

	public String getImageUrl2() {
		return imageUrl2;
	}

	public void setImageUrl2(String imageUrl2) {
		this.imageUrl2 = imageUrl2;
	}

	public String getImageUrl3() {
		return imageUrl3;
	}

	public void setImageUrl3(String imageUrl3) {
		this.imageUrl3 = imageUrl3;
	}

	public String getImageUrl4() {
		return imageUrl4;
	}

	public void setImageUrl4(String imageUrl4) {
		this.imageUrl4 = imageUrl4;
	}

	@JsonIgnore
	public Long getDynamicId() {
		return id;
	}

	public String getCreateTimeListStr() {
		return DateUtils.formatList(createTime);
	}

	public String getCreateTimeDetailStr() {
		return DateUtils.formatDetail(createTime);
	}
}
