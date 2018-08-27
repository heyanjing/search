<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/jspTaglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Insert title here</title>
    <link rel="stylesheet" type="text/css" href="${CSS}/demo/flow.css?${V}"/>
</head>

<body>
<div class="flow-wrap">
    <div class="flow-title">
        <h1>流程设计
            <span><i class="fa fa-save"></i><i class="fa fa-refresh"></i><i class="fa fa-close"></i></span></h1>
    </div>

    <div class="flow-canvas" id="flow-canvas"></div>

</div>
<div class="flow-canvas-menu-container">
    <ul class="flow-canvas-menu" id="flow-canvasmenu">
        <li id='menu-save'><i class="fa fa-save"></i>保存设计</li>
        <li id='menu-refresh'><i class="fa fa-refresh"></i>刷　　新</li>
        <li id='menu-start'><i class="fa fa-play"></i>开始步骤</li>
        <li id='menu-normal'><i class="fa fa-eraser"></i>普通步骤</li>
        <li id='menu-more'><i class="fa fa-circle"></i>会签步骤</li>
        <li id='menu-end'><i class="fa fa-stop"></i>结束步骤</li>
    </ul>
</div>

<div class="step-menu-container">
    <ul class="step-menu">
        <li id="step-cog"><i class="fa fa-cog"></i>流程属性</li>
        <li id="step-table"><i class="fa fa-table"></i>表单字段</li>
        <li id="step-share"><i class="fa fa-share-alt"></i>流程权限</li>
        <li id="step-trash"><i class="fa fa-trash"></i>流程删除</li>
    </ul>
</div>
<script type="text/javascript" src="${JS}/demo/flow.js?${V}"></script>
</body>
</html>