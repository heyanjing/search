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
        table tr td {
            padding-top: 10px;
        }

        .des-title {
            border: none;
            padding-left: 10px;
        }
        .search-treeselect td {
            padding-top: 0;
        }

        #idCard img {
            width: 188px;
            height: 120px;
            margin-left: 10px;
            float: left;
            margin-top: 10px;
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
                                        	<td class="des-title"><label>名　　称：</label></td>
								            <td>
								                <div id="sname" class="search-view"></div>
								            </td>
                                            <td class="des-title"><label>模版类型：</label></td>
                                            <td>
                                                <div id="itype" class="search-view"></div>
                                            </td>
                                        </tr>
                                        <tr>
                                        	<td class="des-title"><label>顺　　序：</label></td>
                                            <td>
                                                <div id="ishoworder" class="search-view"></div>
                                            </td>
											<c:if test="${usertype==1}">
								            <td class="des-title"><label>所属机构：</label></td>
								            <td>
								                <div id="sorgname" class="search-view"></div>
								            </td>
								            </c:if>
                                        </tr>
                                        <tr>
                                            <td class="des-title"><label>备　　注：</label></td>
                                            <td colspan="3">
								                <div id="sdesc" class="search-view"></div>
								            </td>
                                        </tr>

                                    </table>
                                </form>
                            </div>
                            <div class="contentList" id="cont2" style="height:auto;">
							    <div id="treegrid" class="search-treedatagrid" idField="sid" parentField="sparentid" textField="sname" showCheckBox="false" multiSelect="false"
							         alternatingRows="true" expandLevel="4" style="width:100%;height:300px;" ondrawcell="ondrawcell">
							        <div property="columns">
							            <div type="checkcolumn"></div>
							            <div field="sname" width="200" headAlign="center" textAlign="left">名称</div>
							            <div field="itype" width="50" headAlign="center" textAlign="center">资料类型</div>
							            <div field="imust" width="100" headAlign="center" textAlign="center">是否必填</div>
							            <!-- <div field="sfiletplname" width="50" headAlign="center" textAlign="center">文件模版</div> -->
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

<script type="text/javascript" src="${JS}/audittpls/AuditTplsInfo.js?${V}"></script>
</body>
</html>