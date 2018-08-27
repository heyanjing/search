<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/head.jsp" %>
<!doctype html>
<html>
<head>
    <title>建设项目库</title>
    <meta name='description' content='建设项目库'>
</head>
<body>
<!-- 功能按钮开始 -->
<div class="mini-tools" style="text-align: left; height:50px; line-height:50px;">
    <jsp:include page="/WEB-INF/view/menu/MenuButtonMgr.jsp"></jsp:include>
</div>
<!-- 功能按钮结束 -->

<!-- 展开查询开始 -->
<div id="proLibParams" class="develop" style="display:none;">
    <table>
        <tr>
            <td><label>关键字：</label></td>
            <td>
                <div id="keyword" class="search-textbox" width="300" height="25"></div>
            </td>
            <td><input class="org-search" type="button" onclick="queryProLib();" value="查询"/></td>
        </tr>
    </table>
</div>
<!-- 展开查询结束 -->

<!-- 内容开始 -->
<div class="mini-fit">
    <div id="proLibGrid" class="search-datagrid" idField="sid" showCheckBox="true" multiSelect="true" alternatingRows="true"
         showPager="true" pageSize="20" ondrawcell="onDrawCell" style="width:100%;height:100%;">
        <jsp:include page="/WEB-INF/view/menu/RightButtonMgr.jsp"></jsp:include>
        <div property="columns">
            <div type="checkcolumn" width="30" headAlign="center" textAlign="center"></div>
            <div type="indexcolumn" width="30" headAlign="center" textAlign="center">序号</div>
            <div field="sname" width="100" headAlign="center" textAlign="left">项目名称</div>
            <div field="ownername" width="100" headAlign="center" textAlign="center">业主单位</div>
            <div field="auditname" width="100" headAlign="center" textAlign="center">项目归属</div>
            <div field="approvalnum" width="80" headAlign="center" textAlign="left">立项文号</div>
            <div field="feasibilitynum" width="80" headAlign="center" textAlign="left">可研文号</div>
            <div field="calculationnum" width="80" headAlign="center" textAlign="center">概算文号</div>
            <div field="budgetnum" width="80" headAlign="center" textAlign="left">预算文号</div>
            <!-- <div field="bidnum" width="50" headAlign="center" textAlign="left" allowSort="true">招投标文号</div> -->
            <c:choose>
                <c:when test="${param.recycle != null}">
                    <div field="operation" width="100" headAlign="center" textAlign="center">操作</div>
                </c:when>
                <c:when test="${button.width > 0}">
                    <div field="operation" width="${button.width}" headAlign="center" textAlign="center">操作</div>
                </c:when>
            </c:choose>
        </div>
    </div>
</div>
<!-- 内容结束 -->
<script type="text/javascript" src="${JS}/projectlib/ProjectLibMgrHandle.js?${V}"></script>
</body>
</html>