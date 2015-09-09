package com.longcity.modeler.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.util.JSONPObject;

import com.xuexibao.teacher.constant.PageConstant;
import com.xuexibao.teacher.core.AppContext;
import com.xuexibao.teacher.util.PropertyUtil;

public abstract class AbstractController {

	// callback参数不为空就是jsonp的方式返回
	public static final String param_jsonp_name = "callback";

	protected Map<String, Object> getPageParamMap(Integer pageNo, Integer pageSize) {
		if (pageNo == null) {
			pageNo = 1;
		}
		if (pageSize == null) {
			pageSize = PageConstant.PAGE_SIZE_10;
		}

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("pageNo", pageNo);
		paramMap.put("pageSize", pageSize);
		paramMap.put("start", (pageNo - 1) * pageSize);
		paramMap.put("limit", pageSize);

		return paramMap;
	}

	protected Object dataJson(Object data) {
		return dataJson(data, AppContext.getContext().getRequest(), false);
	}

	protected Object errorJson(String msg) {
		return errorJson(msg, AppContext.getContext().getRequest(), false);
	}

	protected Object dataJson(Object data, HttpServletRequest request) {
		return dataJson(data, request, false);
	}

	protected Object dataJson(Object data, HttpServletRequest request, boolean addToken) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("success", true);
		map.put("data", data);
		if (addToken) {
			String tokenName = PropertyUtil.getProperty("token_name");
			map.put(tokenName, AppContext.getStrToken());
		}
		return genBackStr(request, map);
	}

	protected Object errorJson(String msg, HttpServletRequest request) {
		return errorJson(msg, request, false);
	}

	protected Object errorJson(String msg, HttpServletRequest request, boolean addToken) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("success", false);
		map.put("msg", msg);
		if (addToken) {
			String tokenName = PropertyUtil.getProperty("token_name");
			map.put(tokenName, AppContext.getStrToken());
		}
		return genBackStr(request, map);
	}

	private Object genBackStr(HttpServletRequest request, Map<Object, Object> map) {
		String callBackValue = request.getParameter(param_jsonp_name);

		if (StringUtils.isBlank(callBackValue)) {
			return map;
		} else {
			return new JSONPObject(callBackValue, map);
		}
	}

	/**
	 * 学生端调用错误情况下 返回的json
	 * 
	 * @param data
	 * @param request
	 * @return
	 */
	protected Object errorStudentJson(String msg) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("status", -1);
		map.put("msg", msg);
		map.put("result", null);
		return map;
	}

	/**
	 * 学生端调用 返回的json
	 * 
	 * @param data
	 * @param request
	 * @return
	 */
	protected Object dataStudentJson(Object data) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("status", 0);
		map.put("msg", "ok");
		map.put("result", data);
		return map;
	}
}
