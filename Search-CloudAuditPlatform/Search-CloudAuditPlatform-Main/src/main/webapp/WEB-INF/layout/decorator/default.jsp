<%@page language="java" pageEncoding="UTF-8" %>
<!doctype html>
<html>
<head>
    <%@include file="/WEB-INF/layout/taglib/head.jsp" %>
    <title><sitemesh:write property='title'/></title>
    <sitemesh:write property='head'/>
</head>
<body>
<%-- <div>默认页面body中的内容</div>
<a href="${CTX}/logout">退出</a>--%>
<sitemesh:write property='body'/>

</body>
</html>
