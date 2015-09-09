package com.xuexibao.teacher.filter;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.longcity.modeler.model.User;
import com.xuexibao.teacher.exception.BusinessException;
import com.xuexibao.teacher.model.Teacher;
import com.xuexibao.teacher.util.CipherUtil;
import com.xuexibao.teacher.util.GsonUtil;
import com.xuexibao.teacher.util.JsonUtil;
import com.xuexibao.teacher.util.PropertyUtil;

/**
 * 
 * @author oldlu
 * 
 */
public class Token {
	private Integer tid;// teacherId
	private int ths;// token hash code
	@JsonIgnore
	private String strToken;
	private int dvhs;// device hash code

	public static Token toToken(String strToken) {
		String authToken;
		try {
			authToken = CipherUtil.decode(strToken, PropertyUtil.getProperty("key"));
		} catch (Exception e) {
			throw new BusinessException("TOKEN验证错误.",BusinessException.INVALIDE_TOKEN);
		}

		Token token = GsonUtil.json2Object(authToken, Token.class);

		token.setStrToken(strToken);
		return token;
	}

	public static String gen(User user) {
		Token token = new Token();
		token.setTid(user.getId());
		token.setDvhs(user.getDeviceId().hashCode());

		token.setThs(getHashCode(user));

		String str = JsonUtil.obj2Json(token);
		return CipherUtil.encode(str, PropertyUtil.getProperty("key"));
	}

	private static int getHashCode(User user) {
		String str = user.getPhoneNumber() + "|" + user.getPassword() + "|" + user.getDeviceId();
		return str.hashCode();
	}

	public void valid(User user) {
		if (user == null) {
			throw new BusinessException("TOKEN验证错误.",BusinessException.INVALIDE_TOKEN);
		}

		if (StringUtils.isEmpty(user.getDeviceId()) || dvhs != user.getDeviceId().hashCode()) {
			throw new BusinessException("你的账号已经在其他地方登录了", BusinessException.LOGIN_AT_OTHER_PLACE);
		}

		if (ths != getHashCode(user)) {
			throw new BusinessException("TOKEN验证错误.",BusinessException.INVALIDE_TOKEN);
		}
	}

	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public int getThs() {
		return ths;
	}

	public void setThs(int ths) {
		this.ths = ths;
	}

	public void setStrToken(String strToken) {
		this.strToken = strToken;
	}

	public String getStrToken() {
		return strToken;
	}

	public void setDvhs(int dvhs) {
		this.dvhs = dvhs;
	}

	public int getDvhs() {
		return dvhs;
	}
}
