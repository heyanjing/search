<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/jspTaglib.jsp" %>
<!doctype html>
<html>
<head>
    <title>功能页面</title>
    <script type="text/javascript">
    var id = "${funid}";
    </script>
</head>
<body>
<div class="mini-tools" style="text-align: left; height:50px; line-height:50px;">
    <jsp:include page="/WEB-INF/view/menu/MenuButtonMgr.jsp"></jsp:include>
</div>
<div id="funcParams" class="develop" style="display:none;">
    <table>
        <tr>
            <td><label>关键字：</label></td>
            <td>
                <div id="keyword" name="keyword" class="search-textbox" width="200" height="25"></div>
            </td>
            <td><label>实施单位：</label></td>
            <td>
                <div id="orgid" class="search-select" width="150" height="25" idField="sid" textField="sname" placeHolder="请选择"></div>
            </td>
            <td><input class="org-search" type="button" onclick="searchGrid();" value="查询"/></td>
        </tr>
    </table>
</div>
<div class="mini-fit">
    <div id="planlibsGrid" class="search-treedatagrid" idField="sid" parentField="pid" textField="sname" showCheckBox="false" multiSelect="false"
         alternatingRows="true" expandLevel="4" style="width:100%;height:100%;" ondrawcell="datagriddrawcell">
        <jsp:include page="/WEB-INF/view/menu/RightButtonMgr.jsp"></jsp:include>
        <div property="columns">
            <div type="checkcolumn"></div>
            <div field="sname" width="200" headAlign="center" textAlign="left">计划标题</div>
            <div field="orgname" width="50" headAlign="center" textAlign="center">实施单位</div>
            <div field="username" width="80" headAlign="center" textAlign="center">审计组长</div>
            <div field="ldstartdate" width="80" headAlign="center" textAlign="center">开始时间</div>
            <div field="ldenddate" width="50" headAlign="center" textAlign="center">结束时间</div>
            <div field="istate" width="100" headAlign="center" textAlign="center">计划状态</div>
            <div field="action" width="100" headAlign="center" textAlign="center">操作</div>
            <%-- <c:choose>
                <c:when test="${param.recycle != null}">
                    <div field="operation" width="100" headAlign="center" textAlign="center">操作</div>
                </c:when>
                <c:when test="${button.width > 0}">
                    <div field="operation" width="${button.width}" headAlign="center" textAlign="center">操作</div>
                </c:when>
            </c:choose> --%>
        </div>
    </div>
</div>
<script type="text/javascript" src="${JS}/planlibs/PlanlibsapprovalPage.js?${V}"></script>
</body>
</html>