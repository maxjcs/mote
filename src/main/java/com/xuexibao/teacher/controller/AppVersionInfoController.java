package com.xuexibao.teacher.controller;

import com.xuexibao.teacher.model.AppVersionsInfoPojo;
import com.xuexibao.teacher.model.vo.AppVersionsInfoResponseVo;
import com.xuexibao.teacher.service.AppVersionsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 作者：付国庆
 * 时间：15/4/11－上午10:56
 * 描述：提供给app 版本信息操作的相关外部api
 */
@Controller
@RequestMapping("version")
public class AppVersionInfoController extends AbstractController {


    //临时关闭xmpp 1、关闭，0、开启
    private final Integer CLOSE_XMPP = 1;


    @Autowired
    private AppVersionsInfoService appVersionsInfoService;

    @ResponseBody
    @RequestMapping(value = "addVersionInfo")
    public Object addVersionInfo(@ModelAttribute AppVersionsInfoPojo param) {

        Integer resultId = appVersionsInfoService.addVersionInfo(param);
        if (resultId != null) {
            return dataJson("addVersionInfo success id=" + resultId);
        } else {
            return errorJson("addVersionInfo fail");
        }
    }

    @ResponseBody
    @RequestMapping(value = "getLastVersionInfo")
    public Object getLastVersionInfo(@RequestParam(required = false) Integer appType, @RequestParam Integer appVersionCode) {
        AppVersionsInfoResponseVo responseVo = appVersionsInfoService.getLastVersionInfoByAppType(appType, appVersionCode);
        responseVo.setIsOpenXmpp(CLOSE_XMPP);
        if (responseVo != null) {
            return dataJson(responseVo);
        } else {
            return errorJson("getLastVersionInfo fail");
        }

    }
}
