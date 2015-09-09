<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ include file="inc/t.jsp" %>
<%
    String dynamicId = request.getParameter("dynamicId");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="keywords" content="学习宝，作业神器，学霸神器，学习软件，考试软件app，考试神器"/>
    <meta name="description" content="学习宝，全球领先的云识别平台，海量题库，涵盖初高中9科知识点，题目一拍，秒答难题，学生随身携带的错题本，让学习更加简单高效"/>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv="Expires" content="0">
    <title></title>
    <link rel="shortcut icon" href="<%=webServer%>static/images/favicon.ico" type="image/x-icon"/>
    <link rel="stylesheet" href="<%=webServer%>static/css/dynamic/dynamic.css"/>
    <script>
        var WEB_SERVER = "<%=webServer%>";
        var DYNAMIC_ID = "<%=dynamicId%>";
    </script>
    <style>
        .ms-controller { visibility: hidden; }
    </style>
</head>
<body ms-controller="dynamicCtrl" class="ms-controller">
<div class="masker-loading" ms-class="hide:initialized"></div>
<div class="page">
    <div class="dy-main">
        <div class="section">
            <div class="dy-avatar">
                <img ms-if="dynamicData.teacherAvatarUrl" ms-src="{{dynamicData.teacherAvatarUrl}}v6">
                <img ms-if="!dynamicData.teacherAvatarUrl" ms-src="{{defaultAvatarUrl}}">
            </div>
            <div class="dy-body">
                <div class="dy-hd">
                    <span class="user-name">{{dynamicData.teacherNickname}}</span><i
                        class="icon icon-sex" ms-class-1="icon-male:dynamicData.teacherGender==1" ms-class-2="icon-female:dynamicData.teacherGender==2"></i><span
                        class="dy-time">{{dynamicData.createTimeDetailStr}}</span>
                </div>
                <div class="dy-info">
                    <span class="dy-age">{{dynamicData.teacherCourseYear}}年教龄</span>
                    <span class="dy-star"
                          ms-class-0="dy-star-0:dynamicData.teacherStar==0"
                          ms-class-1="dy-star-1:dynamicData.teacherStar==1"
                          ms-class-2="dy-star-2:dynamicData.teacherStar==2"
                          ms-class-3="dy-star-3:dynamicData.teacherStar==3"
                          ms-class-4="dy-star-4:dynamicData.teacherStar==4"
                          ms-class-5="dy-star-5:dynamicData.teacherStar==5"></span>
                </div>
                <div class="dy-grade">
                    {{dynamicData.gradeStr}}：
                </div>
                <div class="dy-bd">
                    <div class="dy-content">{{dynamicData.description}}</div>
                    <div class="dy-image">
                        <div ms-if="dynamicData.imageUrl1!=null&&dynamicData.imageUrl2==null&&dynamicData.imageUrl3==null&&dynamicData.imageUrl4==null">
                            <img class="img-single app-dl-link" ms-src="{{dynamicData.imageUrl1}}v5">
                        </div>
                        <div ms-if="dynamicData.imageUrl1!=null&&dynamicData.imageUrl2!=null">
                            <div class="dy-img-clip" ms-if="dynamicData.imageUrl1!=null">
                                <img class="img-multi app-dl-link" ms-src="{{dynamicData.imageUrl1}}v1"></div>
                            <div class="dy-img-clip" ms-if="dynamicData.imageUrl2!=null">
                                <img class="img-multi app-dl-link" ms-src="{{dynamicData.imageUrl2}}v1"></div>
                            <div class="dy-img-clip" ms-if="dynamicData.imageUrl3!=null">
                                <img class="img-multi app-dl-link" ms-src="{{dynamicData.imageUrl3}}v1"></div>
                            <div class="dy-img-clip" ms-if="dynamicData.imageUrl4!=null">
                                <img class="img-multi app-dl-link" ms-src="{{dynamicData.imageUrl4}}v1"></div>
                        </div>


                    </div>
                    <div class="dy-interact">
                        <div class="dy-reply"><i class="icon icon-reply"></i><span>{{dynamicData.countComment}}</span></div>
                        <div class="dy-up"><i class="icon icon-up"></i><span>{{dynamicData.countUpVote}}</span></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="dy-rep">
        <div class="section" ms-repeat="listMessage">
            <div class="dy-avatar">
                <img ms-if="el.teacherAvatarUrl" ms-src="{{el.teacherAvatarUrl}}v6">
                <img ms-if="!el.teacherAvatarUrl" ms-src="{{defaultAvatarUrl}}">
            </div>
            <div class="dy-body">
                <div class="dy-hd">
                    <div class="pull-left" ms-if="el.type==1">
                        <span class="user-name">{{el.teacherNickname}}</span>
                        <i class="icon icon-sex icon-female" ms-class-1="icon-male:el.teacherGender==1" ms-class-2="icon-female:el.teacherGender==2"></i>
                    </div>
                    <div class="pull-left" ms-if="el.type==2">
                        <span class="user-name">{{el.studentName}}</span>
                        <i class="icon icon-sex icon-female" ms-class-1="icon-male:el.studentGender==1" ms-class-2="icon-female:el.studentGender==2"></i>
                    </div>
                    <span class="dy-time">{{el.createTimeDetailStr}}</span>
                </div>
                <div class="dy-bd">
                    <div class="dy-content">{{el.content}}</div>
                    <div class="dy-rep-img" ms-if="el.imageUrl">
                        <img class="img-single app-dl-link" ms-src="{{el.imageUrl}}v5">
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="dl-bar">
    <a class="btn-dl app-dl-link">下载学习宝，查看更多评论</a>
</div>
<script src="<%=webServer%>static/js/dynamic/dynamic.js"></script>
</body>
</html>
