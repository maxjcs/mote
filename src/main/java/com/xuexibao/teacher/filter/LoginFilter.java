package com.xuexibao.teacher.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.xuexibao.teacher.core.AppContext;
import com.xuexibao.teacher.enums.TeacherStatus;
import com.xuexibao.teacher.exception.BusinessException;
import com.xuexibao.teacher.model.Teacher;
import com.xuexibao.teacher.service.TeacherService;
import com.xuexibao.teacher.util.PropertyUtil;

/**
 * 
 * @author oldlu
 * 
 */
public class LoginFilter implements Filter {
	private TeacherService teacherService;

	public void init(FilterConfig config) throws ServletException {
		ServletContext context = config.getServletContext();

		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
		teacherService = (TeacherService) ctx.getBean("teacherService");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String path = req.getPathInfo();
		if (EscapeUrl.escapeUrls.contains(path)) {
			fc.doFilter(req, res);
			return;
		}

		checkLogin(req, res, fc);
	}

	private void checkLogin(HttpServletRequest request, HttpServletResponse response, FilterChain fc) throws IOException, ServletException {
		String tokenStr = request.getParameter(PropertyUtil.getProperty("token_name"));
		if (StringUtils.isEmpty(tokenStr)) {
			throw new BusinessException("未登录或登录超时.", BusinessException.UNLOGIN);
		}
		Token token = Token.toToken(tokenStr);

		Teacher teacher = teacherService.getTeacher(token.getTid());

		token.valid(teacher);

		int status = TeacherStatus.normal.getValue();
		if (status == teacher.getStatus()) {
			AppContext.setToken(token);
			fc.doFilter(request, response);
			return;
		}
		throw new BusinessException("您的账号因为录题规范问题已被停用.");
	}

	public void destroy() {
	}
}