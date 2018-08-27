<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/jspTaglib.jsp" %>
<!doctype html>
<html>
<head>
    <title>
        <c:choose>
            <c:when test="${not empty SYSTEM_NAME}">
                ${SYSTEM_NAME}
            </c:when>
            <c:otherwise>
                欢迎使用
            </c:otherwise>
        </c:choose>
    </title>
    <link rel="stylesheet" type="text/css" href="${CSS}/login/login.css?${V}"/>
    <link rel="stylesheet" type="text/css" href="${CSS}/fonts/iconfont.css?${V}"/>
</head>
<body>
<div class="wrap">
    <div class="wrap-logo"><h1><img src="${NETWORK_ROOT}${LOGO}" width="60" height="60">${ORG_NAME}</h1></div>
    <div class="wrap-login">
        <h1>${SYSTEM_NAME}</h1>
        <div class="login-from">
            <ul class="change-login">
                <li class="tab-li cur"><a href="javascript:void(0)"><i class="fa fa-user"></i>账号登录</a></li>
                <li class="tab-li"><a href="javascript:void(0)"><i class="fa fa-qrcode"></i>二维码登录</a></li>
            </ul>
            <div class="from-tab">
                <div class="login-user login">
                    <div class="login-normal" style="display:block;">
                        <form action="${CTX}/login" method="post" id="loginForm">
                            <div class="clearfix">
                                <i class="fa fa-user-o"></i>
                                <c:choose>
                                    <c:when test="${DEBUG}">
                                        <input list="cars" id="userName" type="text" name="userName" class="username" placeholder="请输入账号"/>
                                        <datalist id="cars">
                                            <option value="admin">
                                            <option value="市局管理员">
                                            <option value="九龙坡局管理员">
                                            <option value="中介机构管理员">
                                            <option value="建设机构管理员">
                                        </datalist>
                                        <%--<input id="userName" type="text" name="userName" class="username" placeholder="请输入账号" value="admin"/>--%>
                                    </c:when>
                                    <c:otherwise>
                                        <input id="userName" type="text" name="userName" class="username" placeholder="请输入账号" value="admin"/>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <c:if test="${ empty result or result.code==103 or result.code==102}">
                                <div class="clearfix">
                                    <i class="fa fa-lock"></i>
                                    <c:choose>
                                        <c:when test="${DEBUG}">
                                            <input type="password" id="password" name="password" class="password" placeholder="请输入密码" value="admin"/>
                                        </c:when>
                                        <c:otherwise>
                                            <input type="password" id="password" name="password" class="password" placeholder="请输入密码" value=""/>
                                        </c:otherwise>
                                    </c:choose>
                                </div>

                            </c:if>
                            <c:if test="${result.code==101}">
                                <div class="clearfix">
                                    <i class="fa fa-pencil-square-o"></i>
                                    <input type="text" name="code" class="code" placeholder="请输入验证码" style="width:110px; float:left; margin:0 10px 0 auto;"/>
                                    <button id="phonecode" type="button" style="height:35px; line-height:35px; background:#36a5ff; font-size:14px; color:#ffffff; border:none; border-radius:5px; padding:0 18px; display:block">获取动态密码</button>
                                </div>
                            </c:if>
                            <div style="width:250px; height:30px; line-height:30px; text-align:left; margin-top:10px;">
                                <p id="errorMsg" style="display:inline-block; color:red;"> ${result.msg}</p>
                            </div>
                            <div>
                                <button id="submit" type="submit" style="width:280px; height:35px; line-height:35px; margin:15px 0 0 auto; background:#e4771e;border:none; font-size:18px; font-family:'微软雅黑'; font-weight:bold;color:#ffffff; border-radius:5px; ">登　　录</button>
                            </div>
                            <div class="login-link">
                                <span style="float:left;"><a href="${CTX}/common/findPassword">忘记密码？</a> <a href="">常见问题？</a></span><span style="float:right;"><a href="${CTX}/orgs/grorg" target="_blank">机构注册</a>/<a href="${CTX}/user/getAddUserPage" target="_blank"> 用户注册</a></span>
                            </div>
                        </form>
                    </div>
                </div>

                <div class="weixin-login login" style="display:none;">
                    <%--<input id="qrVcode" type="hidden"  value="${qrVcode}">--%>
                    <div class="login-erweima">
                        <img id="qr" src="${CTX}/api/qr" data-src="${CTX}/api/qr">
                        <p>扫描二维码安全登录官方APP更安全</p>
                        <p style="padding:0 0 10px 0;"><a href="">立即下载</a></p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${JS}/login.js?${V}"></script>
</body>
</html>