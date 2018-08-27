<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/head.jsp" %>
<!doctype html>
<html>
<head>
    <title>审批意见</title>
    <meta name='description' content='审批意见'>
    <link rel="stylesheet" type="text/css" href="${CSS}/demo/demo.css"/>
    <style>
        .around_list {
            text-align: right !important;
        }

        table tr td {
            padding-top: 10px;
            width: 80px;
        }

        .search-treeselect td {
            padding-top: 0;
        }
    </style>
</head>
<body>
	<form id="apply">
	    <table class="around_list" style=" margin:0 auto;">
	        <tr>
	            <td style="padding-left:10px;"><label>是否通过：</label></td>
	            <td>
	                <div id="ipass" class="search-textradio" rules="required" idField="value" textField="text" width="200" height="30"></div>
	            </td>
	        </tr>
	        <tr>
	            <td style="padding-left:10px;"><label>审批意见：</label></td>
	            <td>
	                <div id="sopioiongb" class="search-textarea" width="200" height="80"></div>
	            </td>
	        </tr>
	    </table>
	</form>
	<div class="org-btn" colspan="4">
	    <input class="org-save" type="button" onclick="saveData()" value="保存"/>
	    <input class="org-close" type="button" onclick="window.closeWindow('cancel');" value="关闭"/>
	</div>
	<script type="text/javascript" src="${JS}/applys/AuditTplDetailAuditHandle.js"></script>
</body>
</html>