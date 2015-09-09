package com.xuexibao.teacher.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.xuexibao.teacher.dao.ErrorQuestDao;
import com.xuexibao.teacher.model.ErrorQuest;
import com.xuexibao.teacher.model.QuestReasonType;
import com.xuexibao.teacher.model.Teacher;

/**
 * 
 * @author oldlu
 * 
 */
@Service
public class ErrorQuestService {
	@Resource
	private ErrorQuestDao errorQuestDao;
	@Resource
	private TeacherService teacherService;

	/**
	 * @param paramMap
	 * @return
	 */
	public List<ErrorQuest> getErrorQuestList(Map<String, Object> paramMap) {
		List<ErrorQuest> errorQuestList = errorQuestDao.listErrorQuest(paramMap);

		if (!CollectionUtils.isEmpty(errorQuestList)) {
			List<QuestReasonType> reasonTypeList = errorQuestDao.getQuestReasonTypeList(errorQuestList);
			if (!CollectionUtils.isEmpty(reasonTypeList)) {
				Map<Long, QuestReasonType> map = new HashMap<Long, QuestReasonType>();
				for (QuestReasonType reasonType : reasonTypeList) {
					Teacher teacher = teacherService.getTeacher(reasonType.getTeacherId());
					if (teacher != null) {
						reasonType.setTeacherName(teacher.getName());
						reasonType.setPhoneNumber(teacher.getPhoneNumber());
						reasonType.setTeacherNickName(teacher.getNickname());
					}
					 
					map.put(reasonType.getQuestionId(), reasonType);
				}

				for (ErrorQuest errorQuest : errorQuestList) {
					QuestReasonType questReason = map.get(errorQuest.getRealId());

					if (questReason != null) {
						errorQuest.setQuestReason(questReason);
					}
				}
			}
		}
		return errorQuestList;
	}

	public void updateQuestReadStatus(String[] str) {
		errorQuestDao.updateQuestReadStatus(str);
	}

	public void audioErrorQuests(List<ErrorQuest> list) {
		if (!CollectionUtils.isEmpty(list)) {
			errorQuestDao.updateQuestAuditStatus(list);
			for (ErrorQuest item : list) {
				if (item.getAudioResult() == ErrorQuest.AUDIT_RESULT_WRONG_UPDATE) {
					errorQuestDao.updateQuestion(item);
				}
			}
		}
	}
}
