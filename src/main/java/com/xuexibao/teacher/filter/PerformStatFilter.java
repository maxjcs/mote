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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xuexibao.teacher.util.BusinessConstant;

/**
 * @author maxjcs
 *统计每个api请求的时间，记录到另外的log中,超过3s，打error级别日志
 */
public class PerformStatFilter implements Filter{

	private static Logger logger = LoggerFactory.getLogger(PerformStatFilter.class);
	
	private static Logger loggerWarn = LoggerFactory.getLogger("performLogWarn");

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain fc) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String path = req.getPathInfo();
		//api请求开始时间
		Long start = System.currentTimeMillis();
		
		fc.doFilter(request, response);
		
		//api请求结束时间
		Long end  = System.currentTimeMillis();
		long time = end-start;
		if(time>BusinessConstant.MAX_PERFORMLOG_WARN_TIME){
			loggerWarn.error("reqUrl:"+path+",time=="+time+"ms");
		}else{
			logger.info("reqUrl:"+path+",time=="+time+"ms");
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		
	}
	
	public void destroy() {
		
	}

}
