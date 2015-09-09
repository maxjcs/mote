<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	 String api_host = (String) request.getAttribute("api_host");
	 String IOS_STUDENT_DOWN_URL = (String) request.getAttribute("IOS_STUDENT_DOWN_URL");
	 String ANDROID_STUDENT_DOWN_URL = (String) request.getAttribute("ANDROID_STUDENT_DOWN_URL");
	 
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta HTTP-EQUIV="Pragma" CONTENT="no-cache">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title></title>
    <script type="text/javascript" src="<%=basePath%>/teacher/static/js/zepto.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/teacher/static/js/fastclick.min.js"></script>
    <script>
        var timestamp = Date.parse(new Date());
       
        document.write('<link rel="stylesheet" href="<%=basePath%>/teacher/static/css/style.css?t='+timestamp+'"\/>');
        document.write('<script type="text/javascript" src="<%=basePath%>/teacher/static/js/teacher_info.js?t='+timestamp+'"><\/script>');
        
        
         var API_HOST = '<%=api_host%>';
         var IOS_DOWN_URL= ''
         var appDownloadURL = {
        ios: '<%=IOS_STUDENT_DOWN_URL%>',
        android:'<%=ANDROID_STUDENT_DOWN_URL %>'
    };
    </script>
</head>
<body>
<div class="panel">
    <div class="header">
        <div class="logo"><img src="<%=basePath%>/teacher/static/images/icon-logo@2x.png"></div>
        <span class="app-name">安装学习宝，关注老师，解答难题</span><a id="appDownloadBtn" class="btn btn-dl" href="javascript:;">立即下载</a>
    </div>
    <div class="teacher-hd clearfix">
        <p class="avatar"><img id="avatar"></p>
        <dl>
            <dt><span id="name" class="teacher-name"></span><i id="sex" class="icon-sex"></i></dt>
            <dd><span id="teacherIdentify" class="teacher-type"></span></dd>
            <dd>
                <div id="star" class="icon-star"></div>
            </dd>
        </dl>
    </div>
    <div class="separable"></div>
    <div class="teacher-bd">
        <div class="section">
            <div class="title">辅导学科</div>
            <div id="subject" class="content"></div>
        </div>
        <div class="section">
            <div class="title">辅导年级</div>
            <div id="grades" class="content"></div>
        </div>
        <div class="section">
            <div class="title">教龄</div>
            <div id="courseYear" class="content"></div>
        </div>
        <div class="section">
            <div class="title">个人简介</div>
            <div id="selfDescription" class="content"></div>
        </div>
    </div>
</div>
<div class="weixin-tips hide">
    <div class="text">
        <p>点击右上角菜单</p>
        <p>在浏览器中打开并安装</p>
    </div>
</div>
</body>
</html>