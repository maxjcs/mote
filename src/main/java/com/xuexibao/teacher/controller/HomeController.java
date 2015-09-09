package com.xuexibao.teacher.controller;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.xuexibao.teacher.core.AppContext;
import com.xuexibao.teacher.exception.BusinessException;
import com.xuexibao.teacher.model.Feedback;
import com.xuexibao.teacher.model.FeudPointFeeConf;
import com.xuexibao.teacher.model.RatingIncomeConfig;
import com.xuexibao.teacher.model.Teacher;
import com.xuexibao.teacher.service.FeedbackService;
import com.xuexibao.teacher.service.FeudPointFeeConfService;
import com.xuexibao.teacher.service.HomeService;
import com.xuexibao.teacher.service.RatingIncomeConfigService;
import com.xuexibao.teacher.service.RatingService;
import com.xuexibao.teacher.service.TeacherService;
import com.xuexibao.teacher.util.PropertyUtil;
import com.xuexibao.teacher.validator.Validator;

@Controller
@RequestMapping("home")
public class HomeController extends AbstractController {
	@Resource
	private RatingService ratingService;
	@Resource
	private HomeService homeService;
	@Resource
	private FeedbackService feedbackService;
	@Resource
	private TeacherService teacherService;
	@Resource
	private RatingIncomeConfigService ratingIncomeConfigService;
	@Resource
	private FeudPointFeeConfService reudPointFeeConfService;

	@ResponseBody
	@RequestMapping(value = "getRatingList")
	public Object getRatingList() {
		Teacher teacher = teacherService.getRequiredTeacher(AppContext.getTeacherId());
		List<RatingIncomeConfig> listTaskConf = ratingIncomeConfigService.getRatingByTeacherIdentify(teacher.getTeacherIdentify());
		List<FeudPointFeeConf> listFeudConf = reudPointFeeConfService.getRatingByTeacherIdentify(teacher.getTeacherIdentify());

		Map<Integer, List<Object>> map = new LinkedHashMap<Integer, List<Object>>();

		for (RatingIncomeConfig config : listTaskConf) {
//			if (config.getStar() == 0) {// 0星不显示
//				continue;
//			}
			List<Object> list = new ArrayList<Object>();
			if (config.getFee() != 0) {// 没费用不显示
				list.add(config);
			}
			map.put(config.getStar(), list);
		}

		
		Map<Integer, List<Object>> resultMap = new LinkedHashMap<Integer, List<Object>>();

		for (FeudPointFeeConf config : listFeudConf) {
			List<Object> list = map.get(config.getStarEvaluate());
			if (list != null) {
				list.add(config);
			}else{
				list = new ArrayList<Object>();
				list.add(config);
				resultMap.put(config.getStarEvaluate(), list);
			}
		}
		
		resultMap.putAll(map);

		List<Object> result = new ArrayList<Object>();

		for (Integer star : resultMap.keySet()) {
			if (CollectionUtils.isEmpty(resultMap.get(star))) {
				continue;
			}
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("star", star);
			item.put("items", resultMap.get(star));

			result.add(item);
		}

		return dataJson(result);
	}

	@ResponseBody
	@RequestMapping(value = "getPointRuleList")
	public Object getPointRuleList() {
		Teacher teacher = teacherService.getRequiredTeacher(AppContext.getTeacherId());

		return dataJson(homeService.getPointRuleList(teacher.getTeacherIdentify()));
	}

	@ResponseBody
	@RequestMapping(value = "getPoint")
	public Object getPoint() {
		return dataJson(homeService.getPoint(AppContext.getTeacherId()));
	}

	@ResponseBody
	@RequestMapping(value = "getInfo")
	public Object getInfo() {
		return dataJson(homeService.getInfo(AppContext.getTeacherId()));
	}

	@ResponseBody
	@RequestMapping(value = "getQrcode")
	public void getQrcode(HttpServletResponse response) {
		String qrcodeUrl = PropertyUtil.getProperty("qrcode_url");
		Validator.validateBlank(qrcodeUrl, "二维码地址没有配置");

		String content = String.format(qrcodeUrl, AppContext.getTeacherId());
		MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

		Map<EncodeHintType, String> hints = new HashMap<EncodeHintType, String>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

		try {
			BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 300, 300, hints);

			BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);

			ImageIO.write(image, "png", response.getOutputStream());
		} catch (Exception e) {
			throw new BusinessException("二维码生成错误", e);
		}
	}

	@ResponseBody
	@RequestMapping(value = "submitFeedback")
	public Object submitFeedback(Feedback feedback) {
		Validator.validateBlank(feedback.getContent(), "内容不能为空.");

		feedback.setTeacherId(AppContext.getTeacherId());
		feedbackService.addFeedback(feedback);

		return dataJson(true);
	}
}
