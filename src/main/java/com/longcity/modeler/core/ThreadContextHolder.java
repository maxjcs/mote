package com.longcity.modeler.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.longcity.modeler.filter.Token;

/**
 * 
 * @author oldlu
 * 
 */
public class ThreadContextHolder {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Token token;

	public ThreadContextHolder(HttpServletRequest req, HttpServletResponse res) {
		this.request = req;
		this.response = res;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}
}
