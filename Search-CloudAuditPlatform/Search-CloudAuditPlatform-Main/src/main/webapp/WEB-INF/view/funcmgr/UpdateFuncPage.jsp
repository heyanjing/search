<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/jspTaglib.jsp" %>
<!doctype html>
<html>
<head>
    <title>新增功能</title>
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
<form id="form">
    <table class="around_list" style=" margin:0 auto;">
        <tr>
            <td>
                <div id="sid" class="search-texthide"></div>
            </td>
        </tr>
        <tr>
            <td style="padding-top:0;"><label>名　　称：</label></td>
            <td style="padding-top:0;">
                <div id="sname" class="search-textbox" rules="required" width="200" height="30"></div>
            </td>
            <td style="padding-left:10px; padding-top:0;"><label>父　　级：</label></td>
            <td style="padding-top:0;">
                <div id="sparentid" class="search-treeselect" idField="sid" parentField="sparentid" textField="sname" width="200" height="30" placeHolder="请选择父级功能"></div>
            </td>
        </tr>
        <tr>
            <td><label>类　　型：</label></td>
            <td>
                <div id="itype" class="search-select" rules="required" idField="value" textField="text" width="200" height="30" dropdownHeight="200" placeHolder="请选择功能类型" onvaluechanged="onValueChanged"></div>
            </td>
            <td style="padding-left:10px;"><label>图　　标：</label></td>
            <td>
                <div id="sicon" class="search-textbox" width="200" height="30" style="display:inline-block;"></div>
            </td>
        </tr>
        <tr>
            <td><label>调用方法：</label></td>
            <td>
                <div id="spcmethod" class="search-textbox" width="200" height="30" style="display:inline-block;"></div>
            </td>
            <td><label>快捷图标：</label></td>
            <td >
                <div id="sminicon" class="search-textbox" width="200" height="30" style="display:inline-block;"></div>
            </td>
        </tr>
        <tr>
            <td><label>手机支持：</label></td>
            <td>
                <div id="isupportphone" class="search-select" rules="required" idField="value" textField="text" width="200" height="30" dropdownHeight="200" placeHolder="请选择是否支持手机端"></div>
            </td>
            <td style="padding-left:10px;"><label>手机方法：</label>
            </td>
            <td align="left">
                <div id="sandroidmethod" class="search-textbox" width="200" height="30" style="display:inline-block;"></div>
            </td>
        </tr>
        <tr>
            <td><label>显示顺序：</label></td>
            <td>
                <div id="iorder" class="search-textbox" rules="required,int" width="200" height="30" style="display:inline-block;"></div>
            </td>
            <td style="padding-left:10px;"><label>项目有关：</label>
            </td>
            <td align="left">
                <div id="isupportproject" class="search-select" rules="required" idField="value" textField="text" width="200" height="30" dropdownHeight="200" placeHolder="请选择是否项目项有关"></div>
            </td>
        </tr>
        <tr id="tr_sbtnlocation">
            <td><label>显示位置：</label></td>
            <td>
                <div id="sbtnlocation" class="search-select" multiSelect="true" rules="required" idField="value" textField="text" width="200" height="30" dropdownHeight="200" placeHolder="请选择显示位置"></div>
            </td>
            <td><label>绑定事件：</label></td>
            <td>
                <div id="sbindevent" class="search-textbox" width="200" height="30" style="display:inline-block;"></div>
            </td>
        </tr>
        <tr>
            <td><label>参与流程：</label></td>
            <td>
                <div id="ijoinprocess" class="search-select" rules="required" idField="value" textField="text" width="200" height="30" dropdownHeight="200" placeHolder="请选择是否参与流程"></div>
            </td>
            <td><label>流程对应表：</label></td>
            <td>
                <div id="sjoinprocesstable" class="search-select" width="200" height="30" idField="tableName" textField="comments" style="display:inline-block;" multiSelect="true" onvalidate="sjoinprocesstableValidate"></div>
            </td>
        </tr>
        <tr>
            <td><label>备　　注：</label></td>
            <td colspan="3">
                <div id="sdesc" class="search-textarea" width="480" style="display:inline-block;"></div>
            </td>
        </tr>
    </table>
</form>
<div class="org-btn" colspan="4">
    <input class="org-save" type="button" onclick="saveData()" value="保存"/>
    <input class="org-close" type="button" onclick="windowClose()" value="关闭"/>
</div>
<script type="text/javascript" src="${JS}/funcmgr/UpdateFuncHandle.js?${V}"></script>
</body>
</html>