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

        table tr td {
            padding-top: 10px;
        }

        table tr td span a {
            display: inline-block;
            margin-top: 10px;
            padding-left: 1px;
        }

        table tr td span i {
            color: green;
            font-size: 14px !important;
        }
    </style>
    <script type="text/javascript">
        var __usertype = ${usertype};
    </script>
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
            <td><label>名　　称：</label></td>
            <td colspan="3">
                <div id="sname" class="search-textbox" rules="required" width="570" height="30"></div>
            </td>

        </tr>
        <tr>
            <td><label>项目有关：</label>
            </td>
            <td align="left">
                <div id="isupportproject" class="search-textradio" rules="required" idField="value" textField="text" width="200" height="30" style="line-height:30px;float:left;" defaultValue="1"></div>
                <span><a href="javascript:void(0)"><i class="fa fa-question-circle"></i></a></span>
            </td>
            <td id="org1" style="display: none;">
                <label>来源机构：</label>
            </td>
            <td id="org2" style="display: none;">
                <div id="sfromorgid" style="float: left;" class="search-treeselect" idField="sid" parentField="sparentid" textField="sname" width="300" height="30" onvalidate="sfromorgidValidate"></div>
                <span><a href="javascript:void(0)"><i class="fa fa-question-circle"></i></a></span>
            </td>
            <td id="org3" style="display: none;">
                <label>所属机构：</label>
            </td>
            <td id="org4" style="display: none;">
                <div id="sorgid" style="float: left;" class="search-treeselect" idField="sid" parentField="sparentid" textField="sname" width="300" height="30"></div>
                <span><a href="javascript:void(0)"><i class="fa fa-question-circle"></i></a></span>
            </td>
        </tr>
        <tr>
            <td><label>备　　注：</label></td>
            <td colspan="3">
                <div id="sdesc" class="search-textarea" width="570" style="display:inline-block;"></div>
            </td>
        </tr>
    </table>
</form>
<div class="org-btn" colspan="4">
    <input class="org-save" type="button" onclick="saveData()" value="保存"/>
    <input class="org-close" type="button" onclick="windowClose()" value="关闭"/>
</div>
<script type="text/javascript" src="${JS}/functiongroups/UpdateFunctionGroups.js?${V}"></script>
</body>
</html>