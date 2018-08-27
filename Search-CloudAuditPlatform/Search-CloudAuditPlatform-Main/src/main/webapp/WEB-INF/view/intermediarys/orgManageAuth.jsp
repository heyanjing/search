<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/head.jsp" %>
<!doctype html>
<html>
<head>
    <title>机构用户授权</title>
    <meta name='description' content='机构用户授权'>
    <link rel="stylesheet" type="text/css" href="${CSS}/demo/demo.css"/>
    <style type="text/css">
        .sid-btn {
            position: absolute;
            bottom: 0;
            text-align: center;
            margin: 0 auto;
            width: 100%;
            line-height: 58px;
        }
    </style>
</head>
<body>
<div class="mini-fitbox" style="top: 0;">
    <div id="datagrid" class="search-datagrid" idField="srefid" showCheckBox="false" multiSelect="false" alternatingRows="true"
         showPager="false" ondrawcell="datagriddrawcell" style="width:100%;height:100%;"></div>
</div>
<div class="sid-btn">
    <input class="org-save" type="button" onclick="save()" value="保存"/>
    <input class="org-close" type="button" onclick="window.closeWindow('cancel')" value="关闭"/>
</div>
<script type="text/javascript" src="${JS}/intermediarys/orgManageAuth.js"></script>
</body>
</html>