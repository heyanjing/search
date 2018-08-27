<%@page language="java" pageEncoding="UTF-8" %>
<!doctype html>
<html>
<head>
    <%@include file="/WEB-INF/layout/taglib/head.jsp" %>
    <title>默认页面title中的内容: <sitemesh:write property='title'/></title>
    <meta name='description' content='默认页面head中的内容'>
    <sitemesh:write property='head'/>
</head>
<body>
<sitemesh:write property='body'/>

</body>
</html>
