<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/head.jsp" %>
<!doctype html>
<html>
<head>
    <title>功能组</title>
    <meta name='description' content='功能组'>
</head>
<body>
<div class="mini-tools" style="text-align: left; height:50px; line-height:50px;">
    <jsp:include page="/WEB-INF/view/menu/MenuButtonMgr.jsp"></jsp:include>
</div>
<div id="orgquery" class="develop" style="display:none;">
    <table>
        <tr>
            <td><label>项目相关：</label></td>
            <td>
                <div id="isupportproject" class="search-select" idField="value" textField="text" width="150" height="25"></div>
            </td>

            <td><label>关键字：</label></td>
            <td>
                <div id="sname" class="search-textbox" width="150" height="25"></div>
            </td>

            <td><input class="org-search" type="button" onclick="FunGroup();" value="查询"/></td>
        </tr>
    </table>
</div>
<div class="mini-fit">
    <div id="datagrid" class="search-datagrid" idField="sid" showCheckBox="true" multiSelect="true" alternatingRows="true"
         showPager="true" pageSize="20" ondrawcell="datagriddrawcell" style="width:100%;height:100%;">
        <jsp:include page="/WEB-INF/view/menu/RightButtonMgr.jsp"></jsp:include>
        <div property="columns">
            <div type="checkcolumn" width="30" headAlign="center" textAlign="center"></div>
            <div type="indexcolumn" width="30" headAlign="center" textAlign="center">序号</div>
            <div field="sname" width="80" headAlign="center" textAlign="left" allowSort="true">名称</div>
            <div field="orgname" width="200" headAlign="center" textAlign="left" allowSort="true">所属机构</div>
            <c:if test="${orgtype!=101&&usertype!=1}">
            <div field="fromorgname" width="150" headAlign="center" textAlign="left" allowSort="true">来源机构</div>
            </c:if>
            <div field="isupportproject" width="100" headAlign="center" textAlign="center" allowSort="true">项目相关</div>
            <div field="ldtcreatetime" width="100" headAlign="center" textAlign="center" allowSort="true">创建时间</div>
            <div field="sdesc" width="100" headAlign="center" textAlign="left">备注</div>
            <div field="oper" width="150" headAlign="center" textAlign="center" allowSort="true">操作</div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${JS}/functiongroups/functiongroups.js"></script>
</body>
</html>