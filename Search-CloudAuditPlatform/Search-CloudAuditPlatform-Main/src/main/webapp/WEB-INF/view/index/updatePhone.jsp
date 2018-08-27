<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/head.jsp" %>
<!doctype html>
<html>
<head>
    <title>重新绑定</title>
    <meta name='description' content='重新绑定'>
    <link rel="stylesheet" type="text/css" href="${CSS}/demo/demo.css"/>
    <style>
        .around_list {
            margin: 0 auto;
        }

        .around_list tr td {
            padding-top: 10px;
        }

        .around_list .send-verifica {
            color: #fff;
            background: #337ab7;
            padding: 8px;
            margin-left: 5px;
            line-height: 30px;
            border-radius: 5px;
            cursor: pointer;
        }

        .flow-item i {
            font-size: 34px;
            color: #5cb85c;
            vertical-align: middle;
        }

    </style>
</head>
<body>
<div id="alert" style="left: 0;top: 0;margin-left: 0;width:100%; height:100%; display: block;">
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
                            <div class="flow-item" data-title="身份验证" data-viewWillLeave="instance.sphoneValidate">
                                <form id="sphoneValidate">
                                    <table class="around_list">
                                        <tr>
                                            <td>
                                                <label>验证方式：</label>
                                            </td>
                                            <td align="left">
                                                <div id="emailVa" class="search-select" multiSelect="false" idField="value" textField="text" defaultValue="1" width="200" height="30" onvaluechanged="onValueChanged"></div>
                                            </td>
                                        </tr>
                                        <tr id="tr-sphone">
                                            <td>
                                                <label>手机号码：</label>
                                            </td>
                                            <td align="left" id="sphone">
                                            </td>
                                        </tr>
                                        <tr id="tr-validate">
                                            <td>
                                                <label>验证号码：</label>
                                            </td>
                                            <td align="left">
                                                <div id="validate" class="search-textbox" rules="required" width="200" height="28"></div>
                                                <a class="send-verifica" id="send-verifica" onclick="instance.sendVerifica('#send-verifica',false);">发送验证码</a>
                                            </td>
                                        </tr>
                                        <tr id="tr-email">
                                            <td>
                                                <label>邮箱验证：</label>
                                            </td>
                                            <td align="left" id="email">
                                            </td>
                                        </tr>
                                    </table>
                                </form>
                            </div>
                            <div class="flow-item contentList" data-title="设置新手机号码" data-viewWillLeave="instance.updatePhone">
                                <form id="newPhoneFrom">
                                    <table class="around_list">
                                        <tr>
                                            <td>
                                                <label>手机号码：</label>
                                            </td>
                                            <td align="left">
                                                <div id="newphone" class="search-textbox" rules="required" width="200" height="30"></div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <label>验证号码：</label>
                                            </td>
                                            <td align="left">
                                                <div id="newvalidate" class="search-textbox" rules="required" width="200" height="28"></div>
                                                <a class="send-verifica" id="new-send-verifica" onclick="instance.sendVerifica('#new-send-verifica',true);">发送验证码</a>
                                            </td>
                                        </tr>
                                    </table>
                                </form>
                            </div>
                            <div class="flow-item contentList" data-title="完成" style="line-height:200px;">
                                <i class="fa fa-check-circle"></i>
                                修改成功
                            </div>
                        </ol>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="foot-btn">
        <!-- <button type="button" class="btn btn-primary" id="btnBack" style="display: none;">上一步</button> -->
        <button type="button" class="btn btn-default" onclick="instance.endFlow();" style=" display: none;" id="btnok">完成</button>
        <button type="button" class="btn btn-success" id="btnNext">下一步</button>
    </div>
</div>
<script type="text/javascript" src="${JS}/index/updatePhoneHandle.js?${V}"></script>
</body>
</html>