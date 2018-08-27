<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/jspTaglib.jsp" %>
<!doctype html>
<html>
<head>
    <title>申请入库</title>
    <link rel="stylesheet" type="text/css" href="${CSS}/demo/demo.css?${V}"/>
    <style>
        table tr td {
            padding-top: 10px;
        }

        .search-treeselect td {
            padding-top: 0;
        }
    </style>
</head>
<body>
<form id="orgApply">
    <table class="around_list" style=" margin:0 auto;">
        <tr>
            <td style="padding-left:10px;"><label>审计机构：</label></td>
            <td>
                <div id="sauditorgid" class="search-select" rules="required" idField="sid" textField="sname" multiSelect="true" width="200" height="30"></div>
            </td>
        </tr>
    </table>
</form>
<div class="org-btn" colspan="4">
    <input class="org-save" type="button" onclick="saveData()" value="保存"/>
    <input class="org-close" type="button" onclick="window.closeWindow('cancel');" value="关闭"/>
</div>
<script type="text/javascript" src="${JS}/intermediarys/orgApplyWarehous.js?${V}"></script>
</body>
</html>