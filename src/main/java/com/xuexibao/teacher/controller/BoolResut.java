/**
 * @author oldlu
 */
package com.xuexibao.teacher.controller;

public class BoolResut<T> {
	private boolean success;
	private T data;

	BoolResut(boolean success, T t) {
		this.success = success;
		this.data = t;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
