package com.xuexibao.teacher.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xuexibao.teacher.exception.BusinessException;
import com.xuexibao.teacher.filter.PerformStatFilter;

import com.xuexibao.teacher.util.MySSLProtocolSocketFactory;

/**
 * 提供https 无证书 访问
 * 
 * @author maxjcs
 *
 */
public class HttpsUtil {

	private static Logger logger = LoggerFactory.getLogger(HttpsUtil.class);

	private static Logger performLogger = LoggerFactory.getLogger(PerformStatFilter.class);

	private static Logger performLoggerWarn = LoggerFactory.getLogger("performLogWarn");

	public static <T> T httpPost(String url, Map<String, Object> paramMap, Class<T> type) {
		try {
			// 请求开始时间
			long start = System.currentTimeMillis();

			HttpClient client = new HttpClient();
			// Protocol myhttps = new Protocol("https", new
			// MySSLProtocolSocketFactory(), 443);
			// Protocol.registerProtocol("https", myhttps);

			client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
			client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
			PostMethod method = new PostMethod(url);

			for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
				method.addParameter(new NameValuePair(entry.getKey(), entry.getValue().toString()));
			}

			client.executeMethod(method);
			client.getHttpConnectionManager().closeIdleConnections(1);
			String responseStr = method.getResponseBodyAsString();
			logger.info(url + "=>" + responseStr);
			// 请求返回时间
			long end = System.currentTimeMillis();

			long time = end - start;
			if (time > BusinessConstant.MAX_PERFORMLOG_WARN_TIME) {
				performLoggerWarn.error("remoteUrl:" + url + ",time==" + time + "ms");
			} else {
				performLogger.info("remoteUrl:" + url + ",time==" + time + "ms");
			}

			if (StringUtils.isNotBlank(responseStr)) {
				return GsonUtil.json2Object(responseStr, type);
			}
			return null;
		} catch (Exception e) {
			logger.error(url + "?" + GsonUtil.obj2Json(paramMap), e);
			throw new BusinessException("远程请求失败", e);
		}
	}

	public static Map<String, Object> httpPost(String url, Map<String, Object> params) {
		try {
			HttpClient client = new HttpClient();
			Protocol myhttps = new Protocol("https", new MySSLProtocolSocketFactory(), 443);
			Protocol.registerProtocol("https", myhttps);

			// 请求开始时间
			long start = System.currentTimeMillis();

			client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
			client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
			PostMethod method = new PostMethod(url);
			method.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

			for (Map.Entry<String, Object> entry : params.entrySet()) {
				method.addParameter(new NameValuePair(entry.getKey(), entry.getValue().toString()));
			}

			client.executeMethod(method);
			client.getHttpConnectionManager().closeIdleConnections(1);
			String responseStr = method.getResponseBodyAsString();
			logger.info(url + "=>" + responseStr);

			// 请求返回时间
			long end = System.currentTimeMillis();

			long time = end - start;
			if (time > BusinessConstant.MAX_PERFORMLOG_WARN_TIME) {
				performLoggerWarn.error("remoteUrl:" + url + ",time==" + time + "ms");
			} else {
				performLogger.info("remoteUrl:" + url + ",time==" + time + "ms");
			}

			if (StringUtils.isNotBlank(responseStr)) {
				return new ObjectMapper().readValue(responseStr, new TypeReference<Map<String, Object>>() {
				});
			}
			return null;
		} catch (Exception e) {
			logger.error(url + "?" + GsonUtil.obj2Json(params), e);
			throw new BusinessException("远程请求失败", e);
		}
	}

	// 发送json请求
	public static Map<String, Object> httpPost(String url, String body) {
		try {

			HttpClient client = new HttpClient();
			Protocol myhttps = new Protocol("https", new MySSLProtocolSocketFactory(), 443);
			Protocol.registerProtocol("https", myhttps);

			// 请求开始时间
			long start = System.currentTimeMillis();

			client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
			client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
			PostMethod post = new PostMethod(url);
			StringRequestEntity entity = new StringRequestEntity(body, "text/json", "UTF-8");
			post.setRequestEntity(entity);
			post.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			post.setRequestHeader("Content-Length", body.length() + "");
			client.executeMethod(post);
			client.getHttpConnectionManager().closeIdleConnections(1);
			post.getStatusCode();

			// 请求返回时间
			long end = System.currentTimeMillis();

			long time = end - start;
			if (time > BusinessConstant.MAX_PERFORMLOG_WARN_TIME) {
				performLoggerWarn.error("remoteUrl:" + url + ",time==" + time + "ms");
			} else {
				performLogger.info("remoteUrl:" + url + ",time==" + time + "ms");
			}

			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("statusCode", post.getStatusCode());
			resultMap.put("msg", post.getResponseBodyAsString());
			return resultMap;
		} catch (Exception e) {
			logger.error(url + "?" + body, e);
			throw new BusinessException("远程请求失败", e);
		}
	}

	/**
	 * 从request中获得参数Map，并返回可读的Map
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map getParameterMap(HttpServletRequest request) {
		// 参数Map
		Map properties = request.getParameterMap();
		// 返回值Map
		Map returnMap = new HashMap();
		Iterator entries = properties.entrySet().iterator();
		Map.Entry entry;
		String name = "";
		String value = "";
		while (entries.hasNext()) {
			entry = (Map.Entry) entries.next();
			name = (String) entry.getKey();
			Object valueObj = entry.getValue();
			if (null == valueObj) {
				value = "";
			} else if (valueObj instanceof String[]) {
				String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++) {
					value = values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = valueObj.toString();
			}
			returnMap.put(name, value);
		}
		return returnMap;
	}
}
