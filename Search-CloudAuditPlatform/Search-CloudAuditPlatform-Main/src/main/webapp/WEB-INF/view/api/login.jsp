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
    <link rel="stylesheet" type="text/css" href="${CSS}/api/login.css?${V}"/>
    <link rel="stylesheet" type="text/css" href="${CSS}/fonts/iconfont.css?${V}"/>
</head>
<body>
<div class="wrap">
    <div class="wrap-login">
        <div class="login-from">
            <ul class="change-login">
                <li class="tab-li cur"><a href="javascript:void(0)"><i class="fa fa-user"></i>账号登录</a></li>
            </ul>
            <div class="from-tab">
                <div class="login-user login">
                    <div class="login-normal" style="display:block;">
                        <input id="vcode" type="hidden" name="vcode" value="${vcode}">
                        <div class="clearfix">
                            <i class="fa fa-user-o"></i>
                            <input id="susername" type="text" name="susername" class="username" placeholder="请输入账号" value="admin"/>
                        </div>
                        <div class="clearfix">
                            <i class="fa fa-lock"></i>
                            <input id="spassword"  type="password" name="spassword" class="spassword" placeholder="请输入密码" value="admin"/>
                        </div>

                        <div style="width:250px; height:30px; line-height:30px; text-align:left; margin-top:10px;">
                            <p id="errorMsg" style="display:inline-block; color:red;"> ${result.msg}</p>
                        </div>
                        <div>
                            <button id="login" type="submit" style="width:240px; height:35px; line-height:35px; margin:15px 0; background:#e4771e;border:none; font-size:18px; font-family:'微软雅黑'; font-weight:bold;color:#ffffff; border-radius:5px; ">登　　录</button>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${JS}/api/login.js?${V}"></script>
</body>
</html>