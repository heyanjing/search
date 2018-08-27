<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/head.jsp" %>
<!doctype html>
<html>
<head>
    <title>申请详情</title>
    <meta name='description' content='申请详情'>
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
<div id="alert" style="left: 0;top: 0;margin-left: 0;width:100%; display: block; height:100%;">
    <div class="model-content">
        <div class="main">
            <div class="row">
                <div class="col-md-12">
                    <div class="flow">
                        <div class="flowListBox"></div>
                    </div>
                </div>
                <div class="mini-fit">
                    <div class="col-md-12" id="flowDiv">
                        <ol id="iList">
                            <div class="flow-item" data-title="送审申请" style="padding:0 15px 10px 15px;">
                                <table class="around_list" id="applys" style="margin:0 auto;border-collapse:collapse;">
                                    <tr>
                                        <td class="des-title">
                                            <label>项　　目：</label>
                                        </td>
                                        <td>
                                            <div id="sprojectlibidname" class="search-view"></div>
                                        </td>
                                        <td class="des-title">
                                            <label>标准模板类型：</label>
                                        </td>
                                        <td>
                                            <div id="itype" class="search-view"></div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="des-title">
                                            <label>描　　述：</label>
                                        </td>
                                        <td colspan="3">
                                            <div id="sdesc" class="search-view" style="width: 99%; height: 50px;"></div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="des-title">
                                            <label>送审造价：</label>
                                        </td>
                                        <td>
                                            <div id="icost" class="search-view"></div>
                                        </td>
                                        <td class="des-title">
                                            <label>质量等级：</label>
                                        </td>
                                        <td>
                                            <div id="squalitygrade" class="search-view"></div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="des-title">
                                            <label>开工时间：</label>
                                        </td>
                                        <td>
                                            <div id="ldstartdate" class="search-view"></div>
                                        </td>
                                        <td class="des-title">
                                            <label>竣工时间：</label>
                                        </td>
                                        <td>
                                            <div id="ldfinisheddate" class="search-view"></div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="des-title">
                                            <label>批准立项文件：</label>
                                        </td>
                                        <td>
                                            <div id="sprojectfile" class="search-view"></div>
                                        </td>
                                        <td class="des-title">
                                            <label>批准投资估算：</label>
                                        </td>
                                        <td>
                                            <div id="iestimate" class="search-view"></div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="des-title">
                                            <label>批准概算计划文件：</label>
                                        </td>
                                        <td>
                                            <div id="sbudgetfile" class="search-view"></div>
                                        </td>
                                        <td class="des-title">
                                            <label>批准投资概算：</label>
                                        </td>
                                        <td>
                                            <div id="ibudget" class="search-view"></div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="des-title">
                                            <label>批准预算评审文件：</label>
                                        </td>
                                        <td>
                                            <div id="splanfile" class="search-view"></div>
                                        </td>
                                        <td class="des-title">
                                            <label>批准投资预算：</label>
                                        </td>
                                        <td>
                                            <div id="iplan" class="search-view"></div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="des-title">
                                            <label>变更概算或预算文件：</label>
                                        </td>
                                        <td>
                                            <div id="schangefile" class="search-view"></div>
                                        </td>
                                        <td class="des-title">
                                            <label>批准变更金额：</label>
                                        </td>
                                        <td>
                                            <div id="ichange" class="search-view"></div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="des-title">
                                            <label>项目负责人及联系电话：</label>
                                        </td>
                                        <td>
                                            <div id="sleaderandphone" class="search-view"></div>
                                        </td>
                                        <td class="des-title">
                                            <label>联系人及联系电话：</label>
                                        </td>
                                        <td>
                                            <div id="slinkmanandphone" class="search-view"></div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="des-title">
                                            <label>申请单位意见：</label>
                                        </td>
                                        <td colspan="3">
                                            <div id="sopinion" class="search-view" style="width: 99%; height: 50px;"></div>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                            <div class="flow-item" data-title="送审标准" style="width:100%; height: 450px;">
                                <div id="treedatagrid" class="search-treedatagrid" idField="sid" parentField="sparentid" textField="sname"
                                 showCheckBox="false" multiSelect="false" alternatingRows="true" expandLevel="2" ondrawcell="treedatagriddrawcell"
                                 style="width:100%;height:100%;">
                                 	<div property="columns">
						            	<div field="sname" width="200" headAlign="center" textAlign="left">名称</div>
						            	<div field="itype" width="80" headAlign="center" textAlign="center">类型</div>
						            	<div field="imust" width="50" headAlign="center" textAlign="center">是否必填</div>
						            	<div field="ipaper" width="50" headAlign="center" textAlign="center">是否电子文档</div>
						            	<div field="oper" width="100" headAlign="center" textAlign="center">操作</div>
						            </div>
                                </div>
                            </div>
                        </ol>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${JS}/applys/ApplysViewHandle.js"></script>
</body>
</html>