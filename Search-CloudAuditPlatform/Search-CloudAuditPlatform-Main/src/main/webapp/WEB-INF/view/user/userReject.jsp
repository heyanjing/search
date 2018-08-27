<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/head.jsp" %>
<!doctype html>
<html>
<head>
    <title>用户驳回</title>
    <meta name='description' content='用户'>
    <script type="text/javascript">
        var type = "${type}";
    </script>
</head>
<body>
<!-- 功能按钮开始 -->
<div class="mini-tools" style="text-align: left; height:50px; line-height:50px;">
    <jsp:include page="/WEB-INF/view/menu/MenuButtonMgr.jsp"></jsp:include>
</div>
<c:if test="${isaudit == 1 && ((isorgdepartment == 1 && permissionlevel == 1) || isorgdepartment == 2)}">
    <input type="checkbox" id="advanced">
</c:if>
<!-- 功能按钮结束 -->

<!-- 展开查询开始 -->
<div id="userParams" class="develop" style="display:none;">
    <table>
        <tr>
            <c:if test="${type == 1}">
                <td><label>选择机构：</label></td>
                <td>
                    <div id="orgs" class="search-treeselect" idField="sid" parentField="sparentid" textField="sname"
                         onvaluechanged="onvalue" width="300" enabled="true" dropdownHeight="300" height="30"></div>
                </td>
            </c:if>

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
            <div field="sname" width="50" headAlign="center" textAlign="left" allowSort="true">姓名</div>
            <div field="sphone" width="80" headAlign="center" textAlign="center" allowSort="true">电话</div>
            <div field="sidcard" width="100" headAlign="center" textAlign="center" allowSort="true">身份证号码</div>
            <div field="orgname" width="120" headAlign="center" textAlign="left" allowSort="true">机构</div>
            <div field="sgraduateschool" width="120" headAlign="center" textAlign="left" allowSort="true">毕业学校</div>
            <div field="ldgraduationdate" width="80" headAlign="center" textAlign="center" allowSort="true">毕业时间</div>
            <div field="susername" width="50" headAlign="center" textAlign="left" allowSort="true">用户名</div>
            <div field="snickname" width="50" headAlign="center" textAlign="left" allowSort="true">昵称</div>
            <div field="ssignature" width="80" headAlign="center" textAlign="left" allowSort="true">签名</div>
            <div field="semail" width="80" headAlign="center" textAlign="left" allowSort="true">邮箱</div>
            <div field="igender" width="30" headAlign="center" textAlign="center" allowSort="true">性别</div>
            <div field="ldbirthday" width="80" headAlign="center" textAlign="center" allowSort="true">生日</div>
            <div field="saddress" width="100" headAlign="center" textAlign="left" allowSort="true">所在地</div>
            <div field="itype" width="50" headAlign="center" textAlign="center" allowSort="true">用户类型</div>
        </div>
    </div>
</div>
<!-- 内容结束 -->
<script type="text/javascript" src="${JS}/user/userReject.js"></script>
</body>
</html>