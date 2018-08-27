<
<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/jspTaglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>项目计划存储库页面</title>
    <script type="text/javascript">
        var __state = '${param.recycle}' == 'recycle' ? 2 : 1;
    </script>
</head>
<body>
<div class="mini-tools" style="text-align: left; height:50px; line-height:50px;">
    <jsp:include page="/WEB-INF/view/menu/MenuButtonMgr.jsp"></jsp:include>
</div>

<!-- 展开查询开始 -->
<div id="dicParams" class="develop" style="display:none;">
    <table>
        <tr>
            <td><label>关键字：</label></td>
            <td>
                <div id="keyword" name="keyword" class="search-textbox" width="200" height="30"></div>
            </td>

            <td><input class="org-search" type="button" onclick="searchGrid();" value="查询"/></td>
        </tr>
    </table>
</div>

<div class="mini-fit">
    <div id="ProjectLibsgrid" class="search-datagrid" idField="sid" showCheckBox="true" multiSelect="true"
         alternatingRows="true" showPager="true" pageSize="20"
         style="width:100%;height:100%;">
        <jsp:include page="/WEB-INF/view/menu/RightButtonMgr.jsp"></jsp:include>
        <div property="columns">
            <div type="indexcolumn" width="50" headAlign="center" textAlign="center">序号</div>
            <div field="sname" width="100" headAlign="center" allowSort="true">名称</div>
            <div field="proprietorname" width="300" headAlign="center">业主单位</div>
            <div field="auditname" width="100" headAlign="center" textAlign="center">审计单位</div>
            <div field="sumdconstructioncost" width="100" headAlign="center" textAlign="center">概算工程费用（万元）</div>
            <%-- <c:choose>
                <c:when test="${param.recycle != null}">
                    <div field="action" width="180" headAlign="center" textAlign="center" >操作</div>
                </c:when>
                <c:otherwise>
                  <div field="action" width="${button.width +60}" headAlign="center" textAlign="center" >操作</div>
                </c:otherwise>
            </c:choose> --%>
        </div>
    </div>
</div>
<script type="text/javascript" language="javascript" src="${JS}/projectlib/ProjectLibPage.js?v=${RES_VER}"></script>
</body>
</html>