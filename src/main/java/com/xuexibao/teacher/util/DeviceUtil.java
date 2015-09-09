package com.xuexibao.teacher.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.xuexibao.teacher.core.security.RSAUtils;
import com.xuexibao.teacher.exception.BusinessException;
import com.xuexibao.teacher.model.vo.DeviceVO;
import com.xuexibao.teacher.validator.Validator;

/**
 * @author oldlu
 */
public class DeviceUtil {
	public static String decodeDeviceId(String deviceId) {
		Validator.validateBlank(deviceId, "设备编码不能为空");

		try {
			String json = RSAUtils.decode(deviceId);
			DeviceVO vo = GsonUtil.json2Object(json, DeviceVO.class);
			String md5str = vo.AID + vo.WMAC + vo.BMAC + vo.IMEI + vo.PUID;
			return CipherUtil.MD5(md5str);
		} catch (IOException e) {
			throw new BusinessException("私钥文件获取失败", e);
		} catch (Exception e) {
			throw new BusinessException("设备号数据非法，解密出错", e);
		}
	}

	public static String decodeDeviceIdByNode(String deviceId) {

		return "";
	}

	public static void main(String args[]) {
		String url = "http://webapi.91xuexibao.com:3000/api/device/bind";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dev_id", "1");
		paramMap.put("isnew", "2");
		paramMap.put("user_agent", "3");
		paramMap.put("channel", "4");
		paramMap.put("bid", "5");
		paramMap.put("mobile", "6");

		Map<String, Object> result = HttpUtil.httpPost(url, paramMap);
		System.out.println(result);
	}
}
