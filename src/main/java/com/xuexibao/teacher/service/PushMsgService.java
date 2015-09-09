package com.xuexibao.teacher.service;

import com.xuexibao.teacher.dao.PushMsgPersistentDao;
import com.xuexibao.teacher.model.PushMsgPersistentPojo;
import com.xuexibao.teacher.model.Teacher;
import com.xuexibao.teacher.model.vo.PushMsgParamVO;
import com.xuexibao.teacher.util.HttpUtil;
import com.xuexibao.teacher.util.JsonUtil;
import com.xuexibao.teacher.util.PropertyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 作者：付国庆
 * 时间：15/4/10－下午7:45
 * 描述：推送消息相关的封装方法类
 */
@Service
public class PushMsgService {


    private Logger logger = LoggerFactory.getLogger(PushMsgService.class);
    private String pushUrl = PropertyUtil.getProperty("pushUrl");

    private String pushPath = "/api/srv/msg/race";

    // 安卓设备推送需要在尾缀添加,如果有需要在抽取到配置文件中
    private String androidDidSuffix = "@push.91xuexibao.com";

    private Logger log = LoggerFactory.getLogger(PushMsgService.class);

    //消息已读
    private static final String MSG_IS_READ = "Y";
    //消息未读
    private static final String MSG_IS_NOT_READ = "N";
    //消息发送
    private static final String MSG_IS_SEND = "Y";
    //消息未发送
    private static final String MSG_IS_NOT_SEND = "N";


    //设备类型
    private final String DEVICE_TYPE_ANDROID = "1";
    private final String DEVICE_TYPE_IPHONE = "2";

    //ios 消息最大长度
    private final Integer IOS_PUSH_MSG_LENGTH = 15;

    //ios 教师端版本标记
    private final String APP_VERSION_FLAG = "teacher26";

    //安卓消息json结构中的 title key
    private final String KEY_ANDROID_PUSH_MSG_CONTENT_TITLE = "msgTitle";

    @Autowired
    private PushMsgPersistentDao pushMsgPersistentDao;
    @Autowired
    private TeacherService teacherService;

    /**
     * 持久化推送的消息
     *
     * @param content       消息内容
     * @param msgType       消息类型
     * @param receiveUserId 接受消息的用户id
     * @param isSend        根据用户是否在线状态标记是否成功将消息发出
     * @return 新增成功返回插入表主键
     * 失败返回0
     */
    public String persistentPushMsg(Object content, Integer msgType, String receiveUserId, String isSend, String phoneType, String title) {

        if (phoneType == null) {
            log.error("persistentPushMsg error , param phoneType is Illegal");
            return "";
        }
        if (!("android".equals(phoneType)) && !("iphone".equals(phoneType))) {
            log.error("param PhoneType is Illegal");
            return "";
        }

        if (content == null) {
            log.error("param content is null");
            return "";
        }
        PushMsgPersistentPojo pojo = new PushMsgPersistentPojo();
        pojo.setId(UUID.randomUUID().toString());
        if (content instanceof String) {
            pojo.setContent((String) content);
        } else {
            pojo.setContent(JsonUtil.obj2Json(content));
        }

        //fixme msgType 有效性的验证
        pojo.setMsgType(msgType);
        pojo.setUserId(receiveUserId);
        pojo.setAddTime(new Timestamp(System.currentTimeMillis()));
        pojo.setIsRead(MSG_IS_NOT_READ);
        pojo.setIsSend(isSend);
        pojo.setPhoneType(phoneType);
        pojo.setTitle(title);
        pushMsgPersistentDao.insert(pojo);
        return pojo.getId();
    }

    public PushMsgPersistentPojo getPushMsgMsgById(String id) {
        if (!StringUtils.hasLength(id)) {
            log.error("param id is Illegal");
            return null;
        }
        PushMsgPersistentPojo pojo = pushMsgPersistentDao.selectById(id);
        return pojo;

    }

