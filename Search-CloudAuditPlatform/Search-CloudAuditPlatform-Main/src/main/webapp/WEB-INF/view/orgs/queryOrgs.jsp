<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/jspTaglib.jsp" %>
<!doctype html>
<html>
<head>
    <title>功能页面</title>
</head>
<body>
<table>
    <tr>
        <td><label>名称：</label></td>
        <td>
            <div id="sname" class="search-textbox" width="150" height="25"></div>
        </td>

        <td><input class="org-search" type="button" onclick="OrgsMgrHandle();" value="查询"/></td>
    </tr>
</table>
<div class="mini-fit">
    <div id="orgGrid" class="search-treedatagrid" idField="sid" parentField="sparentid" textField="sname" showCheckBox="false" multiSelect="false"
         alternatingRows="true" expandLevel="4" style="width:100%;height:400px;" ondrawcell="ondrawcell">
        <div property="columns">
            <div type="checkcolumn"></div>
            <div field="sname" width="200" headAlign="center" textAlign="left">名称</div>
            <div field="sdes" width="80" headAlign="center" textAlign="left">描述</div>
            <div field="itype" width="50" headAlign="center" textAlign="center">机构类型</div>

        </div>
    </div>
</div>
<div>
    <input type="button" onclick="save()" value="保存"/>
    <input type="button" onclick="window.closeWindow('cancel')" value="关闭"/>
</div>
<script type="text/javascript" src="${JS}/orgs/queryOrgs.js?${V}"></script>
</body>
</html>