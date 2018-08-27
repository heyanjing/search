<%@page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="/WEB-INF/layout/taglib/head.jsp" %>
<!doctype html>
<html>
<head>
    <title>查看区域</title>
    <meta name='description' content='查看区域'>
    <link rel="stylesheet" type="text/css" href="${CSS}/demo/demo.css"/>
    <style>
        .around_list {
            text-align: right !important;
        }

        table tr td {
            padding-top: 10px;
        }

        .search-treeselect td {
            padding-top: 0;
        }

        .des-title {
            border: none;
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

    </style>
</head>
<body>
<form id="Dictionaries" style="overflow:auto;height:auto;">
    <div id="sid" class="search-texthide"></div>
    <table class="useradd_list" style="margin:0 auto; padding-top:10px;">
        <tr>
            <td class="des-title"><label>名称：</label></td>
            <td>
                <div class="search-view">${areas.sname}</div>
            </td>
        </tr>
        <tr>
            <td class="des-title"><label>描述：</label></td>
            <td>
                <div class="search-view">${areas.sdes}</div>
            </td>
        </tr>
        <tr>
            <td class="des-title"><label>所属区域：</label></td>
            <td>
                <div class="search-view">${areas.name}</div>
            </td>
        </tr>

    </table>
</form>
</body>
</html>