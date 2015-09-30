package com.longcity.modeler.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author oldlu
 */
public class HttpUtil {
    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    public static <T> T httpPost(String url, Map<String, Object> paramMap, Class<T> type) {
        try {
            //请求开始时间
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

            if (StringUtils.isNotBlank(responseStr)) {
                return GsonUtil.json2Object(responseStr, type);
            }
            return null;
        } catch (Exception e) {
        	logger.error(url + "?" + GsonUtil.obj2Json(paramMap), e);
        }
        return null;
    }

    public static Map<String, Object> httpPost(String url, Map<String, Object> params) {
        try {
            //请求开始时间
            HttpClient client = new HttpClient();
            client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
            client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
            PostMethod method = new PostMethod(url);
            method.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

            for (Map.Entry<String, Object> entry : params.entrySet()) {
                //空值判断
                if (StringUtils.isNotEmpty(entry.getKey()) && entry.getValue() != null) {
                    method.addParameter(new NameValuePair(entry.getKey(), entry.getValue().toString()));
                }
            }

            client.executeMethod(method);
            client.getHttpConnectionManager().closeIdleConnections(1);
            String responseStr = method.getResponseBodyAsString();
            logger.info(url + "=>" + responseStr);
            
            System.out.println(responseStr);


            if (StringUtils.isNotBlank(responseStr)) {
                return new ObjectMapper().readValue(responseStr, new TypeReference<Map<String, Object>>() {
                });
            }
            
        } catch (Exception e) {
        	logger.error(url + "?" + GsonUtil.obj2Json(params), e);
        }
        return null;
    }


    public static String httpGet(String url) {
        try {
            //请求开始时间
            HttpClient client = new HttpClient();
            client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
            client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
            GetMethod method = new GetMethod(url);
            method.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");


            client.executeMethod(method);
            client.getHttpConnectionManager().closeIdleConnections(1);
            String responseStr = method.getResponseBodyAsString();
            
            return responseStr;

            
        } catch (Exception e) {
        	logger.error(url, e);
        }
        return null;
    }

    /**
     * 从request中获得参数Map，并返回可读的Map
     *
     * @param request
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
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
