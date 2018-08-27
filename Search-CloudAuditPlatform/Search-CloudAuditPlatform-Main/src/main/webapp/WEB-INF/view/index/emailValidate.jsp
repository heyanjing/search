<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/jspTaglib.jsp" %>
<!doctype html>
<html>
<head>
    <title>邮箱验证</title>
    <meta name='description' content='邮箱验证'>
    <link rel="stylesheet" type="text/css" href="${CSS}/demo/demo.css?${V}"/>
    <style>
    .authority {
            width: 100%;
            height: 100%;
            margin: 0 auto;
            text-align: center;
        }

        .around_list .send-verifica {
            color: #fff;
            background: #337ab7;
            padding: 8px;
            vertical-align: top;
            margin-left: 5px;
            line-height: 30px;
            border-radius: 5px;
            cursor: pointer;
        }

        .around_list tr td {
            padding-top: 10px;
        }

        .search-text {
            border-radius: 5px;
        }

        .renew-btn {
            position: absolute;
            left: 0;
            right: 0;
            margin: 0 auto;
        }

        .org-renew {
            width: 80px;
            height: 30px;
            background: #5cb85c;
            border: #4cae4c;
            color: #fff;
            border-radius: 5px;
            margin: 10px;
            cursor: pointer;
        }

        .org-renew:hover {
            background: #449d44;
            border: #398439;
            color: #fff;
        }
    </style>
    <script type="text/javascript">
        var __code = "${code}", __ityp = "${itype}", __flag = ${flag};
    </script>
</head>
<body>
<div class="authority" id="phone-id" style="display: none;">
    <form id="form">
        <table class="around_list" style=" margin:0 auto;">
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
                    <div id="validate" class="search-textbox" rules="required" width="120" height="30" style="display:inline-block;"></div>
                    <a class="send-verifica" id="send-verifica" onclick="instance.sendVerifica();">发送验证码</a>
                </td>
            </tr>
        </table>
    </form>
    <div class="renew-btn" colspan="4">
        <input class="org-renew" type="button" onclick="instance.updatePhone()" value="更新"/>
    </div>
</div>
<div id="email-id" style="display: none;">欢迎使用软件，请点击下列按钮更新邮箱<input class="org-save" type="button" onclick="instance.updateMail();" value="更新"/></div>
<script type="text/javascript" src="${JS}/index/emailValidateHandle.js?${V}"></script>
</body>
</html>