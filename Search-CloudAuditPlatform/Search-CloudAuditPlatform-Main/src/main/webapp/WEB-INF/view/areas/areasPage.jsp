<
<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/jspTaglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>区域页面</title>
    <script type="text/javascript">
        var __state = '${param.recycle}' == 'recycle' ? 2 : 1;
    </script>
</head>
<body>
<div class="mini-tools" style="text-align: left; height:50px; line-height:50px;">
    <jsp:include page="/WEB-INF/view/menu/MenuButtonMgr.jsp"></jsp:include>
</div>

<!-- 展开查询开始 -->
<div id="dicParams" class="develop" style="display:none;">
    <table>
        <tr>
            <td><label>区域：</label></td>
            <td>
                <div id="areas" class="search-treeselect" idField="sid" parentField="sparentid" textField="sname" width="300" height="30" defaultValue="0" placeHolder="请选择"></div>
            </td>

            <td><label>关键字：</label></td>
            <td>
                <div id="keyword" name="keyword" class="search-textbox" width="200" height="30"></div>
            </td>

            <td><input class="org-search" type="button" onclick="searchGrid();" value="查询"/></td>
        </tr>
    </table>
</div>

<div class="mini-fit">
    <div id="areasgrid" class="search-treedatagrid" idField="sid" parentField="sparentid" textField="sname" showCheckBox="false" multiSelect="false"
         alternatingRows="true" expandLevel="4" ondrawcell="datagriddrawcell"
         style="width:100%;height:100%;">
        <jsp:include page="/WEB-INF/view/menu/RightButtonMgr.jsp"></jsp:include>
        <div property="columns">
            <div type="checkcolumn"></div>
            <div field="sname" width="100" headAlign="center" allowSort="true">名称</div>
            <div field="sdes" width="300" headAlign="center" textAlign="right">描述</div>
            <c:choose>
                <c:when test="${param.recycle != null}">
                    <div field="action" width="180" headAlign="center" textAlign="center">操作</div>
                </c:when>
                <c:otherwise>
                    <div field="action" width="${button.width +60}" headAlign="center" textAlign="center">操作</div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
<script type="text/javascript" language="javascript" src="${JS}/areas/areasPage.js?v=${RES_VER}"></script>
</body>
</html>