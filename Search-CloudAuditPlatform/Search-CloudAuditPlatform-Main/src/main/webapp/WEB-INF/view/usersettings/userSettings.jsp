<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/head.jsp" %>
<!doctype html>
<html>
<head>
    <title>用户设置</title>
    <link rel="stylesheet" type="text/css" href="${CSS}/demo/demo.css?${V}"/>
    <meta name='description' content='用户设置'>
    <style type="text/css">
        table tr td label {
            float: right;
        }

        table tr td {
            padding-top: 10px;
        }
    </style>
</head>
<body>
<form id="setting">
    <div id="sid" class="search-texthide"></div>
    <table class="around_list" style="margin:0 auto; margin-top:20px;">
        <tr>
            <td>
                <label>显示分类数：</label>
            </td>
            <td>
                <div id="iclassifynum" class="search-textbox" rules="required,int" width="200" height="30"></div>
            </td>
        </tr>
        <tr>
            <td colspan="4" style="text-align:center; padding-top:5px;">
                <input class="org-save" type="button" onclick="saveData()" value="保存"/>
            </td>
        </tr>
    </table>
</form>
<script type="text/javascript" src="${JS}/usersettings/userSettings.js"></script>
</body>
</html>