    /**
     * 根据消息阅读状态查询消息列表
     *
     * @param isRead 如果readStatus 为空 则 查询所有消息列表
     * @return
     */
    public List<PushMsgPersistentPojo> getPersistentPushMsgByReadStatusAndUserId(String isRead, String receiveUserId) {

        if (StringUtils.hasLength(isRead) && !isRead.equals(MSG_IS_READ) && !isRead.equals(MSG_IS_NOT_READ)) {
            log.error("param readStatus is Illegal");
            return null;
        }
        if (!StringUtils.hasLength(receiveUserId)) {
            log.error("param receiveUserId is null");
            return null;
        }
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("isRead", isRead);
        paramMap.put("userId", receiveUserId);
        List<PushMsgPersistentPojo> results = pushMsgPersistentDao.selectByReadStatusAndUserId(paramMap);
        return results;
    }

    /**
     * 根据表主要键盘 标记消息为已读
     *
     * @param ids
     * @return
     */
    public Boolean markPushMsgAsRead(String ids) {
        if (!StringUtils.hasLength(ids)) {
            log.error("param id is Illegal");
            return false;
        }
        String[] idList = ids.split(",");
        for (String idStr : idList) {
            try {
                pushMsgPersistentDao.setMsgAsRead(idStr);
            } catch (NumberFormatException e) {
                logger.error("param NumberFormatException");
            }

        }
        return true;
    }

    /**
     * 拼接报文，推送给消息服务器
     *
     * @param paramVO
     * @return
     */
    protected Boolean pushRealMsg(PushMsgParamVO paramVO) {
        Map postMap = new HashMap();

        //基本参数类型校验
        if (paramVO == null || paramVO.getPhoneType() == null) {
            log.error("pushRealMsg error , param is Illegal");
            return false;
        }
        if (!("android".equals(paramVO.getPhoneType())) && !("iphone".equals(paramVO.getPhoneType()))) {
            log.error("param PhoneType is Illegal");
            return false;
        }

        // 构造 ios 请求报文
        if (("iphone").equals(paramVO.getPhoneType())) {
            // 添加变量
            Map apsMap = new HashMap();
            // 帖子内容
            String title = paramVO.getTitle();

            if (!StringUtils.hasLength(title)) {
                title = "您有新消息";
            }
            if (title.length() > IOS_PUSH_MSG_LENGTH) {
                apsMap.put("ct", title.substring(0, IOS_PUSH_MSG_LENGTH)  );
                apsMap.put("alert", title.substring(0, IOS_PUSH_MSG_LENGTH) );
            } else {
                apsMap.put("ct", title);
                apsMap.put("alert", title);
            }
            // 用户id
            apsMap.put("ud", paramVO.getUserId());
            // 创建时间
            if (paramVO.getCreateTime() != null) {
                apsMap.put("rt", paramVO.getCreateTime());
            }
            // 帖子id
            if (StringUtils.hasLength(paramVO.getTopicId())) {
                apsMap.put("td", paramVO.getTopicId());
            }
            // push类型
            apsMap.put("type", paramVO.getType());
            String apsStr = JsonUtil.obj2Json(apsMap);
            // post_data
            postMap.put("to", paramVO.getDeviceUniqueId());
            postMap.put("tp", paramVO.getPhoneType());
            postMap.put("msg", apsStr);
        }
        // 构造 android 请求报文hhh
        else

        {
            // android 需要而外拼接
            // 添加变量
            Map apsMap = new HashMap();
            apsMap.put("ct", paramVO.getContent());
            apsMap.put("type", paramVO.getType());
            String apsStr = JsonUtil.obj2Json(apsMap);
            // post_data
            postMap.put("to", paramVO.getDeviceUniqueId() + androidDidSuffix);
            postMap.put("tp", paramVO.getPhoneType());
            postMap.put("msg", apsStr);
        }

        postMap.put("ver", APP_VERSION_FLAG);
        String serverAddress = pushUrl + pushPath;
        try

        {
            Map<String, Object> map = HttpUtil.httpPost(serverAddress, postMap);
            if (map != null && map.containsKey("status")) {
                Integer statusCode = (Integer) map.get("status");
                if (statusCode.intValue() == 0) {
                    // 发送成功
                    log.info((String) map.get("msg"));
                    return true;
                } else {
                    // 发送失败
                    log.info((String) map.get("msg"));
                    return false;
                }
            } else {
                // 发送失败
                return false;
            }
        } catch (
                Exception e
                )

        {
            log.error(e.getMessage(), e);
            return false;
        }

    }


