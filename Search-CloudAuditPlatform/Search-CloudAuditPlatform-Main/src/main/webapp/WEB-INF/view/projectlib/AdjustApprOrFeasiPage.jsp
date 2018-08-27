<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/jspTaglib.jsp" %>
<!doctype html>
<html>
<head>
    <title>t调整</title>
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
            height: 100px;
            border: 1px solid #ccc;
            border-collapse: collapse;
        }

        .construction-table-bd li.table-thisclass table tr td {
            border: 1px solid #ccc;
        }

        .search-treeselect td {
            padding-top: 0;
        }

        .search-textbox {
            width: 100%;
            display: inline-block;
        }

        .search-text {
            border: none;
        }

        .search-datepicker .search-text {
            border: none;
        }

        .treeselect_title span {
            border: none;
        }

        .select_province span {
            border: none;
        }

        .search-area {
            border: none;
        }
    </style>
    <script type="text/javascript">
        var __pagestate = "${pagestate}", __sauditid = "${sauditid}";
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
                <form id="ApprovalForm" style="position:absolute; top:30px; bottom:50px; left:0; width:100%; overflow:auto;">
                    <div id="sprojectlibsid" class="search-texthide"></div>
                    <div id="sid" class="search-texthide"></div>
                    <table>
                        <tr>
                            <td width="30%" height="30" colspan="2">调整项</td>
                            <td width="35%" height="30">原始值</td>
                            <td width="35%" height="30">调整值</td>
                        </tr>
                        <tr>
                            <td colspan="2">项目名称</td>
                            <td id="appr-sname"></td>
                            <td align="center">
                                <div id="sname" rules="required" class="search-textbox" height="30"></div>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">项目业主</td>
                            <td id="appr-ownername"></td>
                            <td>
                                <div id="sproprietororgid" class="search-treeselect" rules="required" idField="sid" parentField="sparentid" textField="sname" width="299" height="30" placeHolder="请选择项目业主" onvalidate="proprietororgValidate"></div>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">审批单位</td>
                            <td id="appr-auditname"></td>
                            <td>
                                <div id="sapprovalorgid" class="search-treeselect" idField="sid" parentField="sparentid" textField="sname" width="299" height="30" placeHolder="请选择审批单位"></div>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">代建单位</td>
                            <td id="appr-constname"></td>
                            <td>
                                <div id="sdeputyorgid" class="search-treeselect" idField="sid" parentField="sparentid" textField="sname" width="299" height="30" placeHolder="请选择代建单位"></div>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">审批文号</td>
                            <td id="appr-sapprovalnum"></td>
                            <td>
                                <div id="sapprovalnum" class="search-textbox" height="30"></div>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">项目地址</td>
                            <td id="appr-saddress"></td>
                            <td>
                                <div id="saddress" class="search-textbox" height="30"></div>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">审批时间</td>
                            <td id="appr-ldapprovaldate"></td>
                            <td>
                                <div id="ldapprovaldate" class="search-datepicker" rules="date:YYYY-MM-DD" height="30" style="text-align:left;width:100%;"></div>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">资金来源</td>
                            <td id="appr-scapitalsource"></td>
                            <td>
                                <div id="scapitalsource" class="search-textbox" height="30"></div>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">项目估算总金额(万元)</td>
                            <td id="appr-destimateamount"></td>
                            <td>
                                <div id="destimateamount" class="search-textbox" height="30"></div>
                            </td>
                        </tr>
                        <tr>
                            <td rowspan="5">招标核准</td>
                            <td>勘察</td>
                            <td id="appr-iprospectingtype"></td>
                            <td>
                                <div id="iprospectingtype" class="search-select" idField="value" textField="text" height="30" width="299" dropdownHeight="200" style="float:left;"></div>
                            </td>
                        </tr>
                        <tr>
                            <td>设计</td>
                            <td id="appr-idesigntype"></td>
                            <td>
                                <div id="idesigntype" class="search-select" idField="value" textField="text" height="30" width="299" dropdownHeight="200" style="float:left;"></div>
                            </td>
                        </tr>
                        <tr>
                            <td>监理</td>
                            <td id="appr-isupervisiontype"></td>
                            <td>
                                <div id="isupervisiontype" class="search-select" idField="value" textField="text" height="30" width="299" dropdownHeight="200" style="float:left;"></div>
                            </td>
                        </tr>
                        <tr>
                            <td>施工</td>
                            <td id="appr-iconstructiontype"></td>
                            <td>
                                <div id="iconstructiontype" class="search-select" idField="value" textField="text" height="30" width="299" dropdownHeight="200" style="float:left;"></div>
                            </td>
                        </tr>
                        <tr>
                            <td>咨询</td>
                            <td id="appr-iintermediarytype"></td>
                            <td>
                                <div id="iintermediarytype" class="search-select" idField="value" textField="text" height="30" width="299" dropdownHeight="200" style="float:left;"></div>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">建设规模和内容</td>
                            <td id="appr-sdesc" style="line-height: 30px; overflow: auto; display:flex; height:100px; border:none; text-align:left;"></td>
                            <td>
                                <div id="sdesc" class="search-textarea" width="300" height="100" style="display:inline-block;"></div>
                            </td>
                        </tr>
                        <c:if test="${pagestate == '2'}">
                            <tr>
                                <td colspan="2">计划开工时间</td>
                                <td id="appr-ldstartdate"></td>
                                <td>
                                    <div id="ldstartdate" class="search-datepicker" rules="date:YYYY-MM-DD" height="30" style="text-align:left;width:100%;"></div>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">计划竣工时间</td>
                                <td id="appr-ldenddate"></td>
                                <td>
                                    <div id="ldenddate" class="search-datepicker" rules="date:YYYY-MM-DD" height="30" style="text-align:left;width:100%;"></div>
                                </td>
                            </tr>
                        </c:if>
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
<script type="text/javascript" src="${JS}/projectlib/AdjustApprOrFeasiHandle.js?${V}"></script>
</body>
</html>