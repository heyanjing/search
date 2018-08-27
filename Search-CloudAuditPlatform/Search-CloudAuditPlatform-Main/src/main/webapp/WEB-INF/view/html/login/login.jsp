<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/jspTaglib.jsp" %>
<!doctype html>
<html>
<head>
    <title>首页title中的内容</title>
    <meta name='description' content='首页head中的内容'>
    <link rel="stylesheet" type="text/css" href="${CSS}/login/login.css?${V}"/>
    <link rel="stylesheet" type="text/css" href="${CSS}/fonts/iconfont.css?${V}"/>
</head>
<body>
<div class="wrap">
    <div class="wrap-logo"><h1><!--<img src="images/mini.png" width="60" height="60">-->重庆市审计局</h1></div>
    <div class="wrap-login">
        <h1>审计管理系统</h1>

        <div class="login-from">
            <ul class="change-login">
                <li class="tab-li cur"><a href="javascript:void(0)"><i class="fa fa-user"></i>账号登录</a></li>
                <li class="tab-li"><a href="javascript:void(0)"><i class="fa fa-qrcode"></i>二维码登录</a></li>
            </ul>
            <div class="from-tab">
                <div class="login-user">
                    <div class="login-normal" style="display:block;">
                        <form action="" method="post" id="loginForm">
                            <div class="clearfix">
                                <i class="fa fa-user-o"></i>
                                <input type="text" name="username" class="username" placeholder="请输入账号"/>
                            </div>
                            <div class="clearfix">
                                <i class="fa fa-lock"></i>
                                <input type="password" name="password" class="password" placeholder="请输入密码"/>
                            </div>
                            <div class="clearfix">
                                <i class="fa fa-pencil-square-o"></i>
                                <input type="text" name="code" class="code" placeholder="请输入验证码" style="width:120px; float:left; margin:0 5px 0 auto;"/>
                                <button type="button" style="height:35px; line-height:35px; background:#36a5ff; font-size:14px; color:#ffffff; border:none; border-radius:5px; padding:0 15px; display:block">获取动态密码</button>
                            </div>
                            <div>
                                <button id="submit" type="submit" style="width:280px; height:35px; line-height:35px; margin:20px 0 5px auto; background:#e4771e;border:none; font-size:18px; font-family:'微软雅黑'; font-weight:bold;color:#ffffff; border-radius:5px; ">登　　录</button>
                            </div>
                            <div class="login-link">
                                <span style="float:left;"><a href="">忘记密码？</a> <a href="">常见问题？</a></span><span style="float:right;"><a href="">机构注册</a>/<a href="/main//user/AddUserPage.jsp"> 用户注册</a></span>
                            </div>
                        </form>
                    </div>
                </div>

                <div class="weixin-login" style="display:none;">
                    <div class="login-erweima">
                        <img src="${IMG}/login/ewm.png">
                        <p>扫描二维码安全登录官方APP更安全</p>
                        <p style="padding:0 0 10px 0;"><a href="">立即下载</a></p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${JS}/html/login/login.js?${V}"></script>
</body>
</html>