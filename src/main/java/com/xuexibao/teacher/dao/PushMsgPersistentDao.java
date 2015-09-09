package com.xuexibao.teacher.dao;

import com.xuexibao.teacher.core.MybatisMapper;
import com.xuexibao.teacher.model.PushMsgPersistentPojo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * 作者：付国庆
 * 时间：15/4/10－下午7:45
 * 描述：表 push_msg_persistent dao
 */
@MybatisMapper
@Repository
public interface PushMsgPersistentDao {
    /**
     * 新增消息
     *
     * @param pojo
     * @return
     */
    Integer insert(PushMsgPersistentPojo pojo);

    /**
     * 根据表主键查询消息pojo
     *
     * @param id
     * @return
     */
    PushMsgPersistentPojo selectById(String id);

    /**
     * 根据消息读取状态查询消息列表（未分页）
     * Y：已读 N：未读
     * userId
     *
     * @param paramMap
     * @return
     */
    List<PushMsgPersistentPojo> selectByReadStatusAndUserId(Map<String, String> paramMap);

    /**
     * 根据消息id标记消息为已读
     *
     * @param id
     */
    void setMsgAsRead(String id);

    /**
     * 根据消息id标记消息为已发送
     *
     * @param id
     */
    void setMsgAsSend(String id);

    /**
     * 根据userId 发送状态查询消息列表
     *
     * @param paramMap
     * @return
     */
    List<PushMsgPersistentPojo> selectBySendStatusAndUserId(Map<String, String> paramMap);

}
