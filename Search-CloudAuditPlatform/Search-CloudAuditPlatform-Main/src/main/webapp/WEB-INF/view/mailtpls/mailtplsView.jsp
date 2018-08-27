<%@page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="/WEB-INF/layout/taglib/head.jsp" %>
<!doctype html>
<html>
<head>
    <title>查看字典</title>
    <meta name='description' content='查看字典'>
    <link rel="stylesheet" type="text/css" href="${CSS}/demo/demo.css"/>
    <style>
        .around_list {
            text-align: right !important;
        }

        table tr td {
            padding-top: 10px;
        }

        .search-treeselect td {
            padding-top: 0;
        }

        .des-title {
            border: none;
        }
		p{
			text-align: left;
		}

    </style>
    
</head>
<body>
<form id="Dictionaries" style="overflow:auto;height:auto;">
    <div id="sid" class="search-texthide"></div>
    <table class="useradd_list" style="margin:0 auto; padding-top:10px;">
        <tr>
            <td class="des-title"><label>标题：</label></td>
            <td width="300" align="left">
                <div class="search-view">${mailtpls.stitle}</div>
            </td>
            <td colspan="3">
            	<div class="search-view">
	                <c:if test="${mailtpls.itype == 1 }">
	                    		绑定邮箱101
	                </c:if>
                    <c:if test="${mailtpls.itype == 2 }">
                        	通过邮箱修改手机号102
                    </c:if>
                </div>
            </td>
        </tr>
        <tr>
        	<td></td>
        	<td></td>
            <td width="100">
                <div id="username" class="search-view"></div>
            </td>
            <td width="100">
                <div id="url" class="search-view" ></div>
            </td>
            <td width="100">
                <div id="sysname" class="search-view"></div>
            </td>
        </tr>
    </table>
    <div style="width: 800px;height:400px; margin: 0 auto; margin-top: 60px; border: 1px solid #ccc; padding-left: 0px;">
    		${mailtpls.scontent }
    </div>
</form>
<script type="text/javascript">
    $(function(){
    	var type = "${mailtpls.itype}";
    	if(type == 1){
			username = "test_101";
			url = "baidu.com_101";
			sysname = "systest_101";
		}else if(type == 2){
			username = "test_102";
			url = "baidu.com_102";
			sysname = "systest_102";
		}
    	$("#username").html(username);
    	$("#url").html(url);
    	$("#sysname").html(sysname);
    });
    </script>
</body>
</html>