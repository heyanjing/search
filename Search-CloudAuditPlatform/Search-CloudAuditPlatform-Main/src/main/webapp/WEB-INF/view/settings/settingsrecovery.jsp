<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/head.jsp" %>
<!doctype html>
<html>
<head>
    <title>恢复系统设置</title>
    <meta name='description' content='恢复系统设置'>

</head>
<body>
<div class="mini-tools" style="text-align: left; height:50px; line-height:50px;">
    <jsp:include page="/WEB-INF/view/menu/MenuButtonMgr.jsp"></jsp:include>
</div>

<div class="mini-fit">
    <div id="datagrid" class="search-datagrid" idField="sid" showCheckBox="false" multiSelect="false" alternatingRows="true"
         showPager="true" pageSize="20" ondrawcell="datagriddrawcell" style="width: 100%; height: 100%;">
        <div property="columns">
            <div type="indexcolumn" width="30" headAlign="center" textAlign="center">序号</div>
            <div field="sorgname" width="150" headAlign="center" textAlign="left">应用单位名称</div>
            <div field="ssystemname" width="150" headAlign="center" textAlign="left">系统名称</div>
            <div field="isupportusernumber" width="100" headAlign="center" textAlign="center">是否启用用户数量控制</div>
            <div field="imaxnumber" width="50" headAlign="center" textAlign="center">最大用户数</div>
            <div field="sorgtype" width="200" headAlign="center" textAlign="left">用户数量不受控制单位类型</div>
            <div field="ldtcreatetime" width="100" headAlign="center" textAlign="center">创建时间</div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${JS}/settings/settingsrecovery.js"></script>
</body>
</html>