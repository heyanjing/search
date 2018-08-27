<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/jspTaglib.jsp" %>
<!doctype html>
<html>
<head>
    <title>功能页面</title>
    <script type="text/javascript">
        $(document).ready(function () {
            initalQuery();
        });
    </script>
</head>
<body>
<div class="mini-tools" style="text-align: left; height:50px; line-height:50px;">
    <jsp:include page="/WEB-INF/view/menu/MenuButtonMgr.jsp"></jsp:include>
</div>
<div id="orgquery" class="develop" style="display:none;">
    <table>
        <tr>
            <!-- 				<td><label>机构类型：</label></td>
                            <td><div id="itype" class="search-select" idField="value" textField="text" width="150" height="25"></div></td>

                            <td><label>是否部门：</label></td>
                            <td><div id="iisdepartment" class="search-select" idField="value" textField="text" width="150" height="25"></div></td>
                             -->
            <td><label>关键字：</label></td>
            <td>
                <div id="sname" class="search-textbox" width="150" height="25"></div>
            </td>

            <td><input class="org-search" type="button" onclick="OrgsMgrHandle();" value="查询"/></td>
        </tr>
    </table>
</div>
<div class="mini-fit">
    <div id="orgGrid" class="search-treedatagrid" idField="sid" parentField="sparentid" textField="sname" showCheckBox="false" multiSelect="false"
         alternatingRows="true" expandLevel="4" style="width:100%;height:100%;" ondrawcell="ondrawcell">
        <jsp:include page="/WEB-INF/view/menu/RightButtonMgr.jsp"></jsp:include>
        <div property="columns">
            <div type="checkcolumn"></div>
            <div field="sname" width="200" headAlign="center" textAlign="left">名称</div>
            <div field="sdes" width="80" headAlign="center" textAlign="left">描述</div>
            <div field="itype" width="50" headAlign="center" textAlign="center">机构类型</div>
            <!-- <div field="lusernumber" width="100" headAlign="center" textAlign="center" >机构允许用户人数</div>
            <div field="iisdepartment" width="50" headAlign="center" textAlign="center" >是否部门</div> -->
            <div field="oper" width="100" headAlign="center" textAlign="center">操作</div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${JS}/orgs/SpecialOrgs.js?${V}"></script>
</body>
</html>