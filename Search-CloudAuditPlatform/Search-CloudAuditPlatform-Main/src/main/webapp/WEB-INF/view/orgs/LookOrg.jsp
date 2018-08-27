<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/head.jsp" %>
<!doctype html>
<html>
<head>
    <title>新增功能</title>
    <meta name='description' content='首页head中的内容'>
    <link rel="stylesheet" type="text/css" href="${CSS}/demo/demo.css?${V}"/>
    <style>
        .around_list {
            text-align: right !important;
        }

        table tr td {
            padding-top: 10px;
        }

        .des-title {
            border: none;
            padding-left: 10px;
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
<div id="alert" class="" style="left: 0;top: 0;margin-left: 0;width:100%; height:100%;display: block;">
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
                            <div class="flow-item" data-title="机构信息" style="padding-top:10px;">
                                <table class="around_list" style="margin:0 auto;border-collapse:collapse;">
                                    <tr>
                                        <td class="des-title"><label>名　　称：</label></td>
                                        <td>
                                            <div id="sname" class="search-view"></div>
                                        </td>
                                        <td class="des-title"><label>父　　级：</label></td>
                                        <td>
                                            <div id="sparentid" class="search-view"></div>
                                        </td>
                                    </tr>

                                    <tr>

                                        <td class="des-title"><label>机构类型：</label></td>
                                        <td>
                                            <div id="itype" class="search-view"></div>
                                        </td>

                                        <td class="des-title"><label>是否部门：</label></td>
                                        <td>
                                            <div id="iisdepartment" class="search-view"></div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="user1" class="des-title"><label>联系人：</label></td>
                                        <td id="user2">
                                            <div id="username" class="search-view"></div>
                                        </td>
                                        <td id="phone1" class="des-title"><label>联系电话：</label></td>
                                        <td id="phone2">
                                            <div id="userphone" class="search-view"></div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="des-title"><label>所属区县：</label></td>
                                        <td>
                                            <div id="areaid" class="search-view"></div>
                                        </td>
                                        <td id="org1" class="des-title"><label>审计机构：</label></td>
                                        <td id="org2">
                                            <div id="sauditorgid" class="search-view"></div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="des-title"><label>描　　述：</label></td>
                                        <td colspan="3">
                                            <div id="sdes" class="search-view"></div>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                            <div class="flow-item contentList" data-title="营业执照" style="height:auto;">
                                <div id="idCard"></div>
                            </div>
                            <div class="flow-item contentList" data-title="机构资质" style="height:100%;" data-viewdidenter="reDrawDatagrid">
                                <div id="datagrid" class="search-treedatagrid" idField="sid" showCheckBox="false" multiSelect="false" alternatingRows="true"
                                     showPager="true" pageSize="20" ondrawcell="datagriddrawcell" onloaded="datagridloaded" style="width:100%;height:100%; overflow:auto;">
                                    <div property="columns">
                                        <div type="indexcolumn" width="30" headAlign="center" textAlign="center">序号</div>
                                        <div field="sname" width="50" headAlign="center" textAlign="center" allowSort="true">名称</div>
                                        <div field="sdesc" width="150" headAlign="center" textAlign="center" allowSort="true">备注</div>
                                        <div field="oper" width="50" headAlign="center" textAlign="center">操作</div>
                                    </div>
                                </div>
                            </div>
                        </ol>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="mask"></div>

<script type="text/javascript" src="${JS}/orgs/LookOrg.js?${V}"></script>
</body>
</html>