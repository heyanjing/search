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
<div>
    <div>sessionId:<input id="sessionId" value="${0}"/></div>
    <div>
        <button id="connect" onclick="connect();">Connect</button>
        <button id="connectAny" onclick="connectAny();">ConnectAny</button>
        <button id="disconnect" disabled="disabled" onclick="disconnect();">Disconnect</button>
    </div>
    <div id="conversationDiv">
        <label>What is your name?</label><input type="text" id="name"/>
        <button id="sendName" onclick="sendName();">Send</button>
        <button id="sendName2" onclick="sendName2();">Send2</button>
        <p id="response"></p>
    </div>
</div>
<script type="text/javascript" src="${JS}/demo/websocket/websocket.js?${V}"></script>

</body>
</html>