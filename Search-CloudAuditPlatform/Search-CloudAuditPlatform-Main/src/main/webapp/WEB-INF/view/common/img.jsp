<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/jspTaglib.jsp" %>
<!doctype html>
<html id="imgHtml">
<head>
    <title>图片展示</title>
    <meta name='description' content='首页head中的内容'>
    <%--<link rel="stylesheet" type="text/css" href="${CSS}/common/img.css?${V}"/>--%>
    <style>
        .discription {
            height: 100%;
            width: 100%;
            background: #000;
        }
    </style>

</head>
<body>
<table class="discription">
    <tr>
        <td>
            <c:choose>
                <c:when test="${isImg}">
                    <img id="img" src="${NETWORK_ROOT}${spath}"/>
                </c:when>
                <c:otherwise>
                    <img id="img" src="${IMG}/common/defaultmax.png"/>
                </c:otherwise>
            </c:choose>
        </td>
    </tr>
</table>

<script type="text/javascript" src="${JS}/common/img.js?${V}"></script>
</body>
</html>