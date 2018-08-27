<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/jspTaglib.jsp" %>
<!doctype html>
<html>
<head>
    <title>新增招投标</title>
    <link rel="stylesheet" type="text/css" href="${CSS}/demo/demo.css?${V}"/>
    <link rel="stylesheet" type="text/css" href="${CSS}/upload/webuploader.css?${V}">
    <style>
        .construction-table {
            width: 100%;
            margin: 0 auto 50px;
        }

        .construction-table-title {
            background: #f1f1f1;
            overflow: hidden;
            zoom: 1;
        }

        .construction-table-title li {
            float: left;
            width: 50%;
            height: 30px;
            line-height: 30px;
            color: #000;
            text-align: center;
            cursor: pointer;
        }

        .construction-table-title li.table-active {
            background: #e1e1e1;
        }

        .construction-table-bd li.this {
            display: none;
            padding: 20px;
            border-top: 0 none;
            font-size: 24px;
        }

        .construction-table-bd li.table-thisclass {
            display: list-item;
        }

        .construction-table-bd li.table-thisclass table {
            width: 100%;
            border: none;
        }

        /**table tr td{padding-top:10px;}**/
    </style>
    <script type="text/javascript">
        var _pro_lib_auditid = "${sauditid}";
    </script>
</head>
<body>
<div class="wrap">
    <div class="construction-table">
        <ul id="table-construction" class="construction-table-title">
            <li class="table-active">基本信息</li>
            <li>相关文件</li>
        </ul>
        <ul id="table-construction-bd" class="construction-table-bd">
            <li class="this table-thisclass">
                <form id="BidForm" style="position:absolute; top:30px; bottom:50px; left:0; width:100%; overflow:auto;">
                    <div id="sprojectlibsid" class="search-texthide"></div>
                    <div id="sid" class="search-texthide"></div>
                    <table class="around_list" style=" margin:0 auto;">
                        <tr>
                            <td style="padding-top:10px;" align="right"><label>项目名称：</label></td>
                            <td style="padding-top:10px;">
                                <div id="sname" class="search-textbox" rules="required" width="200" height="30" style="display:inline-block;"></div>
                            </td>
                            <td style="padding-top:10px;" align="right"><label>标　　段：</label></td>
                            <td style="padding-top:10px;">
                                <div id="ssectionid" class="search-select" idField="sid" textField="sname" width="200" height="30" dropdownHeight="200" placeHolder="-请选择-"></div>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-top:10px;" align="right"><label>招标方式：</label></td>
                            <td style="padding-top:10px;">
                                <div id="ibiddingtype" class="search-select" idField="value" textField="text" width="200" height="30" dropdownHeight="200" placeHolder="-请选择-"></div>
                            </td>
                            <td style="padding-top:10px;" align="right"><label>中 标 人：</label></td>
                            <td style="padding-top:10px;">
                                <div id="sbidder" class="search-textbox" rules="name" width="200" height="30" style="display:inline-block;"></div>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-top:10px;" align="right"><label>项目业主：</label></td>
                            <td style="padding-top:10px;">
                                <div id="sproprietororgid" class="search-treeselect" rules="required" idField="sid" parentField="sparentid" textField="sname" width="200" height="30" placeHolder="-请选择-" onvalidate="proprietororgValidate"></div>
                            </td>
                            <td style="padding-top:10px;" align="right"><label>中标金额(元)：</label></td>
                            <td style="padding-top:10px;" align="left">
                                <div id="dbidamount" class="search-textbox" width="200" height="30" style="display:inline-block;"></div>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-top:10px;" align="right"><label>代建单位：</label></td>
                            <td style="padding-top:10px;">
                                <div id="sdeputyorgid" class="search-treeselect" idField="sid" parentField="sparentid" textField="sname" width="200" height="30" placeHolder="-请选择-"></div>
                            </td>
                            <td style="padding-top:10px;" align="right"><label>中标工期(天)：</label></td>
                            <td style="padding-top:10px;">
                                <div id="ilimitday" class="search-textbox" width="200" height="30" style="display:inline-block;"></div>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-top:10px;" align="right"><label>招标代理机构：</label></td>
                            <td style="padding-top:10px;">
                                <div id="sagencyorgid" class="search-treeselect" idField="sid" parentField="sparentid" textField="sname" width="200" height="30" placeHolder="请选择审批单位"></div>
                            </td>
                            <td style="padding-top:10px;" align="right"><label>中标时间：</label></td>
                            <td style="padding-top:10px;">
                                <div id="ldbiddate" class="search-datepicker" rules="date:YYYY-MM-DD" width="200" height="30" style="text-align:left;"></div>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-top:10px;" align="right"><label>项目经理：</label></td>
                            <td style="padding-top:10px;">
                                <div id=smanager class="search-textbox" rules="name" width="200" height="30" style="text-align:left;"></div>
                            </td>
                            <td style="padding-top:10px;" align="right"><label>联系方式：</label></td>
                            <td style="padding-top:10px;">
                                <div id="sphone" class="search-textbox" rules="phonenumber" width="200" height="30" style="text-align:left;"></div>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-top:10px;" align="right"><label>技术负责人：</label></td>
                            <td style="padding-top:10px;">
                                <div id="swheel" class="search-textbox" rules="name" width="200" height="30" style="text-align:left;"></div>
                            </td>
                            <td style="padding-top:10px;" align="right"><label>身份证号码：</label></td>
                            <td style="padding-top:10px;">
                                <div id="sidcard" class="search-textbox" width="200" height="30" style="text-align:left;" onvalidate="sidcardvalidate"></div>
                            </td>
                        </tr>
                    </table>
                </form>
            </li>
            <li class="this">
                <div id="uploader" class="cl-uploader">
                    <div class="queueList">
                        <!-- 原始拖拽div -->
                        <div id="dndArea" class="placeholder" style="min-height: 120px; width:745px;">
                            <div id="filePicker" class="cl-picker"></div>
                            <p>或将文件拖到这里，单次最多可选300份</p>
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
            </li>
        </ul>
    </div>
    <div class="org-btn" colspan="4" style="position:absolute;bottom:0;">
        <input class="org-save" type="button" onclick="saveData()" value="保存"/>
        <input class="org-close" type="button" onclick="windowClose()" value="取消"/>
    </div>
</div>
<script type="text/javascript" src="${JS}/projectlib/UpdateBidHandle.js?${V}"></script>
</body>
</html>