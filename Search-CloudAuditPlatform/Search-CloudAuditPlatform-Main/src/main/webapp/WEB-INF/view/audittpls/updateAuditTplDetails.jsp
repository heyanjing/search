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
            <td style="padding-top:5px;"><label>资料类型：</label></td>
            <td style="padding-top:5px;">
                <div id="itype" class="search-select" rules="required" idField="value" textField="text" width="300" height="30" placeHolder="请选择资料类型" onvaluechanged="itypechang"></div>
                <span><a href="javascript:void(0)"><i class="fa fa-question-circle"></i></a></span>
            </td>
        </tr>
        <tr>
        	<td id="imust1" style="padding-top:5px;display: none;"><label>是否必填：</label></td>
            <td id="imust2" style="padding-top:5px;display: none;">
                <div id="imust" class="search-textradio" idField="value" textField="text" width="300" defaultValue="1" groupName="imust" style="line-height:30px;float:left;"></div>
                <span><a href="javascript:void(0)"><i class="fa fa-question-circle"></i></a></span>
            </td>
            <td id="sfiletplid1" style="padding-top:5px;display: none;"><label>文件模版：</label></td>
            <td id="sfiletplid2" style="padding-top:5px;display: none;">
                <div id="sfiletplid" class="search-select" idField="sid" textField="sname" width="300" height="30" placeHolder="请选择文件模版"></div>
                <span><a href="javascript:void(0)"><i class="fa fa-question-circle"></i></a></span>
            </td>
        </tr>
        <tr>
            <td id="sparentid1" style="display: none;"><label>父        级：</label></td>
            <td id="sparentid2" style="display: none;">
                <div id="sparentid" class="search-select" idField="sid" textField="sname" width="300" height="30" placeHolder="请选择父级资料类型"></div>
                <span><a href="javascript:void(0)"><i class="fa fa-question-circle"></i></a></span>
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

<script type="text/javascript" src="${JS}/audittpls/updateAuditTplDetails.js?${V}"></script>
</body>
</html>