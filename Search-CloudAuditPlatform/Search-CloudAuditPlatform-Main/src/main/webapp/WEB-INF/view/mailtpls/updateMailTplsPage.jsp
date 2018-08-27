<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/jspTaglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>新增邮件模板</title>
    <link rel="stylesheet" type="text/css" href="${CSS}/demo/demo.css?${V}"/>
    <style>
        table tr td {
            padding-top: 10px;
        }
        #edui1{
        	width: 500px;
        }
    </style>
</head>
<body>
<form id="mailtpls" style="height:auto;">
    <div id="sid" class="search-texthide"></div>
    <table class="useradd_list" style="margin:0 auto;">
    	<tr>
    		<td ><label>标题：</label></td>
	        <td width="300">
	            <div id="stitle" name="stitle" class="search-textbox"
	                 rules="required" width="200" height="30"></div>
	        </td>
            <td colspan="3">
                <div id="itype" class="search-select" rules="required" idField="value" textField="text" width="300" height="30" defaultValue="0" onvaluechanged="onvalue"></div>
            </td>
        </tr>
        <tr>
        	<td></td>
        	<td></td>
            <td>
                <div id="username" name="username" enabled = "false" class="search-textbox" width="100" height="30"></div>
            </td>
            <td>
                <div id="url" name="url" enabled = "false" class="search-textbox" width="100" height="30"></div>
            </td>
            <td>
                <div id="sysname" name="sysname" enabled = "false" class="search-textbox" width="100" height="30"></div>
            </td>
        </tr>

    </table>
    <div style="width: 800px; margin: 0 auto; margin-top: 60px;">
                <!-- 加载编辑器的容器 -->
			    <script id="container" name="content" type="text/plain">
                </script>
            	
    </div>
    <div id="scontent" name="scontent" class="search-texthide"></div>
</form>
<div class="org-btn" colspan="2">
    <input class="org-save" type="button" onclick="save()" value="保存"/>
    <input class="org-close" type="button" onclick="window.closeWindow('cancel')" value="关闭"/>
</div>
<script type="text/javascript" language="javascript" src="${JS}/mailtpls/updateMailTplsPage.js?v=${RES_VER}"></script>
</body>
</html>