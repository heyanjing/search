<
<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/jspTaglib.jsp" %>
<!DOCTYPE>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>区域页面</title>
</head>
<body>
<div class="mini-fit">
    <div id="gridId" class="search-treedatagrid" idField="sid" parentField="sparentid" textField="sname" showCheckBox="false" multiSelect="false" alternatingRows="true" expandLevel="4" style="width:100%;height:100%;">
        <div property="columns">
            <div field="sname" width="100" headAlign="center" allowSort="true">名称</div>
        </div>
    </div>
</div>
<script type="text/javascript" language="javascript" src="${JS}/demo/he/demo.js?v=${RES_VER}"></script>
</body>
</html>