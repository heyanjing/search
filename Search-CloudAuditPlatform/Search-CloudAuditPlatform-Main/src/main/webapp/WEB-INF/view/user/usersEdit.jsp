<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/head.jsp" %>
<!doctype html>
<html>
<head>
    <title>编辑用户</title>
    <meta name='description' content='编辑用户'>
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
                            <div class="flow-item">
                                <form id="users">
                                    <div id="sid" class="search-texthide"></div>
                                    <table class="around_list">
                                        <tr>
                                            <td>
                                                <label>姓　　名：</label>
                                            </td>
                                            <td>
                                                <div id="sname" class="search-textbox" rules="required,name" width="300" height="30"></div>
                                                <span><a href="javascript:void(0)"><i class="fa fa-question-circle"></i></a></span>
                                            </td>
                                            <td style="padding-left:20px;">
                                                <label>电　　话：</label>
                                            </td>
                                            <td>
                                                <div id="sphone" class="search-textbox" rules="required,phonenumber" width="300" height="30"></div>
                                                <span><a href="javascript:void(0)"><i class="fa fa-question-circle"></i></a></span>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td>
                                                <label>身份证号码：</label>
                                            </td>
                                            <td>
                                                <div id="sidcard" class="search-textbox" onvalidate="sidcardvalidate" width="300" height="30"></div>
                                            </td>
                                            <td>
                                                <label>毕业院校：</label>
                                            </td>
                                            <td>
                                                <div id="sgraduateschool" class="search-textbox" width="300" height="30"></div>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td>
                                                <label>毕业时间：</label>
                                            </td>
                                            <td>
                                                <div id="ldgraduationdate" class="search-datepicker" rules="date:YYYY-MM-DD" width="300" height="30" style="text-align:left;"></div>
                                            </td>
                                            <td>
                                                <label>昵　　称：</label>
                                            </td>
                                            <td>
                                                <div id="snickname" class="search-textbox" width="300" height="30"></div>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td>
                                                <label>签　　名：</label>
                                            </td>
                                            <td>
                                                <div id="ssignature" class="search-textbox" width="300" height="30"></div>
                                            </td>
                                            <td>
                                                <label>邮　　箱：</label>
                                            </td>
                                            <td>
                                                <div id="semail" class="search-textbox" rules="email" width="300" height="30"></div>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td>
                                                <label>性　　别：</label>
                                            </td>
                                            <td>
                                                <div id="igender" class="search-select" idField="id" textField="text" defaultValue="1" width="300" height="30"></div>
                                                <span><a href="javascript:void(0)"><i class="fa fa-question-circle"></i></a></span>
                                            </td>
                                            <td>
                                                <label>生　　日：</label>
                                            </td>
                                            <td>
                                                <div id="ldbirthday" class="search-datepicker" rules="date:YYYY-MM-DD" width="300" height="30" style="text-align:left;"></div>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td>
                                                <label>所在地：</label>
                                            </td>
                                            <td>
                                                <div id="saddress" class="search-textbox" width="300" height="30"></div>
                                            </td>
                                            <td>
                                                <label>机　　构：</label>
                                            </td>
                                            <td>
                                                <div id="sorgid" class="search-treeselect" rules="required" idField="id" parentField="pid" textField="text" onvaluechanged="sorgidchange" onloaded="sorgidloaded" width="300" height="30"></div>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td>
                                                <label>职　　务：</label>
                                            </td>
                                            <td>
                                                <div id="sdictionariesid" class="search-select" rules="required" idField="id" textField="text" width="300" height="30"></div>
                                            </td>
                                            <td>
                                                <label>职　　责：</label>
                                            </td>
                                            <td>
                                                <div id="sduties" class="search-textbox" width="300" height="30"></div>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td>
                                                <label>权限级别：</label>
                                            </td>
                                            <td>
                                                <div id="ipermissionlevel" class="search-select" rules="required" idField="id" textField="text" defaultValue="1" onvaluechanged="ipermissionlevelchange" width="300" height="30"></div>
                                                <span><a href="javascript:void(0)"><i class="fa fa-question-circle"></i></a></span>
                                            </td>
                                            <td>
                                                <label>分管机构：</label>
                                            </td>
                                            <td>
                                                <div id="chargeorgs" class="search-select" multiSelect="true" idField="id" textField="text" onloaded="chargeorgsloaded" width="300" height="30"></div>
                                            </td>
                                        </tr>
                                    </table>
                                </form>
                            </div>
                            <div class="flow-item contentList" data-title="资质上传" data-viewDidEnter="initaptitudeupload">
                                <table border="1" style="width:100%; height:100px; border-collapse:collapse; margin-top:10px;">
                                    <tbody id="qua"></tbody>
                                </table>
                                <button type="button" class="qua-add" onclick="Upload2.addQua()" id="addqua">添加</button>
                            </div>
                            <div class="flow-item contentList" data-title="身份证上传" data-viewDidEnter="initidcardupload">
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
<script type="text/javascript" src="${JS}/user/usersEdit.js"></script>
</body>
</html>