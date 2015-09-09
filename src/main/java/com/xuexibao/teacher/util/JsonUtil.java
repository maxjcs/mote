package com.xuexibao.teacher.util;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xuexibao.teacher.exception.ValidateException;

/**
 * @author oldlu
 */
public class JsonUtil {
    private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    private static ObjectMapper mapper = new ObjectMapper();

    public static <T> T json2Obj(String json, Class<T> type) {
        try {
            return mapper.readValue(json, type);
        } catch (Exception e) {
            logger.error("数据格式错误", e);
            throw new ValidateException("数据格式错误", e);
        }
    }

    public static String obj2Json(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            logger.error("数据格式错误", e);
            throw new ValidateException("数据格式错误", e);
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> obj2Map(Object obj) {
        try {
            return mapper.convertValue(obj, HashMap.class);
        } catch (Exception e) {
            logger.error("数据格式错误", e);
            throw new ValidateException("数据格式错误", e);
        }
    }

    /**
     * f返回json格式。
     *
     * @param <T>
     * @param response
     * @param data
     * @param isSuccess
     */
    public static <T> Object strToJsonObject(String data, T t) {
        try {
            if (StringUtils.equals(data, "error")) {
                return null;
            }
            ObjectMapper mapper = new ObjectMapper();
            T object = mapper.readValue(data, new TypeReference<T>() {
            });
            return object;
        } catch (Exception ex) {
            logger.error("数据格式错误", ex);
        }
        return null;
    }

    /**
     * 验证字符串是否是一个有效的json
     *
     * @param json
     * @return
     */


    public static boolean isGoodJson(String json) {
        if (StringUtils.isBlank(json)) {
            return false;
        }
        try {
            new JsonParser().parse(json);
            return true;
        } catch (JsonParseException e) {
            logger.error("bad json: " + json);
            return false;
        }
    }

}
