<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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

        .sid-btn {
            position: absolute;
            bottom: 0;
            text-align: center;
            margin: 0 auto;
            width: 100%;
            line-height: 58px;
        }
    </style>
    <script type="text/javascript">
        var orgid = "${orgid}";
        var type = "${type}";
        var orgdepartment = "${orgdepartment}";
        var funref = "${funref}";
    </script>
</head>
<body>

<div id="authorizationform" style="left: 0;top: 0;margin-left: 0;width:100%; height:100%;">
    <div id="sid" class="search-texthide"></div>
    <table class="useradd_list" style="text-align: right; margin-left: 100px;" id="org">
        <tr>
            <td><label>功能来源：</label></td>
            <td>
                <div id="orgs" class="search-treeselect" idField="id" parentField="pid" textField="text" width="300" height="30" onvaluechanged="onvalue" loaded="rloaded"></div>
            </td>
        </tr>
    </table>

    <div class="mini-fitbox" style="position: absolute;top: 0px">
        <div id="datagrid" class="search-datagrid" idField="sid" showCheckBox="false" multiSelect="true" alternatingRows="true"
             showPager="false" ondrawcell="datagriddrawcell" style="width:100%;height:100%;">
            <div property="columns" id="di">
                <div field="sname" name="sname" width="50" headAlign="center" textAlign="center" allowSort="true">用户名称</div>
                <c:forEach var="fun" items="${names}">
                    <div field="fid_${fun.sid }" width="150" headAlign="center" textAlign="center">${fun.sname }</div>
                </c:forEach>
            </div>
        </div>
    </div>
    <div class="sid-btn">
        <input class="org-save" type="button" onclick="save()" value="保存"/>
        <input class="org-close" type="button" onclick="window.closeWindow('cancel')" value="关闭"/>
    </div>
</div>
<script type="text/javascript" language="javascript" src="${JS}/user/authorizationUser.js?v=${RES_VER}"></script>
</body>
</html>