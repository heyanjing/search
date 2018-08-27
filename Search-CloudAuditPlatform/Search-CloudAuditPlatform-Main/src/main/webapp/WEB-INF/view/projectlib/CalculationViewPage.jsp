<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/jspTaglib.jsp" %>
<!doctype html>
<html>
<head>
    <title>概算预览</title>
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
                <form id="CalculationForm" style="position:absolute; top:30px; bottom:0px; left:0; width:100%; overflow:auto;">
                    <table class="around_list" style=" margin:0 auto; width:710px;">
                        <tr>
                            <td style="padding-top:5px;" align="right"><label>项目名称：</label></td>
                            <td style="padding-top:5px;" colspan="3" align="left">
                                <div id="sname" class=search-label rules="required" width="590" height="30" style="display:inline-block;"></div>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-top:5px;" align="right"><label>审批单位：</label></td>
                            <td style="padding-top:5px;" align="left">
                                <div id="auditname" class="search-label" width="200" height="30" placeHolder="请选择审批单位"></div>
                            </td>
                            <td style="padding-top:5px;" align="right"><label>审批文号：</label></td>
                            <td style="padding-top:5px;" align="left">
                                <div id="sapprovalnum" class="search-label" width="200" height="30" style="display:inline-block;"></div>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-top:5px;" align="right"><label>审批时间：</label></td>
                            <td style="padding-top:5px;" align="left">
                                <div id="ldapprovaldate" class="search-label" width="200" height="30" style="text-align:left;"></div>
                            </td>
                            <td style="padding-top:5px;" align="right"><label>项目概算总投资(元)：</label>
                            </td>
                            <td style="padding-top:5px;" align="left">
                                <div id="dcalculationamount" class="search-label" width="200" height="30" style="display:inline-block;"></div>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-top:5px;" align="right"><label>项目业主：</label></td>
                            <td style="padding-top:5px;" align="left">
                                <div id="ownername" class="search-label" width="200" height="30"></div>
                            </td>
                            <td style="padding-top:5px;" align="right"><label>其中工程费用(元)：</label></td>
                            <td style="padding-top:5px;" align="left">
                                <div id="dconstructioncost" class="search-label" width="200" height="30" style="display:inline-block;"></div>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-top:5px;" align="right"><label>代建单位：</label></td>
                            <td style="padding-top:5px;" align="left">
                                <div id="constname" class="search-label" width="200" height="30"></div>
                            </td>
                            <td style="padding-top:5px;" align="right"><label>其中工程建设其他费用(元)：</label></td>
                            <td style="padding-top:5px;" align="left">
                                <div id="dconstructionothercost" class="search-label" width="200" height="30" style="display:inline-block;"></div>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-top:5px;" align="right"><label>项目地址：</label></td>
                            <td style="padding-top:5px;" align="left">
                                <div id="saddress" class="search-label" width="200" height="30" style="display:inline-block;"></div>
                            </td>
                            <td style="padding-top:5px;" align="right"><label>其中建设贷款利息(元)：</label></td>
                            <td style="padding-top:5px;" align="left">
                                <div id="dloaninterest" class="search-label" width="200" height="30" style="display:inline-block;"></div>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-top:5px;" align="right"><label>资金来源：</label></td>
                            <td style="padding-top:5px;" align="left">
                                <div id="scapitalsource" class="search-label" width="200" height="30" style="display:inline-block;"></div>
                            </td>
                            <td style="padding-top:5px;" align="right"><label>其中预备费(元)：</label></td>
                            <td style="padding-top:5px;" align="left">
                                <div id="dreservecost" class="search-label" width="200" height="30" style="display:inline-block;"></div>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-top:5px;" align="right"><label>计划开工时间：</label></td>
                            <td style="padding-top:5px;" align="left">
                                <div id="ldstartdate" class="search-label" width="200" height="30" style="text-align:left;"></div>
                            </td>
                            <td style="padding-top:5px;" align="right"><label>计划竣工时间：</label>
                            </td>
                            <td style="padding-top:5px;" align="left">
                                <div id="ldenddate" class="search-label" width="200" height="30" style="text-align:left;"></div>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-top:5px;" align="right"><label>建设规模和内容：</label></td>
                            <td style="padding-top:5px;" colspan="3" align="left">
                                <div id="sdesc" class="search-label" multiline="true" width="590" height="80" style="display:inline-block;"></div>
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
<script type="text/javascript" src="${JS}/projectlib/CalculationViewHandle.js?${V}"></script>
</body>
</html>