<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/jspTaglib.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title>流程设计测试</title>
</head>

<body>
<input type="button" onclick="openFlowDesign();" value="打开流程设计"/>
<input type="button" onclick="openColorFlowDesign();" value="流程设计"/>
<input type="button" onclick="openformpermissions();" value="流程设计-表单权限"/>
<script type="text/javascript" src="${JS}/demo/flowtest.js?${V}"></script>
</body>
</html>