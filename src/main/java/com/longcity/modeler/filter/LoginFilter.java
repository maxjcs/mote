package com.longcity.modeler.filter;

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

import com.longcity.modeler.core.AppContext;
import com.longcity.modeler.enums.UserStatus;
import com.longcity.modeler.exception.BusinessException;
import com.longcity.modeler.model.User;
import com.longcity.modeler.service.UserService;
import com.longcity.modeler.util.PropertyUtil;

/**
 * 
 * @author oldlu
 * 
 */
public class LoginFilter implements Filter {
	private UserService userService;

	public void init(FilterConfig config) throws ServletException {
		ServletContext context = config.getServletContext();

		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
		userService = (UserService) ctx.getBean("userService");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String path = req.getPathInfo();
		if (EscapeUrl.escapeUrls.contains(path)||path.contains("back")) {
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

		User user = userService.getUserById(token.getTid());

		token.valid(user);

		if (UserStatus.normal.getValue() == user.getStatus()) {
			AppContext.setToken(token);
			fc.doFilter(request, response);
			return;
		}
		throw new BusinessException("您的账号已被停用.");
	}

	public void destroy() {
	}
}