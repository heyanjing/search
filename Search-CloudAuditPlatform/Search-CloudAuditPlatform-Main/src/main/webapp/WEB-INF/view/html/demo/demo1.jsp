<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/jspTaglib.jsp" %>
<!doctype html>
<html>
<head>
    <title>普通弹框</title>
    <meta name='description' content='首页head中的内容'>
    <link rel="stylesheet" type="text/css" href="${CSS}/demo/demo1.css?${V}"/>

</head>
<body>

<input type="button" value="弹出框" id="btn1">
<div id="popup">
    <div class="title">
        <p data-title="Popup Text"></p>
        <span><i class="glyphicon glyphicon-remove"></i></span>
    </div>
    <div class="cont">
        <div class="cont-nr"></div>
        <div class="modal-footer">
            <button type="button" class="btn btn-primary" id="shortcutEnter">确认</button>
            <button type="button" class="btn btn-primary" id="shortcutEnter">取消</button>
        </div>

    </div>
</div>
<div id="mask_shadow"></div>

<script type="text/javascript" src="${JS}/html/demo/popup.js?${V}"></script>
<script src="js/jquery.min.js"></script>
<script src="js/popup.js"></script>
<script>
    $(function () {

        /**
         ifDrag: 是否拖拽
         dragLimit: 拖拽限制范围
         */
        $('#popup').popup({ifDrag: true, dragLimit: true});

    });
</script>
</body>
</html>