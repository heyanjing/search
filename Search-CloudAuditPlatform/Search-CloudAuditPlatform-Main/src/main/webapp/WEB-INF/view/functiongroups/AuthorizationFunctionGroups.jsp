<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/jspTaglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>用户授权</title>
    <style>
        .table {
            border-collapse: collapse;
        }

        .table td {
            border: 1px solid #000;
        }
    </style>
    <script type="text/javascript">
        var __usertype = ${usertype};
    </script>
</head>
<body>

<form id=authorizationform>
    <table class="useradd_list"
           style="text-align: right; margin-left: 100px;">
        <tr id="org1">
            <td><label>来源机构：</label></td>
            <td>
                <div id="aorgid" class="search-treeselect" idField="sid" parentField="sparentid" textField="sname" width="300" height="30" onvaluechanged="get"></div>
            </td>
        </tr>
        <tr id="org2">
            <td><label>审计单位：</label></td>
            <td>
                <div id="sorgid" class="search-treeselect" idField="sid" parentField="sparentid" textField="sname" width="300" height="30" onvaluechanged="get"></div>
            </td>
        </tr>
    </table>


    <div class="fixedTb" style="position:relative;">
        <div class="flow-item contentList" data-title="授权功能" style="height:450px;">
            <div id="treedatagrid" class="search-treedatagrid" idField="id" parentField="pid" textField="text" ondrawcell="drawcell"
                 multiSelect="true" selectedRows="false" alternatingRows="true" expandLevel="0" style="width: 100%; height: 100%;">
                <div property="columns">
                </div>
            </div>
        </div>
    </div>


    <div>
        <input type="button" onclick="save()" value="保存"/>
        <input type="button" onclick="window.closeWindow('cancel')" value="关闭"/>
    </div>
</form>


<script type="text/javascript" language="javascript" src="${JS}/functiongroups/AuthorizationFunctionGroups.js?v=${RES_VER}"></script>
</body>
</html>