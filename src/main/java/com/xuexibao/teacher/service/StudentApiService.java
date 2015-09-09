package com.xuexibao.teacher.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.xuexibao.teacher.constant.PageConstant;
import com.xuexibao.teacher.dao.AudioDao;
import com.xuexibao.teacher.exception.BusinessException;
import com.xuexibao.teacher.model.Audio;
import com.xuexibao.teacher.model.rpcvo.AudioBuyTotalNumVO;
import com.xuexibao.teacher.model.rpcvo.AudioEvaluateCount;
import com.xuexibao.teacher.model.rpcvo.AudioGoodTag;
import com.xuexibao.teacher.model.rpcvo.AudioSetBuyCountVO;
import com.xuexibao.teacher.model.rpcvo.AudioSetBuyStatusVO;
import com.xuexibao.teacher.model.rpcvo.AudioSetEvaluateCount;
import com.xuexibao.teacher.model.rpcvo.AudioSetEvaluateDetail;
import com.xuexibao.teacher.model.rpcvo.AudioSetRankVO;
import com.xuexibao.teacher.model.rpcvo.AudioSetStudentComment;
import com.xuexibao.teacher.model.rpcvo.AudioTotalPrice;
import com.xuexibao.teacher.model.rpcvo.AudioTotalPriceVO;
import com.xuexibao.teacher.model.rpcvo.BuyAndCommentCount;
import com.xuexibao.teacher.model.rpcvo.RpcVO;
import com.xuexibao.teacher.model.rpcvo.TeacherBalanceVO;
import com.xuexibao.teacher.pay.dao.PayAudioDao;
import com.xuexibao.teacher.pay.model.PayAudio;
import com.xuexibao.teacher.util.HttpsUtil;
import com.xuexibao.teacher.util.JsonUtil;
import com.xuexibao.teacher.util.PropertyUtil;
import com.xuexibao.teacher.util.StudentApiHttpUtil;
import com.xuexibao.webapi.student.client.T_UserService;
import com.xuexibao.webapi.student.model.User;

/**
 * 
 * @author fengbin 调用学生端API server 走加密 调用支付系统 走HTTPS 但缺个 证书功能，后期修改
 * 
 */
@Service
public class StudentApiService {
	// 学习宝服务端
	private String studentApiSystemUrl = PropertyUtil.getProperty("studentApiSystemUrl");
	// 钱包服务端
	private String paySystemUrl = PropertyUtil.getProperty("paySystemUrl");;

	private final String URL_GET_TEACHER_BALANCE = "/api/teacher/balance";

	private final String URL_AUDIO_TOTAL_PRICE = "/api/teacher/audioTotalPrice";
	// 更新钱包新接口
	private final String URL_UPDATE_TEACHER_BALANCE = "/api/teacher/star_charge";
	// 课件的购买总数
	private final String URL_GET_COURSEWARE_TOTALBUY = "/api/teacher/courseWareTotalBuy";
	// 课件的购买记录
	private final String URL_GET_COURSEWARE_BUYORDER = "/api/teacher/courseWare/buyOrder";
	// 课件是否被学生购买

	// 习题集购买数量
	private final String URL_GET_AUDIOSET_BUYCOUNT = "/api/teacher/exercises/buyCount";

	private final String URL_GET_AUDIOSET_BUYSTATUS = "/api/teacher/exercises/buyStatus";

	private final String URL_GET_AUDIOSET_RANK = "/api/teacher/exercises/rank";

	private final String URL_GET_BUY_COMMNETCOUNT = "/api/teacher/buyAndCommentCount";

	// 抢答推送新音频
	private String messageNewAudioPush = "/api/message/newAudioPush";

	private final String URL_GET_AUDIO_SET_EVALUATE_COUNT = "/api/exercises/getExercisesCommentCountByIds";
	private final String URL_GET_AUDIO_SET_EVALUATE_DETAIL = "/api/exercises/getExercisesCommentsById";
	private final String URL_GET_AUDIO_SET_MY_COMMENT = "/api/exercises/getUserCommentByIds";

