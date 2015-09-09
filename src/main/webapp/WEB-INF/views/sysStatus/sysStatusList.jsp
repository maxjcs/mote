<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	 Map monitorMap = (Map) request.getAttribute("monitorMap");
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
   
</head>
<body>
<div class="panel">

            <div class="title">系统状态</div>
       <div class="title">db: <%= monitorMap.get("dbValue") %></div>
            <div class="title">redis: <%= monitorMap.get("redisValue") %></div>
            <div class="title">thrift: <%= monitorMap.get("thriftValue") %></div>
                 
</div>

</body>
</html>