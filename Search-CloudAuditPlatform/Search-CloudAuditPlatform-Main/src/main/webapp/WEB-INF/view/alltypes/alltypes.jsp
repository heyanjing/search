<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/jspTaglib.jsp" %>
<!doctype html>
<html>
<head>
    <title>alltypes title中的内容</title>
    <meta name='description' content='alltypes head中的内容'>
</head>
<body>
页面一 <br>
${userKey}<br>
sessionId:${sessionId} <br>
page:${page}<br>
<a href="javascript:void(0)" id="btn1">异步请求</a><br>
<img id="img1" src="${IMG}/index.jpg"/>


<script type="text/javascript" src="${JS}/alltypes/alltypes.js?${V}"></script>
</body>
</html>