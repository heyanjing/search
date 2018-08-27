<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/jspTaglib.jsp" %>
<div property="contextmenu">
    <c:choose>
        <c:when test="${param.recycle == null}">
            <c:forEach items="${button.list}" var="func">
                <c:if test="${fn:contains(func.sbtnlocation, '103')}">
                    <div id="${func.sid}" type="menu" text="${func.sname}" data="{sid:'${func.sid}'}" onclick="${func.spcmethod}"></div>
                    <!-- icon="${func.sicon}" -->
                </c:if>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <div id="delData" type="menu" text="删除" enabled="true" onclick="delData"></div>
            <div id="restData" type="menu" text="恢复" enabled="true" onclick="restData"></div>
        </c:otherwise>
    </c:choose>
    <div id="detail" type="menu" text="详情" enabled="true" onclick="queryDetail"></div>
</div>
