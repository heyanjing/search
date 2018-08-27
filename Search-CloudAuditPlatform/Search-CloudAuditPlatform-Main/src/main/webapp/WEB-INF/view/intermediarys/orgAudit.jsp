<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/head.jsp" %>
<!doctype html>
<html>
<head>
    <title>机构审核</title>
    <meta name='description' content='机构审核'>
    <script type="text/javascript">
        var _orgtype = "${currentUser.orgtype}";
        var _usertype = "${currentUser.usertype}";
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
    <div id="datagrid" class="search-datagrid" idField="sid" showCheckBox="true" multiSelect="true" alternatingRows="true"
         showPager="true" pageSize="20" ondrawcell="datagriddrawcell" style="width:100%;height:100%;">
        <jsp:include page="/WEB-INF/view/menu/RightButtonMgr.jsp"></jsp:include>
        <div property="columns">
            <div type="checkcolumn" width="30" headAlign="center" textAlign="center"></div>
            <div type="indexcolumn" width="30" headAlign="center" textAlign="center">序号</div>
            <div field="orgname" width="200" headAlign="center" textAlign="left">机构名称</div>
            <c:if test="${currentUser.usertype == 1 || fn:contains(currentUser.orgtype, 102)}">
                <div field="auditorgname" width="200" headAlign="center" textAlign="left">审计机构</div>
            </c:if>
            <div field="itype" width="200" headAlign="center" textAlign="left">机构类型</div>
            <div field="username" width="100" headAlign="center" textAlign="left">联系人姓名</div>
            <div field="sphone" width="100" headAlign="center" textAlign="center">联系人电话</div>
            <c:if test="${button.width > 0}">
            <div field="oper" width="${button.width}" headAlign="center" textAlign="center">操作</div>
            </c:if>
        </div>
    </div>
</div>
<!-- 内容结束 -->
<script type="text/javascript" src="${JS}/intermediarys/orgAudit.js"></script>
</body>
</html>