<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/jspTaglib.jsp" %>
<!doctype html>
<html>
<head>
    <title>首页title中的内容</title>
    <meta name='description' content='首页head中的内容'>
    <link rel="stylesheet" type="text/css" href="${CSS}/index.css?${V}"/>
</head>
<body>
${result}<br><br><br><br>
<div class="index">首页body中的内容xxx${now}</div>
<form action="${CTX}/login" method="post">
    <input type="text" name="userName" value="jack">
    <input type="password" name="password" value="我是密码1">
    <input type="submit" value="登陆">
</form>
<%--<img src="${CTX}/code/gif"/>--%>
<img id="img1" src="${CTX}/code/jpg"/><br>
<img id="img1" src="${IMG}/index.jpg"/>
<script type="text/javascript" src="${JS}/index.js?${V}"></script>

</body>
</html>