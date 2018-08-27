<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/head.jsp" %>
<!doctype html>
<html>
<head>
    <title>编辑功能组</title>
    <meta name='description' content='编辑功能组'>
    <link rel="stylesheet" type="text/css" href="${CSS}/demo/demo.css"/>
    <style type="text/css">
        table tr td {
            padding-top: 10px;
        }

        .search-treeselect td {
            padding-top: 0;
        }
    </style>
    <script type="text/javascript">
        var __usertype = ${usertype};
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
                    <div class="col-md-12" id="flowDiv" style="height:100%;">
                        <ol id="iList" style="height:100%;">
                            <div class="flow-item" data-title="功能组" data-viewWillLeave="functiongroupvalidate">
                                <form id="functiongroup">
                                    <div id="sid" class="search-texthide"></div>
                                    <table class="around_list" style=" margin:0 auto;">
                                        <tr>
                                            <td>
                                                <label>名　　称：</label>
                                            </td>
                                            <td>
                                                <div id="sname" class="search-textbox" rules="required" width="300" height="30"></div>
                                            </td>
                                            <td>
                                                <label>来源机构：</label>
                                            </td>
                                            <td>
                                                <div id="sfromorgid" class="search-treeselect" rules="required" idField="id" parentField="pid" textField="text" onvaluechanged="sfromorgidchange" width="300" height="30"></div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <label>备　　注：</label>
                                            </td>
                                            <td colspan="3">
                                                <div id="sdesc" class="search-textarea" width="670"></div>
                                            </td>
                                        </tr>
                                    </table>
                                </form>
                            </div>
                            <div class="flow-item contentList" data-title="授权功能" style="height:100%;">
                                <div id="treedatagrid" class="search-treedatagrid" idField="id" parentField="pid" textField="text" showCheckBox="true"
                                     multiSelect="true" selectedRows="false" alternatingRows="true" expandLevel="0" style="width: 100%; height: 100%;">
                                    <div property="columns">
                                        <div field="text" width="200" headAlign="left">功能名称</div>
                                    </div>
                                </div>
                            </div>
                        </ol>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="foot-btn">
        <button type="button" class="btn btn-primary disabled" id="btnBack" style="display: none;">上一步</button>
        <button type="button" class="btn btn-default" onclick="saveData()" style="float: right; margin-right: 10px; display: none;" id="btnok">完成</button>
        <button type="button" class="btn btn-success" style="float: right; margin-right: 10px;" id="btnNext">下一步</button>
    </div>
</div>
<script type="text/javascript" src="${JS}/functiongroups/functiongroupsedit.js"></script>
</body>
</html>