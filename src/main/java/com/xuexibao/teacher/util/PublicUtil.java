package com.xuexibao.teacher.util;

import org.apache.commons.lang3.StringUtils;

public class PublicUtil {
	public static boolean isEmpty(String value) {
		return StringUtils.isEmpty(value) || "null".equals(value);
	}
}
