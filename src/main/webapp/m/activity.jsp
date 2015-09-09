<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ include file="inc/t.jsp" %>
<%
    String token = request.getParameter("token");
    String version = request.getParameter("version");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="format-detection" content="telephone=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv="Expires" content="0">
    <title>充值返现</title>
    <link rel="shortcut icon" href="<%=webServer%>static/images/favicon.ico" type="image/x-icon"/>
    <link rel="stylesheet" href="<%=webServer%>static/css/activity/activity-v<%=version%>.css"/>
    <script>
        var WEB_SERVER = "<%=webServer%>";
        var TOKEN = '<%=token%>';
    </script>
    <script src="<%=webServer%>static/js/activity/activity-v<%=version%>.js"></script>

</head>
<body ms-controller="activityCtrl" class="ms-controller">
<%if (version == null || token == null) {%>
    Parameter is null！
<%} else {%>
    <%if (version.equals("1.4")) {%>
        <%@ include file="activity/v1.4/main.jsp" %>
    <%} else {%>
        Parameter "version" error！
    <%}%>
<%}%>
</body>
</html>
