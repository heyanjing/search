<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/jspTaglib.jsp" %>
<!doctype html>
<html>
<head>
    <title>新增功能</title>
    <meta name='description' content='首页head中的内容'>
    <link rel="stylesheet" type="text/css" href="${CSS}/demo/demo.css?${V}"/>
    <link rel="stylesheet" type="text/css" href="${CSS}/upload/webuploader.css">
    <style>
        .around_list {
            text-align: right !important;
        }

        table tr td {
            padding-top: 10px;
        }

        .search-treeselect {
            float: left;
        }

        .search-select {
            float: left;
        }

        .search-textradio {
            float: left;
        }

        table tr td span a {
            display: inline-block;
            margin-top: 10px;
            padding-left: 3px;
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
    <table class="around_list">
        <tr>
            <td>
                <div id="sid" class="search-texthide"></div>
            </td>
        </tr>
        <tr>
            <td><label>名　　称：</label></td>
            <td>
                <div id="sname" class="search-textbox" rules="required" width="300" height="30"></div>
            </td>
            <td><label style="margin-left:10px;">父　　级：</label></td>
            <td>
                <div id="sparentid" style="float: left;" class="search-treeselect" idField="sid" parentField="sparentid" textField="sname" width="300" height="30" placeHolder="请选择父级机构" onvaluechanged="changsparentid"></div>
                <span><a href="javascript:void(0)"><i class="fa fa-question-circle"></i></a></span>
            </td>
        </tr>
        <tr>
            <td><label>机构类型：</label></td>
            <td>
                <div id="itype" class="search-select" rules="required" idField="value" textField="text" width="300" height="30" placeHolder="请选择机构类型" multiSelect="true"></div>
                <span><a href="javascript:void(0)"><i class="fa fa-question-circle"></i></a></span>
            </td>
            <td id="area1"><label>所属区县：</label></td>
            <td id="area2">
                <div id="sareaid" class="search-treeselect" rules="required" idField="sid" parentField="sparentid" textField="sname" width="300" height="30" placeHolder="请选择所属区县"></div>
                <span><a href="javascript:void(0)"><i class="fa fa-question-circle"></i></a></span>
            </td>
        </tr>
        <tr>
            <td><label>描　　述：</label></td>
            <td colspan="3">
                <div id="sdes" class="search-textarea" width="680" style="float:left;"></div>
            </td>
        </tr>
        <tr>
            <td colspan="4" style="text-align:center; padding-top:5px;">
                <input class="org-save" type="button" onclick="saveData()" value="保存"/>
                <input class="org-close" type="button" onclick="windowClose()" value="关闭"/>
            </td>
        </tr>
    </table>
</form>

<script type="text/javascript" src="${JS}/orgs/EditSpecialOrg.js?${V}"></script>
</body>
</html>