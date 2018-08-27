<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/jspTaglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>字典信息编辑</title>
    <link rel="stylesheet" type="text/css" href="${CSS}/demo/demo.css?${V}"/>
    <style>
        table tr td {
            padding-top: 10px;
        }
    </style>
</head>
<body>
<form id="Dictionaries" style="height:auto;">
    <div id="sid" class="search-texthide"></div>
    <table class="useradd_list" style="margin:0 auto;">
        <tr>
            <td><label>名称：</label></td>
            <td>
                <div id="sname" name="sname" class="search-textbox" rules="required,length:25" width="300" height="30"></div>
            </td>
        </tr>
        <tr>
            <td><label>类型：</label></td>
            <td>
                <div id="itype" class="search-select" rules="required" idField="value" textField="text" width="300" height="30" defaultValue="0"></div>
            </td>
        </tr>
        <tr>
            <td><label>描述：</label></td>
            <td>
                <div id="sdesc" name="sdesc" class="search-textarea" width="300" height="30"></div>
            </td>
        </tr>

    </table>
</form>
<div class="org-btn" colspan="2">
    <input class="org-save" type="button" onclick="save()" value="保存"/>
    <input class="org-close" type="button" onclick="window.closeWindow('cancel')" value="关闭"/>
</div>
<script type="text/javascript" language="javascript" src="${JS}/dictionaries/updateDictionariesPage.js?v=${RES_VER}"></script>
</body>
</html>