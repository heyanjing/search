<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/jspTaglib.jsp" %>
<!doctype html>
<html>
<head>
    <title>新增功能</title>
    <meta name='description' content='首页head中的内容'>
    <link rel="stylesheet" type="text/css" href="${CSS}/demo/demo.css?${V}"/>
    <link rel="stylesheet" type="text/css" href="${CSS}/upload/webuploader.css">
    <style>
        .around_list {
            text-align: right !important;
        }

        table tr td {
            padding-top: 10px;
        }

        .search-treeselect {
            float: left;
        }

        .search-select {
            float: left;
        }

        .search-textradio {
            float: left;
        }

        table tr td span a {
            display: inline-block;
            margin-top: 10px;
            padding-left: 3px;
        }

        table tr td span i {
            color: green;
            font-size: 14px !important;
        }
    </style>
    <script type="text/javascript">
        var __usertype = ${usertype};
    </script>
</head>

<body>
<div id="alert" class="" style="left: 0;top: 0;margin-left: 0;width:100%; height:100%;">
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
                        <ol id="iList" style="padding:0 15px 0 15px;height:100%;">
                            <div id="cont1">
                                <form id="form1">
                                    <table class="around_list">
                                        <tr>
                                            <td style="padding:0;">
                                                <div id="sid" class="search-texthide"></div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <label>姓　　名：</label>
                                            </td>
                                            <td>
                                                <div id="username" class="search-textbox" rules="required,name" width="260" height="30"></div>
                                                <span><a href="javascript:void(0)"><i class="fa fa-question-circle"></i></a></span>
                                            </td>
                                            <td style="padding-left:20px;">
                                                <label>电　　话：</label>
                                            </td>
                                            <td>
                                                <div id="userphone" class="search-textbox" rules="required,phonenumber" width="260" height="30"></div>
                                                <span><a href="javascript:void(0)"><i class="fa fa-question-circle"></i></a></span>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <label>身份证号码：</label>
                                            </td>
                                            <td>
                                                <div id="sidcard" class="search-textbox" rules="required" onvalidate="sidcardvalidate" width="260" height="30"></div>
                                            </td>
                                            <td>
                                                <label>创建管理员：</label>
                                            </td>
                                            <td>
                                                <div id="ismanmager" class="search-textradio" idField="value" textField="text" width="200" height="30" style="line-height:30px;float:left;" defaultValue="2" onvaluechanged="changismanmager"></div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <label>毕业院校：</label>
                                            </td>
                                            <td>
                                                <div id="sgraduateschool" class="search-textbox" width="260" height="30"></div>
                                            </td>
                                            <td>
                                                <label>毕业时间：</label>
                                            </td>
                                            <td>
                                                <div id="ldgraduationdate" class="search-datepicker" rules="date:YYYY-MM-DD" width="260" height="30" style="text-align:left;"></div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <label>昵　　称：</label>
                                            </td>
                                            <td>
                                                <div id="snickname" class="search-textbox" width="260" height="30"></div>
                                            </td>
                                            <td>
                                                <label>签　　名：</label>
                                            </td>
                                            <td>
                                                <div id="ssignature" class="search-textbox" width="260" height="30"></div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <label>性　　别：</label>
                                            </td>
                                            <td>
                                                <div id="igender" class="search-select" idField="id" textField="text" defaultValue="1" width="260" height="30"></div>
                                                <span><a href="javascript:void(0)"><i class="fa fa-question-circle"></i></a></span>
                                            </td>
                                            <td>
                                                <label>生　　日：</label>
                                            </td>
                                            <td>
                                                <div id="ldbirthday" class="search-datepicker" rules="date:YYYY-MM-DD" width="260" height="30" style="text-align:left;"></div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <label>邮　　箱：</label>
                                            </td>
                                            <td>
                                                <div id="semail" class="search-textbox" rules="email" width="260" height="30"></div>
                                            </td>
                                            <td>
                                                <label>所 在 地：</label>
                                            </td>
                                            <td>
                                                <div id="saddress" class="search-textbox" width="260" height="30"></div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <label>职　　务：</label>
                                            </td>
                                            <td>
                                                <div id="sdictionariesid" class="search-select" idField="id" textField="text" width="260" height="30"></div>
                                            </td>
                                            <td>
                                                <label>职　　责：</label>
                                            </td>
                                            <td>
                                                <div id="sduties" class="search-textbox" width="260" height="30"></div>
                                            </td>
                                        </tr>
                                    </table>
                                </form>
                            </div>
                            <div class="contentList" id="cont2" style="margin-top:10px; height:100%;">
                                <div class="fixedTb" style="position:relative; height:100%;">
                                    <div class="flow-item contentList" data-title="授权功能" style="display:block; height:100%;">
                                        <div id="datagrid" class="search-treedatagrid" idField="id" textField="sname" ondrawcell="drawcell"
                                             style="width: 100%; height: 100%;">
                                            <div property="columns">
                                                <c:forEach var="cls" items="${headlist}" varStatus="status">
                                                    <div field="${cls.head}" width="80" headAlign="center" textAlign="center">${cls.sname}</div>
                                                </c:forEach>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </ol>
                    </div>
                </div>
            </div>
            <div class="foot-btn">
                <button type="button" class="btn btn-primary" id="btnBack">上一步</button>
                <button type="button" class="btn btn-default" style="display:none" id="btnok" onclick="saveData()">保存</button>
                <button type="button" class="btn btn-success" id="btnNext">下一步</button>
            </div>
        </div>
    </div>
</div>
<div id="mask"></div>

<script type="text/javascript" src="${JS}/orgs/AddManagerBySpecialOrg.js?${V}"></script>
</body>
</html>