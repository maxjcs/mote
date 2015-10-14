package com.longcity.modeler.validator;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.longcity.modeler.exception.ValidateException;
import com.longcity.modeler.util.FileUtil;
import com.longcity.modeler.util.ValidateUtil;


/**
 * 
 * @author czl
 * 
 */
public class Validator {
	// 允许图片类型
	private static String[] fileType = new String[] { "png", "jpg", "jpeg" };

	// 文件大小
	private static long maxSize = 50 * 1024 * 1024;

	public static void validateLtZero(Number num, String msg) {
		if (num == null || num.longValue() < 1) {
			throw new ValidateException(msg);
		}
	}

	public static void validateBlank(String str, String msg) {
		if (StringUtils.isBlank(str)) {
			throw new ValidateException(msg);
		}
	}
	
	public static void validateMinLength(String str,int length,String msg) {
		if (StringUtils.isBlank(str)&&str.length()<length) {
			throw new ValidateException(msg);
		}
	}

	public static void validateBlank(Long value, String msg) {
		if (value == null) {
			throw new ValidateException(msg);
		}
	}

	public static void validateBlank(Integer value, String msg) {
		if (value == null) {
			throw new ValidateException(msg);
		}
	}

	public static void validateBlank(Double value, String msg) {
		if (value == null) {
			throw new ValidateException(msg);
		}
	}

	@SuppressWarnings("rawtypes")
	public static void validateCollectionEmpty(Collection value, String msg) {
		if (value == null || value.isEmpty()) {
			throw new ValidateException(msg);
		}
	}

	public static void validateMobile(String str, String msg) {
		validateBlank(str, "手机号不能为空.");
		if (!ValidateUtil.isMobile(str)) {
			throw new ValidateException(msg);
		}
	}

	public static void validateImageFile(MultipartFile file) {
		if (file == null || file.isEmpty()) {
			return;
		}
		if (file.getSize() > maxSize) {
			throw new ValidateException("文件太大，不能超过2M");
		}
		if (!FileUtil.isAllowType(fileType, file.getOriginalFilename())) {
			throw new ValidateException("图片类型不对，只能上传png,jpg,jpeg");
		}
	}

	public static void validateFile(MultipartFile file, String msg) {
		if (file == null || file.isEmpty()) {
			throw new ValidateException(msg);
		}
	}

	public static void validateContainsEnum(List<?> all, Object item, String msg) {
		if (!all.contains(item)) {
			throw new ValidateException(msg);
		}
	}
}
