<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/jspTaglib.jsp" %>
<!doctype html>
<html>
<head>
    <title>首页title中的内容</title>
    <meta name='description' content='首页head中的内容'>
    <link rel="stylesheet" type="text/css" href="${CSS}/index/index.css?${V}"/>
    <link rel="stylesheet" type="text/css" href="${CSS}/fonts/iconfont.css?${V}"/>
    <script type="text/javascript">
        var __usertype = "${usertype}";
    </script>
</head>
<body>
<div class="wrap">
    <div class="wrap-top">
        <div class="main-header">
            <span class="logo">审计管理系统</span>
        </div>
        <div class="nav-left">
            <ul class="topLevelMenus">
                <li class="nav-item"><a href="javascript:void(0);"><i class="fa fa-user-plus"></i>个人工作台</a></li>
                <li><a href="javascript:void(0);"><i class="fa fa-cube"></i>项目与计划</a></li>
                <li><a href="javascript:void(0);"><i class="fa fa-book"></i>资料与送审</a></li>
                <li><a href="javascript:void(0);"><i class="fa fa-shopping-bag"></i>管理与考核</a></li>
                <li><a href="javascript:void(0);"><i class="fa fa-line-chart"></i>数据与分析</a></li>
                <li><a href="javascript:void(0);"><i class="fa fa-bar-chart"></i>统计与查询</a></li>
                <li><a href="javascript:void(0);"><i class="fa fa-gear"></i>系统设置</a></li>
            </ul>
        </div>
        <div class="navbar">
            <!-- <div class="navbar-renovate">
                <ul>
                    <li><a href="javascript:void(0);"><i class="fa fa-refresh"></i></a></li>
                    <li><a href="javascript:void(0);"><i class="fa fa-th-large"></i></a></li>
                    <li><div class="navbar-input"><input type="text" value="" placeholder="请输入关键词"/><i class="glyphicon glyphicon-search"></i></div></li>
                </ul>
             </div> -->

            <div class="navbar-renonateright">
                <ul>
                    <li><a href="javascript:void(0);"><i class="fa fa-envelope"></i></a></li>
                    <li><a href="javascript:void(0);"><i class="fa fa-bell"></i></a></li>
                    <li style="margin-top:-3px;"><a href="javascript:void(0);" class="top-js"><i><img src="${IMG}/index/images.png" style="width:30px; height:30px;vertical-align:middle;"></i><span style="color:#ffffff;">系统管理员</span></a>
                        <!--鼠标移上去-->
                        <ul class="top-erji">
                            <li>昵称【姓名】：张三</li>
                            <li>工作天数：100天</li>
                            <li>在线时长：2小时</li>
                            <li>有效期：2018年5月5日</li>
                        </ul>
                        <!--鼠标移上去-->
                    </li>
                    <li><a href="javascript:void(0);" onclick="instance.expandUser();"><i class="fa fa-gear"></i></a>
                        <!--鼠标点击效果-->
                        <div class="top-linkstab" style="display:none;">
                            <ul class="toptab-hd">
                                <li class="active">个人档案</li>
                                <li>系统设置</li>
                            </ul>

                            <ul class="toptab-bd">
                                <li class="thisclass">
                                    <div class="top-linksbd"><strong>个人信息</strong><span><a onclick="instance.updateBasicMess();">修改</a></span></div>
                                    <div class="top-linkslb">
                                        <p>登 录 名：<input id="susername" type="text" style="border:none;" readonly="readonly"/></p>
                                        <p>昵　　称：<input id="snickname" type="text" style="border:none;" readonly="readonly"/></p>
                                        <p>姓　　名：<input id="sname" type="text" style="border:none;" readonly="readonly"/></p>
                                        <p>性　　别：<input id="igender" type="text" style="border:none;" readonly="readonly"/></p>
                                        <!--  <p>年龄：<input id="igender" type="text" style="border:none;" readonly="readonly"/></p>  -->
                                        <p>所 在 地：<input id="saddress" type="text" style="border:none;" readonly="readonly"/></p>
                                        <p>身份证号：<input id="sidcard" type="text" style="border:none;" readonly="readonly"/></p>
                                        <p>所在单位：<input id="orgname" type="text" style="border:none;" readonly="readonly"/></p>
                                        <p>职　　务：<input id="susername" type="text" style="border:none;" readonly="readonly"/></p>
                                        <p>毕业院校：<input id="sgraduateschool" type="text" style="border:none;" readonly="readonly"/></p>
                                        <p>毕业时间：<input id="ldgraduationdate" type="text" style="border:none;" readonly="readonly"/></p>
                                        <p>个性签名：<input id="ssignature" type="text" style="border:none;" readonly="readonly"/></p>
                                        <!-- <p>证书：<input id="susername" type="text" style="border:none;" readonly="readonly"/></p> -->
                                    </div>
                                    <div class="top-linksbd"><strong>账户安全</strong></div>
                                    <div class="top-linkslb">
                                        <p>密码：<span>******</span><a onclick="instance.updateUserPassword();">修改密码</a></p>
                                        <p>手机：<span id="sphone"></span><a onclick="instance.updatePhone();">重新绑定</a></p>
                                        <p>邮箱：<span id="semail"></span><a onclick="instance.updateEmail();">绑定邮箱</a></p>
                                    </div>
                                </li>

                                <li>内容2</li>
                            </ul>

                        </div>
                        <!--鼠标点击效果-->
                    </li>
                    <li><a href="javascript:void(0);" onclick="instance.logout();"><i class="fa fa-power-off"></i></a></li>
                </ul>
            </div>
        </div>
    </div>

    <div class="right">
        <div class="type">
            <!--左边内容  开始  -->
            <div class="nav">
                <div class="nav-top">
                    <span class="logo-mini">菜单列表</span>
                    <div id="mini"><i class="fa fa-bars hideMenu"></i></div>
                </div>
            </div>
            <!--左边内容  结束  -->

            <!--右边内容  开始  -->
            <!--右边center 开始-->
            <div class="path">
                <div class="path-tabs">
                    <ul>
                        <li><i class="fa fa-home"></i><span>当前位置：</span></li>
                        <li><a href="javascript:void(0);"><span>首页</span></a></li>
                        <li><span id="locatSpan"></span></li>
                    </ul>
                </div>

                <!--右边center 结束-->

                <!--tab主体部分开始-->
                <div class="section right-full">
                    <section>
                        <div class="tab">
                            <div class="tabs_over"></div>
                            <div class="scroll">
                                <a><img src="${IMG}/index/left.png" width="16" height="16"></a>
                                <a><img src="${IMG}/index/right.png" width="16" height="16"></a>
                            </div>
                            <ul class="tab-bd">
                                <li class="thisclass"><!-- ${CTX}/funcmgr/gotoFuncMgrPage -->
                                    <iframe id="main_iframe" src="" scrolling="no" frameborder="no" border="0" style="width: 100%; height:100%;"></iframe>
                                </li>
                            </ul>
                        </div>
                    </section>
                </div>
                <!--tab主体部分结束-->
            </div>
            <!--主体部分开始-->
            <!--右边内容  结束  -->
        </div>
    </div>
</div>
<script type="text/javascript" src="${JS}/index/index.js?${V}"></script>
</body>
</html>