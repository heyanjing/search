<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/head.jsp" %>
<!doctype html>
<html>
<head>
    <title>机构详情</title>
    <meta name='description' content='机构详情'>
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

        #business img {
            width: 188px;
            height: 120px;
            margin-left: 10px;
            float: left;
            margin-top: 10px;
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
                    <div class="col-md-12" id="flowDiv" style="height:100%;">
                        <ol id="iList" style="height:100%;">
                            <div class="flow-item" data-title="用户信息" style="padding-top:10px;">
                                <table class="around_list" style="margin:0 auto;border-collapse:collapse;">
                                    <tr>
                                        <td class="des-title">
                                            <label>机构名称：</label>
                                        </td>
                                        <td>
                                            <div id="sname" class="search-view"></div>
                                        </td>
                                        <td class="des-title">
                                            <label>父级机构：</label>
                                        </td>
                                        <td>
                                            <div id="sparentname" class="search-view"></div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="des-title">
                                            <label>机构类型：</label>
                                        </td>
                                        <td>
                                            <div id="itype" class="search-view"></div>
                                        </td>
                                        <td class="des-title">
                                            <label>联系人：</label>
                                        </td>
                                        <td>
                                            <div id="username" class="search-view"></div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="des-title">
                                            <label>手机号码：</label>
                                        </td>
                                        <td>
                                            <div id="userphone" class="search-view"></div>
                                        </td>
                                        <td class="des-title">
                                            <label>描述：</label>
                                        </td>
                                        <td>
                                            <div id="sdes" class="search-view"></div>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                            <div class="flow-item contentList" data-title="营业执照">
                                <div id="business" style="width:100%; height:100%; margin:0 auto;"></div>
                            </div>
                            <div class="flow-item contentList" data-title="机构资质" style="height:100%;" data-viewDidEnter="reDrawDatagrid">
                                <div id="datagrid" class="search-datagrid" idField="sid" showCheckBox="false" multiSelect="false" alternatingRows="true"
                                     showPager="false" ondrawcell="datagriddrawcell" style="width:100%;height:100%; overflow:auto;">
                                    <div property="columns">
                                        <div type="indexcolumn" width="30" headAlign="center" textAlign="center">序号</div>
                                        <div field="sdictionariename" width="50" headAlign="center" textAlign="center">名称</div>
                                        <div field="sdesc" width="150" headAlign="center" textAlign="left">备注</div>
                                        <div field="oper" width="50" headAlign="center" textAlign="center">操作</div>
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
<script type="text/javascript" src="${JS}/intermediarys/orgAuditView.js"></script>
</body>
</html>