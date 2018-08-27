<%@page language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@page import="com.search.cap.main.Capm" %>

<c:set var="JQUERY" value="${LIB}/jquery"/>
<c:set var="BOOTSTRAP" value="${LIB}/bootstrap"/>
<c:set var="WEBUPLOADER" value="${LIB}/webuploader"/>
<c:set var="SEARCHUI" value="${LIB}/searchui"/>
<c:set var="MOMENT" value="${LIB}/moment"/>
<c:set var="LAYDATE" value="${LIB}/laydate"/>
<c:set var="D3" value="${LIB}/d3"/>
<c:set var="WEBSOCKET" value="${LIB}/websocket"/>
<c:set var="UEDITOR" value="${LIB}/ueditor"/>


<c:set var="DEBUG" value="<%=Capm.DEBUG%>"/>
<c:set var="ORG_NAME" value="<%=Capm.ORG_NAME%>"/>
<c:set var="SYSTEM_NAME" value="<%=Capm.SYSTEM_NAME%>"/>
<c:set var="NETWORK_ROOT" value="<%=Capm.Upload.NETWORK_ROOT%>"/>
<c:set var="ICON" value="<%=Capm.ICON%>"/>
<c:set var="LOGO" value="<%=Capm.LOGO%>"/>
