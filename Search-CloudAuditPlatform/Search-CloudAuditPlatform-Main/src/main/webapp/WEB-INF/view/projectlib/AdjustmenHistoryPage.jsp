<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/head.jsp" %>
<!doctype html>
<html>
<head>
    <title>项目库调整历史</title>
    <meta name='description' content='项目库调整历史'>
</head>
<body>
<div id="proLibParams" class="develop">
    <table>
        <tr>
            <td><label>关键字：</label></td>
            <td>
                <div id="keyword" class="search-textbox" width="300" height="25"></div>
            </td>
            <td><input class="org-search" type="button" onclick="queryHistory();" value="查询"/></td>
        </tr>
    </table>
</div>
<!-- 内容开始 -->
<div class="mini-fit">
    <div id="historyGrid" class="search-datagrid" idField="sid" showCheckBox="true" multiSelect="true" alternatingRows="true"
         showPager="true" pageSize="20" style="width:100%;height:100%;">
        <div property="columns">
            <!-- <div type="checkcolumn" width="30" headAlign="center" textAlign="center"></div> -->
            <div type="indexcolumn" width="30" headAlign="center" textAlign="center">序号</div>
            <div field="ldtcreatetime" width="80" headAlign="center" textAlign="center" allowSort="true">时间</div>
            <div field="sname" width="80" headAlign="center" textAlign="center">用户</div>
            <div field="scontent" width="300" headAlign="center" textAlign="left">内容</div>
        </div>
    </div>
</div>
<!-- 内容结束 -->
<script type="text/javascript" src="${JS}/projectlib/AdjustmenHistoryHandle.js?${V}"></script>
</body>
</html>