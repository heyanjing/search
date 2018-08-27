<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/head.jsp" %>
<!doctype html>
<html>
<head>
    <title>流程设计</title>
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
    <script type="text/javascript">
        var __usertype = ${usertype};
    </script>
</head>

<body>
<div class="flow-item" data-title="流程设计信息" style="padding-top:10px;">
    <table class="around_list" style="margin:0 auto;border-collapse:collapse;">
        <tr>
            <td class="des-title"><label>名　　称：</label></td>
            <td>
                <div id="sname" class="search-view"></div>
            </td>
            <td class="des-title"><label>项目相关：</label></td>
            <td>
        		<div id="isupportproject" class="search-view"></div>
        	</td>
        </tr>
        <tr>
        	<td id="sfromorgname1" class="des-title"><label>来源机构：</label></td>
        	<td id="sfromorgname2">
        		<div id="sfromorgname" class="search-view"></div>
        	</td>
        	<c:if test="${usertype==1}">
            <td class="des-title"><label>所属机构：</label></td>
            <td>
                <div id="sorgname" class="search-view"></div>
            </td>
            </c:if>
            <td class="des-title"><label>描　　述：</label></td>
            <td>
                <div id="sdesc" class="search-view"></div>
            </td>
        </tr>
    </table>
</div>
<div id="mask"></div>

<script type="text/javascript" src="${JS}/processdesigns/ProcessDesignInfo.js?${V}"></script>
</body>
</html>