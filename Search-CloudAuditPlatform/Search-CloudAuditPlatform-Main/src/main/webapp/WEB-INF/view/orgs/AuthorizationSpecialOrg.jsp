<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/jspTaglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>用户授权</title>
    <style>
        .table {
            border-collapse: collapse;
        }

        .table td {
            border: 1px solid #000;
        }
    </style>
</head>
<body>

<form id=authorizationform>
    <div class="fixedTb" style="position:relative;">
        <div class="flow-item contentList" data-title="授权功能" style="height:450px;">
            <div id="treedatagrid" class="search-treedatagrid" idField="id" textField="sname" ondrawcell="drawcell"
                 multiSelect="true" selectedRows="false" alternatingRows="true" expandLevel="0" style="width: 100%; height: 100%;">
                <div property="columns">
                    <div field="sname" width="200" headAlign="left">机构名称</div>
                    <c:forEach var="cls" items="${headlist}" varStatus="status">
                        <div field="${cls.head}" width="80" headAlign="center" textAlign="center">${cls.sname}</div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>


    <div>
        <input class="org-save" type="button" onclick="save()" value="保存"/>
        <input class="org-close" type="button" onclick="window.closeWindow('cancel')" value="关闭"/>
    </div>
</form>


<script type="text/javascript" language="javascript" src="${JS}/orgs/AuthorizationSpecialOrg.js?v=${RES_VER}"></script>
</body>
</html>