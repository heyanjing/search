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

        .des-title {
            border: none;
        }

        .search-view {
            width: 200px;
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
            <td><label>名　　称：</label></td>
            <td>
                <div id="sname" class="search-view"></div>
            </td>
            <td style="padding-left:10px;"><label>父　　级：</label></td>
            <td>
                <div id="parentname" class="search-view"></div>
            </td>
        </tr>
        <tr>
            <td><label>类　　型：</label></td>
            <td>
                <div id="itype" class="search-view"></div>
            </td>
            <td style="padding-left:10px;"><label>图　　标：</label></td>
            <td>
                <div id="sicon" class="search-view"></div>
            </td>
        </tr>
        <tr>
            <td><label>调用方法：</label></td>
            <td colspan="3">
                <div id="spcmethod" class="search-view" style="width: 480px;"></div>
            </td>
        </tr>
        <tr>
            <td><label>手机支持：</label></td>
            <td>
                <div id="isupportphone" class="search-view"></div>
            </td>
            <td style="padding-left:10px;"><label>手机方法：</label>
            </td>
            <td align="left">
                <div id="sandroidmethod" class="search-view"></div>
            </td>
        </tr>
        <tr>
            <td><label>显示顺序：</label></td>
            <td>
                <div id="iorder" class="search-view"></div>
            </td>
            <td style="padding-left:10px;"><label>项目有关：</label>
            </td>
            <td align="left">
                <div id="isupportproject" class="search-view"></div>
            </td>
        </tr>
        <tr id="tr_sbtnlocation">
            <td><label>显示位置：</label></td>
            <td>
                <div id="sbtnlocation" class="search-view"></div>
            </td>
            <td><label>绑定事件：</label></td>
            <td>
                <div id="sbindevent" class="search-view"></div>
            </td>
        </tr>
        <tr>
            <td><label>参与流程：</label></td>
            <td>
                <div id="ijoinprocess" class="search-view"></div>
            </td>
            <td><label>流程对应表：</label></td>
            <td>
                <div id="sjoinprocesstable" class="search-view"></div>
            </td>
        </tr>
        <tr>
            <td><label>备　　注：</label></td>
            <td colspan="3">
                <div id="sdesc" class="search-view" style="width: 480px;"></div>
            </td>
        </tr>
    </table>
</form>
<script type="text/javascript" src="${JS}/funcmgr/FuncViewHandle.js?${V}"></script>
</body>
</html>