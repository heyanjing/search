<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/jspTaglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>邮件设置</title>
    <link rel="stylesheet" type="text/css" href="${CSS}/demo/demo.css?${V}"/>

    <style>
        table tr td {
            padding: 10px 0 0 0;
        }
    </style>

</head>
<body>
<form id=MailSettingsform>
    <div id="sid" class="search-texthide"></div>
    <table class="useradd_list" style="text-align: right;margin:0 auto;">
        <tr>
            <td><label>邮件服务器：</label></td>
            <td>
                <div id="smailserver" name="smailserver" class="search-textbox"
                     rules="required" width="300" height="30"></div>
            </td>
        </tr>
        <tr>
            <td><label>超时时间：</label></td>
            <td>
                <div id="itimeout" name="itimeout" class="search-textbox"
                     rules="required,length:10" width="300" height="30"></div>
            </td>
        </tr>
        <tr>
            <td><label>是否需要授权：</label></td>
            <td>
                <div id="ineedauth" name="ineedauth" class="search-textradio"
                     idField="value" textField="text" width="300" height="30"></div>
            </td>
        </tr>
        <tr>
            <td><label>用户名：</label></td>
            <td>
                <div id="susername" name="susername"
                     class="search-textbox" width="300" height="30" rules="required"></div>
            </td>
        </tr>
        <tr>
            <td><label>密码：</label></td>
            <td>
                <div id="spassword" name="spassword"
                     class="search-textpassword" width="300" height="30" rules="required"></div>
            </td>
        </tr>
        <tr>
            <td><label>发件人地址：</label></td>
            <td>
                <div id="ssenderaddr" name="ssenderaddr"
                     class="search-textbox" width="300" height="30" rules="required"></div>
            </td>
        </tr>
        <tr>
            <td><label>发件人昵称：</label></td>
            <td>
                <div id="ssendernick" name="ssendernick"
                     class="search-textbox" width="300" height="30" rules="required"></div>
            </td>
        </tr>

        <tr>
            <td colspan="2" style="text-align:center;">
                <input class="org-save" type="button" onclick="save()" value="保存"/>
            </td>
        </tr>
    </table>
</form>
<script type="text/javascript" language="javascript" src="${JS}/mailsettings/Mailsettingspage.js?v=${RES_VER}"></script>
</body>
</html>