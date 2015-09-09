package com.xuexibao.teacher.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xuexibao.teacher.filter.PerformStatFilter;

/**
 * 请求学生端，在url后加密串。对方解密后，核对密钥才能通过
 * 
 * @author oldlu
 */
public class StudentApiHttpUtil {
	private static Logger logger = LoggerFactory.getLogger(StudentApiHttpUtil.class);

	private static Logger performLogger = LoggerFactory.getLogger(PerformStatFilter.class);

	private static Logger performLoggerWarn = LoggerFactory.getLogger("performLogWarn");

	private static String secretKey = PropertyUtil.getProperty("studentapi_secret_value");

	private static String _skey = PropertyUtil.getProperty("studentapi_secret_name");

	public static <T> T httpPost(String url, Map<String, Object> paramMap, Class<T> type) {
		try {
			// 加密前的原文
			String secretStr = secretKey + "&t=" + System.currentTimeMillis();
			// 加密后的密文
			String encodeStr = EncryptUtil.encrypt(secretStr);
			// 加为请求参数
			paramMap.put(_skey, encodeStr);
			// 请求开始时间
			long start = System.currentTimeMillis();

			HttpClient client = new HttpClient();
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
			logger.error("远程请求失败"+ url + "?" + GsonUtil.obj2Json(paramMap), e);
//			throw new BusinessException("远程请求失败", e);
			return null;
		}
	}

	public static Map<String, Object> httpPost(String url, Map<String, Object> params) {
		try {
			// 加密前的原文
			String secretStr = secretKey + "&t=" + System.currentTimeMillis();
			// 加密后的密文
			String encodeStr = EncryptUtil.encrypt(secretStr);
			// 加为请求参数
			params.put(_skey, encodeStr);

			// 请求开始时间
			long start = System.currentTimeMillis();

			HttpClient client = new HttpClient();
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
			logger.error("远程请求失败"+ url + "?" + GsonUtil.obj2Json(params), e);
//			throw new BusinessException("远程请求失败", e);
			return null;
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
