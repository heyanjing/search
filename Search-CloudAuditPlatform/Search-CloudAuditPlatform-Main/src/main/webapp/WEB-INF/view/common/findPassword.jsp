<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/head.jsp" %>
<!doctype html>
<html>
<head>
    <title>找回密码</title>
    <meta name='description' content='找回密码'>
    <link rel="stylesheet" type="text/css" href="${CSS}/demo/demo.css"/>
    <link rel="stylesheet" type="text/css" href="${CSS}/login/sign.css?${V}"/>
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
            padding-top: 30px;
        }

        .search-treeselect td {
            padding-top: 0;
        }

        .search-select {
            float: left;
        }

        #email-content i {
            font-size: 48px;
            color: blue;
            vertical-align: middle;
            margin-right: 20px;
        }

        #email-content span {
            display: inline-block;
            vertical-align: middle;
        }
    </style>
</head>
<body>
<div class="wrap">
    <div class="password-circle" style="width: 1000px; height:600px; top:10%; left: 0; box-shadow: 0 5px 8px 5px rgba(144, 144, 144, 0.5);  margin: 0 auto; position: relative;background:#ffffff; border-radius:5px;">
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
                                    <div class="flow-item" data-title="找回方式" data-viewWillLeave="findtype">
                                        <form id="findtypeform" style="margin-top:150px;">
                                            <table class="around_list" style="margin:0 auto;">
                                                <tr>
                                                    <td>
                                                        <label>找回方式：</label>
                                                    </td>
                                                    <td>
                                                        <div id="type" class="search-select" idField="id" textField="text" defaultValue="1" rules="required" onvaluechanged="typechanged" width="300" height="30"></div>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <label>手机号码：</label>
                                                    </td>
                                                    <td>
                                                        <div id="flag" class="search-textbox" rules="required,mobile" width="300" height="30"></div>
                                                    </td>
                                                </tr>
                                            </table>
                                        </form>
                                    </div>
                                    <div class="flow-item contentList" data-title="找回密码">
                                        <div id="email-content" style="line-height:490px;width:100%; height:490px;"><i class="fa fa-check-circle"></i><span>邮件发送成功，请登录邮箱更改密码！</span></div>
                                        <div id="phone-content">
                                            <form id="changepasswdform" style="margin-top:130px;">
                                                <table class="around_list" style="margin:0 auto;">
                                                    <tr>
                                                        <td>
                                                            <label>新密码：</label>
                                                        </td>
                                                        <td>
                                                            <div id="newpasswdone" class="search-textpassword" rules="required" width="300" height="30"></div>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>
                                                            <label>确认密码：</label>
                                                        </td>
                                                        <td>
                                                            <div id="newpasswdtwo" class="search-textpassword" rules="required" width="300" height="30"></div>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>
                                                            <label>验证码：</label>
                                                        </td>
                                                        <td>
                                                            <div id="code" class="search-textbox" rules="required" width="200" height="30"></div>
                                                            <button type="button" onclick="sendSMS(this);" style="width:100px; height:33px; ">获取验证码</button>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </form>
                                        </div>
                                    </div>
                                </ol>
                            </div>
                        </div>
                    </div>
                    <div class="foot-btn">
                        <button type="button" class="btn btn-default" id="btnok" onclick="saveData()">保存</button>
                        <button type="button" class="btn btn-success" style="" id="btnNext">下一步</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${JS}/common/findPassword.js"></script>
</body>
</html>