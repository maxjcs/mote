/**
 * @author oldlu
 */
package com.xuexibao.teacher.model.rpcvo;

import java.util.ArrayList;
import java.util.List;

import com.xuexibao.teacher.util.DateUtils;

public class AudioSetEvaluateDetail extends RpcVO {
	public Data data = new Data();

	public static class Item {
		String profile_image_url;
		int gender;
		String comment;
		int type;
		long createTime;
		String name;
		String id;
		
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getCreateTimeStr() {
			return DateUtils.formatList(createTime);
		}

		public String getProfile_image_url() {
			return profile_image_url;
		}

		public void setProfile_image_url(String profile_image_url) {
			this.profile_image_url = profile_image_url;
		}

		public int getGender() {
			return gender;
		}

		public void setGender(int gender) {
			this.gender = gender;
		}

		public String getComment() {
			return comment;
		}

		public void setComment(String comment) {
			this.comment = comment;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}

		public long getCreateTime() {
			return createTime;
		}

		public void setCreateTime(long createTime) {
			this.createTime = createTime;
		}
	}

	public static class Data {
		public int totalNum;
		public List<Item> data = new ArrayList<Item>();
	}
}
