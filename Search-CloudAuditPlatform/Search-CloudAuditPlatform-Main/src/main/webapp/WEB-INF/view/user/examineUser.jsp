<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/jspTaglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>用户注册</title>
    <link rel="stylesheet" type="text/css" href="${CSS}/upload/webuploader.css">
    <link rel="stylesheet" type="text/css" href="${CSS}/demo/demo.css?${V}"/>
    <link rel="stylesheet" type="text/css" href="${CSS}/login/sign.css?${V}"/>
    <script type="text/javascript">
        var sid = "";
        var osid = "";
        var state = false;

    </script>
    <style>
        table tr td {
            padding: 10px 0 0 0;
        }

        .mini-fit {
            bottom: 58px;
        }
    </style>
</head>

<body>
<div class="wrap">
    <div class="user-circle" style="width: 800px; height:510px;  left: 0; box-shadow: 0 5px 8px 5px rgba(144, 144, 144, 0.5);  margin: 0 auto; position: relative;background:#ffffff; border-radius:5px;">
        <div id="alert" style="left: 0;top: 0;margin-left: 0;width:100%;height:100%;display:block;">
            <div class="mode2-content" style="position:absolute;top:50px; bottom:0; height:auto; width:100%;">
                <div class="main">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="flow">
                                <div class="flowListBox"></div>
                            </div>
                        </div>
                        <div class="mini-fit">
                            <div class="col-md-12" id="flowDiv">
                                <ol id="iList" style="padding:15px 15px 0 15px">
                                    <div id="contA" style="margin-top: 10px;">
                                        <form id=Usersform>
                                            <div id="sid" class="search-texthide"></div>
                                            <table class="useradd_list"
                                                   style=" text-align: right; margin-left:100px;">
                                                <tr>
                                                    <td><label>选择职务：</label></td>
                                                    <td>
                                                        <div id="organduserrefs"
                                                             class="search-select" idField="sid" textField="sname"
                                                             width="300" enabled="true" height="30"></div>
                                                    </td>
                                                </tr>
                                            </table>
                                        </form>
                                    </div>

                                    <div class="contentList" id="contB" style="height: 300px;">
                                        <div id="datagrid" class="search-datagrid" idField="sid" showCheckBox="false" multiSelect="true" alternatingRows="true"
                                             showPager="false" ondrawcell="datagriddrawcell" style="width:100%;height:100%;">
                                            <div property="columns" id="di">
                                                <div field="sname" name="sname" width="50" headAlign="center" textAlign="center" allowSort="true">用户名称</div>
                                            </div>
                                        </div>
                                    </div>

                                </ol>
                            </div>
                        </div>
                    </div>
                    <div class="foot-btn">
                        <button type="button" class="btn btn-primary" id="btnBack">通过</button>
                        <button type="button" class="btn btn-default"
                                style=" display: none" id="btnok" onclick="save()">完成
                        </button>
                        <button type="button" class="btn btn-success"
                                margin-right: 10px;
                        " id="btnNext">驳回</button>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>
<!-- <div id="mask" class="mask"></div> -->
<script type="text/javascript" language="javascript"
        src="${JS}/user/examine.js?v=${RES_VER}"></script>
<script type="text/javascript" src="${JS}/user/Applyflow.js?${V}"></script>
<!-- <script type="text/javascript">
    var myAlert = document.getElementById("alert");
    var myMask = document.getElementById('mask');
    myMask.style.display = "block";
    myAlert.style.display = "block";
</script> -->

</body>
</html>