<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/head.jsp" %>
<!doctype html>
<html>
<head>
    <title>机构管理</title>
    <meta name='description' content='机构管理'>
    <script type="text/javascript">
        var _orgid = '${orgid}';
        var _module = "${module}";
    </script>
</head>
<body>
<!-- 功能按钮开始 -->
<div class="mini-tools" style="text-align: left; height:50px; line-height:50px;">
    <jsp:include page="/WEB-INF/view/menu/MenuButtonMgr.jsp"></jsp:include>
</div>
<!-- 功能按钮结束 -->

<!-- 展开查询开始 -->
<div id="orgParams" class="develop" style="display:none;">
    <table>
        <tr>
            <td><label>机构类型：</label></td>
            <td>
                <div id="itype" class="search-select" idField="value" textField="text" defaultValue="-1" width="200" height="25"></div>
            </td>

            <td><label>关键字：</label></td>
            <td>
                <div id="keyword" class="search-textbox" width="300" height="25"></div>
            </td>

            <td><input class="org-search" type="button" onclick="query();" value="查询"/></td>
        </tr>
    </table>
</div>
<!-- 展开查询结束 -->

<!-- 内容开始 -->
<div class="mini-fit">
    <div id="datagrid" class="search-treedatagrid" idField="id" parentField="pid" textField="sname" showCheckBox="false"
         multiSelect="false" alternatingRows="true" expandLevel="1" style="width:100%;height:100%;" ondrawcell="datagriddrawcell">
        <jsp:include page="/WEB-INF/view/menu/RightButtonMgr.jsp"></jsp:include>
        <div property="columns">
            <div field="sname" width="150" headAlign="center" textAlign="left">机构名称</div>
            <div field="sorgtype" width="150" headAlign="center" textAlign="left">机构类型</div>
            <div field="username" width="100" headAlign="center" textAlign="left">联系人姓名</div>
            <div field="userphone" width="80" headAlign="center" textAlign="center">联系人电话</div>
            <div field="oper" width="${button.width}" headAlign="center" textAlign="center">操作</div>
        </div>
    </div>
</div>
<!-- 内容结束 -->
<script type="text/javascript" src="${JS}/intermediarys/orgManage.js"></script>
</body>
</html>