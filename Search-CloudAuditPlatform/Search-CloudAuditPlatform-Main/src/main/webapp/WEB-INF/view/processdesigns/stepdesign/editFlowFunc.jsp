<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/jspTaglib.jsp" %>
<!doctype html>
<html>
<head>
    <title>选择功能</title>
    <meta name='description' content='首页head中的内容'>
    <link rel="stylesheet" type="text/css" href="${CSS}/demo/demo.css?${V}"/>
    <link rel="stylesheet" type="text/css" href="${CSS}/upload/webuploader.css">
</head>

<body>
<form id="form">
    <table class="around_list">
    	<tr>
    		<td><lable>对应功能:</lable></td>
    		<td>
    			<div id="sfunctionid" class="search-select" idField="sid" textField="sname" defaultValue="-1" width="300" height="30" onvalidate="sfunctionidValidate"></div>
    		</td>
    	</tr>
        <tr>
            <td colspan="2" style="text-align:center; padding-top:5px;">
                <input type="button" onclick="saveData()" value="保存"/>
                <input type="button" onclick="windowClose()" value="关闭"/>
            </td>
        </tr>
    </table>
</form>

<script type="text/javascript" src="${JS}/processdesigns/stepdesign/editFlowFunc.js?${V}"></script>
</body>
</html>