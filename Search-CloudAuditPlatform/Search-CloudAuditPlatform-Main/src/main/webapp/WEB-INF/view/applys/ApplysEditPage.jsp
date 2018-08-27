<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/head.jsp" %>
<!doctype html>
<html>
<head>
    <title>申请编辑</title>
    <meta name='description' content='申请编辑'>
    <link rel="stylesheet" type="text/css" href="${CSS}/demo/demo.css"/>
    <link rel="stylesheet" type="text/css" href="${CSS}/upload/webuploader.css">
    <style>
        .around_list {
            text-align: right !important;
        }

        table tr td span a {
            display: inline-block;
            margin-top: 10px;
            padding-left: 1px;
        }

        table tr td span i {
            color: green;
            font-size: 14px !important;
        }

        table tr td {
            padding-top: 10px;
        }

        .search-treeselect td {
            padding-top: 0;
        }

        .search-select {
            float: left;
        }
    </style>
    <script type="text/javascript">
    	var __usertype = '${usertype}';
    </script>
</head>
<body>
<div id="alert" style="left: 0;top: 0;margin-left: 0;width:100%; display: block; height:100%;">
    <div class="model-content">
        <div class="main">
            <div class="row">
                <div class="col-md-12">
                    <div class="flow">
                        <div class="flowListBox"></div>
                    </div>
                </div>
                <div class="mini-fitbox">
                    <div class="col-md-12" id="flowDiv">
                        <ol id="iList">
                            <div class="flow-item" data-title="送审申请" style="padding:0 15px">
                                <form id="applys">
                                    <table class="around_list">
										<tr>
											<td>
                                                <label>项　　目：</label>
                                            </td>
                                            <td>
                                                <div id="sprojectlibid" class="search-select" idField="id" textField="text" rules="required" onloaded="sprojectlibidloaded" width="300" height="30"></div>
                                            </td>
                                            <td>
                                                <label>标准模板类型：</label>
                                            </td>
                                            <td>
                                                <div id="itype" class="search-select" idField="id" textField="text" defaultValue="101" rules="required" width="300" height="30"></div>
                                            </td>
										</tr>
										<tr>
											<td>
                                                <label>描　　述：</label>
                                            </td>
                                            <td colspan="3">
                                                <div id="sdesc" class="search-textarea" width="710" height="60"></div>
                                            </td>
										</tr>
										<tr>
											<td>
                                                <label>送审造价：</label>
                                            </td>
                                            <td>
                                                <div id="icost" class="search-textbox" rules="required,number" width="300" height="30"></div>
                                            </td>
                                            <td>
                                                <label>质量等级：</label>
                                            </td>
                                            <td>
                                                <div id="squalitygrade" class="search-textbox" rules="required" width="300" height="30"></div>
                                            </td>
										</tr>
										<tr>
											<td>
                                                <label>开工时间：</label>
                                            </td>
                                            <td>
                                                <div id="ldstartdate" class="search-datepicker" width="300" height="30"></div>
                                            </td>
                                            <td>
                                                <label>竣工时间：</label>
                                            </td>
                                            <td>
                                                <div id="ldfinisheddate" class="search-datepicker" width="300" height="30"></div>
                                            </td>
										</tr>
										<tr>
											<td>
                                                <label>批准立项文件：</label>
                                            </td>
                                            <td>
                                                <div id="sprojectfile" class="search-textbox" rules="required" width="300" height="30"></div>
                                            </td>
                                            <td>
                                                <label>批准投资估算：</label>
                                            </td>
                                            <td>
                                                <div id="iestimate" class="search-textbox" rules="required,number" width="300" height="30"></div>
                                            </td>
										</tr>
										<tr>
											<td>
                                                <label>批准概算计划文件：</label>
                                            </td>
                                            <td>
                                                <div id="sbudgetfile" class="search-textbox" rules="required" width="300" height="30"></div>
                                            </td>
                                            <td>
                                                <label>批准投资概算：</label>
                                            </td>
                                            <td>
                                                <div id="ibudget" class="search-textbox" rules="required,number" width="300" height="30"></div>
                                            </td>
										</tr>
										<tr>
											<td>
                                                <label>批准预算评审文件：</label>
                                            </td>
                                            <td>
                                                <div id="splanfile" class="search-textbox" rules="required" width="300" height="30"></div>
                                            </td>
                                            <td>
                                                <label>批准投资预算：</label>
                                            </td>
                                            <td>
                                                <div id="iplan" class="search-textbox" rules="required,number" width="300" height="30"></div>
                                            </td>
										</tr>
										<tr>
											<td>
                                                <label>变更概算或预算文件：</label>
                                            </td>
                                            <td>
                                                <div id="schangefile" class="search-textbox" rules="required" width="300" height="30"></div>
                                            </td>
                                            <td>
                                                <label>批准变更金额：</label>
                                            </td>
                                            <td>
                                                <div id="ichange" class="search-textbox" rules="required,money" width="300" height="30"></div>
                                            </td>
										</tr>
										<tr>
											<td>
                                                <label>项目负责人及联系电话：</label>
                                            </td>
                                            <td>
                                                <div id="sleaderandphone" class="search-textbox" rules="required,phonenumber" width="300" height="30"></div>
                                            </td>
                                            <td>
                                                <label>联系人及联系电话：</label>
                                            </td>
                                            <td>
                                                <div id="slinkmanandphone" class="search-textbox" rules="required,phonenumber" width="300" height="30"></div>
                                            </td>
										</tr>
										<tr>
											<td>
                                                <label>申请单位意见：</label>
                                            </td>
                                            <td colspan="3">
                                                <div id="sopinion" class="search-textarea" width="710" height="60"></div>
                                            </td>
										</tr>
                                    </table>
                                </form>
                            </div>
                            <div class="flow-item" data-title="送审标准" data-viewWillEnter="auditTplDetailWillEnter" style="width:100%;height:400px;">
                                <div id="treedatagrid" class="search-treedatagrid" idField="sid" parentField="sparentid" textField="sname"
                                 showCheckBox="false" multiSelect="false" alternatingRows="true" expandLevel="2" ondrawcell="treedatagriddrawcell"
                                 style="width:100%;height:100%;" onloaded="treedatagridloaded">
                                 	<div property="columns">
						            	<div field="sname" width="200" headAlign="center" textAlign="left">名称</div>
						            	<div field="itype" width="80" headAlign="center" textAlign="center">类型</div>
						            	<div field="imust" width="50" headAlign="center" textAlign="center">是否必填</div>
						            	<div field="ipaper" width="50" headAlign="center" textAlign="center">是否电子文档</div>
						            	<div field="oper" width="100" headAlign="center" textAlign="center">操作</div>
						            </div>
                                </div>
                            </div>
                            <div class="flow-item" data-title="审核意见" style="padding: 0 15px; height: 400px;" data-viewWillEnter="opinionWillEnter">
                                  <jsp:include page="/WEB-INF/view/processsteps/ProcessSteps.jsp"></jsp:include> 
                            </div>
                        </ol>
                    </div>
                </div>
            </div>
            <div class="foot-btn">
                <button type="button" class="btn btn-primary" style="display:none" id="btnBack">上一步</button>
                <button type="button" class="btn btn-default" id="btnok" onclick="saveData(false)">保存</button>
                <button type="button" class="btn btn-default" id="btnokAndSubmit" onclick="saveData(true)">保存并提交</button>
                <button type="button" class="btn btn-default" style="display:none" id="auditBack" onclick="auditBack()">回退</button>
                <button type="button" class="btn btn-success" style="" id="btnNext">下一步</button>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${JS}/applys/ApplysEditHandle.js"></script>
</body>
</html>