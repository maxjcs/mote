/**
 * 
 */
package com.xuexibao.teacher.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xuexibao.teacher.exception.BusinessException;
import com.xuexibao.teacher.util.EncryptUtil;
import com.xuexibao.teacher.util.PropertyUtil;

/**
 * @author maxjcs
 *
 */
public class StudentApiFilter implements Filter{
	
	private static Logger log = LoggerFactory.getLogger(StudentApiFilter.class);
	
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,FilterChain fc) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String path = req.getPathInfo();
		String secretStr = request.getParameter("_skey");
		//学生端请求的url，需要验证加密串
		if(StudentApiUrl.apiUrls.contains(path)){
			checkSecret(request, response, fc);
			return;
		}else{
			fc.doFilter(request, response);
		}
		
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}
	
	/**
	 * 检查请求密钥的正确性
	 * @param request
	 * @param response
	 * @param fc
	 * @throws IOException
	 * @throws ServletException
	 */
	private void checkSecret(ServletRequest request, ServletResponse response, FilterChain fc) throws IOException, ServletException {
		String secretStr = request.getParameter("_skey");
		if (StringUtils.isEmpty(secretStr)) {
			throw new BusinessException("请求密钥为空.");
		}
		if(StringUtils.contains(EncryptUtil.decrypt(secretStr), PropertyUtil.getProperty("studentapi_secret_value"))){
			fc.doFilter(request, response);
			return;
		}else{
			throw new BusinessException("请求密钥不正确.");
		}
	}



}
