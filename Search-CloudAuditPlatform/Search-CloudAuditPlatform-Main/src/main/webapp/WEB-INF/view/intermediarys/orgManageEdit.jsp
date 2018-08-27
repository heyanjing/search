<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/head.jsp" %>
<!doctype html>
<html>
<head>
    <title>编辑机构</title>
    <meta name='description' content='编辑机构'>
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
        var _orgtype = "${currentUser.orgtype}";
        var _usertype = "${currentUser.usertype}";
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
                        <ol id="iList" style="padding:0 15px 0 15px;">
                            <div id="baseinfo" class="flow-item" data-title="基本信息">
                                <form id="orgs">
                                    <div id="sid" class="search-texthide"></div>
                                    <div id="userid" class="search-texthide"></div>
                                    <table class="around_list">
                                        <tr>
                                            <td>
                                                <label>名　　称：</label>
                                            </td>
                                            <td>
                                                <div id="sname" class="search-textbox" rules="required" onblur="snameblur" width="300" height="30"></div>
                                            </td>
                                            <td>
                                                <label>父　　级：</label>
                                            </td>
                                            <td>
                                                <div id="sparentid" class="search-treeselect" idField="id" parentField="parentid" textField="text" defaultValue="-1" width="300" height="30"></div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <label>机构类型：</label>
                                            </td>
                                            <td>
                                                <div id="itype" class="search-select" rules="required" idField="value" textField="text" multiSelect="true" width="300" height="30"></div>
                                            </td>
                                            <td>
                                                <label>审计机构：</label>
                                            </td>
                                            <td>
                                                <div id="sauditorgid" class="search-treeselect" rules="required" idField="id" parentField="parentid" textField="text" width="300" height="30"></div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <label>创建管理员：</label>
                                            </td>
                                            <td>
                                                <div id="createadmin" class="search-textradio" rules="required" idField="value" textField="text" defaultValue="2" onvaluechanged="createadminchange" width="300" height="30"></div>
                                            </td>
                                        </tr>
                                        <tr id="adminperson" style="display: none;">
                                            <td>
                                                <label>联系人：</label>
                                            </td>
                                            <td>
                                                <div id="username" class="search-textbox" width="300" height="30"></div>
                                            </td>
                                            <td>
                                                <label>手机号：</label>
                                            </td>
                                            <td>
                                                <div id="userphone" class="search-textbox" width="300" height="30"></div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td><label>描　　述：</label></td>
                                            <td colspan="3">
                                                <div id="sdes" class="search-textarea" width="680" style="display:inline-block; float:left;"></div>
                                            </td>
                                        </tr>
                                    </table>
                                </form>
                            </div>
                            <div id="license" class="flow-item contentList" data-title="营业执照" data-viewDidEnter="licensedidenter">
                                <div id="uploader" class="cl-uploader">
                                    <div class="queueList">
                                        <!-- 原始拖拽div -->
                                        <div id="dndArea" class="placeholder" style="min-height: 120px;">
                                            <div id="filePicker" class="cl-picker"></div>
                                            <p>或将照片拖到这里，单次最多可选300张</p>
                                        </div>
                                    </div>
                                    <!-- 状态div -->
                                    <div class="statusBar" style="display: none;">
                                        <div class="progress">
                                            <span class="text">0%</span> <span class="percentage"></span>
                                        </div>
                                        <div class="info"></div>
                                        <div class="btns">
                                            <div id="filePicker2" class="cl-picker2"></div>
                                            <!-- <div class="uploadBtn">开始上传</div> -->
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div id="aptitudes" class="flow-item contentList" data-title="机构资质" data-viewDidEnter="aptitudesdidenter">
                                <table border="1" style="width:100%; height:100px; border-collapse:collapse; margin-top:10px;">
                                    <tbody id="qua"></tbody>
                                </table>
                                <button type="button" class="qua-add" onclick="Upload2.addQua()" id="addqua">添加</button>
                            </div>
                            <div id="auth" class="flow-item contentList" data-title="授权" data-hidden="true" data-viewWillEnter="authwillenter" data-viewDidEnter="authdidenter" style="height:200px;">
                                <div id="datagrid" class="search-datagrid" idField="sid" showCheckBox="false" multiSelect="false" alternatingRows="true"
                                     showPager="false" ondrawcell="datagriddrawcell" style="width:100%;height:100%;"></div>
                            </div>
                        </ol>
                    </div>
                </div>
            </div>
            <div class="foot-btn">
                <button type="button" class="btn btn-primary" style="display:none" id="btnBack">上一步</button>
                <button type="button" class="btn btn-default" id="btnok" onclick="saveData()">保存</button>
                <button type="button" class="btn btn-success" style="" id="btnNext">下一步</button>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${JS}/intermediarys/orgManageEdit.js"></script>
</body>
</html>