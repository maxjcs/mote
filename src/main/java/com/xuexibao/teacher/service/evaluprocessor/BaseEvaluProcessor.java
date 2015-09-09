package com.xuexibao.teacher.service.evaluprocessor;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.xuexibao.teacher.dao.AudioApproveDao;
import com.xuexibao.teacher.dao.AudioDao;
import com.xuexibao.teacher.enums.AudioStatus;
import com.xuexibao.teacher.exception.BusinessException;
import com.xuexibao.teacher.model.Audio;
import com.xuexibao.teacher.model.AudioApprove;
import com.xuexibao.teacher.model.Evaluation;
import com.xuexibao.teacher.model.Teacher;
import com.xuexibao.teacher.pay.model.PayAudio;
import com.xuexibao.teacher.pay.service.PayAudioService;
import com.xuexibao.teacher.service.CommonConfigService;
import com.xuexibao.teacher.service.FeeService;
import com.xuexibao.teacher.service.PointLogService;
import com.xuexibao.teacher.service.StudentApiService;
import com.xuexibao.teacher.service.evaluprocessor.task.AudioMonyUtil;

/**
 * @author oldlu
 */
public class BaseEvaluProcessor {
	private static Logger logger = LoggerFactory.getLogger(BaseEvaluProcessor.class);

	@Resource
	protected AudioDao audioDao;
	@Resource
	protected PointLogService pointLogService;
	@Resource
	protected FeeService feeService;
	@Resource
	protected PayAudioService payAudioService;
	@Resource
	protected StudentApiService studentApiService;
	@Resource
	protected AudioApproveDao audioApproveDao;
	@Resource
	protected CommonConfigService commonConfigService;
	@Autowired
	protected AudioMonyUtil audioMonyUtil;

	// 默认好评
	protected AudioApprove addGoodApprove(String audioId) {
		AudioApprove approve = new AudioApprove();
		approve.setApprovor("system");
		approve.setAudioId(audioId);
		approve.setEvalution(Evaluation.LEVEL_GOOD);
		approve.setStatus(AudioApprove.STATUS_Y);

		audioApproveDao.addApprove(approve);
		return approve;
	}

	// 进入音频库
	public void addAudioToPayClub(AudioApprove approve, Teacher teacher) {
		if (approve.getEvalution() == Evaluation.LEVEL_GOOD) {
			Audio audio = audioDao.queryAudioById(approve.getAudioId());
			if (audio == null) {
				logger.error("音频[" + approve.getAudioId() + "]不存在");
				throw new BusinessException("音频[" + approve.getAudioId() + "]不存在");
			}

			audio.setStatus(AudioStatus.checked.getValue());
			audioDao.updateStatus(audio.getId(), audio.getStatus());

			PayAudio payAudio = new PayAudio();
			BeanUtils.copyProperties(audio, payAudio);
			payAudio.setApprovor(approve.getApprovor());
			payAudio.setGold(10);//默认金币

			payAudioService.addPayAudio(payAudio);

			logger.info("[" + approve.getAudioId() + "]进入音频库");
		}
	}

	public void addAudioToPayClub(String audioId, Teacher teacher) {
		Audio audio = audioDao.queryAudioById(audioId);
		if (audio == null) {
			logger.error("audio not exists [" + audioId + "]不存在");
			throw new BusinessException("音频[" + audioId + "]不存在");
		}
		PayAudio payAudio = new PayAudio();
		BeanUtils.copyProperties(audio, payAudio);
		payAudio.setApprovor("system");
		int source = payAudio.getType();
		payAudio.setGold(10);
//		if(source==AudioType.whiteboard.getValue()){
//			//白板价格
//			payAudio.setGold(20);
//		}
		
        logger.info("payAudio id:"+audioId);
		payAudioService.addPayAudio(payAudio);

		logger.info("[" + audioId + "]进入音频库");
	}

	public Map<String, Object> getAudioIncome(Audio audio, Teacher teacher) {
		return new HashMap<String, Object>();
	}
}
