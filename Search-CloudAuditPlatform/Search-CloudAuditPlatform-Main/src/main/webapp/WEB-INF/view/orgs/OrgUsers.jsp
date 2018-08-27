<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/head.jsp" %>
<!doctype html>
<html>
<head>
    <title>功能组</title>
    <meta name='description' content='功能组'>
</head>
<body>
<div class="mini-fit">
    <div id="datagrid" class="search-datagrid" idField="sid" showCheckBox="true" multiSelect="true" alternatingRows="true"
         showPager="true" pageSize="20" ondrawcell="datagriddrawcell" style="width:100%;height:100%;">
<!--         <div property="contextmenu">
            <div id="ismanager" type="menu" text="升为管理员" enabled="true" onclick="ismanager"></div>
        </div> -->
        <div property="columns">
            <div type="indexcolumn" width="30" headAlign="center" textAlign="center">序号</div>
            <div field="username" width="80" headAlign="center" textAlign="left" allowSort="true">姓名</div>
            <div field="orgname" width="200" headAlign="center" textAlign="left" allowSort="true">所属机构</div>
            <div field="sphone" width="200" headAlign="center" textAlign="left" allowSort="true">联系电话</div>
            <div field="iisdepartment" width="100" headAlign="center" textAlign="center" allowSort="true">是否部门</div>
            <div field="smanagerid" width="100" headAlign="center" textAlign="center" allowSort="true">用户类型</div>
            <div field="oper" width="150" headAlign="center" textAlign="center" allowSort="true">操作</div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${JS}/orgs/OrgUsers.js"></script>
</body>
</html>