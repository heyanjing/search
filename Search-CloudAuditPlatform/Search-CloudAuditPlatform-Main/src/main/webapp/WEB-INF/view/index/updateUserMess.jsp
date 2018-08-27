<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/head.jsp" %>
<!doctype html>
<html>
<head>
    <title>修改个人信息</title>
    <meta name='description' content='更改邮箱'>
    <link rel="stylesheet" type="text/css" href="${CSS}/demo/demo.css"/>
    <style>
        .around_list {
            text-align: inherit;
        }

        table tr td {
            padding-top: 10px;
        }

        .flow-item i {
            font-size: 34px;
            color: #5cb85c;
            vertical-align: middle;
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
                            <div class="flow-item" data-title="修改个人信息" data-viewWillLeave="instance.updateUser">
                                <form id="userFrom">
                                    <div id="sid" class="search-texthide"></div>
                                    <table class="around_list" style="margin:0 auto;">
                                        <tr>
                                            <td>
                                                <label>登 录 名：</label>
                                            </td>
                                            <td>
                                                <div id="susername" class="search-textbox" rules="required" width="200" height="30"></div>
                                            </td>
                                            <td>
                                                <label>姓　　名：</label>
                                            </td>
                                            <td>
                                                <div id="sname" class="search-textbox" rules="required" width="200" height="30"></div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <label>昵　　称：</label>
                                            </td>
                                            <td>
                                                <div id="snickname" class="search-textbox" rules="required" width="200" height="30"></div>
                                            </td>
                                            <td>
                                                <label>所 在 地：</label>
                                            </td>
                                            <td>
                                                <div id="saddress" class="search-textbox" rules="required" width="200" height="30"></div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <label>毕业院校：</label>
                                            </td>
                                            <td>
                                                <div id="sgraduateschool" class="search-textbox" rules="required" width="200" height="30"></div>
                                            </td>
                                            <td>
                                                <label>毕业时间：</label>
                                            </td>
                                            <td>
                                                <div id="ldgraduationdate" class="search-datepicker" rules="required,date:YYYY-MM-DD" width="200" height="30" style="text-align:initial;"></div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <label>性　　别：</label>
                                            </td>
                                            <td>
                                                <div id="igender" class="search-select" rules="required" idField="id" textField="text" defaultValue="1" width="200" height="30"></div>
                                            </td>
                                            <td>
                                                <label>生　　日：</label>
                                            </td>
                                            <td>
                                                <div id="ldbirthday" class="search-datepicker" rules="required,date:YYYY-MM-DD" width="200" height="30" style="text-align:initial;"></div>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td>
                                                <label>签　　名：</label>
                                            </td>
                                            <td colspan="3">
                                                <div id="ssignature" class="search-textarea" width="480" height="50" style="display:inline-block;"></div>
                                            </td>
                                        </tr>
                                    </table>
                                </form>
                            </div>
                            <div class="flow-item contentList" data-title="完成" style="line-height:250px;">
                                <i class="fa fa-check-circle"></i>
                                修改成功
                            </div>
                        </ol>
                    </div>
                </div>
                <div class="foot-btn">
                    <!-- <button type="button" class="btn btn-primary" id="btnBack" style="display: none;">上一步</button> -->
                    <button type="button" class="btn btn-default" onclick="instance.endFlow();" style=" display: none;" id="btnok">完成</button>
                    <button type="button" class="btn btn-success" id="btnNext">保存</button>
                </div>
            </div>
        </div>
    </div>

</div>
<script type="text/javascript" src="${JS}/index/updateUserMessHandle.js?${V}"></script>
</body>
</html>