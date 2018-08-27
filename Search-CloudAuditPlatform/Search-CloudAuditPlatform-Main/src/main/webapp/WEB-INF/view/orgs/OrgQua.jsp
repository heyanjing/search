<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/jspTaglib.jsp" %>
<!doctype html>
<html>
<head>
    <title>查看资质</title>
    <meta name='description' content='首页head中的内容'>
    <link rel="stylesheet" type="text/css" href="${CSS}/demo/demo.css?${V}"/>
    <link rel="stylesheet" type="text/css" href="${CSS}/upload/webuploader.css">
    <style>
        .around_list {
            text-align: right !important;
        }
    </style>
</head>

<body>

<table border="1" style="width:200px; height:100px; border-collapse:collapse;">
    <tbody id="qua"></tbody>
</table>

<script type="text/javascript" src="${JS}/orgs/OrgQua.js?${V}"></script>
</body>
</html>