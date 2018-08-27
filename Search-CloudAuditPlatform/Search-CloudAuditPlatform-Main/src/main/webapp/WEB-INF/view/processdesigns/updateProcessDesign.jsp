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
            <td style="padding-top:5px;"><label>项目相关：</label></td>
            <td style="padding-top:5px;">
                <div id="isupportproject" class="search-textradio" idField="value" textField="text" width="300" defaultValue="1" groupName="isupportproject" style="line-height:30px;float:left;"></div>
                <span><a href="javascript:void(0)"><i class="fa fa-question-circle"></i></a></span>
            </td>
        </tr>
        <tr>
            <td id="sorgid1"><label>所属机构：</label></td>
            <td id="sorgid2">
                <div id="sorgid" class="search-treeselect" idField="sid" parentField="sparentid" textField="sname" width="300" height="30" placeHolder="请选择所属机构" onvalidate="sorgidValidate"></div>
                <span><a href="javascript:void(0)"><i class="fa fa-question-circle"></i></a></span>
            </td>
            <td id="sfromorgid1"><label>来源机构：</label></td>
            <td id="sfromorgid2">
                <div id="sfromorgid" class="search-treeselect" idField="sid" parentField="sparentid" textField="sname" width="300" height="30" placeHolder="请选择来源机构"></div>
                <span><a href="javascript:void(0)"><i class="fa fa-question-circle"></i></a></span>
            </td>
        </tr>
        <tr>
            <td><label>描　　述：</label></td>
            <td colspan="3">
                <div id="sdesc" class="search-textarea" width="680" style="float:left;"></div>
            </td>
        </tr>
        <tr>
            <td colspan="4" style="text-align:center; padding-top:5px;">
                <input type="button" onclick="saveData()" value="保存"/>
                <input type="button" onclick="windowClose()" value="关闭"/>
            </td>
        </tr>
    </table>
</form>

<script type="text/javascript" src="${JS}/processdesigns/updateProcessDesign.js?${V}"></script>
</body>
</html>