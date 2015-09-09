package com.xuexibao.teacher.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xuexibao.teacher.enums.DictType;
import com.xuexibao.teacher.model.Dict;
import com.xuexibao.teacher.service.DictService;
import com.xuexibao.teacher.validator.Validator;
import com.xuexibao.webapi.teacher.client.T_DictService;
import com.xuexibao.webapi.teacher.model.City;
import com.xuexibao.webapi.teacher.model.Grade;
import com.xuexibao.webapi.teacher.model.GradeSubject;
import com.xuexibao.webapi.teacher.model.School;
import com.xuexibao.webapi.teacher.model.Subject;


@Controller
@RequestMapping("dict")
public class DictController extends AbstractController {
	
	private static Logger logger = LoggerFactory.getLogger(DictController.class);
	
	@Resource
	private DictService dictService;
	
	@Resource
	T_DictService t_DictService;//中间件的 字典服务
	/**
	 * 获取省份list
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getProvinceList")
	public Object getProvinceList(HttpServletRequest request) {
		List<City> list = t_DictService.getProvinceList();
		if(list!=null){
			return dataJson(list,request);
		}else{
			logger.error("getProvinceList(),中间件服务器异常！");
			return errorJson("服务器异常，请重试.", request);
		}
	}
	
	/**
	 * 获取学科list
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getSubjectList")
	public Object getSubjectList(HttpServletRequest request) {
		List<Subject> list = t_DictService.getSubjectList();
		if(list!=null){
			return dataJson(list,request);
		}else{
			logger.error("getSubjectList(),中间件服务器异常！");
			return errorJson("服务器异常，请重试.", request);
		}
	}
	
	/**
	 * 获取城市list
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getCityList")
	public Object getCityList(HttpServletRequest request) {
		List<City> list = t_DictService.getCityList();
		List<City> noChildren = new ArrayList<City>();
		for(City city:list){
			if(city.getChildren()==null||city.getChildren().isEmpty()){
				noChildren.add(city);
			}
		}
		list.removeAll(noChildren);
		if(list!=null){
			return dataJson(list,request);
		}else{
			logger.error("getCityList(),中间件服务器异常！");
			return errorJson("服务器异常，请重试.", request);
		}
	}
	
	/**
	 * 根据省份id获取学校列表
	 * @param provId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getSchoolListByProvId")
	public Object getSchoolListByProvId(HttpServletRequest request,int provId){
		Validator.validateBlank(provId, "省份Id不能为空!");
		List<School> list = t_DictService.getSchoolListByProvId(provId);
		if(list!=null){
			return dataJson(list,request);
		}else{
			logger.error("getSchoolListByProvId(),中间件服务器异常！");
			return errorJson("服务器异常，请重试.", request);
		}
	}
	
	/**
	 * 获取年级列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getGradeList")
	public Object getGradeList(HttpServletRequest request){
		List<Grade> list = t_DictService.getGradeList();
		if(list!=null){
			return dataJson(list,request);
		}else{
			logger.error("getGradeList(),中间件服务器异常！");
			return errorJson("服务器异常，请重试.", request);
		}
	}
	
	/**
	 * 根据年级id获取科目List
	 * @param gradeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getSubjectListByGrade")
	public Object getSubjectListByGrade(HttpServletRequest request,int gradeId){
		Validator.validateBlank(gradeId, "年级Id不能为空!");
		List<GradeSubject> list= t_DictService.getSubjectListByGrade(gradeId);
		if(list!=null){
			return dataJson(list,request);
		}else{
			logger.error("getSubjectListByGrade(),中间件服务器异常！");
			return errorJson("服务器异常，请重试.", request);
		}
	}
	
	/**
	 * 获取年纪、学科、关系
	 * @param gradeId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "getGradeSubjectList")
	public Object getGradeSubjectList(HttpServletRequest request){
		Map map = t_DictService.getGradeSubjectList();
		if(map!=null){
			return dataJson(map,request);
		}else{
			logger.error("getGradeSubjectList(),中间件服务器异常！");
			return errorJson("服务器异常，请重试.", request);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "getGenderList")
	public Object getGenderList() {
		return dataJson(dictService.listDictByType(DictType.gender));
	}

	@ResponseBody
	@RequestMapping(value = "getTeacherIdentifyList")
	public Object getTeacherIdentifyList() {
		return dataJson(dictService.listDictByType(DictType.teacherIdentify));
	}

	@ResponseBody
	@RequestMapping(value = "getTeachingTimeList")  
	public Object getTeachingTimeList() {
		List<Dict> list = dictService.listDictByType(DictType.teachingTime);

		Map<String, List<Dict>> result = new LinkedHashMap<String, List<Dict>>();
		if (!CollectionUtils.isEmpty(list)) {
			for (Dict dict : list) {
				String firstChar = dict.getFirstChar();
				List<Dict> subs = result.get(firstChar);
				if (subs == null) {
					subs = new ArrayList<Dict>();
					result.put(firstChar, subs);
				}
				subs.add(dict);
			}
		}
		return dataJson(result.values());
	}
}
