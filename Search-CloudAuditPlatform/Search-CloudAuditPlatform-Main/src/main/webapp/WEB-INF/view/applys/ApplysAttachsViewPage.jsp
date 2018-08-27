<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/head.jsp" %>
<!doctype html>
<html>
<head>
    <title>申请附件下载</title>
    <meta name='description' content='申请附件下载'>
    <link rel="stylesheet" type="text/css" href="${CSS}/demo/demo.css"/>
    <style>
        .around_list {
            text-align: right !important;
        }

        table tr td {
            padding-top: 10px;
            width: 80px;
        }

        .des-title {
            border: none;
        }

        .search-view {
            width: 280px;
            text-align: left;
            padding-left: 5px;
            border: 1px solid #ccc;
            height: 35px;
            overflow: auto;
            border-radius: 5px;
            line-height: 35px;
        }

        .search-treeselect td {
            padding-top: 0;
        }
    </style>
</head>
<body>
	    <div id="datagrid" class="search-datagrid" idField="sid" showCheckBox="true" multiSelect="true" alternatingRows="true"
	         showPager="true" pageSize="20" ondrawcell="datagriddrawcell" style="width:100%; height:100%;">
	        <div property="columns">
	            <div type="indexcolumn" width="30" headAlign="center" textAlign="center">序号</div>
	            <div field="snameattach" width="80" headAlign="center" textAlign="left">名称</div>
	            <div field="stypeattach" width="50" headAlign="center" textAlign="center">类型</div>
	            <div field="isizeattach" width="50" headAlign="center" textAlign="center">大小</div>
	            <div field="oper" width="100" headAlign="center" textAlign="center">操作</div>
	        </div>
	    </div>
	<script type="text/javascript" src="${JS}/applys/ApplysAttachsViewHandle.js"></script>
</body>
</html>