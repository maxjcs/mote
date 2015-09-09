<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ include file="inc/t.jsp" %>
<%
    String teacherId = request.getParameter("teacherId");
    String questionId = request.getParameter("questionId");
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
    <title>超详细的难题解答</title>
    <link rel="shortcut icon" href="<%=webServer%>static/images/favicon.ico" type="image/x-icon"/>
    <link rel="stylesheet" href="<%=webServer%>static/css/share-qa/qa-detail.css"/>
    <script>
        var WEB_SERVER = "<%=webServer%>";
        var Params = {
            teacherId: "<%=teacherId%>",
            questionId: "<%=questionId%>"
        }
    </script>
    <script src="<%=webServer%>static/lib/fastclick.min.js"></script>
    <script src="<%=webServer%>static/lib/avalon.modern.min.js"></script>
    <script src="<%=webServer%>static/lib/zepto.min.js"></script>
    <script src="<%=webServer%>static/js/share-qa/qa-detail.min.js"></script>
    <style>
        .ms-controller { visibility: hidden; }
    </style>
</head>
<body ms-controller="appCtrl" class="ms-controller">
<div class="masker-loading" ms-class="hide:initialized"></div>
<div class="appdl-hd">
    <div class="logo"><img src="<%=webServer%>static/images/icon-logo@2x.png"></div>
    <span class="app-name">学习宝，一拍就知道</span><a class="btn-dl" href="http://www.xuexibao.cn/html/download.html">立即下载</a>
</div>
<div class="panel">
    <div class="section">
        <div class="qa-item">
            <div class="qa-hd">
                <i class="icon icon-doc"></i>
                <span class="title">题目</span>
                <span class="subject">【{{qaVo.gradeName}} {{qaVo.subjectName}}】</span>
            </div>
            <div class="qa-bd">
                <div id="qaContent" class="qa-content question-cnt" ms-html="qaVo.content">
                </div>
                <div id="showMoreBtn" class="fold"><i class="icon icon-arrow"></i><span>显示全部</span></div>
            </div>
        </div>
        <div class="qa-item">
            <div class="qa-hd">
                <i class="icon icon-light"></i>
                <span class="title">解答</span>
            </div>
            <div class="qa-bd">
                <div class="audio-explain">
                    <div class="course-info">
                        <a id="playerShowBtn" class="play-btn-sm"><i class="icon"></i></a>
                        <span class="play-ani play-ani-pasue"></span>
                        <span class="teacher-name">{{qaVo.nickname}}老师</span>
                        <span class="course-type" ms-text="qaVo.audioType==2?'白板讲解':'音频讲解'"></span>
                    </div>
                    <div class="audio-player-wrapper hide">
                        <div id="audioPlayer" class="audio-player"></div>
                    </div>
                </div>
                <div class="qa-content" ms-html="qaVo.solution">
                </div>
            </div>
        </div>
        <div class="qa-item">
            <div class="qa-hd">
                <i class="icon icon-star"></i>
                <span class="title">知识点</span>
            </div>
            <div class="qa-bd">
                <div class="qa-content" ms-html="qaVo.knowledge">
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
