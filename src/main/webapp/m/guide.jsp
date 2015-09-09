<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ include file="inc/t.jsp" %>
<%
    String catid = request.getParameter("catid");
    String version = request.getParameter("version");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no,minimal-ui">
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv="Expires" content="0">
    <title>用户指南</title>
    <link rel="shortcut icon" href="<%=webServer%>static/images/favicon.ico" type="image/x-icon"/>
    <link rel="stylesheet" href="<%=webServer%>static/css/common/reset.css"/>
    <link rel="stylesheet" href="<%=webServer%>static/css/common/swiper.min.css"/>
    <link rel="stylesheet" href="<%=webServer%>static/css/guide/guide-v<%=version%>.css"/>
    <script src="<%=webServer%>static/lib/swiper.min.js"></script>
    <script src="<%=webServer%>static/js/guide/guide-v<%=version%>.js"></script>
</head>
<body>
<%if (version == null || catid == null) {%>
    Parameter is null！
<%} else {%>
    <%if (version.equals("1.3")) {%>
        <%if (catid.equals("1")) {%>
            <%@ include file="guide/v1.3/slider-1.jsp" %>
        <%} else if (catid.equals("2")) {%>
            <%@ include file="guide/v1.3/slider-2.jsp" %>
        <%} else if (catid.equals("3")) {%>
            <%@ include file="guide/v1.3/slider-3.jsp" %>
        <%} else if (catid.equals("4")) {%>
            <%@ include file="guide/v1.3/slider-4.jsp" %>
        <%} else {%>
            Parameter "catid" error！
        <%}%>
    <%} else if (version.equals("1.4")) {%>
        <%if (catid.equals("1")) {%>
            <%@ include file="guide/v1.4/slider-1.jsp" %>
        <%} else if (catid.equals("2")) {%>
            <%@ include file="guide/v1.4/slider-2.jsp" %>
        <%} else if (catid.equals("3")) {%>
            <%@ include file="guide/v1.4/slider-3.jsp" %>
        <%} else if (catid.equals("4")) {%>
            <%@ include file="guide/v1.4/slider-4.jsp" %>
        <%} else if (catid.equals("5")) {%>
            <%@ include file="guide/v1.4/slider-5.jsp" %>
        <%} else {%>
            Parameter "catid" error！
        <%}%>
    <%} else {%>
        Parameter "version" error！
    <%}%>
<%}%>
</body>
</html>