    /**
     * 推送消息至终端
     * 异步执行发送推送消息报文
     *
     * @param paramVO
     * @return 发送成功返回true
     */
    @Async
    public Boolean pushMsg(PushMsgParamVO paramVO) {
        // check param
        if (!StringUtils.hasLength(paramVO.getPhoneType())) {
            log.error("param PhoneType is null");
            return false;
        }
        if (!("android".equals(paramVO.getPhoneType())) && !("iphone".equals(paramVO.getPhoneType()))) {
            log.error("param PhoneType is Illegal");
            return false;
        }
        if (!StringUtils.hasLength(paramVO.getDeviceUniqueId())) {
            log.error("param DeviceUniqueId is null");
            return false;
        }
        if (!StringUtils.hasLength(paramVO.getUserId())) {
            log.error("param UserId is null");
            return false;
        }

        // check userId and deviceId is Matching
        Teacher teacher = teacherService.getTeacher(paramVO.getUserId());
        log.info("TeacherId=" + teacher.getId());
        log.info("DeviceId=" + teacher.getLastDeviceId());
        log.info("DeviceType=" + teacher.getLastDeviceType());
        log.info("OnlineStatus=" + teacher.getOnlineStatus());
        log.info("paramVO.getDeviceUniqueId()=" + paramVO.getDeviceUniqueId());
        log.info("teacher.getLastDeviceId()" + teacher.getLastDeviceId());
        if (teacher == null) {
            log.error("param userId is Invalid");
            return false;
        }
        if ("android".equals(paramVO.getPhoneType()) && !paramVO.getDeviceUniqueId().equalsIgnoreCase(teacher.getLastDeviceId())) {
            log.error("android param userId - deviceId not matching");
            return false;
        }
        if ("iphone".equals(paramVO.getPhoneType()) && !paramVO.getDeviceUniqueId().equalsIgnoreCase(teacher.getIosToken())) {
            log.error("iphone param userId - deviceId not matching");
            return false;
        }

        // check online status 如果不在线，则持久化消息，下次触发重新发送
        if (teacher.isOnline()) {
            Boolean result = pushRealMsg(paramVO);
            if (result) {
                //发送给pushSever 成功
                persistentPushMsg(paramVO.getContent(), paramVO.getType(), paramVO.getUserId(), MSG_IS_SEND, paramVO.getPhoneType(), paramVO.getTitle());
                return true;
            } else {
                //发送给pushSever 失败，持久化信息
                persistentPushMsg(paramVO.getContent(), paramVO.getType(), paramVO.getUserId(), MSG_IS_NOT_SEND, paramVO.getPhoneType(), paramVO.getTitle());
                return false;
            }
        } else {
            //持久化未发送的消息
            persistentPushMsg(paramVO.getContent(), paramVO.getType(), paramVO.getUserId(), MSG_IS_NOT_SEND, paramVO.getPhoneType(), paramVO.getTitle());
            return false;
        }
    }

    /**
     * 将对应的的用户 发送失败的推送消息 重新推送给 pushServer
     *
     * @param teacherId
     */
    @Async
    public void reSendPushMsg(String teacherId) {
        if (!StringUtils.hasLength(teacherId)) {
            log.error("teacherId is null");
            return;
        }
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("userId", teacherId);
        paramMap.put("isSend", MSG_IS_NOT_SEND);
        List<PushMsgPersistentPojo> pojos = pushMsgPersistentDao.selectBySendStatusAndUserId(paramMap);
        for (PushMsgPersistentPojo pojo : pojos) {
            reSendEveryPushMsg(pojo);
        }
    }

    private void reSendEveryPushMsg(PushMsgPersistentPojo pojo) {
        Teacher teacher = teacherService.getTeacher(pojo.getUserId());
        if (teacher == null) {
            log.error("is not exist teacher teacherId=" + pojo.getUserId());
            return;
        }
        PushMsgParamVO paramVO = PushMsgParamVO.createMessage(teacher, pojo.getMsgType(), pojo.getContent());
        paramVO.setTitle(pojo.getTitle());

        if (pushRealMsg(paramVO)) {
            //标记消息为已经发送
            pushMsgPersistentDao.setMsgAsSend(pojo.getId());
        } else {
            log.error("reSendMsg fail teacher = ", pojo.getUserId());
        }
    }


    public void setPushUrl(String pushUrl) {
        this.pushUrl = pushUrl;
    }

    public void setPushPath(String pushPath) {
        this.pushPath = pushPath;
    }
}
