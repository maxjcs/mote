package com.xuexibao.teacher.controller;

import com.xuexibao.teacher.core.AppContext;
import com.xuexibao.teacher.model.PushMsgPersistentPojo;
import com.xuexibao.teacher.model.vo.PushMsgParamVO;
import com.xuexibao.teacher.service.PushMsgService;
import com.xuexibao.teacher.service.StudentApiService;
import com.xuexibao.teacher.util.JsonUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fuguoqing 仅仅是测试使用，非对外暴露接口
 *         <p>
 *         param: phoneType //推送目的终端类型，值必须选择 "android" or "iphone"
 *         deviceUniqueId //设备唯一标识，如果是 iphone,则 deviceUniqueId 代表
 *         pushtoken；如果是android，则deviceUniqueId代表devid userId //设备关联用户的id
 *         content //推送内容 createTime //消息创建时间 type //xxx topicId //帖子id,目前v3版本
 *         iphone中作为请求参数
 */
@Controller
@RequestMapping("push")
public class PushMsgController extends AbstractController {

    @Resource
    private PushMsgService pushMsgService;

    @Deprecated
    @ResponseBody
    @RequestMapping(value = "testPush")
    public Object testPush(PushMsgParamVO paramVO) {

        /*
        Map content = new HashMap();

        content.put("id", 123);
        content.put("msgTitle", "测试ios推送消息");


        paramVO.setContent(content);
        */
        pushMsgService.pushMsg(paramVO);
        Map t = new HashMap();
        t.put("msg", "success");
        return dataJson(t);

    }

    @Deprecated
    @ResponseBody
    @RequestMapping(value = "persistentPushMsg")
    public Object persistentPushMsg(@RequestParam String content, @RequestParam Integer msgType, @RequestParam String receiveUserId) {
        String result = pushMsgService.persistentPushMsg(content, msgType, receiveUserId, "N", "android","测试推送消息");
        Map<String, String> map = new HashMap<String, String>();
        map.put("insertResultId", result);
        return dataJson(map);
    }

    @ResponseBody
    @RequestMapping(value = "getPersistentPushMsgByReadStatus")
    public Object getPersistentPushMsgByReadStatus(@RequestParam(required = false) String readStatus) {

        String teacherId = AppContext.getTeacherId();
        List<PushMsgPersistentPojo> results = pushMsgService.getPersistentPushMsgByReadStatusAndUserId(readStatus, teacherId);
        return dataJson(results);
    }

    @ResponseBody
    @RequestMapping(value = "markPushMsgAsRead")
    public Object markPushMsgAsRead(@RequestParam(required = false) String id) {

        if (pushMsgService.markPushMsgAsRead(id)) {
            return dataJson(new Boolean(true));
        } else {
            return errorJson("id=" + id + "markPushMsgAsRead fail!");
        }
    }

    @ResponseBody
    @RequestMapping(value = "getPushMsgMsgById")
    public Object getPushMsgMsgById(@RequestParam(required = false) String id) {
        PushMsgPersistentPojo pojo = pushMsgService.getPushMsgMsgById(id);
        return dataJson(pojo);
    }

    @ResponseBody
    @RequestMapping(value = "reSendPushMsg")
    public Object reSendPushMsg(@RequestParam(required = false) String teacherId) {
        pushMsgService.reSendPushMsg(teacherId);
        return dataJson(teacherId);
    }


}
