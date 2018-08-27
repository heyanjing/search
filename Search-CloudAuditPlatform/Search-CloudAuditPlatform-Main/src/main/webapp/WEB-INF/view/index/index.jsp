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
            <span class="logo">${SYSTEM_NAME}</span>
        </div>
        <div class="nav-left">
            <ul id="menus" class="topLevelMenus menus">
                <c:forEach var="cls" items="${classifyList}" varStatus="status">
                    <c:choose>
                        <c:when test="${empty classfiyNum or classfiyNum==0}">
                            <li class="cls <c:if test="${status.first}">nav-itemx</c:if>" data-id="${cls.sid}" data-div="${cls.spcmethod}" data-menu="menu">
                                <a href="javascript:void(0);">
                                    <i class="fa ${cls.sicon}"></i>
                                        ${cls.sname}
                                </a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <c:if test="${status.index<classfiyNum}">
                                <li class="cls <c:if test="${status.first}">nav-itemx</c:if>" data-id="${cls.sid}" data-div="${cls.spcmethod}" data-menu="menu">
                                    <a href="javascript:void(0);">
                                        <i class="fa ${cls.sicon}"></i>
                                            ${cls.sname}
                                    </a>
                                </li>
                            </c:if>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                <!-- <li class="nav-item"><a href="javascript:void(0);"><i class="fa fa-user-plus"></i>个人工作台</a></li>
                 <li><a href="javascript:void(0);"><i class="fa fa-cube"></i>项目与计划</a></li>
                 <li><a href="javascript:void(0);"><i class="fa fa-book"></i>资料与送审</a></li>
                 <li><a href="javascript:void(0);"><i class="fa fa-shopping-bag"></i>管理与考核</a></li>
                 <li><a href="javascript:void(0);"><i class="fa fa-line-chart"></i>数据与分析</a></li>
                 <li><a href="javascript:void(0);"><i class="fa fa-bar-chart"></i>统计与查询</a></li>
                 <li><a href="javascript:void(0);"><i class="fa fa-gear"></i>系统设置</a></li> -->
                <li id="more-icon" class="more-icon"><a href="javascript:void(0);"><i class="fa fa-th-large"></i></a></li>
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
                    <li id="userSimpleInfo" title="用户信息" style="margin-top:-3px;">
                        <a href="javascript:void(0);" class="top-js">
                            <i>
                                <img src="${IMG}/index/images.png" style="width:30px; height:30px;vertical-align:middle;">
                            </i>
                            <span style="color:#ffffff;">${userName}</span>
                        </a>
                        <!--鼠标移上去-->
                        <ul class="top-erji">
                            <%-- <li>昵称【姓名】：张三</li>
                             <li>用户类型：<p>普通用户</p><a>【切换】</a></li>
                             <li>所在单位：<p>xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx</p><a>【切换】</a></li>
                             <li>担任职务：xxxxx</li>--%>
                            <c:if test="${not empty currentUserType}">
                                <li>用户类型：<p>
                                    <c:choose>
                                        <c:when test="${currentUserType eq 2}">管理员</c:when>
                                        <c:otherwise>普通用户</c:otherwise>
                                    </c:choose>
                                </p>
                                    <a href="<c:choose>
                                            <c:when test="${currentUserType eq 2}">
                                                ${CTX}/index/switchUser?currentUserType=4
                                            </c:when>
                                            <c:otherwise>
                                               ${CTX}/index/switchUser?currentUserType=2
                                            </c:otherwise>
                                        </c:choose>" target="_self" style="color:blue;">
                                        【切换】
                                    </a>
                                </li>
                            </c:if>
                            <c:if test="${not empty currentOrg}">
                                <li>所在单位：<p>${currentOrg}</p>

                                    <a id="changeOrg" href="javascript:void(0);" style="color:blue;">
                                        【切换】
                                    </a>
                                    <input type="hidden" data-all='${orgList}'>
                                </li>
                            </c:if>
                            <c:if test="${not empty currentPosition}">
                                <li>担任职务：${currentPosition}</li>
                            </c:if>
                        </ul>
                        <!--鼠标移上去-->
                    </li>
                    <li id="userDetailInfo" title="个人档案与设置">
                        <a href="javascript:void(0);"><i class="fa fa-gear"></i></a>
                        <!--鼠标点击效果-->
                        <div class="top-linkstab" style="display:none;">
                            <ul id="detailInfo" class="toptab-hd">
                                <li class="active">个人档案</li>
                                <li>系统设置</li>
                            </ul>
                            <ul id="corresponding" class="toptab-bd">
                                <li class="thisclass">
                                    <div class="top-linksbd"><strong>个人信息</strong><span><a id="baseMsg">修改</a></span></div>
                                    <div class="top-linkslb">
                                        <p>登 录 名：<input id="susername" type="text" value="" style="border:none;" readonly="readonly"/></p>
                                        <p>昵　　称：<input id="snickname" type="text" value="" style="border:none;" readonly="readonly"/></p>
                                        <p>姓　　名：<input id="sname" type="text" value="" style="border:none;" readonly="readonly"/></p>
                                        <p>性　　别：<input id="igender" type="text" value="" style="border:none;" readonly="readonly"/></p>
                                        <!--  <p>年龄：<input id="igender" type="text" style="border:none;" readonly="readonly"/></p>  -->
                                        <p>所 在 地：<input id="saddress" type="text" value="" style="border:none;" readonly="readonly"/></p>
                                        <p>身份证号：<input id="sidcard" type="text" value="" style="border:none;" readonly="readonly"/></p>
                                        <!-- <p>所在单位：<input id="orgname" type="text" value="" style="border:none;" readonly="readonly"/></p>
                                        <p>职　　务：<input id="susernamex" type="text" value="" style="border:none;" readonly="readonly"/></p> -->
                                        <p>毕业院校：<input id="sgraduateschool" type="text" value="" style="border:none;" readonly="readonly"/></p>
                                        <p>毕业时间：<input id="ldgraduationdate" type="text" value="" style="border:none;" readonly="readonly"/></p>
                                        <p>个性签名：<input id="ssignature" type="text" value="" style="border:none;" readonly="readonly"/></p>
                                        <!-- <p>证书：<input id="susername" type="text" style="border:none;" readonly="readonly"/></p> -->
                                    </div>
                                    <div class="top-linksbd"><strong>账户安全</strong></div>
                                    <div class="top-linkslb">
                                        <p>密码：<span>******</span><a id="userPwd">修改密码</a></p>
                                        <p>手机：<span id="sphone"></span><a id="userPhone">重新绑定</a></p>
                                        <p>邮箱：<span id="semail"></span><a id="userEmail">绑定邮箱</a></p>
                                    </div>
                                </li>

                                <li>内容2</li>
                            </ul>
                        </div>
                        <!--鼠标点击效果-->
                    </li>
                    <li title="退出"><a href="javascript:void(0)" id="logout"><i class="fa fa-power-off"></i></a></li>
                </ul>
            </div>
        </div>
    </div>

    <!-- more排版  开始-->
    <div id="more" class="more" style="display: none;">
        <div class="more-lb">
            <ul class="menus">
                <c:forEach var="cls" items="${classifyList}" varStatus="status">
                    <li class="color${status.count} cls" data-cls="${cls}" data-id="${cls.sid}" data-div="${cls.spcmethod}">
                        <a href="javascript:void(0);">
                            <i class="fa ${cls.sicon}"></i>
                            <span>${cls.sname}</span>
                        </a>
                    </li>
                </c:forEach>
                <%-- <li class="color1"><a href="javascript:void(0);"><i class="fa fa-user-plus"></i><span>个人工作台</span></a></li>
                 <li class="color2"><a href="javascript:void(0);"><i class="fa fa-cube"></i><span>项目与计划</span></a></li>
                 <li class="color3"><a href="javascript:void(0);"><i class="fa fa-book"></i><span>资料与送审</span></a></li>
                 <li class="color4"><a href="javascript:void(0);"><i class="fa fa-shopping-bag"></i><span>管理与考核</span></a></li>
                 <li class="color5"><a href="javascript:void(0);"><i class="fa fa-line-chart"></i><span>数据与分析</span></a></li>
                 <li class="color6"><a href="javascript:void(0);"><i class="fa fa-bar-chart"></i><span>统计与查询</span></a></li>
                 <li class="color7"><a href="javascript:void(0);"><i class="fa fa-gear"></i><span>系统设置</span></a></li>
                 <li class="color8"><a href="javascript:void(0);"><i class="fa fa-user-plus"></i><span>个人工作台</span></a></li>
                 <li class="color9"><a href="javascript:void(0);"><i class="fa fa-cube"></i><span>项目与计划</span></a></li>
                 <li class="color10"><a href="javascript:void(0);"><i class="fa fa-book"></i><span>资料与送审</span></a></li>
                 <li class="color11"><a href="javascript:void(0);"><i class="fa fa-shopping-bag"></i><span>管理与考核</span></a></li>
                 <li class="color12"><a href="javascript:void(0);"><i class="fa fa-line-chart"></i><span>数据与分析</span></a></li>
                 <li class="color13"><a href="javascript:void(0);"><i class="fa fa-bar-chart"></i><span>统计与查询</span></a></li>
                 <li class="color14"><a href="javascript:void(0);"><i class="fa fa-gear"></i><span>系统设置</span></a></li>--%>
            </ul>
        </div>
    </div>
    <!-- more排版  结束-->
    <div id="right" class="right">

        <c:forEach var="vb" items="${viewBeanList}" varStatus="status">
            <c:choose>
                <c:when test="${vb.viewType eq 301}">
                    <div id="${vb.divId}" data-type="${vb.viewType}" class="type" style="display:
                    <c:if test="${status.first}">block</c:if> <c:if test="${not status.first}">none</c:if>">
                        个人工作台
                    </div>
                </c:when>
                <c:when test="${vb.viewType eq 302}">
                    <div id="${vb.divId}" data-type="${vb.viewType}" class="type" style="display:
                    <c:if test="${status.first}">block</c:if> <c:if test="${not status.first}">none</c:if>">
                        <div id="${vb.divId}nav" class="nav">
                            <div class="nav-top">
                                <span id="${vb.divId}logo-mini" class="logo-mini">菜单列表</span>
                                <div id="${vb.divId}mini" class="mini"><i class="fa fa-bars hideMenu"></i></div>
                            </div>
                            <div class="mini-fitmenu menuscroll">
                            	<div>
	                            <ul>
	                                <c:forEach var="classify2" items="${vb.classify2List}" varStatus="statusModule">
	                                    <li class="nav-item" data-id="${classify2.module.sid}">
	                                        <a href="javascript:void(0);">
	                                            <i class="fa ${classify2.module.sicon}"></i>
	                                            <span>${classify2.module.sname}</span>
	                                            <i class="my-icon nav-more"></i>
	                                        </a>
	                                        <ul>
	                                            <c:forEach var="node" items="${classify2.nodes}" varStatus="statusNode">
	                                                <li class="node" data-id="${node.sid}" data-method="${node.spcmethod}" data-parent-id="${classify2.module.sid}" data-parent-name="${classify2.module.sname}" data-name="${node.sname}">
	                                                    <a href="javascript:void(0);">
	                                                        <span>${node.sname}</span>
	                                                    </a>
	                                                </li>
	                                            </c:forEach>
	                                        </ul>
	                                    </li>
	                                </c:forEach>
	                            </ul>
	                            </div>
                            </div>
                        </div>
                        <div id="${vb.divId}path" class="path">
                            <div id="${vb.divId}path-tabs" class="path-tabs">
                                <ul>
                                    <li><i class="fa fa-home"></i><span>当前位置：</span></li>
                                    <li><a href="javascript:void(0);"><span>首页</span></a></li>
                                    <li class="location">
                                        <a href="javascript:void(0);">
                                            <span id="${vb.divId}locatSpan">
                                                    <%--系统维护-系统设置--%>
                                            </span>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                            <div id="${vb.divId}section" class="section">
                                <section>
                                    <div class="tab">
                                        <div class="tabs_over">
                                            <ul id="${vb.divId}lables" class="tab-hd lables" style="display:none;">　
                                                    <%-- <li><span>首　页</span></li>
                                                     <li class="active">企业信息企业信息</li>
                                                     <li>功能管理</li>--%>
                                            </ul>
                                        </div>
                                        <div class="scroll">
                                            <a><img src="${IMG}/index/left.png" width="16" height="16"></a>
                                            <a><img src="${IMG}/index/right.png" width="16" height="16"></a>
                                        </div>
                                        <ul id="${vb.divId}tab-bd" class="tab-bd">
                                            <li class="thisclass">
                                                <iframe id="${vb.divId}main_iframe" src="" scrolling="no" frameborder="no" border="0" style="width: 100%; height:100%;"></iframe>
                                            </li>
                                        </ul>
                                    </div>
                                </section>
                            </div>
                        </div>
                    </div>
                </c:when>
                <c:when test="${vb.viewType eq 303}">
                    <div id="${vb.divId}" data-type="${vb.viewType}" class="type" style="display:
                    <c:if test="${status.first}">block</c:if>
                    <c:if test="${not status.first}">none</c:if>">
                        管理与考核
                    </div>
                </c:when>
                <c:when test="${vb.viewType eq 304}">
                    <div id="${vb.divId}" data-type="${vb.viewType}" class="type" style="display:
                    <c:if test="${status.first}">block</c:if>
                    <c:if test="${not status.first}">none</c:if>">
                        系统设置
                    </div>
                </c:when>
                <c:otherwise>
                    <div id="nothing" data-type="${vb.viewType}" class="type" style="display:
                    <c:if test="${status.first}">block</c:if>
                    <c:if test="${not status.first}">none</c:if>">
                        暂无内容
                    </div>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <%--<div id="grgzt" data-methed="xa" class="type" style="display: none">
            个人工作台
        </div>
        <div id="glykh" data-methed="xb" class="type" style="display: none">
            管理与考核
        </div>
        <div id="pmyjh" data-methed="xc" class="type" >
            <!--左边内容  开始  -->
            <div class="nav">
                <div class="nav-top">
                    <span class="logo-mini">菜单列表</span>
                    <div id="mini"><i class="fa fa-bars hideMenu"></i></div>
                </div>
                <ul>
                    <li class="nav-item">
                        <a href="javascript:void(0);"><i class="fa fa-bars"></i><span>网站配置</span><i class="my-icon nav-more"></i></a>
                        <ul>
                            <li><a href="javascript:void(0);"><span>网站设置</span></a></li>
                            <li><a href="javascript:void(0);"><span>友情链接</span></a></li>
                            <li><a href="javascript:void(0);"><span>分类管理</span></a></li>
                            <li><a href="javascript:void(0);"><span>系统日志</span></a></li>
                        </ul>
                    </li>
                    <li class="nav-item">
                        <a href="javascript:void(0);"><i class="fa fa-bars"></i><span>网站配置</span><i class="my-icon nav-more"></i></a>
                        <ul>
                            <li><a href="javascript:void(0);"><span>网站设置</span></a></li>
                            <li><a href="javascript:void(0);"><span>友情链接</span></a></li>
                            <li><a href="javascript:void(0);"><span>分类管理</span></a></li>
                            <li><a href="javascript:void(0);"><span>系统日志</span></a></li>
                        </ul>
                    </li>
                    <li class="nav-item">
                        <a href="javascript:void(0);"><i class="fa fa-bars"></i><span>网站配置</span><i class="my-icon nav-more"></i></a>
                        <ul>
                            <li><a href="javascript:void(0);"><span>网站设置</span></a></li>
                            <li><a href="javascript:void(0);"><span>友情链接</span></a></li>
                            <li><a href="javascript:void(0);"><span>分类管理</span></a></li>
                            <li><a href="javascript:void(0);"><span>系统日志</span></a></li>
                        </ul>
                    </li>
                    <li class="nav-item">
                        <a href="javascript:void(0);"><i class="fa fa-bars"></i><span>网站配置</span><i class="my-icon nav-more"></i></a>
                        <ul>
                            <li><a href="javascript:void(0);"><span>网站设置</span></a></li>
                            <li><a href="javascript:void(0);"><span>友情链接</span></a></li>
                            <li><a href="javascript:void(0);"><span>分类管理</span></a></li>
                            <li><a href="javascript:void(0);"><span>系统日志</span></a></li>
                        </ul>
                    </li>
                </ul>
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
        </div>--%>
    </div>
</div>
<script type="text/javascript" src="${JS}/index/index.js?${V}"></script>
</body>
</html>