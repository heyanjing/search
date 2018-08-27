<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/head.jsp" %>
<!doctype html>
<html>
<head>
    <title>申请</title>
    <meta name='description' content='申请'>
    <script type="text/javascript">
    	var __functionid = '${param.id}';
    	var __typefield = '${typefield}';
    </script>
</head>
<body>
<!-- 功能按钮开始 -->
<div class="mini-tools" style="text-align: left; height:50px; line-height:50px;">
    <jsp:include page="/WEB-INF/view/menu/MenuButtonMgr.jsp"></jsp:include>
</div>
<!-- 功能按钮结束 -->

<!-- 展开查询开始 -->
<div id="applyParams" class="develop" style="display:none;">
    <table>
        <tr>
            <td><label>关键字：</label></td>
            <td>
                <div id="keyword" class="search-textbox" width="200" height="25"></div>
            </td>

            <td><input class="org-search" type="button" onclick="query();" value="查询"/></td>
        </tr>
    </table>
</div>
<!-- 展开查询结束 -->

<!-- 内容开始 -->
<div class="mini-fit">
    <div id="datagrid" class="search-datagrid" idField="sid" showCheckBox="true" multiSelect="true" alternatingRows="true"
         showPager="true" pageSize="20" ondrawcell="datagriddrawcell" style="width:100%;height:100%;">
        <jsp:include page="/WEB-INF/view/menu/RightButtonMgr.jsp"></jsp:include>
        <div property="columns">
            <div type="checkcolumn" width="30" headAlign="center" textAlign="center"></div>
            <div type="indexcolumn" width="30" headAlign="center" textAlign="center">序号</div>
            <div field="projectname" width="150" headAlign="center" textAlign="left">项目名称</div>
            <div field="itype" width="80" headAlign="center" textAlign="left">送审标准模板类型</div>
            <div field="icost" width="60" headAlign="center" textAlign="right">送审造价</div>
            <div field="squalitygrade" width="60" headAlign="center" textAlign="left">质量等级</div>
            <div field="ldstartdate" width="60" headAlign="center" textAlign="center">开工时间</div>
            <div field="ldfinisheddate" width="60" headAlign="center" textAlign="center">竣工时间</div>
            <div field="sprojectfile" width="60" headAlign="center" textAlign="left">批准立项文件</div>
            <div field="iestimate" width="60" headAlign="center" textAlign="right">批准投资估算</div>
            <div field="sbudgetfile" width="60" headAlign="center" textAlign="left">批准概算计划文件</div>
            <div field="ibudget" width="60" headAlign="center" textAlign="right">批准投资概算</div>
            <div field="splanfile" width="60" headAlign="center" textAlign="left">批准预算评审文件</div>
            <div field="iplan" width="60" headAlign="center" textAlign="right">批准投资预算</div>
            <div field="schangefile" width="60" headAlign="center" textAlign="left">变更概算或预算文件</div>
            <div field="ichange" width="60" headAlign="center" textAlign="right">批准变更金额</div>
            <div field="sleaderandphone" width="60" headAlign="center" textAlign="left">项目负责人及联系电话</div>
            <div field="slinkmanandphone" width="60" headAlign="center" textAlign="left">联系人及联系电话</div>
            <c:if test="${button.width > 0}">
            <div field="oper" width="${button.width}" headAlign="center" textAlign="center">操作</div>
            </c:if>
        </div>
    </div>
</div>
<!-- 内容结束 -->
<script type="text/javascript" src="${JS}/applys/ApplysHandle.js"></script>
</body>
</html>