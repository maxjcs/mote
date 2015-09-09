package com.xuexibao.teacher.exception;

public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = -4077393888379762050L;

	public final static int UNLOGIN = 1;// 未登录
	public final static int LOGIN_AT_OTHER_PLACE = 2;// 其他地方已经登录
	public final static int INVALIDE_TOKEN = 3;// 无效token
	private int code;

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}

	public BusinessException(String message, int code) {
		super(message);
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
