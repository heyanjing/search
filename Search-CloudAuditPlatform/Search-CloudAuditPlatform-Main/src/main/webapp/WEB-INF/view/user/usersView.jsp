<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/head.jsp" %>
<!doctype html>
<html>
<head>
    <title>查看用户</title>
    <meta name='description' content='查看用户'>
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

        #idCard img {
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
                                            <label>姓　　名：</label>
                                        </td>
                                        <td>
                                            <div id="sname" class="search-view"></div>
                                        </td>
                                        <td class="des-title">
                                            <label>电　　话：</label>
                                        </td>
                                        <td>
                                            <div id="sphone" class="search-view"></div>
                                        </td>
                                    </tr>

                                    <tr>
                                        <td class="des-title">
                                            <label>身份证号码：</label>
                                        </td>
                                        <td>
                                            <div id="sidcard" class="search-view"></div>
                                        </td>
                                        <td class="des-title">
                                            <label>毕业院校：</label>
                                        </td>
                                        <td>
                                            <div id="sgraduateschool" class="search-view"></div>
                                        </td>
                                    </tr>

                                    <tr>
                                        <td class="des-title">
                                            <label>毕业时间：</label>
                                        </td>
                                        <td>
                                            <div id="ldgraduationdate" class="search-view"></div>
                                        </td>
                                        <td class="des-title">
                                            <label>昵　　称：</label>
                                        </td>
                                        <td>
                                            <div id="snickname" class="search-view"></div>
                                        </td>
                                    </tr>

                                    <tr>
                                        <td class="des-title">
                                            <label>签　　名：</label>
                                        </td>
                                        <td>
                                            <div id="ssignature" class="search-view" style="line-height:20px;"></div>
                                        </td>
                                        <td class="des-title">
                                            <label>邮　　箱：</label>
                                        </td>
                                        <td>
                                            <div id="semail" class="search-view"></div>
                                        </td>
                                    </tr>

                                    <tr>
                                        <td class="des-title">
                                            <label>性　　别：</label>
                                        </td>
                                        <td>
                                            <div id="igender" class="search-view"></div>
                                        </td>
                                        <td class="des-title">
                                            <label>生　　日：</label>
                                        </td>
                                        <td>
                                            <div id="ldbirthday" class="search-view"></div>
                                        </td>
                                    </tr>

                                    <tr>
                                        <td class="des-title">
                                            <label>所在地：</label>
                                        </td>
                                        <td>
                                            <div id="saddress" class="search-view"></div>
                                        </td>
                                        <td class="des-title">
                                            <label>用户类型：</label>
                                        </td>
                                        <td>
                                            <div id="itype" class="search-view"></div>
                                        </td>
                                    </tr>

                                    <tr>
                                        <td class="des-title">
                                            <label>机　　构：</label>
                                        </td>
                                        <td>
                                            <div id="orgname" class="search-view"></div>
                                        </td>
                                        <td class="des-title">
                                            <label>职　　务：</label>
                                        </td>
                                        <td>
                                            <div id="sdictionariesname" class="search-view"></div>
                                        </td>
                                    </tr>

                                    <tr>
                                        <td class="des-title">
                                            <label>职　　责：</label>
                                        </td>
                                        <td>
                                            <div id="sduties" class="search-view"></div>
                                        </td>
                                        <td class="des-title">
                                            <label>权限级别：</label>
                                        </td>
                                        <td>
                                            <div id="ipermissionlevel" class="search-view"></div>
                                        </td>
                                    </tr>

                                    <tr>
                                        <td class="des-title">
                                            <label>分管机构：</label>
                                        </td>
                                        <td>
                                            <div id="chargeorgs" class="search-view"></div>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                            <div class="flow-item contentList" data-title="资质" style="height:100%;" data-viewdidenter="reDrawDatagrid">
                                <div id="datagrid" class="search-datagrid" idField="sid" showCheckBox="false" multiSelect="false" alternatingRows="true"
                                     showPager="false" pageSize="9999" ondrawcell="datagriddrawcell" onloaded="datagridloaded" style="width:100%;height:100%; overflow:auto;">
                                    <div property="columns">
                                        <div type="indexcolumn" width="30" headAlign="center" textAlign="center">序号</div>
                                        <div field="sdictionariename" width="50" headAlign="center" textAlign="center">名称</div>
                                        <div field="sdesc" width="150" headAlign="center" textAlign="center">备注</div>
                                        <div field="oper" width="50" headAlign="center" textAlign="center">操作</div>
                                    </div>
                                </div>
                            </div>
                            <div class="flow-item contentList" data-title="身份证">
                                <div id="idCard" style="width:100%; height:100%; margin:0 auto; "></div>
                            </div>
                        </ol>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${JS}/user/usersView.js"></script>
</body>
</html>