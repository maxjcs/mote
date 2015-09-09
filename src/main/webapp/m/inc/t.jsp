<%
    String webServer;
    if ((request.getServerPort() == 80) || (request.getServerPort() == 443)) {
        webServer = request.getScheme() + "://" + request.getServerName() + request.getContextPath() + "/";
    } else {
        webServer = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
    }
%>