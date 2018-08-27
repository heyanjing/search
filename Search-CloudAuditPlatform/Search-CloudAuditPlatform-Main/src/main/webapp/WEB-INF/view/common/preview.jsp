<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/jspTaglib.jsp" %>
<!doctype html>
<html>
<head>
    <title>图片展示</title>
    <meta name='description' content='首页head中的内容'>
    <link rel="stylesheet" type="text/css" href="${CSS}/common/preview.css?${V}"/>
</head>
<body>
<iframe id="iframe" src="" style="display: none;width:100%; height:100%;"></iframe>
<div id="imgDiv" style="width:100%; height:100%; overflow:hidden;background:#000;">
    <img id="img" src="" style="position:absolute; ">
</div>

<!-- <div class="Apic" ><img src="" id="img"/></div> -->
<!-- 顶部按钮 -->
<div id="action" class="pic-btnbg">
    <div class="pic-btn"><i class="fa fa-download"></i></div>
    <div class="pic-btn rotation" style="display: none;"><i class="fa fa-rotate-left"></i></div>
    <div class="pic-btn rotation" style="display: none;"><i class="fa fa-rotate-right "></i></div>
    <div class="pic-btn"><i class="fa fa-times"></i></div>
</div>

<!-- 中间左右切换 -->
<div class="pic-middle-left">
    <div id="pre-left" class="pre-left max-btn" style="display: none;"><i class="fa fa-angle-left"></i></div>
</div>
<div class="pic-middle-right">
    <div id="pre-right" class="pre-right max-btn" style="display: none;"><i class="fa fa-angle-right"></i></div>
</div>

<!-- 底部内容 -->
<div class="pic-text">
    <div class="pic-infotitle">
        <h2 id="name"><%--《雨花石》--%></h2>
        <p id="desc"><%--我是一颗小小的石头--%></p>
    </div>

    <div id="pic-infobtn" class="pic-infobtn" style="display: none;">
        <div id="pre" class="pre mini-btn"><i class="fa fa-chevron-circle-left"></i></div>
        <div class="smalpic">
            <div id="imgBox" class="smallbox">
                <%-- <a href="" class="active">
                        <img src="${IMG}/login/ewm.png"  >
                        <span>
                             <i class="fa fa-check-square selected"></i>
                        </span>
                     </a>
                 <a href=""><img src="${IMG}/login/ewm.png"><span><i class="fa fa-check-square"></i></span></a>
                 <a href=""><img src="${IMG}/login/ewm.png"><span><i class="fa fa-check-square"></i></span></a>
                 <a href=""><img src="${IMG}/login/ewm.png"><span><i class="fa fa-check-square"></i></span></a>
                 <a href=""><img src="${IMG}/login/ewm.png"><span><i class="fa fa-check-square"></i></span></a>
                 <a href=""><img src="${IMG}/login/ewm.png"><span><i class="fa fa-check-square"></i></span></a>
                 <a href=""><img src="${IMG}/login/ewm.png"><span><i class="fa fa-check-square"></i></span></a>
                 <a href=""><img src="${IMG}/login/ewm.png"><span><i class="fa fa-check-square"></i></span></a>
                 <a href=""><img src="${IMG}/login/ewm.png"><span><i class="fa fa-check-square"></i></span></a>
                 <a href=""><img src="${IMG}/login/ewm.png"><span><i class="fa fa-check-square"></i></span></a>
                 <a href=""><img src="${IMG}/login/ewm.png"><span><i class="fa fa-check-square"></i></span></a>--%>
            </div>
        </div>
        <div id="next" class="next mini-btn"><i class="fa fa-chevron-circle-right"></i></div>
    </div>
</div>


<script type="text/javascript" src="${JS}/common/preview.js?${V}"></script>
</body>
</html>