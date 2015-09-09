/**
 * 
 */
package com.xuexibao.teacher.model.vo;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * @author maxjcs
 *
 */
public class AudioTeacherVO implements Comparable<Object>{
	
	String audioId;
	
	String teacherId;
	
	String mediaText;
	
	Integer gold;
	
	String url;
	
	long duration; 
	
	String nickname;
	
	String audioName;
	
	@JsonIgnore
	Integer star=0;//星级
	@JsonIgnore
	Boolean isFollowed=false;//是否关注
	int audioType;//音频类型，音频or白板
	@JsonIgnore
	Date createTime;
	@JsonIgnore
	int sortNumber;
	
	//白板url类型 1,白板 2 视频
  	private int wbType;
  	
  	
	public int getWbType() {
		return wbType;
	}
	public void setWbType(int wbType) {
		this.wbType = wbType;
	}
	public String getAudioName() {
		return audioName;
	}
	public void setAudioName(String audioName) {
		this.audioName = audioName;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Integer getGold() {
		return gold;
	}
	public void setGold(Integer gold) {
		this.gold = gold;
	}
	public String getAudioId() {
		return audioId;
	}
	public void setAudioId(String audioId) {
		this.audioId = audioId;
	}
	public String getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}
	public String getMediaText() {
		return mediaText;
	}
	public void setMediaText(String mediaText) {
		this.mediaText = mediaText;
	}
	public Integer getStar() {
		return star;
	}
	public void setStar(Integer star) {
		this.star = star;
	}
	public Boolean getIsFollowed() {
		return isFollowed;
	}
	public void setIsFollowed(Boolean isFollowed) {
		this.isFollowed = isFollowed;
	}
    
	public int getAudioType() {
		return audioType;
	}
	public void setAudioType(int audioType) {
		this.audioType = audioType;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getSortNumber() {
		return sortNumber;
	}
	public void setSortNumber(int sortNumber) {
		this.sortNumber = sortNumber;
	}	
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	@Override
	public int compareTo(Object arg0) {
		if(arg0 instanceof AudioTeacherVO){  
			AudioTeacherVO s=(AudioTeacherVO)arg0;  
			return -1*(this.sortNumber-s.sortNumber);//降序排列
        }  
        return -1;
	}

}
