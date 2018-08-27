<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/head.jsp" %>
<!doctype html>
<html>
<head>
    <title>机构审核</title>
    <meta name='description' content='机构审核'>
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
                        <ol id="iList" style="padding:0 15px 0 15px;">
                            <div class="flow-item" data-title="审核" data-viewWillLeave="auditWillLeave">
                                <form id="intermediarys">
                                    <table class="around_list">
                                        <tr>
                                            <td>
                                                <label>描述：</label>
                                            </td>
                                            <td>
                                                <div id="sdesc" class="search-textarea" width="650" height="300"></div>
                                            </td>
                                        </tr>
                                    </table>
                                </form>
                            </div>
                            <div class="flow-item contentList" data-title="授权" data-viewDidEnter="reDrawDatagrid">
                                <div id="datagrid" class="search-datagrid" idField="sid" showCheckBox="false" multiSelect="true" alternatingRows="true"
                                     showPager="false" ondrawcell="datagriddrawcell" style="width:100%;height:100%;">
                                </div>
                            </div>
                        </ol>
                    </div>
                </div>
            </div>
            <div class="foot-btn">
                <button type="button" class="btn btn-default" id="btnok" onclick="saveData()">保存</button>
                <button type="button" class="btn btn-success" style="" id="btnNext">通过</button>
                <button type="button" class="btn btn-success" style="" id="reject">驳回</button>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${JS}/intermediarys/orgAuditHandle.js"></script>
</body>
</html>