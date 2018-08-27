<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/jspTaglib.jsp" %>
<!doctype html>
<html>
<head>
    <title>新增功能</title>
    <meta name='description' content='首页head中的内容'>
    <link rel="stylesheet" type="text/css" href="${CSS}/demo/demo.css?${V}"/>
    <link rel="stylesheet" type="text/css" href="${CSS}/upload/webuploader.css">
    <style>
        .around_list {
            text-align: right !important;
        }

        /* table tr td{padding-top:10px;} */
        .search-treeselect {
            float: left;
        }

        .search-select {
            float: left;
        }

        .search-textradio {
            float: left;
        }

        table tr td span a {
            display: inline-block;
            margin-top: 10px;
            padding-left: 3px;
        }

        table tr td span i {
            color: green;
            font-size: 14px !important;
        }
    </style>
    <script type="text/javascript">
        var __usertype = ${usertype};
    </script>
</head>

<body>
<div id="alert" class="" style="left: 0;top: 0;margin-left: 0;width:100%; height:100%;">
    <div class="model-content">
        <div class="main">
            <div class="row">
                <div class="col-md-12">
                    <div class="flow">
                        <div class="flowListBox"></div>
                    </div>
                </div>
                <div class="mini-fitbox">
                    <div class="col-md-12" id="flowDiv" style="height:100%;">
                        <ol id="iList" style="padding:0 15px 0 15px;height:100%;">
                            <div id="cont1">
                                <form id="form">
                                    <table class="around_list">
                                        <tr>
                                            <td>
                                                <div id="sid" class="search-texthide"></div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="padding-top:5px;"><label>名　　称：</label></td>
                                            <td style="padding-top:5px;">
                                                <div id="sname" class="search-textbox" rules="required" width="300" height="30"></div>
                                            </td>
                                            <td style="padding-top:5px;"><label>模版类型：</label></td>
                                            <td style="padding-top:5px;">
                                                <div id="itype" class="search-select" rules="required" idField="value" textField="text" width="300" height="30" placeHolder="请选择模版类型"></div>
                                                <span><a href="javascript:void(0)"><i class="fa fa-question-circle"></i></a></span>
                                            </td>
                                        </tr>
                                        <tr>
                                        	<td style="padding-top:5px;"><label>顺　　序：</label></td>
                                            <td style="padding-top:5px;">
                                                <div id="ishoworder" class="search-textbox" rules="number" width="300" height="30"></div>
                                            </td>
											<td id="sorgid1"><label>所属机构：</label></td>
								            <td id="sorgid2">
								                <div id="sorgid" class="search-treeselect" idField="sid" parentField="sparentid" textField="sname" width="300" height="30" placeHolder="请选择所属机构"></div>
								                <span><a href="javascript:void(0)"><i class="fa fa-question-circle"></i></a></span>
								            </td>
                                        </tr>
                                        <tr>
                                            <td style="padding-top:5px;"><label>备　　注：</label></td>
                                            <td colspan="3" style="padding-top:5px;">
                                                <div id="sdesc" class="search-textarea" width="680" style="float:left;"></div>
                                            </td>
                                        </tr>

                                    </table>
                                </form>
                            </div>
                            <div class="contentList" id="cont2" style="height:auto;">
	                            <div class="mini-tools" style="text-align: left; height:50px; line-height:50px;">
								    <div class="search-button blue" onclick="add();" text="新增" style="margin-left: 10px; width: 80px; height: 30px; line-height: 30px;">新增</div>
								</div>
							    <div id="treegrid" class="search-treedatagrid" idField="sid" parentField="sparentid" textField="sname" showCheckBox="false" multiSelect="false"
							         alternatingRows="true" expandLevel="4" style="width:100%;height:300px;" ondrawcell="ondrawcell">
							        <div property="columns">
							            <div type="checkcolumn"></div>
							            <div field="sname" width="200" headAlign="center" textAlign="left">名称</div>
							            <div field="itype" width="50" headAlign="center" textAlign="center">资料类型</div>
							            <div field="imust" width="100" headAlign="center" textAlign="center">是否必填</div>
							            <!-- <div field="sfiletplname" width="50" headAlign="center" textAlign="center">文件模版</div> -->
							            <div field="oper" width="100" headAlign="center" textAlign="center">操作</div>
							        </div>
							    </div>
                            </div>
                        </ol>
                    </div>
                </div>
            </div>
            <div class="foot-btn">
                <button type="button" class="btn btn-primary" id="btnBack">上一步</button>
                <button type="button" class="btn btn-default" style="display:none" id="btnok" onclick="saveData()">保存</button>
                <button type="button" class="btn btn-success" id="btnNext">下一步</button>
            </div>
        </div>
    </div>
</div>
<div id="mask"></div>

<script type="text/javascript" src="${JS}/audittpls/updateAuditTpls.js?${V}"></script>
</body>
</html>