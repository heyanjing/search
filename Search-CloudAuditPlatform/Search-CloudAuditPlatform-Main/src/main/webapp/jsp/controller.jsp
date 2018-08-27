<%@ page language="java" contentType="text/html; charset=UTF-8"
         import="com.baidu.ueditor.ActionEnter"
         import="com.search.cap.main.Capm"
         import="java.io.File"
         pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%

    request.setCharacterEncoding("utf-8");
    response.setHeader("Content-Type", "text/html");

    String rootPath = application.getRealPath("");

    out.write(new ActionEnter(request, rootPath, Capm.Ueditor.CONFIG_PATH, Capm.Upload.ROOT + File.separator + Capm.Upload.SERVER_NAME).exec());

%>