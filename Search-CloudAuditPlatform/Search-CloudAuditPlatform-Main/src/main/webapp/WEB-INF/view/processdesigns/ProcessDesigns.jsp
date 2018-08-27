<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/head.jsp" %>
<!doctype html>
<html>
<head>
    <title>流程设计</title>
    <meta name='description' content='流程设计'>
</head>
<script type="text/javascript">
    var __usertype = ${usertype};
</script>
<body>
<div class="mini-tools" style="text-align: left; height:50px; line-height:50px;">
    <jsp:include page="/WEB-INF/view/menu/MenuButtonMgr.jsp"></jsp:include>
</div>
<div id="orgquery" class="develop" style="display:none;">
    <table>
        <tr>
        	<td><label>名称：</label></td>
            <td>
                <div id="sname" class="search-textbox" width="150" height="25"></div>
            </td>
            <td><label>状态：</label></td>
            <td>
                <div id="istate" class="search-select" idField="value" textField="text" width="150" height="25"></div>
            </td>
            <td><input class="org-search" type="button" onclick="ProcessDesigns();" value="查询"/></td>
        </tr>
    </table>
</div>
<div class="mini-fit">
    <div id="datagrid" class="search-datagrid" idField="sid" showCheckBox="true" alternatingRows="true"
         showPager="true" pageSize="20" ondrawcell="datagriddrawcell" style="width:100%;height:100%;">
        <jsp:include page="/WEB-INF/view/menu/RightButtonMgr.jsp"></jsp:include>
        <div property="columns">
            <div type="indexcolumn" width="30" headAlign="center" textAlign="center">序号</div>
            <div field="sname" width="150" headAlign="center" textAlign="left" allowSort="true">名称</div>
            <c:if test="${usertype==1}">
            <div field="sorgname" width="150" headAlign="center" textAlign="left" allowSort="true">所属机构</div>
            </c:if>
            <%-- <c:if test="${orgtype!=101}">
            <div field="sfromorgname" width="150" headAlign="center" textAlign="left" allowSort="true">来源机构</div>
            </c:if> --%>
            <div field="isupportproject" width="50" headAlign="center" textAlign="center" allowSort="true">项目相关</div>
            <div field="istate" width="80" headAlign="center" textAlign="center">状态</div>
            <div field="oper" width="120" headAlign="center" textAlign="center" allowSort="true">操作</div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${JS}/processdesigns/ProcessDesigns.js"></script>
</body>
</html>