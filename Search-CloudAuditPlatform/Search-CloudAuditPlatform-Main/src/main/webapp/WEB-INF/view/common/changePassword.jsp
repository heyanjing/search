<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/head.jsp" %>
<!doctype html>
<html>
<head>
    <title>更改密码</title>
    <meta name='description' content='更改密码'>
    <link rel="stylesheet" type="text/css" href="${CSS}/public/public.css"/>
    <link rel="stylesheet" type="text/css" href="${CSS}/login/sign.css?${V}"/>
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
            padding-top: 20px;
        }

        .emile-title {
            width: 100%;
            height: 40px;
            border-bottom: 2px #5cb85c solid;
            text-align: left;
        }

        .emile-title h4 {
            line-height: 40px;
            margin-left: 20px;
            font-size: 18px;
            font-family: "微软雅黑";
        }
    </style>
    <script type="text/javascript">
        var email = "${param.email}";
        var code = "${param.code}";
    </script>
</head>
<body>
<div class="wrap">
    <div class="email-circle" style="width: 1000px; height:600px; top:10%; left: 0; box-shadow: 0 5px 8px 5px rgba(144, 144, 144, 0.5);  margin: 0 auto; position: relative;background:#ffffff; border-radius:5px;">
        <div class="emile-title">
            <h4>邮箱修改密码</h4>
        </div>
        <form id="changepasswdform" style="width:100%; height:490px; position: absolute; overflow:auto;">
            <table class="around_list" style="margin:0 auto; margin-top:150px;">
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
            </table>
        </form>
        <button type="button" class="org-save" id="btnok" onclick="saveData()" style="position: absolute; bottom:30px; ">保存</button>
    </div>
</div>
<script type="text/javascript" src="${JS}/common/changePassword.js"></script>
</body>
</html>