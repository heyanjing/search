<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/jspTaglib.jsp" %>
<!doctype html>
<html>
<head>
    <title>新增预算</title>
    <link rel="stylesheet" type="text/css" href="${CSS}/demo/demo.css?${V}"/>
    <link rel="stylesheet" type="text/css" href="${CSS}/upload/webuploader.css?${V}">
    <style>
        .construction-table {
            width: 100%;
            margin: 0 auto;
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

        .segment-information {
            border-collapse: collapse;
            width: 510px !important;
        }

        .segment-information tr th {
            border: 1px #ccc solid;
            height: 30px;
            background: #f1f1f1;
        }

        .segment-information tr td {
            border: 1px #ccc solid;
            height: 30px;
        }

        .segment-information tr td i.fa-plus-circle {
            font-size: 18px;
            color: #209191;
            padding: 0 5px 0 5px;
        }

        .segment-information tr td i.fa-trash-o {
            font-size: 18px;
            color: red;
            padding: 0 5px 0 5px;
        }

        .segment-information tr td i.fa-reply {
            font-size: 18px;
            color: green;
            padding: 0 5px 0 5px;
        }

        .segment-information .search-text {
            border: none;
            border-radius: 0
        }

        #attach img {
            width: 188px;
            height: 120px;
            margin-left: 10px;
            float: left;
            margin-top: 10px;
        }

        /* table tr td{padding-top:10px;} */
    </style>
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
                <form id="BudgeForm" style="position:absolute; top:30px; left:0; width:100%; overflow:auto;">
                    <table class="around_list" style=" margin:0 auto; width:600px;">
                        <tr>
                            <td style="padding-top:5px;" align="right"><label>项目名称：</label></td>
                            <td style="padding-top:5px;" colspan="3" align="left">
                                <div id="sname" class="search-label" width="510" height="30" style="display:inline-block;"></div>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-top:5px;" align="right"><label>审批单位：</label></td>
                            <td style="padding-top:5px;" align="left">
                                <div id="auditname" class="search-label" width="200" height="30"></div>
                            </td>
                            <td style="padding-top:5px; padding-left:20px;" align="right"><label>审批文号：</label></td>
                            <td style="padding-top:5px;" align="left">
                                <div id="sapprovalnum" class="search-label" width="200" height="30" style="display:inline-block;"></div>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-top:5px;" align="right"><label>项目业主：</label></td>
                            <td style="padding-top:5px;" align="left">
                                <div id="ownername" class="search-label" width="200" height="30"></div>
                            </td>
                            <td style="padding-top:5px;" align="right"><label>审批时间：</label></td>
                            <td style="padding-top:5px;" align="left">
                                <div id="ldapprovaldate" class="search-label" width="200" height="30" style="display:inline-block;"></div>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-top:5px;" align="right"><label>代建单位：</label></td>
                            <td style="padding-top:5px;" align="left">
                                <div id="constname" class="search-label" width="200" height="30"></div>
                            </td>
                            <td style="padding-top:5px;" align="right"><label>资金来源：</label></td>
                            <td style="padding-top:5px;" align="left">
                                <div id="scapitalsource" class="search-label" width="200" height="30" style="display:inline-block;"></div>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-top:5px;" align="right"><label>项目地址：</label></td>
                            <td style="padding-top:5px;" colspan="3" align="left">
                                <div id="saddress" class="search-label" width="510" height="30" style="display:inline-block;"></div>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-top:5px;" align="right"><label>标段信息：</label></td>
                            <td style="padding-top:5px;" colspan="3">
                                <table class="segment-information">
                                    <tr id="tr_bud">
                                        <th width="130">标段名称</th>
                                        <th width="100">项目预算工程</br>费用(元)</th>
                                        <th width="100">其中建设安装</br>工程费(元)</th>
                                        <th width="100">其中招标代理</br>服务费(元)</th>
                                    </tr>

                                    <!-- <tr id="sect_tr_0">
                                        <td><input id="sname_0" sname="sname" style="width: 100%; height:30px;border: none;"/></td>
                                        <td><input id="dbudgetamount_0" dbudgetamount="dbudgetamount" style="width: 100%; height:30px;border: none;"/></td>
                                        <td><input id="dengineeringcost_0" dengineeringcost="dengineeringcost" style="width: 100%; height:30px;border: none;"/></td>
                                        <td><input id="dcommissioncost_0" dcommissioncost="dcommissioncost" style="width: 100%; height:30px;border: none;"/></td>
                                        <td><i id="add_0" class="fa fa-plus-circle" onclick="instance.addTr('0');"></i><i id="del_0" class="fa fa-trash-o" onclick="instance.deleTr('0');" >
                                        </i><i id="restore_0" class="fa fa-reply" onclick="instance.restoreTab('0');" ></i><input sid="sid" id="sid_0" type="text" style="display:none;" value="add"/></td>
                                    </tr>

                                    <tr id="sect_tr_0">
                                        <td><div id="sname_0" class="search-textbox" rules="required"  width="130" height="30" ></div> </td>
                                        <td><div id="dbudgetamount_0" class="search-textbox" rules="money"  width="100" height="30" ></div></td>
                                        <td><div id="dengineeringcost_0" class="search-textbox" rules="money"  width="100" height="30" ></div></td>
                                        <td><div id="dcommissioncost_0" class="search-textbox" rules="money"  width="100" height="30" ></div></td>
                                        <td><i id="add_0" class="fa fa-plus-circle" onclick="instance.addTr('0');"></i><i id="del_0" class="fa fa-trash-o" onclick="instance.deleTr('0');" >
                                        </i><i id="restore_0" class="fa fa-reply" onclick="instance.restoreTab('0');" ></i><input sid="sid" id="sid_0" type="text" style="display:none;" value="add"/></td>
                                    </tr>-->
                                </table>

                            </td>
                        </tr>
                    </table>
                </form>
            </li>
            <li class="this">
                <div id="attach" style="width:100%; height:100%; margin:0 auto; "></div>
            </li>
        </ul>
    </div>
</div>
<script type="text/javascript" src="${JS}/projectlib/BudgetViewHandle.js?${V}"></script>
</body>
</html>