	private final String URL_GET_AUDIO_EVALUATE_COUNT = "/api/audio/getCommentCountsByIds";

	private final String URL_GET_AUDIO_GOOD_TAG = "/api/teacher/audiosHasPraise";

	private final String URL_GET_AUDIO_TOTAL_PRICE_AND_NUM = "/api/teacher/getAudioTotalPriceAndNum";

	private final String URL_GET_AUDIO_TOTAL_PRICE = "/api/teacher/getAudiosTotalPrice";

	private final String URL_TEACHER_TREND_DELETE = "/api/teacher/trendDelete";
	private final String URL_TEACHER_TRENT_PUBLISH = "/api/teacher/trendPublish";

	// 习题集的好评率
	private final String URL_AUDIO_SET_RATE = "/api/teacher/exercisesRate";

	// 关注的学生列表
	private final String URL_FOLLOWED_LIST = "/api/teacher/followList";

	private static Logger logger = LoggerFactory.getLogger(StudentApiService.class);

	@Autowired
	private AudioDao audioDao;
	@Resource
	private PayAudioDao payAudioDao;

	@Resource
	private T_UserService t_UserService;

	/**
	 * 获取音频购买总数
	 * 
	 * @param teacherId
	 * @return
	 */

	public Map<String, AudioBuyTotalNumVO.Item> getAudioTotalPriceAndNum(List<String> audioIds,
			boolean throwException) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("audioIds", StringUtils.join(audioIds.toArray(), ","));

