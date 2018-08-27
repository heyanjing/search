<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/head.jsp" %>
<!doctype html>
<html>
<head>
    <title>新增功能</title>
    <meta name='description' content='首页head中的内容'>
    <link rel="stylesheet" type="text/css" href="${CSS}/demo/demo.css?${V}"/>
    <style>
        .around_list {
            text-align: right !important;
        }

        table tr td {
            padding-top: 10px;
        }

        .des-title {
            border: none;
            padding-left: 10px;
        }

        .search-view {
            width: 280px;
            text-align: left;
            padding-left: 5px;
            border: 1px solid #ccc;
            height: 35px;
            overflow: auto;
            border-radius: 5px;
            line-height: 35px;
        }

        .search-treeselect td {
            padding-top: 0;
        }

        #idCard img {
            width: 188px;
            height: 120px;
            margin-left: 10px;
            float: left;
            margin-top: 10px;
        }
    </style>
</head>

<body>
<table class="around_list" style="margin:0 auto;border-collapse:collapse;">
    <tr>
        <td class="des-title"><label>名　　称：</label></td>
        <td>
            <div id="sname" class="search-view"></div>
        </td>
    </tr>

    <tr>
        <td class="des-title"><label>项目有关：</label></td>
        <td>
            <div id="isupportproject" class="search-view"></div>
        </td>
    </tr>

    <tr>
        <td class="des-title"><label>备　　注：</label></td>
        <td colspan="3">
            <div id="sdesc" class="search-view" style="width: 650px;"></div>
        </td>
    </tr>
</table>
<script type="text/javascript" src="${JS}/functiongroups/LookFunctionGroup.js?${V}"></script>
</body>
</html>