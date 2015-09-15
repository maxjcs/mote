package com.longcity.modeler.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.longcity.modeler.controller.AbstractController;
import com.longcity.modeler.core.AppContext;
import com.longcity.modeler.exception.BusinessException;
import com.longcity.modeler.exception.ValidateException;
import com.longcity.modeler.util.GsonUtil;

/**
 * 
 * @author oldlu
 * 
 */
public class ExceptionFilter extends AbstractController implements Filter {
	private static Logger log = LoggerFactory.getLogger(ExceptionFilter.class);

	private static ObjectMapper mapper = new ObjectMapper();

	public void init(FilterConfig config) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();

		if (multipartResolver.isMultipart(req)) {
			req = multipartResolver.resolveMultipart(req);
		}
		
		log.info(req.getPathInfo() + " params=" + GsonUtil.obj2Json(req.getParameterMap()));

		//response.setContentType("text/plain;charset=UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		try {
			AppContext.init(req, res);
			fc.doFilter(req, res);
		} catch (Exception e) {
			convertException(req, res, e);
		} finally {
			AppContext.destroy();
		}
	}

	private void convertException(HttpServletRequest req, HttpServletResponse res, Exception e) {
		Throwable cause = getException(e, ValidateException.class);
		if (cause != null) {
			write(req, res, cause.getMessage());
			log.info(req.getPathInfo() + "  验证异常:" + e.getMessage());
			return;
		}

		cause = getException(e, BusinessException.class);
		if (cause != null) {
			BusinessException busi = (BusinessException) cause;
			
			write(req, res, busi.getMessage(), busi.getCode());
			log.info(req.getPathInfo() + "  逻辑异常:" + cause.getMessage());
			return;
		}

		write(req, res, "系统异常");
		log.error(req.getPathInfo() + "  系统异常:" + e.getMessage(), e);
	}

	private Throwable getException(Exception ex, Class<?> clazz) {
		Throwable cause = ex;
		while (cause != null) {
			if (clazz.isInstance(cause))
				return cause;
			cause = cause.getCause();
		}
		return null;
	}

	private void write(HttpServletRequest req, HttpServletResponse res, String msg) {
		write(req, res, msg, -1);
	}

	private void write(HttpServletRequest req, HttpServletResponse res, String msg, int code) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("msg", msg);
		
		map.put("success", false);
		if (code != 0) {
			map.put("code", code);
		}

		if (res.isCommitted()) {
			return;
		}
		try {
			res.getWriter().append(mapper.writeValueAsString(map));
		} catch (Exception e) {
			log.error("初始化数据输出流失败!", e);
		}
	}

	public void destroy() {
	}
}