		AudioBuyTotalNumVO result = StudentApiHttpUtil.httpPost(paySystemUrl + URL_GET_AUDIO_TOTAL_PRICE_AND_NUM, paramMap,
				AudioBuyTotalNumVO.class);
		if (result == null || !result.success) {
			logger.error("获取习题集购买次数", JsonUtil.obj2Json(result));
			if (throwException) {
				throw new BusinessException(result.msg);
			}
			return null;
		} else {
			return result.data;
		}
	}

	public Map<String,Integer> getAudioTotalPrice(List<String> audioIds, String teacherId,
			boolean throwException) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("audioIds", StringUtils.join(audioIds.toArray(), ","));
		paramMap.put("teacherId", teacherId);
		paramMap.put("priceType", 2);

		AudioTotalPrice result = HttpsUtil.httpPost(paySystemUrl + URL_GET_AUDIO_TOTAL_PRICE, paramMap,
				AudioTotalPrice.class);
		if (result == null || !result.success) {
			logger.error("获取音频分成金额", JsonUtil.obj2Json(result));
			if (throwException) {
				throw new BusinessException(result.msg);
			}
			return null;
		} else {
			return result.data;
		}
	}

	/**
	 * 获取习题集购买次数
	 * 
	 * @param teacherId
	 * @return
	 */

	public AudioSetRankVO.Item getAudioSetRank(Integer pageno, String subjectIds, String gradeIds, Integer count,
			boolean throwException) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("pageno", pageno);
		paramMap.put("count", count);
		if (!StringUtils.isEmpty(subjectIds)) {
			paramMap.put("subjectIds", subjectIds);
		}
		if (!StringUtils.isEmpty(gradeIds)) {
			paramMap.put("gradeIds", gradeIds);
		}
		AudioSetRankVO result = HttpsUtil.httpPost(paySystemUrl + URL_GET_AUDIOSET_RANK, paramMap,
				AudioSetRankVO.class);
		if (result == null || !result.success) {
			logger.error("获取习题销量列表错误", JsonUtil.obj2Json(result));
			if (throwException) {
				throw new BusinessException(result.msg);
			}
			return null;
		} else {
			return result.data;
		}
	}

	@Async
	public void deleteTeacherTrend(Long trendId, String teacherId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("trend_id", trendId);
		paramMap.put("teacher_id", teacherId);
		RpcVO result = StudentApiHttpUtil.httpPost(studentApiSystemUrl + URL_TEACHER_TREND_DELETE, paramMap,
				RpcVO.class);
		if (result == null || !result.success) {
			logger.error("删除教师动态", JsonUtil.obj2Json(result));
		} else {
			logger.info("动态[" + trendId + "]已在学生端删除");
		}
	}

	@Async
	public void publishTeacherTrend(Long trendId, String teacherId,String type) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("trend_id", trendId);
		paramMap.put("teacher_id", teacherId);
		paramMap.put("trend_type", type);

		RpcVO result = StudentApiHttpUtil.httpPost(studentApiSystemUrl + URL_TEACHER_TRENT_PUBLISH, paramMap,
				RpcVO.class);
		if (result == null || !result.success) {
			logger.error("发布教师动态" + JsonUtil.obj2Json(result));
		} else {
			logger.info("动态[" + trendId + "]已发布到学生端");
		}
	}

	/**
	 * 获取习题集购买次数
	 * 
	 * @param teacherId
	 * @return
	 */
	public Map<String, Integer> getAudioSetBuyCount(List<String> listId, boolean throwException) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("exercisesIds", StringUtils.join(listId.toArray(), ","));

		AudioSetBuyCountVO result = HttpsUtil.httpPost(paySystemUrl + URL_GET_AUDIOSET_BUYCOUNT, paramMap,
				AudioSetBuyCountVO.class);
		if (result == null || !result.success) {
			logger.error("获取习题集购买次数", JsonUtil.obj2Json(result));
			if (throwException) {
				throw new BusinessException(result.msg);
			}
			return null;
		} else {
			return result.data;
		}
	}

	public List<AudioEvaluateCount.Item> getAudioEvaluateList(List<String> listId, boolean throwException) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("audios", StringUtils.join(listId.toArray(), ","));
		AudioEvaluateCount result = StudentApiHttpUtil.httpPost(studentApiSystemUrl + URL_GET_AUDIO_EVALUATE_COUNT,
				paramMap, AudioEvaluateCount.class);
		if (result == null || !result.success) {
			logger.error("获取音频评价数量", JsonUtil.obj2Json(result));
			if (throwException) {
				throw new BusinessException(result.msg);
			}
			return null;
		} else {
			return result.data;
		}
	}

	public List<AudioGoodTag.Item> getAudioGoodTag(List<String> listId, boolean throwException) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("audios", StringUtils.join(listId.toArray(), ","));

		AudioGoodTag result = StudentApiHttpUtil.httpPost(studentApiSystemUrl + URL_GET_AUDIO_GOOD_TAG, paramMap,
				AudioGoodTag.class);
		if (result == null || !result.success) {
			logger.error("获取音频好品情况", JsonUtil.obj2Json(result));
			if (throwException) {
				throw new BusinessException(result.msg);
			}
			return null;
		} else {
			return result.data;
		}
	}

	public Map<String, AudioSetEvaluateCount.Item> getAudioSetEvaluateDetail(List<String> listId,
			boolean throwException) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("exercisesIds", StringUtils.join(listId.toArray(), ","));

		AudioSetEvaluateCount result = StudentApiHttpUtil.httpPost(
				studentApiSystemUrl + URL_GET_AUDIO_SET_EVALUATE_COUNT, paramMap, AudioSetEvaluateCount.class);
		if (result == null || !result.success) {
			logger.error("获取习题集评价数量", JsonUtil.obj2Json(result));
			if (throwException) {
				throw new BusinessException(result.msg);
			}
			return null;
		} else {
			return result.data;
		}
	}

	public List<AudioSetStudentComment.Item> getAudioSetStudentCommentList(List<String> listId, String studentId,
			boolean throwException) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("exercisesIds", StringUtils.join(listId.toArray(), ","));
		paramMap.put("userId", studentId);

		AudioSetStudentComment result = StudentApiHttpUtil.httpPost(studentApiSystemUrl + URL_GET_AUDIO_SET_MY_COMMENT,
				paramMap, AudioSetStudentComment.class);
		if (result == null || !result.success) {
			logger.error("获取习题集评价列表", JsonUtil.obj2Json(result));
			if (throwException) {
				throw new BusinessException(result.msg);
			}
			return null;
		} else {
			return result.data;
		}
	}

	public List<AudioSetEvaluateDetail.Item> getAudioSetEvaluateList(String setId, Integer evaluteType, Integer pageNo,
			boolean throwException) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("exercisesId", setId);
		paramMap.put("type", evaluteType);
		paramMap.put("pageNo", pageNo);
		paramMap.put("count", PageConstant.PAGE_SIZE_10);

		AudioSetEvaluateDetail result = StudentApiHttpUtil.httpPost(
				studentApiSystemUrl + URL_GET_AUDIO_SET_EVALUATE_DETAIL, paramMap, AudioSetEvaluateDetail.class);
		if (result == null || !result.success) {
			logger.error("获取习题集评价明细", JsonUtil.obj2Json(result));
			if (throwException) {
				throw new BusinessException(result.msg);
			}
			return null;
		} else {
			return result.data.data;
		}
	}

	/**
	 * 获取习题集状态
	 * 
	 * @param teacherId
	 * @return
	 */
	public BuyAndCommentCount.Item getAudioBuyAndCommentCount(List<String> listId, String teacherId,
			boolean throwException) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("audioIds", StringUtils.join(listId.toArray(), ","));
		paramMap.put("teacherId", teacherId);

		BuyAndCommentCount result = HttpsUtil.httpPost(paySystemUrl + URL_GET_BUY_COMMNETCOUNT, paramMap,
				BuyAndCommentCount.class);
		if (result == null || !result.success) {
			logger.error("获取音频购买数", JsonUtil.obj2Json(result));
			if (throwException) {
				throw new BusinessException(result.msg);
			}
			return null;
		} else {
			return result.data;
		}
	}

	/**
	 * 获取习题集状态
	 * 
	 * @param teacherId
	 * @return
	 */
	public Map<String, Boolean> getAudioSetBuyStatus(List<String> listId, String studentId, boolean throwException) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("exercisesIds", StringUtils.join(listId.toArray(), ","));
		paramMap.put("studentId", studentId);

		AudioSetBuyStatusVO result = HttpsUtil.httpPost(paySystemUrl + URL_GET_AUDIOSET_BUYSTATUS, paramMap,
				AudioSetBuyStatusVO.class);
		if (result == null || !result.success) {
			logger.error("获取习题集购买状态", JsonUtil.obj2Json(result));
			if (throwException) {
				throw new BusinessException(result.msg);
			}
			return null;
		} else {
			return result.data;
		}
	}

	/**
	 * 获取教师钱包余额
	 * 
	 * @param teacherId
	 * @return
	 */
	public int getTeacherBalance(String teacherId, boolean throwException) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("teacherId", teacherId);

		TeacherBalanceVO balance = HttpsUtil.httpPost(paySystemUrl + URL_GET_TEACHER_BALANCE, paramMap,
				TeacherBalanceVO.class);
		if (balance == null || !balance.success) {
			logger.error("获取钱包余额失败", JsonUtil.obj2Json(balance));
			if (throwException) {
				throw new BusinessException(balance.msg);
			}
			return 0;
		} else {
			return balance.data.balance;
		}
	}

	/**
	 * 获取音频总分成
	 * 
	 * @param teacherId
	 * @return
	 */
	public AudioTotalPriceVO.Item getAudioTotoalPrice(String audioId, boolean throwException) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("audioId", audioId);

		PayAudio audio = payAudioDao.queryAudioById(audioId);

		if (audio == null) {
			return new AudioTotalPriceVO().data;
		}
		AudioTotalPriceVO result = HttpsUtil.httpPost(paySystemUrl + URL_AUDIO_TOTAL_PRICE, paramMap,
				AudioTotalPriceVO.class);
		if (result == null || !result.success) {
			logger.error("获取音频分成失败", JsonUtil.obj2Json(result));
			if (throwException) {
				throw new BusinessException("获取音频分成失败");
			}
			return null;
		} else {
			return result.data;
		}
	}

	public AudioTotalPriceVO.Item getAudioTotoalPrice(String audioId) {
		return getAudioTotoalPrice(audioId, true);
	}

	/**
	 * 更新钱包余额
	 * 
	 * @param teacherId
	 * @param money
	 */
	public void updateTeacherBalance(String teacherId, int money, String audioId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("teacherId", teacherId);
		paramMap.put("audioId", audioId);
		paramMap.put("money", money);

		Audio audio = audioDao.queryAudioById(audioId);
		if (audio != null) {
			paramMap.put("source", audio.getSource());
			paramMap.put("audioName", audio.getName());
			paramMap.put("feudType", audio.getFeudType());

		}

		RpcVO result = HttpsUtil.httpPost(paySystemUrl + URL_UPDATE_TEACHER_BALANCE, paramMap, RpcVO.class);
		if (result == null || !result.success) {
			logger.error("update golden package error:" + JsonUtil.obj2Json(result));
			throw new BusinessException(result.msg);
		}
	}

	/**
	 * 教师已解答，推送音频或白板信息
	 * 
	 * @param paramVO
	 * @return
	 */

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map pushAudioWtOnline(String paramsStr, String teacherName, String strSubject) {
		Map postMap = new HashMap();
		// userId:图片或者音频ID:question_real_id:
		// String paramsStr =
		// "547456a45a527e3f61942285:2d0df0e0-9578-11e4-83d5-5188a3ef30f3:23088814:1";
		postMap.put("params", paramsStr);
		StringBuilder sb = new StringBuilder();
		sb.append("为你找到了一个新的音频讲解");
		if (!StringUtils.isBlank(strSubject)) {
			sb.append("(").append(strSubject).append(")");
		}
		postMap.put("alert_text", sb.toString());
		postMap.put("name", teacherName);

		String serverAddress = studentApiSystemUrl + messageNewAudioPush;
		try {
			Map<String, Object> map = StudentApiHttpUtil.httpPost(serverAddress, postMap);
			return map;

		} catch (Exception e) {
			logger.error("pushAudioWtOnline error:" + e.getMessage());
			logger.info(e.getMessage(), e);
			return null;
		}

	}

	/**
	 * 课件的对某个学生的购买状态
	 * 
	 * @param cwIdList
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map getCourseWareTotalBuy(String cwIds, String studentId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("cwIds", cwIds);
		paramMap.put("studentId", studentId);
		Map map = StudentApiHttpUtil.httpPost(studentApiSystemUrl + URL_GET_COURSEWARE_TOTALBUY, paramMap, Map.class);
		return map;
	}

	// /**
	// * 课件的购买数量
	// * @param cwIdList
	// * @return
	// */
	// @SuppressWarnings("rawtypes")
	// public Map getCourseWareSaleNum(String cwIds){
	// Map<String, Object> paramMap = new HashMap<String, Object>();
	// paramMap.put("cwIds", cwIds);
	// Map map = HttpUtil.httpPost(studentApiSystemUrl +
	// URL_GET_COURSEWARE_TOTALNUM, paramMap, Map.class);
	// return map;
	// }

	/**
	 * 课件的购买记录
	 * 
	 * @param cwIdList
	 * @return
	 */
	public Map getCourseWareBuyOrder(Long productId, Integer pageno, Integer count) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("productId", productId);
		paramMap.put("pageno", pageno);
		paramMap.put("count", count);
		Map map = StudentApiHttpUtil.httpPost(studentApiSystemUrl + URL_GET_COURSEWARE_BUYORDER, paramMap, Map.class);
		return map;
	}

	// /**
	// * 批量获取学生信息
	// *
	// * @param studentIds
	// * 以','分割的字符串
	// * @return 以用户的ID对应的Map
	// */
	// @SuppressWarnings({ "unchecked", "rawtypes" })
	// public HashMap<String, Map> getUsersInfo(String studentIds) {
	// // 从学生端调用http接口，然后封装到对应的LearnTalk中
	// String url = studentApiSystemUrl + perInfosByIdsUriPath; //
	// "http://192.168.1.231:30000/api/user/getPerInfosByIds";
	// Map params = new HashMap<String, String>();
	// params.put("user_ids", studentIds);
	// Map<String, Object> returnObj = StudentApiHttpUtil.httpPost(url, params);
	//
	// List<Map> resultList = (List<Map>) returnObj.get("result");
	// HashMap<String, Map> usersInfo = new HashMap<String, Map>();
	//
	// for (Map t : resultList) {
	// if (!t.containsKey("user_id")) {
	// continue;
	// }
	// usersInfo.put((String) t.get("user_id"), t);
	// }
	// return usersInfo;
	// }

	/**
	 * 批量获取学生信息
	 * 
	 * @param studentIds
	 *            以','分割的字符串
	 * @return 以用户的ID对应的Map
	 */
	public HashMap<String, User> getUsersInfo(Set<String> studentIds) {
		HashMap<String, User> usersInfo = new HashMap<String, User>();
		try{
			if(!studentIds.isEmpty()){
				// 中间件获取用户列表
				List<String> userIds = new ArrayList<String>();
				for(String userid:studentIds){
					if(StringUtils.isNotEmpty(userid)){
						userIds.add(userid);
					}
				}
				logger.info("中间件获取用户,user="+StringUtils.join(userIds, ","));
				List<User> userList = t_UserService.getUserByIds(userIds);
				if (userList == null || userList.size() == 0) {
					return usersInfo;
				}
				
				// 转换成map
				for (User user : userList) {
					usersInfo.put(user.get_id(), user);
				}
			}
		}catch(Exception ex){
			logger.error("中间件获取用户信息出错,user="+studentIds,ex);
		}
		return usersInfo;
	}

	// /**
	// * 通过学生ID获取学生详细信息
	// *
	// * @param studentId
	// * @return map
	// */
	// @SuppressWarnings({ "unchecked", "rawtypes" })
	// public HashMap<String, String> getSingleUsersInfo(String studentId) {
	// // 从学生端调用http接口，然后封装到对应的LearnTalk中
	// String url = studentApiSystemUrl + perInfosByIdUriPath; //
	// "http://192.168.1.231:30000/api/user/getPerInfosByIds";
	// Map params = new HashMap<String, String>();
	// params.put("user_id", studentId);
	// Map<String, Object> returnObj = StudentApiHttpUtil.httpPost(url, params);
	// Map resultMap = (Map) returnObj.get("result");
	// return (HashMap<String, String>) resultMap;
	// }

	/**
	 * 习题集的好评率
	 * 
	 * @param teacherIds
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map<String, String> getExercisesRate(String teacherIds) {

		// 从学生端调用http接口，然后封装到对应的LearnTalk中
		String url = studentApiSystemUrl + URL_AUDIO_SET_RATE; // "http://192.168.1.231:30000/api/user/getPerInfosByIds";
		Map params = new HashMap<String, String>();
		params.put("teacherIds", teacherIds);
		Map<String, Object> returnObj = StudentApiHttpUtil.httpPost(url, params);
		if(returnObj!=null){
			Map dataMap = (Map) returnObj.get("data");
			List<Map> mapList = (List<Map>) dataMap.get("teacherRate");
			Map<String, String> resultMap = new HashMap<String, String>();
			for (Map map : mapList) {
				resultMap.put((String) map.get("teacherId"), (String) map.get("rate"));
			}
			return resultMap;
		}else{
			return new HashMap<String, String>();
		}
		
	}

	/**
	 * 关注老师的列表
	 * 
	 * @param teacherIds
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getFollowList(String teacherId, Integer sortType, Integer gradeId, Integer pageNo, Integer pageSize) {
		// 从学生端调用http接口
		String url = studentApiSystemUrl + URL_FOLLOWED_LIST;
		Map params = new HashMap<String, String>();
		params.put("teacherId", teacherId);
		params.put("sortType", sortType);
		params.put("gradeId", gradeId);
		params.put("pageNo", pageNo);
		params.put("pageSize", pageSize);
		Map<String, Object> returnObj = StudentApiHttpUtil.httpPost(url, params);
		Map dataMap = (Map) returnObj.get("data");
		return dataMap;
	}

}
