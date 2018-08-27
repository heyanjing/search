<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/head.jsp" %>
<!doctype html>
<html>
<head>
    <title>用户</title>
    <meta name='description' content='用户'>
    <script type="text/javascript">
        var type = "${type}";
        var userorgid = "${userorgid}";
    </script>
</head>
<body>
<!-- 功能按钮开始 -->
<div class="mini-tools" style="text-align: left; height:50px; line-height:50px;">
    <jsp:include page="/WEB-INF/view/menu/MenuButtonMgr.jsp"></jsp:include>
</div>
<!-- 功能按钮结束 -->

<!-- 展开查询开始 -->
<div id="userParams" class="develop" style="display:none;">
    <table>
        <tr>
            <c:if test="${type == 1}">
                <td><label>选择机构：</label></td>
                <td>
                    <div id="orgs" class="search-treeselect" idField="sid" parentField="sparentid" textField="sname"
                         onvaluechanged="onvalue" width="300" enabled="true" dropdownHeight="300" height="30" defaultValue="0"></div>
                </td>
            </c:if>
            <td><label>关键字：</label></td>
            <td>
                <div id="keyword" class="search-textbox" width="300" height="25"></div>
            </td>

            <!-- <td><label>姓名：</label></td>
            <td><div id="sname" class="search-textbox" width="150" height="25"></div></td>

            <td><label>用户名：</label></td>
            <td><div id="susername" class="search-textbox" width="150" height="25"></div></td>

            <td><label>电话：</label></td>
            <td><div id="sphone" class="search-textbox" width="150" height="25"></div></td>

            <td><label>身份证：</label></td>
            <td><div id="sidcard" class="search-textbox" width="150" height="25"></div></td>

            <td><label>所属机构：</label></td>
            <td><div id="sorgid" class="search-treeselect" idField="id" parentField="pid" textField="text" defaultValue="-1" width="150" height="25"></div></td> -->

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
            <div field="sname" width="50" headAlign="center" textAlign="left">姓名</div>
            <div field="sphone" width="80" headAlign="center" textAlign="center">电话</div>
            <div field="sidcard" width="100" headAlign="center" textAlign="center">身份证号码</div>
            <div field="orgname" width="120" headAlign="center" textAlign="left">机构</div>
            <div field="sgraduateschool" width="120" headAlign="center" textAlign="left">毕业学校</div>
            <div field="ldgraduationdate" width="80" headAlign="center" textAlign="center">毕业时间</div>
            <div field="susername" width="50" headAlign="center" textAlign="left">用户名</div>
            <div field="snickname" width="50" headAlign="center" textAlign="left">昵称</div>
            <div field="ssignature" width="80" headAlign="center" textAlign="left">签名</div>
            <div field="semail" width="80" headAlign="center" textAlign="left">邮箱</div>
            <div field="igender" width="30" headAlign="center" textAlign="center">性别</div>
            <div field="ldbirthday" width="80" headAlign="center" textAlign="center">生日</div>
            <div field="saddress" width="100" headAlign="center" textAlign="left">所在地</div>
            <div field="itype" width="50" headAlign="center" textAlign="center">用户类型</div>
            <!-- <div field="oper" width="150" headAlign="center" textAlign="center">操作</div> -->
            <c:choose>
                <c:when test="${param.recycle != null}">
                    <div field="oper" width="100" headAlign="center" textAlign="center">操作</div>
                </c:when>
                <c:when test="${button.width > 0 && (currentUser.usertype == 1 || currentUser.orgusertype == 2)}">
                    <div field="oper" width="${button.width + 100}" headAlign="center" textAlign="left">操作</div>
                </c:when>
                <c:when test="${currentUser.usertype == 1 || currentUser.orgusertype == 2}">
                    <div field="oper" width="100" headAlign="center" textAlign="left">操作</div>
                </c:when>
            </c:choose>
        </div>
    </div>
</div>
<!-- 内容结束 -->
<script type="text/javascript" src="${JS}/user/users.js"></script>
</body>
</html>