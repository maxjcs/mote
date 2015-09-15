package com.longcity.manage.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author fuguoqing
 */
public class BaseController {

    private final String SESSION_KEY_ISLOGIN = "IS_LOGIN";

    protected Object dataJson(Object data) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("success", true);
        map.put("data", data);

        return map;
    }

    protected Object errorJson(String msg) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("success", false);
        map.put("msg", msg);
        return map;
    }

//    /**
//     * 获取登录用户的信息
//     *
//     * @param session
//     * @return
//     */
//    protected SystemUserPojo getOperatorDetailInfo(HttpSession session) {
//        return (SystemUserPojo) session.getAttribute(LoginFilter.SESSION_KEY_SYSTEM_USER);
//    }
//
//    /**
//     * 获取操作人的账号
//     *
//     * @param session
//     * @return
//     */
//    protected String getOperatorAccount(HttpSession session) {
//        SystemUserPojo pojo = this.getOperatorDetailInfo(session);
//        if (pojo != null) {
//            return pojo.getUserAccount();
//        } else {
//            return null;
//        }
//    }
//
//    /**
//     * 获取操作人的姓名
//     *
//     * @param session
//     * @return
//     */
//    protected String getOperatorName(HttpSession session) {
//        SystemUserPojo pojo = this.getOperatorDetailInfo(session);
//        if (pojo != null) {
//            return pojo.getName();
//        } else {
//            return null;
//        }
//    }
//
//    protected List<SystemUserRolePojo> getUserRoles(HttpSession session) {
//
//        return (List<SystemUserRolePojo>) session.getAttribute(LoginFilter.SESSION_KEY_SYSTEM_ROLE_INFO);
//    }
    
    protected String getExportPath(HttpSession session, HttpServletRequest request) {
        if (session == null || request == null) {
            return "";
        }
        String contextPath = request.getContextPath();
        String realExportPath = session.getServletContext().getRealPath(contextPath);
        realExportPath = realExportPath.replace(contextPath, "") + contextPath;
        return realExportPath;
    }


}
