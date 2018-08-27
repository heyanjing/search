<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/jspTaglib.jsp" %>
<!doctype html>
<html>
<head>
    <title>招投标详情</title>
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

        #attach img {
            width: 188px;
            height: 120px;
            margin-left: 10px;
            float: left;
            margin-top: 10px;
        }

        /**table tr td{padding-top:10px;}**/
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
                <form id="BidForm" style="position:absolute; top:30px; left:0; width:100%; overflow:auto;">
                    <div id="sprojectlibsid" class="search-texthide"></div>
                    <div id="sid" class="search-texthide"></div>
                    <table class="around_list" style=" margin:0 auto;width:600px;">
                        <tr>
                            <td style="padding-top:5px;" align="right"><label>项目名称：</label></td>
                            <td style="padding-top:5px;" align="left">
                                <div id="sname" class="search-label" rules="required" width="200" height="30" style="display:inline-block;"></div>
                            </td>
                            <td style="padding-top:5px;" align="right"><label>标　　段：</label></td>
                            <td style="padding-top:5px;" align="left">
                                <div id="sectname" class="search-label" width="200" height="30"></div>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-top:5px;" align="right"><label>招标方式：</label></td>
                            <td style="padding-top:5px;" align="left">
                                <div id="ibiddingtype" class="search-label" width="200" height="30"></div>
                            </td>
                            <td style="padding-top:5px;" align="right"><label>中 标 人：</label></td>
                            <td style="padding-top:5px;" align="left">
                                <div id="sbidder" class="search-label" width="200" height="30"></div>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-top:5px;" align="right"><label>项目业主：</label></td>
                            <td style="padding-top:5px;" align="left">
                                <div id="ownername" class="search-label" width="200" height="30"></div>
                            </td>
                            <td style="padding-top:5px;" align="right"><label>中标金额(元)：</label></td>
                            <td style="padding-top:5px;" align="left">
                                <div id="dbidamount" class="search-label" width="200" height="30" style="display:inline-block;"></div>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-top:5px;" align="right"><label>代建单位：</label></td>
                            <td style="padding-top:5px;" align="left">
                                <div id="constname" class="search-label" width="200" height="30"></div>
                            </td>
                            <td style="padding-top:5px;" align="right"><label>中标工期(天)：</label></td>
                            <td style="padding-top:5px;" align="left">
                                <div id="ilimitday" class="search-label" width="200" height="30" style="display:inline-block;"></div>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-top:5px;" align="right"><label>招标代理机构：</label></td>
                            <td style="padding-top:5px;" align="left">
                                <div id="agencyname" class="search-label" width="200" height="30"></div>
                            </td>
                            <td style="padding-top:5px;" align="right"><label>中标时间：</label></td>
                            <td style="padding-top:5px;" align="left">
                                <div id="ldbiddate" class="search-label" width="200" height="30" style="text-align:left;"></div>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-top:5px;" align="right"><label>项目经理：</label></td>
                            <td style="padding-top:5px;" align="left">
                                <div id=smanager class="search-label" width="200" height="30" style="text-align:left;"></div>
                            </td>
                            <td style="padding-top:5px;" align="right"><label>联系方式：</label></td>
                            <td style="padding-top:5px;" align="left">
                                <div id="sphone" class="search-label" width="200" height="30" style="text-align:left;"></div>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding-top:5px;" align="right"><label>技术负责人：</label></td>
                            <td style="padding-top:5px;" align="left">
                                <div id="swheel" class="search-label" width="200" height="30" style="text-align:left;"></div>
                            </td>
                            <td style="padding-top:5px;" align="right"><label>身份证号码：</label></td>
                            <td style="padding-top:5px;" align="left">
                                <div id="sidcard" class="search-label" width="200" height="30" style="text-align:left;"></div>
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
<script type="text/javascript" src="${JS}/projectlib/BidViewHandle.js?${V}"></script>
</body>
</html>