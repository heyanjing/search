<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/jspTaglib.jsp" %>
<!doctype html>
<html>
<head>
    <title>功能页面</title>
</head>
<body>
<div class="mini-tools" style="text-align: left; height:50px; line-height:50px;">
    <jsp:include page="/WEB-INF/view/menu/MenuButtonMgr.jsp"></jsp:include>
</div>
<div id="funcParams" class="develop" style="display:none;">
    <table>
        <tr>
            <td><label>类　　型：</label></td>
            <td>
                <div id="itype" class="search-select" width="150" height="25" idField="value" textField="text" placeHolder="全部"></div>
            </td>
            <td><label>关键字：</label></td>
            <td>
                <div id="keyword" name="keyword" class="search-textbox" width="200" height="25"></div>
            </td>
            <!-- <td><label>功能名称：</label></td>
            <td><div id="sname" class="search-textbox" width="150" height="25"></div></td>
            <td><label>手机端支持：</label></td>
            <td><div id="isupportphone" class="search-select" width="150" height="25" idField="value"  textField="text" placeHolder="全部"></div></td>
            <td><label>是否项目相关：</label></td>
            <td><div id="isupportproject" class="search-select" width="150" height="25" idField="value"  textField="text" placeHolder="全部"></div></td> -->
            <td><input class="org-search" type="button" onclick="onFuncSearch();" value="查询"/></td>
        </tr>
    </table>
</div>
<div class="mini-fit">
    <div id="funcGrid" class="search-treedatagrid" idField="sid" parentField="sparentid" textField="sname" showCheckBox="false" multiSelect="false"
         alternatingRows="true" expandLevel="4" style="width:100%;height:100%;" ondrawcell="ondrawcell">
        <jsp:include page="/WEB-INF/view/menu/RightButtonMgr.jsp"></jsp:include>
        <div property="columns">
            <div type="checkcolumn"></div>
            <div field="sname" width="200" headAlign="center" textAlign="left">名称</div>
            <div field="itype" width="50" headAlign="center" textAlign="center">类型</div>
            <div field="spcmethod" width="80" headAlign="center" textAlign="left">调用方法</div>
            <div field="sicon" width="80" headAlign="center" textAlign="center">图标</div>
            <div field="isupportphone" width="50" headAlign="center" textAlign="center">移动端支持</div>
            <div field="sandroidmethod" width="100" headAlign="center" textAlign="left">移动端调</br>用方法</div>
            <div field="isupportproject" width="50" headAlign="center" textAlign="center">是否与</br>项目有关</div>
            <div field="sdesc" width="100" headAlign="center" textAlign="center">备注</div>
            <div field="iorder" width="50" headAlign="center" textAlign="center">显示顺序</div>
            <!-- <div field="istate" width="50" headAlign="center" textAlign="center" >状态</div> -->
            <c:choose>
                <c:when test="${param.recycle != null}">
                    <div field="operation" width="100" headAlign="center" textAlign="center">操作</div>
                </c:when>
                <c:when test="${button.width > 0}">
                    <div field="operation" width="${button.width}" headAlign="center" textAlign="center">操作</div>
                </c:when>
            </c:choose>
        </div>
    </div>
</div>
<script type="text/javascript" src="${JS}/funcmgr/FuncMgrHandle.js?${V}"></script>
</body>
</html>