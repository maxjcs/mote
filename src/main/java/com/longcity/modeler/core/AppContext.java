package com.longcity.modeler.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.longcity.modeler.filter.Token;

/**
 * 
 * @author oldlu
 * 
 */
public final class AppContext {
	private static final ThreadLocal<ThreadContextHolder> context = new ThreadLocal<ThreadContextHolder>();

	public static void destroy() {
		context.set(null);
	}

	public static void init(HttpServletRequest req, HttpServletResponse res) {
		context.set(new ThreadContextHolder(req, res));
	}

	public static Integer getUserId() {
		return context.get().getToken().getTid();
	}

	public static String getStrToken() {
		return context.get().getToken().getStrToken();
	}

	public static Token getToken() {
		return context.get().getToken();
	}

	public static void setToken(Token token) {
		context.get().setToken(token);
	}

	public static ThreadContextHolder getContext() {
		return context.get();
	}
}
