<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/head.jsp" %>
<!doctype html>
<html>
<head>
    <title>机构入库信息</title>
    <meta name='description' content='机构入库信息'>
    <script type="text/javascript">
        var intermediarysState = "${intermediarysState}";
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
            <div type="indexcolumn" width="50" headAlign="center" textAlign="center">序号</div>
            <c:if test="${intermediarysState != 98}">
                <div field="orgname" width="300" headAlign="center" textAlign="left">机构名称</div>
                <div field="ldtcreatetime" width="100" headAlign="center" textAlign="center">创建时间</div>
                <div field="ldtupdatetime" width="100" headAlign="center" textAlign="center">更新时间</div>
                <div field="sdesc" width="300" headAlign="center" textAlign="left">备注</div>
            </c:if>
            <c:if test="${intermediarysState == 98}">
                <div field="orgname" width="650" headAlign="center" textAlign="left">机构名称</div>
                <div field="ldtcreatetime" width="120" headAlign="center" textAlign="center">创建时间</div>
            </c:if>
            <c:if test="${button.width > 0}">
            <div field="oper" width="${button.width}" headAlign="center" textAlign="center" >操作</div>
            </c:if>
        </div>
    </div>
</div>
<!-- 内容结束 -->
<script type="text/javascript" src="${JS}/intermediarys/orgPutaway.js"></script>
</body>
</html>