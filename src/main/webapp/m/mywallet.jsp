<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ include file="inc/t.jsp" %>
<%
    String token = request.getParameter("token");
    String version = request.getParameter("version");
    String debug = request.getParameter("debug");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no,minimal-ui">
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="format-detection" content="telephone=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv="Expires" content="0">
    <title>我的钱包</title>
    <link rel="shortcut icon" href="<%=webServer%>static/images/favicon.ico" type="image/x-icon"/>
    <link rel="stylesheet" href="<%=webServer%>static/css/mywallet/mywallet-v<%=version%>.css"/>
    <script>
        var WEB_SERVER = '<%=webServer%>';
        var TOKEN = '<%=token%>';
        var DEBUG_MODE = <%=debug%>;
    </script>
    <script src="<%=webServer%>static/js/mywallet/mywallet-v<%=version%>.js"></script>

</head>
<body ms-controller="mywalletCtrl" class="ms-controller">
<%if (version == null || token == null) {%>
    Parameter is null！
<%} else {%>
    <%if (version.equals("1.4")) {%>
        <%@ include file="mywallet/v1.4/main.jsp" %>
    <%} else {%>
        Parameter "version" error！
    <%}%>
<%}%>
</body>
</html>
